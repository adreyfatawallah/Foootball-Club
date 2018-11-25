package com.example.adrey.footballclub.ui.home

import com.example.adrey.footballclub.model.league.League

interface HomeView {

    fun showProgress()

    fun hideProgress()

    fun saveLeague(leagues: List<League>)
}