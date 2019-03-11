package com.kevinmhankes.jeopardyplayalong.ui

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kevinmhankes.jeopardyplayalong.R
import com.kevinmhankes.jeopardyplayalong.model.JeopardyTotal.dailyDouble
import com.kevinmhankes.jeopardyplayalong.model.JeopardyTotal.runningTotal
import com.kevinmhankes.jeopardyplayalong.util.JeopardyPlayAlongUtil

/**
 * @author Kevin.
 * Created/Modified on April 01, 2018
 */
class GameRoundsActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_rounds)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment, FirstRoundFragment.newInstance(), "firstRound")
                    .commit()
        }

        runningTotal = 0
        dailyDouble = 0
        setRunningTotalText()
        setOnClickListeners()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.daily_double -> handleDailyDouble()
        }
        setRunningTotalText()
    }

    fun nextRound(view: View) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment, SecondRoundFragment.newInstance(), "secondRound")
                .addToBackStack(null)
                .commit()
    }

    fun finalJeopardy(view: View) {
        val intent = Intent(this, FinalJeopardyActivity::class.java)
        startActivity(intent)
    }

    fun setRunningTotalText() {
        val textView = findViewById<TextView>(R.id.running_total)
        textView.text = getString(R.string.dollar_sign, runningTotal)
    }

    private fun setOnClickListeners() {
        val dailyDouble = findViewById<Button>(R.id.daily_double)
        dailyDouble.setOnClickListener(this)
    }

    fun handleDailyDouble() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.daily_double)

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog, which ->
            if (input.text.toString() != "") {
                dailyDouble = Integer.parseInt(input.text.toString())
                verifyDailyDoubleWager()
                dialog.dismiss()
            } else {
                handleDailyDouble()
                dialog.dismiss()
            }
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }

        builder.show()
    }

    fun handleDailyDoubleCorrectness() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.daily_double)

        val textView = TextView(this)
        textView.setText(R.string.daily_double_correctness)
        builder.setView(textView)

        builder.setPositiveButton("Yes") { dialog, which ->
            runningTotal += dailyDouble
            setRunningTotalText()
        }
        builder.setNegativeButton("No") { dialog, which ->
            runningTotal -= dailyDouble
            setRunningTotalText()
        }

        builder.show()
    }

    fun verifyDailyDoubleWager() {
        if (dailyDouble < JeopardyPlayAlongUtil.LOWEST_DAILY_DOUBLE_WAGER || dailyDouble > Math.max(JeopardyPlayAlongUtil.HIGHEST_DAILY_DOUBLE_WAGER_ROUND_1, runningTotal)) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.daily_double)

            val textView = TextView(this)
            textView.text = getString(R.string.invalid_daily_double_wager, Math.max(JeopardyPlayAlongUtil.HIGHEST_DAILY_DOUBLE_WAGER_ROUND_1, runningTotal))
            builder.setView(textView)

            builder.setNeutralButton("Ok") { dialog, which -> handleDailyDouble() }

            builder.show()
        } else {
            handleDailyDoubleCorrectness()
        }
    }
}
