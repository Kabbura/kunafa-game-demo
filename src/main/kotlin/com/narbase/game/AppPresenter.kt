package com.narbase.game

import com.narbase.kunafa.core.components.TextView
import com.narbase.kunafa.core.components.View
import com.narbase.kunafa.core.presenter.Presenter

class AppPresenter : Presenter() {
    val cells = arrayOfNulls<TextView>(9)
    var statusTextView: TextView? = null
    private var turn = "X"
    private var gameEnded = false

    override fun onViewCreated(view: View) {
        statusTextView?.text = "Turn: $turn player"

    }

    fun onCellClicked(index: Int) {
        val cell = cells[index]
        if (cell?.text?.isNotEmpty() == true || gameEnded) return
        cell?.text = turn
        flipTurn()
    }

    private fun flipTurn() {
        turn = if (turn == "X") "O" else "X"
        statusTextView?.text = "Turn: $turn player"
    }

}