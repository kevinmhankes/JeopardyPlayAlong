package com.kevinmhankes.jeopardyplayalong.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.kevinmhankes.jeopardyplayalong.R
import com.kevinmhankes.jeopardyplayalong.model.JeopardyTotal
import com.kevinmhankes.jeopardyplayalong.model.JeopardyTotal.runningTotal

/**
 * @author Kevin.
 * Created/Modified on March 06, 2019
 */

class SecondRoundFragment : Fragment(), View.OnClickListener, View.OnLongClickListener {

    companion object {

        fun newInstance(): SecondRoundFragment {
            return SecondRoundFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_second_round, container, false)

        setSecondRoundOnClickListeners(view)

        return view
    }

    private fun setSecondRoundOnClickListeners(view: View) {
        val n400r2 = view.findViewById<Button>(R.id.n400r2)
        n400r2.setOnClickListener(this)
        n400r2.setOnLongClickListener(this)
        val n800r2 = view.findViewById<Button>(R.id.n800r2)
        n800r2.setOnClickListener(this)
        n800r2.setOnLongClickListener(this)
        val n1200r2 = view.findViewById<Button>(R.id.n1200)
        n1200r2.setOnClickListener(this)
        n1200r2.setOnLongClickListener(this)
        val n1600r2 = view.findViewById<Button>(R.id.n1600)
        n1600r2.setOnClickListener(this)
        n1600r2.setOnLongClickListener(this)
        val n2000r2 = view.findViewById<Button>(R.id.n2000)
        n2000r2.setOnClickListener(this)
        n2000r2.setOnLongClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.n400r2 -> runningTotal += 400
            R.id.n800r2 -> runningTotal += 800
            R.id.n1200 -> runningTotal += 1200
            R.id.n1600 -> runningTotal += 1600
            R.id.n2000 -> runningTotal += 2000
            R.id.daily_double -> (activity as GameRoundsActivity).handleDailyDouble()
        }
        (activity as GameRoundsActivity).setRunningTotalText()
    }

    override fun onLongClick(view: View): Boolean {
        when (view.id) {
            R.id.n400r2 -> runningTotal -= 400
            R.id.n800r2 -> runningTotal -= 800
            R.id.n1200 -> runningTotal -= 1200
            R.id.n1600 -> runningTotal -= 1600
            R.id.n2000 -> runningTotal -= 2000
        }
        (activity as GameRoundsActivity).setRunningTotalText()
        return true
    }
}