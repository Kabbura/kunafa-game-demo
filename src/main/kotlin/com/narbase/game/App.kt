package com.narbase.game

import com.narbase.kunafa.core.components.*
import com.narbase.kunafa.core.components.layout.LinearLayout
import com.narbase.kunafa.core.css.*
import com.narbase.kunafa.core.dimensions.dependent.matchParent
import com.narbase.kunafa.core.dimensions.px
import com.narbase.kunafa.core.drawable.Color
import com.narbase.kunafa.core.lifecycle.LifecycleOwner

fun main(args: Array<String>) {
    page {
        mount(AppComponent())
    }
}

class AppComponent : Component() {

    private var statusTextView: TextView? = null
    private val cells = arrayOfNulls<Button>(9)
    private var buttonsLayout: LinearLayout? = null
    private var turn = "X"

    private val historyEntries = arrayListOf<Int>()
    private var gameEnded = false

    override fun onViewMounted(lifecycleOwner: LifecycleOwner) {
        statusTextView?.text = "Turn: $turn player"
    }

    override fun View?.getView() = horizontalLayout {
        style {
            width = matchParent
            margin = 16.px.toString()
            justifyContent = JustifyContent.Center

        }
        verticalLayout {
            horizontalLayout {
                cells[0] = addCell(0)
                cells[1] = addCell(1)
                cells[2] = addCell(2)
            }
            horizontalLayout {
                cells[3] = addCell(3)
                cells[4] = addCell(4)
                cells[5] = addCell(5)
            }
            horizontalLayout {
                cells[6] = addCell(6)
                cells[7] = addCell(7)
                cells[8] = addCell(8)
            }
        }
        buttonsLayout = verticalLayout {
            style {
                paddingRight = 16.px
                width = 200.px
                alignItems = Alignment.Center
            }
            statusTextView = textView {
            }
        }
    }

    private fun LinearLayout.addCell(index: Int): Button? {
        return button {
            style {
                width = 30.px
                height = 30.px
                backgroundColor = if (index.rem(2) == 0) Color(220, 220, 220) else Color.white
                textAlign = TextAlign.Center
                fontSize = 18.px
                border = "none"
            }
            onClick = {
                onCellClicked(index)
            }
        }
    }

    private fun addHistoryButton(buttonText: String, index: Int) {
        buttonsLayout?.apply {
            button {
                text = buttonText
                style {
                    marginTop = 8.px
                }
                onClick = {
                    onHistoryButtonClicked(index)
                }
            }
        }
    }

    private fun deleteLastButton() {
        if (buttonsLayout?.children?.isNotEmpty() != true) return
        buttonsLayout?.children?.last()?.let {
            buttonsLayout?.removeChild(it)
        }
    }

    private fun onCellClicked(index: Int) {
        val cell = cells[index]
        if (cell?.text?.isNotEmpty() == true || gameEnded) return
        cell?.text = turn
        historyEntries.add(index)
        addHistoryButton("Reset: $turn at cell: $index", index)
        flipTurn()
        val winner = getWinner() ?: return
        statusTextView?.text = "$winner is the winner!"
        gameEnded = true
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

    private fun onHistoryButtonClicked(index: Int) {
        if (historyEntries.isEmpty()) return
        while (true) {
            if (historyEntries.last() == index || historyEntries.isEmpty()) {
                resetLastTurn()
                return
            }
            resetLastTurn()
        }
    }

    private fun resetLastTurn() {
        if (historyEntries.isEmpty()) return
        val lastTurn = historyEntries.last()
        cells[lastTurn]?.let {
            it.text = ""
        }
        historyEntries.remove(lastTurn)
        deleteLastButton()
        flipTurn()
        gameEnded = false
    }
}