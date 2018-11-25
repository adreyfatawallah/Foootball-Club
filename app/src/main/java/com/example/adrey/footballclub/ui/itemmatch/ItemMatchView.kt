package com.example.adrey.footballclub.ui.itemmatch

import com.example.adrey.footballclub.model.match.Match

interface ItemMatchView {

    fun showProgress()

    fun hideProgress()

    fun showMatch(match: List<Match>?)
}