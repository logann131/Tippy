package dev.nguyen.tippy

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import dev.nguyen.tippy.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    // lateinit promises that the system will init the variable
    // before using it
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // app backend
        super.onCreate(savedInstanceState)
        // initializes the binding object which will be used
        // to access Views in the activity_main.xml layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculateTip() }

        // automatically hide keyboard after hit enter
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }
    }

    private fun calculateTip() {
        // .text from EditView return an editable type, not a string
        // that's why to convert it to double, needs to convert to a string first
        // if (cost == null) { return } can be replaced with ?: return
        // val cost = binding.costOfService.text.toString().toDoubleOrNull() ?: return
        val cost = binding.costOfServiceEditText.text.toString().toDoubleOrNull()

        if (cost == null) {
            binding.tipResult.text = ""
            return
        }

        // use "when" function instead of if/else stm
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
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
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}