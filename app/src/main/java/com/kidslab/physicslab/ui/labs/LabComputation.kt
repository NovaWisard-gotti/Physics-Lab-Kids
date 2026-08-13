package com.kidslab.physicslab.ui.labs

/**
 * Resultado de correr un experimento: valor numérico + unidad para guardar en Room,
 * una descripción en español para mostrar en la etapa "Observa", y una función que
 * decide si una opción de predicción concuerda con lo que realmente pasó.
 */
data class LabComputation(
    val resultValue: Double,
    val resultUnitSymbol: String,
    val resultDescriptionEs: String,
    val matchesOption: (String?) -> Boolean
)
