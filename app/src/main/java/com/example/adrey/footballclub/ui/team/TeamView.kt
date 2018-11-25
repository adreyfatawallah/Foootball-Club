package com.example.adrey.footballclub.ui.team

import com.example.adrey.footballclub.model.team.Team

interface TeamView {

    fun showProgressTeam()

    fun hideProgressTeam()

    fun showTeam(team: List<Team>?)

    fun showSearch(team: List<Team>?)
}