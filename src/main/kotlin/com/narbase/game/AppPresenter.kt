package com.narbase.game

import com.narbase.kunafa.core.components.TextView
import com.narbase.kunafa.core.components.View
import com.narbase.kunafa.core.presenter.Presenter

class AppPresenter : Presenter() {
    val cells = arrayOfNulls<TextView>(9)
    override fun onViewCreated(view: View) {

    }

    fun onCellClicked(index: Int) {

    }
}