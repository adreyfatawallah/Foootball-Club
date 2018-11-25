package com.example.adrey.footballclub.ui.itemteamreview

import com.example.adrey.footballclub.api.ApiRepository
import com.example.adrey.footballclub.api.Api.getTeam
import com.example.adrey.footballclub.model.team.TeamResponse
import com.example.adrey.footballclub.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class ItemReviewPresenter(private val view: ItemReviewView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson,
                          private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getReview(idTeam: String) {
        async(context.main) {
            val team = bg {
                gson.fromJson(
                        apiRepository.doRequest(getTeam(idTeam)),
                        TeamResponse::class.java)
            }

            view.showReview(team.await().team)
        }
    }
}