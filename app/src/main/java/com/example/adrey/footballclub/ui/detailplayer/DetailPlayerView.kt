package com.example.adrey.footballclub.ui.detailplayer

import com.example.adrey.footballclub.model.player.Player

interface DetailPlayerView {

    fun showProgress()

    fun hideProgress()

    fun showDetailPlayer(player: List<Player>)
}