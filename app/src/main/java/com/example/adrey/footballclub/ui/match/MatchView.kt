package com.example.adrey.footballclub.ui.match

import com.example.adrey.footballclub.model.match.Match

interface MatchView {

    fun showProgress()

    fun hideProgress()

    fun showSearchMatch(match: List<Match>?)
}