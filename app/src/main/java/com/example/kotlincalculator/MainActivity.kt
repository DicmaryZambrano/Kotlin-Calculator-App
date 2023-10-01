package com.example.kotlincalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var calculator: Calculator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calculator = Calculator()

        initComponents()
        setButtonListeners()
    }

    private fun initComponents() {
        // Initialize calculator display and buttons
        calculator.calDisplay = findViewById(R.id.calDisplay)

        calculator.btn0 = findViewById(R.id.btn0)
        calculator.btn1 = findViewById(R.id.btn1)
        calculator.btn2 = findViewById(R.id.btn2)
        calculator.btn3 = findViewById(R.id.btn3)
        calculator.btn4 = findViewById(R.id.btn4)
        calculator.btn5 = findViewById(R.id.btn5)
        calculator.btn6 = findViewById(R.id.btn6)
        calculator.btn7 = findViewById(R.id.btn7)
        calculator.btn8 = findViewById(R.id.btn8)
        calculator.btn9 = findViewById(R.id.btn9)

        calculator.btnDiv = findViewById(R.id.btnDiv)
        calculator.btnMult = findViewById(R.id.btnMult)
        calculator.btnAdd = findViewById(R.id.btnAdd)
        calculator.btnSub = findViewById(R.id.btnSub)

        calculator.btnClr = findViewById(R.id.btnClr)
        calculator.btnDel = findViewById(R.id.btnDel)
        calculator.btnEqual = findViewById(R.id.btnEqual)
        calculator.btnDot = findViewById(R.id.btnDot)
        calculator.btnSign = findViewById(R.id.btnSign)
    }

    private fun setButtonListeners() {
        // Set listeners for number buttons and operator buttons
        calculator.numberButtons.forEach { button ->
            button.setOnClickListener {
                calculator.numButtonClicked(button)
            }
        }

        calculator.opButtons.forEach { button ->
            button.setOnClickListener {
                calculator.opButtonClicked(button)
            }
        }

        calculator.btnEqual.setOnClickListener {
            calculator.buttonEqualClick()
        }

        calculator.btnClr.setOnClickListener {
            calculator.buttonClearClick()
        }

        calculator.btnDel.setOnClickListener {
            calculator.buttonDeleteClick()
        }

        calculator.btnDot.setOnClickListener {
            calculator.buttonDotClick()
        }

        calculator.btnSign.setOnClickListener {
            calculator.buttonSignClick()
        }
    }
}

class Calculator {
    lateinit var calDisplay: TextView

    lateinit var btn0: Button
    lateinit var btn1: Button
    lateinit var btn2: Button
    lateinit var btn3: Button
    lateinit var btn4: Button
    lateinit var btn5: Button
    lateinit var btn6: Button
    lateinit var btn7: Button
    lateinit var btn8: Button
    lateinit var btn9: Button
    lateinit var btnDiv: Button
    lateinit var btnMult: Button
    lateinit var btnAdd: Button
    lateinit var btnSub: Button
    lateinit var btnClr: Button
    lateinit var btnDel: Button
    lateinit var btnEqual: Button
    lateinit var btnDot: Button
    lateinit var btnSign: Button

    private var numString = StringBuilder()
    private var operator: Operator = Operator.NONE
    private var isOpClicked: Boolean = false
    private var operand1: Double = 0.0
    private var operand2: Double = 0.0

    val numberButtons: Array<Button> by lazy {
        arrayOf(
            btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnClr, btnDel,
            btnDiv, btnMult, btnSub, btnAdd, btnEqual, btnDot, btnSign
        )
    }

    val opButtons: List<Button> by lazy {
        listOf(btnDiv, btnMult, btnSub, btnAdd)
    }

    fun buttonEqualClick() {
        // Perform calculation when equal button is clicked
        operand2 = numString.toString().toDouble()
        val result: Double = when (operator) {
            Operator.DIV -> operand1 / operand2
            Operator.MULT -> operand1 * operand2
            Operator.SUB -> operand1 - operand2
            Operator.ADD -> operand1 + operand2
            else -> 0.0
        }
        numString.clear()
        numString.append(result.toString())
        updateDisplay()
        isOpClicked = true
    }

    fun opButtonClicked(button: Button) {
        // Handle operator button click
        operator = when (button.text) {
            "/" -> Operator.DIV
            "X" -> Operator.MULT
            "-" -> Operator.SUB
            "+" -> Operator.ADD
            else -> Operator.NONE
        }
        isOpClicked = true
    }

    fun numButtonClicked(button: Button) {
        // Handle number button click
        if (isOpClicked) {
            operand1 = numString.toString().toDouble()
            numString.clear()
            isOpClicked = false
        }
        numString.append(button.text)
        updateDisplay()
    }

    fun buttonClearClick() {
        // Clear the calculator's state and display
        numString.clear()
        operand1 = 0.0
        operand2 = 0.0
        operator = Operator.NONE
        numString.append("0")
        updateDisplay()
    }

    fun buttonDeleteClick() {
        // Handle delete button click
        if (numString.isNotEmpty()) {
            numString.deleteCharAt(numString.length - 1)
        }
        if (numString.isEmpty()) {
            numString.append("0")
        }
        updateDisplay()
    }

    fun buttonDotClick() {
        // Handle dot button click to add decimal point
        if (!numString.contains(".")) {
            numString.append(".")
            updateDisplay()
        }
    }

    fun buttonSignClick() {
        // Toggle the sign of the current number
        if (numString.isNotEmpty()) {
            val currentValue = numString.toString().toDouble()
            val newValue = -currentValue
            numString.clear()
            numString.append(newValue.toString())
            updateDisplay()
        }
    }

    private fun updateDisplay() {
        // Update the calculator display
        calDisplay.text = numString.toString()
    }
}

enum class Operator {ADD, SUB, MULT, DIV, NONE }