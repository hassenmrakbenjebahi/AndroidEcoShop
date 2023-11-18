package tn.esprit.ecoshope.ui.adapter

import tn.esprit.ecoshope.model.History

interface OnListItemHistoryClick {
    fun onItemHistoryClick(history: History)
}