package com.narbase.game

import com.narbase.kunafa.core.components.*
import com.narbase.kunafa.core.components.layout.LinearLayout
import com.narbase.kunafa.core.css.*
import com.narbase.kunafa.core.dimensions.dependent.matchParent
import com.narbase.kunafa.core.dimensions.px
import com.narbase.kunafa.core.drawable.Color
import com.narbase.kunafa.core.lifecycle.LifecycleOwner
import com.narbase.kunafa.core.lifecycle.Observable

fun main(args: Array<String>) {
    page {
        mount(AppComponent(AppViewModel()))
    }
}

class AppComponent(private val appViewModel: AppViewModel) : Component() {

    private var statusTextView: TextView? = null
    private val cells = arrayOfNulls<Button>(9)
    private var buttonsLayout: LinearLayout? = null

    override fun onViewMounted(lifecycleOwner: LifecycleOwner) {
        setupObservers()
        appViewModel.initializeState()
    }

    private fun setupObservers() {
        appViewModel.statusText.observe {
            statusTextView?.text = "$it"
        }
        appViewModel.cellsStateUpdated.observe { updateCells(appViewModel.cellsState) }
        appViewModel.nextHistoryButtonText.observe {
            it ?: return@observe
            addHistoryButton(it.first, it.second)
        }
        appViewModel.deleteLastButtonEvent.observe { deleteLastButton() }
    }

    private fun updateCells(cellsState: Array<String?>?) {
        cellsState?.forEachIndexed { index, text ->
            cells[index]?.text = text
        }
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
        verticalLayout {
            style {
                paddingRight = 16.px
                width = 200.px
                alignItems = Alignment.Center
            }
            statusTextView = textView {
            }
            buttonsLayout = verticalLayout {
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
                appViewModel.onCellClicked(index)
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
                    appViewModel.onHistoryButtonClicked(index)
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
}

class AppViewModel {
    val statusText = Observable<String>()
    val cellsStateUpdated = Observable<Unit>()
    val cellsState = arrayOfNulls<String>(9)
    val nextHistoryButtonText = Observable<Pair<String, Int>>()
    val deleteLastButtonEvent = Observable<Unit>()

    private var gameEnded = false
    private val historyEntries = arrayListOf<Int>()
    private var turn = "X"

    fun initializeState() {
        cellsStateUpdated.value = Unit
        statusText.value = "Turn: $turn player"
    }

    fun onCellClicked(index: Int) {
        val cell = cellsState[index] ?: ""
        if (cell.isNotEmpty() || gameEnded) return
        cellsState[index] = turn
        cellsStateUpdated.value = Unit
        historyEntries.add(index)
        nextHistoryButtonText.value = ("Reset: $turn at cell: $index" to index)
        flipTurn()
        val winner = getWinner() ?: return
        statusText.value = "$winner is the winner!"
        gameEnded = true
    }

    fun onHistoryButtonClicked(index: Int) {
        if (historyEntries.isEmpty()) return
        while (true) {
            if (historyEntries.last() == index || historyEntries.isEmpty()) {
                resetLastTurn()
                cellsStateUpdated.value = Unit
                return
            }
            resetLastTurn()
        }
    }

    private fun resetLastTurn() {
        if (historyEntries.isEmpty()) return
        val lastTurn = historyEntries.last()
        cellsState[lastTurn] = ""
        historyEntries.remove(lastTurn)
        deleteLastButtonEvent.value = Unit
        flipTurn()
        gameEnded = false
    }

    private fun flipTurn() {
        turn = if (turn == "X") "O" else "X"
        statusText.value = "Turn: $turn player"
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
            if (cellsState[it[0]]?.isNotEmpty() == true &&
                    cellsState[it[0]] == cellsState[it[1]] &&
                    cellsState[it[0]] == cellsState[it[2]])
                return cellsState[it[0]]
        }
        return null
    }
}