package com.kidslab.physicslab.ui.components

import android.graphics.Paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp

/**
 * Dibuja un emoji centrado en [center] dentro de un [Canvas], en lugar de una figura abstracta
 * (círculo, cuadrado). Así los niños ven autos, pelotas, cajas, etc. en vez de puntos genéricos.
 *
 * [size] se expresa en Dp (no píxeles crudos): un tamaño en píxeles físicos se ve minúsculo en
 * pantallas de alta densidad, así que aquí se convierte usando la densidad real del dispositivo.
 */
fun DrawScope.drawEmoji(emoji: String, center: Offset, size: Dp) {
    val sizePx = size.toPx()
    drawContext.canvas.nativeCanvas.apply {
        val paint = Paint().apply {
            textSize = sizePx
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        val metrics = paint.fontMetrics
        val textY = center.y - (metrics.ascent + metrics.descent) / 2f
        drawText(emoji, center.x, textY, paint)
    }
}
