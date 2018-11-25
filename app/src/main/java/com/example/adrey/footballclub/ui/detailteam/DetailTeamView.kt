package com.example.adrey.footballclub.ui.detailteam

import com.example.adrey.footballclub.model.team.Team

interface DetailTeamView {

    fun showProgress()

    fun hideProgress()

    fun showDetailTeam(teams: List<Team>)
}