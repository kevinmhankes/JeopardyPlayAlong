package com.kevinmhankes.jeopardyplayalong.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import com.kevinmhankes.jeopardyplayalong.R

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kevinmhankes.jeopardyplayalong.model.JeopardyTotal.finalJeopardyWager
import com.kevinmhankes.jeopardyplayalong.model.JeopardyTotal.runningTotal

/**
 * @author Kevin.
 * Created/Modified on April 10, 2018
 */
class FinalJeopardyActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.final_jeopardy)
        setRunningTotalText()
        setOnClickListeners()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.wager -> handleFinalJeopardyWager()
        }
        setRunningTotalText()
    }

    fun restartGame(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun setRunningTotalText() {
        val textView = findViewById<TextView>(R.id.running_total)
        textView.text = getString(R.string.dollar_sign, runningTotal)
    }

    private fun setOnClickListeners() {
        val finalJeopardyWager = findViewById<Button>(R.id.wager)
        finalJeopardyWager.setOnClickListener(this)
    }

    private fun handleFinalJeopardyWager() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.final_jeopardy)

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog, which ->
            if (input.text.toString() != "") {
                finalJeopardyWager = Integer.parseInt(input.text.toString())
                verifyFinalJeopardyCorrectness()
                dialog.dismiss()
            } else {
                handleFinalJeopardyWager()
                dialog.dismiss()
            }
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }

        builder.show()
    }

    private fun handleFinalJeopardyCorrectness() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.final_jeopardy)

        val textView = TextView(this)
        textView.setText(R.string.final_jeopardy_correctness)
        builder.setView(textView)

        builder.setPositiveButton("Yes") { dialog, which ->
            runningTotal += finalJeopardyWager
            setRunningTotalText()
            displayFinalTotal()
        }
        builder.setNegativeButton("No") { dialog, which ->
            runningTotal -= finalJeopardyWager
            setRunningTotalText()
            displayFinalTotal()
        }

        builder.show()
    }

    private fun verifyFinalJeopardyCorrectness() {
        if (finalJeopardyWager < MINIMUM_FINAL_JEOPARDY_WAGER || finalJeopardyWager > runningTotal) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.final_jeopardy)

            val textView = TextView(this)
            textView.text = getString(R.string.invalid_final_jeopardy_wager, runningTotal)
            builder.setView(textView)

            builder.setNeutralButton("Ok") { dialog, which -> handleFinalJeopardyWager() }

            builder.show()
        } else {
            handleFinalJeopardyCorrectness()
        }
    }

    private fun displayFinalTotal() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.final_jeopardy)

        val textView = TextView(this)
        textView.text = getString(R.string.final_total, runningTotal)
        builder.setView(textView)

        builder.setNeutralButton("Ok") { dialog, which -> dialog.dismiss() }

        builder.show()
    }

    companion object {

        private val MINIMUM_FINAL_JEOPARDY_WAGER = 0
        private val RUNNING_TOTAL = "RUNNING_TOTAL"
    }
}
