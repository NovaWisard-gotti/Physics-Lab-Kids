package com.kidslab.physicslab.ui.labs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kidslab.physicslab.data.local.entity.ExperimentEntity
import com.kidslab.physicslab.data.repository.PhysicsLabRepository
import com.kidslab.physicslab.ui.components.PeoeStage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class GuidedLabViewModel(
    private val repository: PhysicsLabRepository,
    private val topicId: String,
    private val computeResult: (ExperimentEntity, Map<String, Double>) -> LabComputation
) : ViewModel() {

    private val _experiments = MutableStateFlow<List<ExperimentEntity>>(emptyList())
    val experiments: StateFlow<List<ExperimentEntity>> = _experiments.asStateFlow()

    var currentIndex by mutableStateOf(0)
        private set

    var stage by mutableStateOf(PeoeStage.PREDICT)
        private set

    val paramValues: SnapshotStateMap<String, Double> = mutableStateMapOf()

    var predictedOption by mutableStateOf<String?>(null)
        private set

    var computation by mutableStateOf<LabComputation?>(null)
        private set

    var animationTrigger by mutableStateOf(0)
        private set

    var badgeJustEarned by mutableStateOf<String?>(null)
        private set

    private var userId: Long = 0L

    init {
        viewModelScope.launch {
            userId = repository.getActiveProfile()?.id ?: 0L
            repository.observeExperimentsByTopic(topicId).collect { list ->
                val wasEmpty = _experiments.value.isEmpty()
                _experiments.value = list
                if (wasEmpty && list.isNotEmpty()) {
                    loadExperiment(list.first())
                }
            }
        }
    }

    private fun loadExperiment(experiment: ExperimentEntity) {
        viewModelScope.launch {
            val params = repository.observeParametersByExperiment(experiment.id).first()
            paramValues.clear()
            params.forEach { paramValues[it.paramKey] = it.defaultValue }
            stage = PeoeStage.PREDICT
            predictedOption = null
            computation = null
        }
    }

    fun selectExperiment(index: Int) {
        val list = experiments.value
        if (index !in list.indices) return
        currentIndex = index
        loadExperiment(list[index])
    }

    fun updateParam(key: String, value: Double) {
        paramValues[key] = value
    }

    fun choosePrediction(option: String) {
        predictedOption = option
        stage = PeoeStage.EXPERIMENT
    }

    fun runExperiment() {
        val experiment = experiments.value.getOrNull(currentIndex) ?: return
        val result = computeResult(experiment, paramValues.toMap())
        computation = result
        stage = PeoeStage.OBSERVE
        animationTrigger++

        viewModelScope.launch {
            val paramsJson = paramValues.entries.joinToString(prefix = "{", postfix = "}") { (k, v) ->
                "\"$k\":$v"
            }
            val runId = repository.saveExperimentRun(
                experimentId = experiment.id,
                userId = userId,
                inputParamsJson = paramsJson,
                resultValue = result.resultValue,
                resultUnitSymbol = result.resultUnitSymbol
            )
            repository.saveObservation(
                runId = runId,
                observationEs = result.resultDescriptionEs,
                matchedPrediction = result.matchesOption(predictedOption)
            )
        }
    }

    fun goToExplain() {
        stage = PeoeStage.EXPLAIN
    }

    fun nextExperiment() {
        val list = experiments.value
        if (currentIndex < list.size - 1) {
            selectExperiment(currentIndex + 1)
        }
    }

    fun replay() {
        stage = PeoeStage.PREDICT
        predictedOption = null
        computation = null
    }

    class Factory(
        private val repository: PhysicsLabRepository,
        private val topicId: String,
        private val computeResult: (ExperimentEntity, Map<String, Double>) -> LabComputation
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            GuidedLabViewModel(repository, topicId, computeResult) as T
    }
}
