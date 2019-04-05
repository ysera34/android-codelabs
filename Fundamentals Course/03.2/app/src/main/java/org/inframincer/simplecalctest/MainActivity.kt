package org.inframincer.simplecalctest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.inframincer.simplecalctest.Calculator.Operator.*

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        operation_add_btn.setOnClickListener {
            computeAndDisplayResult(ADD)
        }
        operation_sub_btn.setOnClickListener {
            computeAndDisplayResult(SUB)
        }
        operation_div_btn.setOnClickListener {
            try {
                computeAndDisplayResult(DIV)
            } catch (iae: IllegalArgumentException) {
                Log.e(TAG, "IllegalArgumentException", iae)
                operation_result_text_view.text = getString(R.string.computationError)
            }
        }
        operation_mul_btn.setOnClickListener {
            computeAndDisplayResult(MUL)
        }
    }

    private fun computeAndDisplayResult(operator: Calculator.Operator) {
        var firstOperand = 0.0
        var secondOperand = 0.0
        try {
            firstOperand = operand_one_edit_text.text.toString().toDouble()
            secondOperand = operand_two_edit_text.text.toString().toDouble()
        } catch (nfe: NumberFormatException) {
            Log.e(TAG, "NumberFormatException", nfe)
            operation_result_text_view.text = getString(R.string.computationError)
        }
        val result = Calculator.compute(operator, firstOperand, secondOperand)
        operation_result_text_view.text = result.toString()
    }
}
