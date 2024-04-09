package com.example.calculator

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.calculator.databinding.ActivityMainBinding
import java.text.DecimalFormat
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.clearBtn.setOnClickListener {
            binding.input.text = ""
            binding.output.text = ""
            binding.output.visibility = View.GONE
        }

        binding.bracketBtn.setOnClickListener { binding.input.text = addToInput("(")}
        binding.rBracketBtn.setOnClickListener { binding.input.text = addToInput(")")}
        binding.croxxBtn.setOnClickListener {
            val removedLast = binding.input.text.toString().dropLast(1)
            binding.input.text = removedLast
        }
        binding.btn0.setOnClickListener { binding.input.text = addToInput("0")}
        binding.btn1.setOnClickListener { binding.input.text = addToInput("1")}
        binding.btn2.setOnClickListener { binding.input.text = addToInput("2")}
        binding.btn3.setOnClickListener { binding.input.text = addToInput("3")}
        binding.btn4.setOnClickListener { binding.input.text = addToInput("4")}
        binding.btn5.setOnClickListener { binding.input.text = addToInput("5")}
        binding.btn6.setOnClickListener { binding.input.text = addToInput("6")}
        binding.btn7.setOnClickListener { binding.input.text = addToInput("7")}
        binding.btn8.setOnClickListener { binding.input.text = addToInput("8")}
        binding.btn9.setOnClickListener { binding.input.text = addToInput("9")}
        binding.btnDot.setOnClickListener { binding.input.text = addToInput(".")}
        binding.divideBtn.setOnClickListener { binding.input.text = addToInput("/")}
        binding.multiplyBtn.setOnClickListener { binding.input.text = addToInput("x")}
        binding.subtractionBtn.setOnClickListener { binding.input.text = addToInput("-")}
        binding.additionBtn.setOnClickListener { binding.input.text = addToInput("+")}

        binding.equalsBtn.setOnClickListener { showResult()}

    }

    private fun addToInput(value: String): String{
        return binding.input.text.toString()+value
    }

    private fun getInputExpression(): String {
        var expression = binding.input.text.replace(Regex("÷"), "/")
        expression = expression.replace(Regex("x"), "*")
        return expression
    }

    private fun showResult(){
        try {
            val expression = getInputExpression()
            val result = Expression(expression).calculate()
            if (result.isNaN()) {
                binding.output.text = "Expression Error"
                binding.output.setTextColor(Color.RED)
            } else {
                binding.output.text = DecimalFormat("0.######").format(result).toString()
                binding.output.setTextColor(Color.BLACK)
            }
            if(!binding.output.isVisible){
                val anim = AnimationUtils.loadAnimation(this, R.anim.output_anim)
                binding.output.animation = anim
                binding.output.visibility = View.VISIBLE

                val inputAnim = AnimationUtils.loadAnimation(this, R.anim.input_anim)
                binding.input.animation = inputAnim
            }
        } catch (e: Exception) {
            binding.output.text = ""
            Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
        }
    }
}