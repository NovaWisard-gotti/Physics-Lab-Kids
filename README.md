# 🔬 Física Lab (Physics Lab Kids)

App educativa Android para aprender Física experimentando, no memorizando.
Pensada para niños de 8 a 12 años, 100% en español, 100% offline.

- **Package**: `com.kidslab.physicslab`
- **Versión**: 1.0.0
- **Repositorio**: `physics-lab-kids-android`
- **minSdk**: 24 · **compileSdk/targetSdk**: 34 · **JDK**: 17

## 🎯 Objetivo

Que los conceptos básicos de Física (movimiento, velocidad, fuerza, masa,
gravedad, fricción, energía y máquinas simples) se entiendan **viendo y
tocando** simulaciones interactivas, no leyendo definiciones. Las fórmulas
aparecen siempre *después* de la intuición, nunca antes.

## 🧪 Los 8 laboratorios

| # | Laboratorio | Qué se descubre |
|---|---|---|
| 1 | Movimiento | Distancia = velocidad × tiempo |
| 2 | Velocidad | Quién gana una carrera y por qué |
| 3 | Fuerza | Más fuerza, más aceleración |
| 4 | Masa | Más masa, menos aceleración |
| 5 | Gravedad | La masa no cambia el tiempo de caída |
| 6 | Fricción | Cada superficie frena distinto |
| 7 | Energía | La energía se transforma, no desaparece |
| 8 | Máquinas simples | Palancas, poleas y rampas multiplican la fuerza |

Cada laboratorio incluye **3 experimentos guiados** (24 en total) que siguen
siempre el mismo método científico:

**Predice 🤔 → Experimenta 🧪 → Observa 👀 → Explica 💡**

Además la app tiene un **cuaderno de científico** con progreso, 8 insignias
coleccionables y 30 preguntas de repaso.

## 🛠️ Tecnología

- Kotlin + Jetpack Compose + Material 3
- Canvas de Compose para las simulaciones
- Arquitectura MVVM
- Room (persistencia local, 11 tablas)
- Corrutinas / Flow / StateFlow
- Inyección de dependencias manual (sin Hilt)
- JDK 17, minSdk 24, desugaring de `java.time`
- Sin motor de videojuegos externo: todas las animaciones son Canvas de Compose

## 📂 Estructura del proyecto

```
app/src/main/java/com/kidslab/physicslab/
├── domain/          → lógica de física pura (sin Android), 100% testeable
│   ├── engine/       (MovementEngine, ForceMassEngine, FrictionEngine,
│   │                  GravityEngine, EnergyEngine, SimpleMachineEngine)
│   └── model/         (unidades, enums de intensidad/superficie/planeta)
├── data/
│   ├── local/        (Room: entidades, DAOs, AppDatabase)
│   ├── repository/   (PhysicsLabRepository)
│   └── seed/          (datos base: 8 temas, 24 experimentos, 30 preguntas, 8 insignias)
├── di/               (AppContainer: inyección de dependencias manual)
└── ui/
    ├── scientist/     (crear científico junior)
    ├── labs/          (pantalla de laboratorios + los 8 laboratorios)
    ├── notebook/       (cuaderno: progreso, insignias, quiz)
    ├── components/     (PEOE stepper, tarjetas de predicción/comparación, etc.)
    └── navigation/     (NavHost y rutas)
```

## 🧾 Documentación incluida

- `MEMORIA_DESCRIPTIVA.md` / `.pdf` — visión general del proyecto y decisiones pedagógicas
- `MANUAL_USUARIO.md` / `.pdf` — cómo usar la app, pensado para niños y familias
- `MANUAL_TECNICO.md` / `.pdf` — arquitectura, motores de física y cómo extender la app
- `BASE_DE_DATOS.md` — esquema completo con diagrama ER (Mermaid)
- `database/schema.sql` y `database/sample_data.sql` — SQL de las 11 tablas y datos de ejemplo
- `SUBIR_A_GITHUB.md` — guía paso a paso para subir el proyecto sin usar la terminal

## ✅ Pruebas

- `domain/`: pruebas unitarias de los 6 motores de física (movimiento uniforme,
  distancia, relación fuerza/masa, fricción, caída libre, energía, máquinas simples)
- `data/`: pruebas con Robolectric + Room en memoria (predicciones, progreso,
  insignias y persistencia)

## 🚀 CI/CD (GitHub Actions)

- **android-build.yml** — compila y corre las pruebas en cada push/PR
- **docs-build.yml** — genera los 3 PDFs de documentación a partir de los Markdown
- **release.yml** — compila el APK de release y publica "Física Lab v1.0.0" con
  el archivo `FisicaLab-v1.0.0.apk`

## 🔒 Privacidad

Física Lab no usa internet, no tiene login, no declara permisos y no envía
datos a ningún servidor. Todo el progreso del científico junior se guarda
únicamente en el dispositivo (Room/SQLite local).
