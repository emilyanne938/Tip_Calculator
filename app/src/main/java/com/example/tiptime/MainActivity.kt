package com.example.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    //Declares a top-level variable in the class for the binding object
    //Defined at the this level because it will be used throughout class
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener{ calculateTip() }
    }

    private fun calculateTip() {

        //get the cost of service amount from user
        val stringInTextField = binding.costOfService.text.toString() //.text gets text property from object (chaining)
        //you can reference UI element based on its resource ID name in camel case. (PS. needs to be string too)

        val cost = stringInTextField.toDouble() //kotlin method for string to double = toDouble()

        //get to the tip percentage from user.

        if (cost == null || cost == 0.0){
            displayTip(0.0)
            return
        }

        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20   //get corresponding percentage
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        //calculate the tip and round it up
        var tip = tipPercentage * cost      //use var b/c the value might change

        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }

        //system auto format numbers based on language
        displayTip(tip)
    }

    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}