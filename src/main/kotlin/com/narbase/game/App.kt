package com.narbase.game

import com.narbase.kunafa.core.components.*
import com.narbase.kunafa.core.components.layout.Alignment
import com.narbase.kunafa.core.components.layout.JustifyContent
import com.narbase.kunafa.core.components.layout.LinearLayout
import com.narbase.kunafa.core.dimensions.dependent.matchParent
import com.narbase.kunafa.core.dimensions.independent.px
import com.narbase.kunafa.core.drawable.Color

fun main(args: Array<String>) {
    val presenter = AppPresenter()
    AppView(presenter).setup()
}

class AppView(val appPresenter: AppPresenter) {
    fun setup() {
        page {
            presenter = appPresenter
            horizontalLayout {
                width = matchParent
                margin = 16.px
                justifyContent = JustifyContent.Center
                verticalLayout {
                    horizontalLayout {
                        appPresenter.cells[0] = addCell(0)
                        appPresenter.cells[1] = addCell(1)
                        appPresenter.cells[2] = addCell(2)
                    }
                    horizontalLayout {
                        appPresenter.cells[3] = addCell(3)
                        appPresenter.cells[4] = addCell(4)
                        appPresenter.cells[5] = addCell(5)
                    }
                    horizontalLayout {
                        appPresenter.cells[3] = addCell(6)
                        appPresenter.cells[4] = addCell(7)
                        appPresenter.cells[5] = addCell(8)
                    }
                }
                verticalLayout {
                    paddingStart = 16.px
                    width = 200.px
                    alignItems = Alignment.Center
                    textView {
                        text = "Turn: X player"
                    }
                }
            }
        }
    }

    private fun LinearLayout.addCell(index: Int): TextView? {
        var cell: TextView? = null
        verticalLayout {
            width = 30.px
            height = 30.px
            background = if (index.rem(2) == 0) Color.rgb(220, 220, 220) else Color.white
            justifyContent = JustifyContent.Center
            cell = textView {
                width = matchParent
                textSize = 18.px
                textAlign = TextView.TextAlign.Center
            }
            onClick = {
                appPresenter.onCellClicked(index)
            }
        }
        return cell
    }
}