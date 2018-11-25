package com.example.adrey.footballclub.ui.itemteamplayer

import com.example.adrey.footballclub.api.ApiRepository
import com.example.adrey.footballclub.api.Api.getTeamPlayer
import com.example.adrey.footballclub.model.player.PlayerResponse
import com.example.adrey.footballclub.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class ItemPlayerPresenter(private val view: ItemPlayerView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson,
                          private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPlayer(idTeam: String) {
        async(context.main) {
            val player = bg {
                gson.fromJson(
                        apiRepository.doRequest(getTeamPlayer(idTeam)),
                        PlayerResponse::class.java)
            }

            view.showPlayer(player.await().player)
        }
    }
}