package dev.nguyen.tippy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.nguyen.tippy.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    // lateinit promises that the system will init the variable
    // before using it
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // app backend
        super.onCreate(savedInstanceState)
        // initializes the binding object which will be used
        // to access Views in the activity_main.xml layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener{ calculateTip() }
    }

    fun calculateTip() {
        // .text from EditView return an editable type, not a string
        // that's why to convert it to double, needs to convert to a string first
        val cost = binding.costOfService.text.toString().toDouble()

        val tipPercentageSelectedId = binding.tipOptions.checkedRadioButtonId

        // use when func instead of if/else stm
        val tipPercentage = when (tipPercentageSelectedId) {
            R.id.option_fifteen_percent -> 0.15
            R.id.option_eighteen_percent -> 0.18
            else -> 0.20
        }

        var tip = cost * tipPercentage

        val isRoundUp = binding.roundUpSwitch.isChecked
        if (isRoundUp) {
            tip = kotlin.math.ceil(tip)
        }

        // Since each currency has its own format => NumberFormat.getCurrencyInstance will be handy
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)

        // Updated tip_result view id
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)

    }
}