package com.test2.screens

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import java.util.Collections.max

const val stepYCount = 5

class DiagramCustomView @JvmOverloads constructor(
    private val context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var stepY: Float = 0f
    private var stepX: Float = 0f

    private var viewHeight: Float = 0f
    private var viewWidth: Float = 0f

    private val paddingStart = 27f.toPx()
    private val paddingBottom = 12f.toPx()
    private val extraPadding = 12f.toPx()

    private var stepYValue = 0
    private var diagram: List<Expenses> = listOf()
    private var expenseMaxValue: Float = 0f

    private val rectanglePaint: Paint =
        Paint().apply {
            color = context.getColor(R.color.diagram_rectangle)
            strokeWidth = 1f.toPx()
            textSize = 10f.toPx()
        }


    private val graphPaint: Paint =
        Paint().apply {
            style = Paint.Style.STROKE
            color = context.getColor(R.color.teal_200)
            strokeWidth = 1f.toPx()
        }

    private var rectangleLines: FloatArray = floatArrayOf()


    private val graphPath: Path by lazy { Path().apply { moveTo(paddingStart, viewHeight - paddingBottom) } }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        viewWidth = measuredWidth.toFloat()
        viewHeight = measuredHeight.toFloat()
        val rectangleHeight = viewHeight - paddingBottom
        stepY = (rectangleHeight - extraPadding) / stepYCount.toFloat()
        stepX = (viewWidth - paddingStart - extraPadding) / diagram.size.toFloat()


        rectangleLines = arrayOf(
            paddingStart,
            0f,
            paddingStart,
            rectangleHeight,
            paddingStart,
            rectangleHeight,
            viewWidth,
            rectangleHeight
        ).toFloatArray()

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


    override fun onDraw(canvas: Canvas) {
        canvas.drawHorizontalValues()
        canvas.drawVerticalValues()
        canvas.drawGraph()
        canvas.drawLines(rectangleLines, rectanglePaint)
        super.onDraw(canvas)
    }

    private fun Canvas.drawGraph() {
        var coordinateX: Float = paddingStart
        var coordinateY: Float
        diagram.forEach { expenses ->
            coordinateX += stepX
            coordinateY =
                viewHeight - paddingBottom - (expenses.food * (viewHeight - paddingBottom - extraPadding) / expenseMaxValue)
            graphPath.lineTo(coordinateX, coordinateY)
            graphPaint.style = Paint.Style.FILL
            drawCircle(coordinateX, coordinateY, 2f.toPx(), graphPaint)
            graphPaint.style = Paint.Style.STROKE
        }
        graphPath.lineTo(viewWidth - extraPadding, viewHeight - paddingBottom)
        graphPath.close()

        drawPath(graphPath, graphPaint)
        graphPaint.color = context.getColor(R.color.teal_200_transparent_50)
        graphPaint.style = Paint.Style.FILL
        drawPath(graphPath, graphPaint)
    }

    private fun Canvas.drawHorizontalValues() {
        drawText("0", paddingStart - extraPadding, viewHeight, rectanglePaint)
        var stepXCounter = paddingStart + stepX
        val stepYCounter: Float = viewHeight
        repeat(diagram.size) {
            drawText(it.plus(1).toString(), stepXCounter, stepYCounter, rectanglePaint)
            stepXCounter += stepX
        }
    }

    private fun Canvas.drawVerticalValues() {
        val stepXCounter = 0f
        var stepYCounter = viewHeight - paddingBottom - stepY
        repeat(stepYCount) {
            drawText((it.plus(1) * stepYValue).toString(), stepXCounter, stepYCounter, rectanglePaint)
            rectanglePaint.color = context.getColor(R.color.diagram_lines)
            drawLine(paddingStart, stepYCounter, viewWidth, stepYCounter, rectanglePaint)
            rectanglePaint.color = context.getColor(R.color.diagram_rectangle)
            stepYCounter -= stepY
        }
    }

    data class Expenses(
        val food: Float,
        val entertainment: Float,
        val taxi: Float
    )


    fun buildDiagram(diagram: List<Expenses>) {
        this.diagram = diagram
        expenseMaxValue = diagram.getMaxValue()
        stepYValue = (expenseMaxValue / stepYCount).toInt()
        invalidate()
    }

    private fun List<Expenses>.getMaxValue() =
        maxOf { max(listOf(it.food, it.taxi, it.entertainment)) }

}

private fun Float.toPx() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this,
    Resources.getSystem().displayMetrics
)