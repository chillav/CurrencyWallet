package com.krasovitova.currencywallet.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class GraphView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : View(context, attrs, defStyleAttrs) {

    private val points = mutableListOf<GraphPoint>()
    private var xMin = 0
    private var xMax = 0
    private var yMin = 0
    private var yMax = 0

    private val outerCirclePaint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 7f
        style = Paint.Style.STROKE
    }

    private val innerCirclePaint = Paint().apply {
        color = Color.WHITE
    }

    private val linePaint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 7f
        isAntiAlias = true
    }

    private val axisLinePaint = Paint().apply {
        color = Color.RED
        strokeWidth = 10f
    }

    fun setData(newPoints: List<GraphPoint>) {
        xMin = newPoints.minByOrNull { it.xVal }?.xVal ?: 0
        xMax = newPoints.maxByOrNull { it.xVal }?.xVal ?: 0
        yMin = newPoints.minByOrNull { it.yVal }?.yVal ?: 0
        yMax = newPoints.maxByOrNull { it.yVal }?.yVal ?: 0
        points.clear()
        points.addAll(newPoints)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        points.forEachIndexed { index, currentPoint ->
            val realX = currentPoint.xVal.toRealX()
            val realY = currentPoint.yVal.toRealY()

            if (index < points.size.dec()) {
                val nextPoint = points[index.inc()]
                val startX = currentPoint.xVal.toRealX()
                val startY = currentPoint.yVal.toRealY()
                val endX = nextPoint.xVal.toRealX()
                val endY = nextPoint.yVal.toRealY()
                canvas.drawLine(startX, startY, endX, endY, linePaint)
            }

            canvas.drawCircle(realX, realY, 7f, innerCirclePaint)
            canvas.drawCircle(realX, realY, 7f, outerCirclePaint)
        }

        // Y axis
        canvas.drawLine(0f, 0f, 0f, height.toFloat(), axisLinePaint)
        // X axis
        canvas.drawLine(0f, height.toFloat(), width.toFloat(), height.toFloat(), axisLinePaint)
    }

    private fun Int.toRealX() = toFloat() / xMax * width
    private fun Int.toRealY() = toFloat() / yMax * height
}

data class GraphPoint(
    val xVal: Int,
    val yVal: Int
)
