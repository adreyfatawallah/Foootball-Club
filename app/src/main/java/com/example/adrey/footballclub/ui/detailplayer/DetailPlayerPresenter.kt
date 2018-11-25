package com.example.adrey.footballclub.ui.detailplayer

import com.example.adrey.footballclub.api.ApiRepository
import com.example.adrey.footballclub.api.Api.getPlayerDetail
import com.example.adrey.footballclub.model.player.PlayerDetailResponse
import com.example.adrey.footballclub.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailPlayerPresenter(private val view: DetailPlayerView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getDetailPlayer(idPlayer: String) {
        view.showProgress()

        async(context.main) {
            val player = bg {
                gson.fromJson(
                        apiRepository.doRequest(getPlayerDetail(idPlayer)),
                        PlayerDetailResponse::class.java)
            }

            view.showDetailPlayer(player.await().player)
            view.hideProgress()
        }
    }
}