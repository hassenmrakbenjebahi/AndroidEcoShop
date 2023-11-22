package tn.esprit.ecoshope.ui.history.Adapter

import tn.esprit.ecoshope.model.history.History

interface OnListItemHistoryClick {
    fun onItemHistoryClick(history: History)
}