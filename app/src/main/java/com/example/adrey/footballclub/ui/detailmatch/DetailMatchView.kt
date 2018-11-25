package com.example.adrey.footballclub.ui.detailmatch

import com.example.adrey.footballclub.model.match.Match
import com.example.adrey.footballclub.model.team.Team

interface DetailMatchView {

    fun showProgress()

    fun hideProgress()

    fun showDetailMatch(detailMatch: Match, teamHome: Team, teamAway: Team)
}