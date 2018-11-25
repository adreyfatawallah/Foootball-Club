package com.example.adrey.footballclub.ui.detailteam

import com.example.adrey.footballclub.api.ApiRepository
import com.example.adrey.footballclub.api.Api.getTeam
import com.example.adrey.footballclub.model.team.TeamResponse
import com.example.adrey.footballclub.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailTeamPresenter(private val view: DetailTeamView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson,
                          private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamDetail(idTeam: String) {
        view.showProgress()

        async(context.main) {
            val team = bg {
                gson.fromJson(
                        apiRepository.doRequest(getTeam(idTeam)),
                        TeamResponse::class.java)
            }

            view.showDetailTeam(team.await().team)
            view.hideProgress()
        }
    }
}