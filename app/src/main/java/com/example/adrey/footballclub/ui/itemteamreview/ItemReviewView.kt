package com.example.adrey.footballclub.ui.itemteamreview

import com.example.adrey.footballclub.model.team.Team

interface ItemReviewView {

    fun showReview(team: List<Team>)
}