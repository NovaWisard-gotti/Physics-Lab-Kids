# Reglas ProGuard/R8 para Fisica Lab.
# La app no usa minificacion en la build actual (isMinifyEnabled = false),
# este archivo queda listo por si se activa en el futuro.

-keep class com.kidslab.physicslab.data.local.entity.** { *; }
