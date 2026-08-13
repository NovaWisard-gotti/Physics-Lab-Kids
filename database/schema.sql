-- Física Lab — esquema de base de datos (SQLite / Room)
-- 11 tablas. Ejecutar en orden porque hay claves foráneas.

PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS user_profile (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    name            TEXT NOT NULL,
    avatarId        TEXT NOT NULL,
    coatColorHex    TEXT NOT NULL,
    createdAt       INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS physics_topic (
    id              TEXT PRIMARY KEY,
    key             TEXT NOT NULL,
    nameEs          TEXT NOT NULL,
    descriptionEs   TEXT NOT NULL,
    iconEmoji       TEXT NOT NULL,
    orderIndex      INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS experiment (
    id                  TEXT PRIMARY KEY,
    topicId             TEXT NOT NULL,
    titleEs             TEXT NOT NULL,
    instructionEs       TEXT NOT NULL,
    predictQuestionEs   TEXT NOT NULL,
    explanationEs       TEXT NOT NULL,
    difficulty          INTEGER NOT NULL,
    orderIndex          INTEGER NOT NULL,
    FOREIGN KEY (topicId) REFERENCES physics_topic(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_experiment_topicId ON experiment(topicId);

CREATE TABLE IF NOT EXISTS experiment_parameter (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    experimentId    TEXT NOT NULL,
    paramKey        TEXT NOT NULL,
    labelEs         TEXT NOT NULL,
    unitSymbol      TEXT NOT NULL,
    minValue        REAL NOT NULL,
    maxValue        REAL NOT NULL,
    defaultValue    REAL NOT NULL,
    FOREIGN KEY (experimentId) REFERENCES experiment(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_experiment_parameter_experimentId ON experiment_parameter(experimentId);

CREATE TABLE IF NOT EXISTS prediction (
    id                  INTEGER PRIMARY KEY AUTOINCREMENT,
    experimentId        TEXT NOT NULL,
    userId              INTEGER NOT NULL,
    predictedOptionEs   TEXT NOT NULL,
    createdAt           INTEGER NOT NULL,
    FOREIGN KEY (experimentId) REFERENCES experiment(id) ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES user_profile(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_prediction_experimentId ON prediction(experimentId);
CREATE INDEX IF NOT EXISTS idx_prediction_userId ON prediction(userId);

CREATE TABLE IF NOT EXISTS experiment_run (
    id                  INTEGER PRIMARY KEY AUTOINCREMENT,
    experimentId        TEXT NOT NULL,
    userId              INTEGER NOT NULL,
    inputParamsJson     TEXT NOT NULL,
    resultValue         REAL NOT NULL,
    resultUnitSymbol    TEXT NOT NULL,
    ranAt               INTEGER NOT NULL,
    FOREIGN KEY (experimentId) REFERENCES experiment(id) ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES user_profile(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_experiment_run_experimentId ON experiment_run(experimentId);
CREATE INDEX IF NOT EXISTS idx_experiment_run_userId ON experiment_run(userId);

CREATE TABLE IF NOT EXISTS observation (
    id                  INTEGER PRIMARY KEY AUTOINCREMENT,
    experimentRunId     INTEGER NOT NULL,
    observationEs       TEXT NOT NULL,
    matchedPrediction   INTEGER NOT NULL, -- boolean: 0 o 1
    FOREIGN KEY (experimentRunId) REFERENCES experiment_run(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_observation_experimentRunId ON observation(experimentRunId);

CREATE TABLE IF NOT EXISTS quiz_question (
    id              TEXT PRIMARY KEY,
    topicId         TEXT NOT NULL,
    questionEs      TEXT NOT NULL,
    optionAEs       TEXT NOT NULL,
    optionBEs       TEXT NOT NULL,
    optionCEs       TEXT NOT NULL,
    correctOption   TEXT NOT NULL, -- 'A', 'B' o 'C'
    FOREIGN KEY (topicId) REFERENCES physics_topic(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_quiz_question_topicId ON quiz_question(topicId);

CREATE TABLE IF NOT EXISTS quiz_attempt (
    id                  INTEGER PRIMARY KEY AUTOINCREMENT,
    quizQuestionId      TEXT NOT NULL,
    userId              INTEGER NOT NULL,
    selectedOption      TEXT NOT NULL,
    correct             INTEGER NOT NULL, -- boolean: 0 o 1
    attemptedAt         INTEGER NOT NULL,
    FOREIGN KEY (quizQuestionId) REFERENCES quiz_question(id) ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES user_profile(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_quiz_attempt_quizQuestionId ON quiz_attempt(quizQuestionId);
CREATE INDEX IF NOT EXISTS idx_quiz_attempt_userId ON quiz_attempt(userId);

CREATE TABLE IF NOT EXISTS badge (
    id              TEXT PRIMARY KEY,
    key             TEXT NOT NULL,
    nameEs          TEXT NOT NULL,
    descriptionEs   TEXT NOT NULL,
    iconEmoji       TEXT NOT NULL,
    criteriaEs      TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS user_badge (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    userId      INTEGER NOT NULL,
    badgeId     TEXT NOT NULL,
    earnedAt    INTEGER NOT NULL,
    FOREIGN KEY (userId) REFERENCES user_profile(id) ON DELETE CASCADE,
    FOREIGN KEY (badgeId) REFERENCES badge(id) ON DELETE CASCADE
);
