package com.kevinmhankes.jeopardyplayalong.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.kevinmhankes.jeopardyplayalong.R
import com.kevinmhankes.jeopardyplayalong.model.JeopardyTotal.runningTotal

/**
 * @author Kevin.
 * Created/Modified on March 06, 2019
 */

class FirstRoundFragment : Fragment(), View.OnClickListener, View.OnLongClickListener {

    companion object {

        fun newInstance(): FirstRoundFragment {
            return FirstRoundFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_first_round, container, false)

        setFirstRoundOnClickListeners(view)

        return view
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.n200 -> runningTotal = 200
            R.id.n400 -> runningTotal += 400
            R.id.n600 -> runningTotal += 600
            R.id.n800 -> runningTotal += 800
            R.id.n1000 -> runningTotal += 1000
        }
        (activity as GameRoundsActivity).setRunningTotalText()
    }

    override fun onLongClick(view: View): Boolean {
        when (view.id) {
            R.id.n200 -> runningTotal -= 200
            R.id.n400 -> runningTotal -= 400
            R.id.n600 -> runningTotal -= 600
            R.id.n800 -> runningTotal -= 800
            R.id.n1000 -> runningTotal -= 1000
        }
        (activity as GameRoundsActivity).setRunningTotalText()
        return true
    }

    private fun setFirstRoundOnClickListeners(view: View) {
        val n200 = view.findViewById<Button>(R.id.n200)
        n200.setOnClickListener(this)
        n200.setOnLongClickListener(this)
        val n400 = view.findViewById<Button>(R.id.n400)
        n400.setOnClickListener(this)
        n400.setOnLongClickListener(this)
        val n600 = view.findViewById<Button>(R.id.n600)
        n600.setOnClickListener(this)
        n600.setOnLongClickListener(this)
        val n800 = view.findViewById<Button>(R.id.n800)
        n800.setOnClickListener(this)
        n800.setOnLongClickListener(this)
        val n1000 = view.findViewById<Button>(R.id.n1000)
        n1000.setOnClickListener(this)
        n1000.setOnLongClickListener(this)
    }
}