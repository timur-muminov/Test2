package com.test2.screens

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.FontMetrics
import android.graphics.Path
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.test2.screens.DiagramCustomView.Expenses
import java.lang.Float.max
import java.text.DecimalFormat
import java.text.NumberFormat
import kotlin.math.roundToInt

class DiagramView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var maxExpense = 0f
    var data = listOf<Expenses>()
        set(value) {
            field = value
            maxExpense = value.maxOf(Expenses::food)
            path.rewind()
            invalidate()
        }

    val labelPaint: TextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    val labelFormat: NumberFormat = DecimalFormat.getNumberInstance()

    val borderPaint: Paint = Paint()
    val guidelinePaint: Paint = Paint()

    val chartFillPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val chartStrokePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).also { it.style = Paint.Style.STROKE }
    var chartJoinDrawable: Drawable? = GradientDrawable().also { it.shape = GradientDrawable.OVAL }
        set(value) { field = value; invalidate() }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.rewind()
    }

    private val metrics = FontMetrics()
    private val leftTexts = ArrayList<String>()
    private val path = Path()
    override fun onDraw(canvas: Canvas) {
        canvas.translate(paddingLeft.toFloat(), paddingTop.toFloat())
        val width = width - paddingLeft - paddingRight
        val height = height - paddingTop - paddingBottom

        labelPaint.getFontMetrics(metrics)
        val textPadding = labelPaint.measureText("0")
        val textHeight = metrics.bottom - metrics.top
        val chartHeight = height - textHeight - borderPaint.strokeWidth - textPadding
        val leftTextsCount = chartHeight.toInt() / textHeight.toInt() / 2
        var leftTextsWidth = 0f
        repeat(leftTextsCount) {
            val guidelineValue = labelFormat.format(maxExpense / (leftTextsCount-1) * it)
            leftTextsWidth = max(leftTextsWidth, labelPaint.measureText(guidelineValue))
            leftTexts += guidelineValue
        }
        labelPaint.textAlign = Paint.Align.RIGHT
        repeat(leftTextsCount) {
            val y = chartHeight - chartHeight / (leftTextsCount-1) * it
            val yOff = -metrics.top / (leftTextsCount-1) * it
            canvas.drawText(leftTexts[it], leftTextsWidth, y + yOff, labelPaint)
            if (it != 0)
                canvas.drawLine(leftTextsWidth + borderPaint.strokeWidth + textPadding, y, width.toFloat(), y, guidelinePaint)
        }
        leftTexts.clear()
        leftTextsWidth += textPadding
        val chartWidth = width - leftTextsWidth - borderPaint.strokeWidth

        // v border
        canvas.drawLine(leftTextsWidth + borderPaint.strokeWidth / 2f, 0f, leftTextsWidth + borderPaint.strokeWidth / 2f, chartHeight + borderPaint.strokeWidth / 2f, borderPaint)
        // h border
        canvas.drawLine(leftTextsWidth, chartHeight + borderPaint.strokeWidth / 2f, width.toFloat(), chartHeight + borderPaint.strokeWidth / 2f,  borderPaint)

        canvas.translate(leftTextsWidth + borderPaint.strokeWidth, 0f)
        labelPaint.textAlign = Paint.Align.CENTER
        val size = data.lastIndex
        for (index in data.indices) {
            val y = chartHeight + borderPaint.strokeWidth + textPadding - metrics.top
            val text = (index + 1).toString()
            val offset = labelPaint.measureText(text) / 2f * index / size
            canvas.drawText(text, chartWidth * index / size - offset, y, labelPaint)
        }

        if (path.isEmpty && data.isNotEmpty())
            buildPath(chartWidth, chartHeight)

        canvas.drawPath(path, chartFillPaint)
        canvas.drawPath(path, chartStrokePaint)

        if (chartJoinDrawable != null)
            drawJoins(canvas, chartWidth, chartHeight, chartJoinDrawable!!)
    }

    private fun buildPath(width: Float, height: Float) {
        path.moveTo(0f, height)
        val size = data.lastIndex
        for (index in data.indices) {
            val expenses = data[index]
            path.lineTo(width * index / size, height - (expenses.food * height / maxExpense))
        }
        path.lineTo(width, height)
    }
    private fun drawJoins(canvas: Canvas, width: Float, height: Float, d: Drawable) {
        val size = data.lastIndex
        for (index in 1 until size) {
            val expenses = data[index]
            val x = (width * index / size).roundToInt()
            val y = (height - (expenses.food * height / maxExpense)).roundToInt()
            val intw = d.intrinsicWidth
            val inth = d.intrinsicHeight
            d.setBounds(
                x - intw / 2, y - inth / 2,
                x + intw / 2 + intw % 2, y + inth / 2 + inth % 2,
            )
            d.draw(canvas)
        }
    }

}
