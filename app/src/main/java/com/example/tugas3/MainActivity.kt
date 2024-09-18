package com.example.tugas3

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private var currentInput: String = ""
    private var operator: String? = null
    private var operand1: Double? = null
    private var operand2: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)

        // Set angka tombol
        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        numberButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { appendNumber((it as Button).text.toString()) }
        }

        // Set operasi tombol
        findViewById<Button>(R.id.btnAdd).setOnClickListener { setOperator("+") }
        findViewById<Button>(R.id.btnSubtract).setOnClickListener { setOperator("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { setOperator("*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { setOperator("/") }

        // Tombol hasil (=)
        findViewById<Button>(R.id.btnEqual).setOnClickListener { calculateResult() }

        // Tombol clear (C)
        findViewById<Button>(R.id.btnClear).setOnClickListener { clear() }
    }

    private fun appendNumber(number: String) {
        currentInput += number
        tvResult.text = currentInput
    }

    private fun setOperator(op: String) {
        operand1 = currentInput.toDoubleOrNull()
        operator = op
        currentInput = ""
        Log.d("Calculator", "Operator set: $operator, Operand1: $operand1")
    }

    private fun calculateResult() {
        operand2 = currentInput.toDoubleOrNull()
        Log.d("Calculator", "Operand2: $operand2, Operator: $operator, Operand1: $operand1")

        if (operand1 != null && operand2 != null && operator != null) {
            val result = when (operator) {
                "+" -> operand1!! + operand2!!
                "-" -> operand1!! - operand2!!
                "*" -> operand1!! * operand2!!
                "/" -> if (operand2!! != 0.0) operand1!! / operand2!! else Double.NaN
                else -> 0.0
            }
            tvResult.text = if (result.isNaN()) "Error" else result.toString()
        } else {
            tvResult.text = "Error"
        }
        // Do not clear inputs here to allow chaining calculations
    }

    private fun clear() {
        currentInput = ""
        operand1 = null
        operand2 = null
        operator = null
        tvResult.text = ""
        Log.d("Calculator", "Cleared")
    }
}
