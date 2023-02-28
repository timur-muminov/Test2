package com.test2.screens

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.test2.R
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val diagram = findViewById<DiagramView>(R.id.diagram)
        diagram.labelPaint.textSize = 12f.sp
        diagram.borderPaint.strokeWidth = 1f.dp
        diagram.borderPaint.color = 0xAA000000.toInt()
        diagram.guidelinePaint.strokeWidth = 1f.dp
        diagram.guidelinePaint.color = 0x7F000000
        diagram.chartFillPaint.color = 0x7F00AABB
        diagram.chartStrokePaint.color = 0xFF00AABB.toInt()
        diagram.chartStrokePaint.strokeWidth = 1f.dp
        (diagram.chartJoinDrawable as GradientDrawable).setSize(4.dp, 4.dp)
        (diagram.chartJoinDrawable as GradientDrawable).setColor(0xFF00AABB.toInt())
        diagram.setPadding(0, 2.dp, 0, 0)
        diagram.data = listOf(
            DiagramCustomView.Expenses(1000f, 0f, 0f),
            DiagramCustomView.Expenses(4000f, 0f, 0f),
            DiagramCustomView.Expenses(5000f, 0f, 0f),
            DiagramCustomView.Expenses(9000f, 0f, 0f),
            DiagramCustomView.Expenses(4500f, 0f, 0f),
            DiagramCustomView.Expenses(8500f, 0f, 0f),
            DiagramCustomView.Expenses(5500f, 0f, 0f),
            DiagramCustomView.Expenses(500f, 0f, 0f),
            DiagramCustomView.Expenses(200f, 0f, 0f),
            DiagramCustomView.Expenses(10500f, 0f, 0f),
            DiagramCustomView.Expenses(3300f, 0f, 0f),
            DiagramCustomView.Expenses(4300f, 0f, 0f)
        )
    }

    private val Int.sp: Int get() = (this * resources.displayMetrics.scaledDensity).roundToInt()
    private val Int.dp: Int get() = (this * resources.displayMetrics.density).roundToInt()
    private val Float.sp: Float get() = this * resources.displayMetrics.scaledDensity
    private val Float.dp: Float get() = this * resources.displayMetrics.density
}