package com.narbase.game

import com.narbase.kunafa.core.components.*
import com.narbase.kunafa.core.components.layout.Alignment
import com.narbase.kunafa.core.components.layout.JustifyContent
import com.narbase.kunafa.core.components.layout.LinearLayout
import com.narbase.kunafa.core.dimensions.dependent.matchParent
import com.narbase.kunafa.core.dimensions.independent.px
import com.narbase.kunafa.core.drawable.Color

fun main(args: Array<String>) {
    AppView().setup()
}

class AppView {
    fun setup() {
        page {
            horizontalLayout {
                width = matchParent
                margin = 16.px
                justifyContent = JustifyContent.Center
                verticalLayout {
                    horizontalLayout {
                        addCell(0)
                        addCell(1)
                        addCell(2)

                    }
                    horizontalLayout {
                        addCell(3)
                        addCell(4)
                        addCell(5)

                    }
                    horizontalLayout {
                        addCell(6)
                        addCell(7)
                        addCell(8)

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

    private fun LinearLayout.addCell(index: Int) {
        verticalLayout {
            width = 30.px
            height = 30.px
            background = if (index.rem(2) == 0) Color.rgb(220, 220, 220) else Color.white
            justifyContent = JustifyContent.Center
            textView {
                width = matchParent
                textSize = 18.px
                textAlign = TextView.TextAlign.Center
            }
        }
    }
}