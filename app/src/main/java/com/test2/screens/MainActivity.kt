package com.test2.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test2.DiagramCustomView
import com.test2.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<DiagramCustomView>(R.id.diagram).buildDiagram(
            listOf(
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
        )
    }
}