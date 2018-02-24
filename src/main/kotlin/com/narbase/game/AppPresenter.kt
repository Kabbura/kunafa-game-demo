package com.narbase.game

import com.narbase.kunafa.core.components.TextView
import com.narbase.kunafa.core.components.View
import com.narbase.kunafa.core.presenter.Presenter

class AppPresenter : Presenter() {
    var view: AppView? = null
    val cells = arrayOfNulls<TextView>(9)
    var statusTextView: TextView? = null
    private val historyEntries = arrayListOf<Int>()
    private var turn = "X"
    private var gameEnded = false

    override fun onViewCreated(view: View) {
        statusTextView?.text = "Turn: $turn player"
    }

    fun onCellClicked(index: Int) {
        val cell = cells[index]
        if (cell?.text?.isNotEmpty() == true || gameEnded) return
        cell?.text = turn
        historyEntries.add(index)
        view?.addHistoryButton("Reset: $turn at cell: $index", index)
        flipTurn()
        getWinner()?.let {
            statusTextView?.text = "$it is the winner!"
            gameEnded = true
        }
    }

    private fun flipTurn() {
        turn = if (turn == "X") "O" else "X"
        statusTextView?.text = "Turn: $turn player"
    }

    private val winningCombinations = arrayListOf(
            arrayListOf(0, 1, 2),
            arrayListOf(3, 4, 5),
            arrayListOf(6, 7, 8),
            arrayListOf(0, 3, 6),
            arrayListOf(1, 4, 7),
            arrayListOf(2, 5, 8),
            arrayListOf(0, 4, 8),
            arrayListOf(2, 4, 6)
    )

    private fun getWinner(): String? {
        winningCombinations.forEach {
            if (cells[it[0]]?.text?.isNotEmpty() == true &&
                    cells[it[0]]?.text == cells[it[1]]?.text &&
                    cells[it[0]]?.text == cells[it[2]]?.text)
                return cells[it[0]]?.text
        }
        return null
    }


}