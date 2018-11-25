package com.example.adrey.footballclub.ui.detailmatch

import com.example.adrey.footballclub.api.Api.getMatchDetail
import com.example.adrey.footballclub.api.ApiRepository
import com.example.adrey.footballclub.api.Api.getTeam
import com.example.adrey.footballclub.model.match.MatchResponse
import com.example.adrey.footballclub.model.team.TeamResponse
import com.example.adrey.footballclub.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailMatchPresenter(private val view: DetailMatchView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getDetailMatch(idEvent: String, idHome: String, idAway: String) {
        view.showProgress()

        async(context.main) {
            val detailMatch = bg {
                gson.fromJson(
                        apiRepository.doRequest(getMatchDetail(idEvent)),
                        MatchResponse::class.java)
            }

            val teamHome = bg {
                gson.fromJson(
                        apiRepository.doRequest(getTeam(idHome)),
                        TeamResponse::class.java)
            }

            val teamAway = bg {
                gson.fromJson(
                        apiRepository.doRequest(getTeam(idAway)),
                        TeamResponse::class.java)
            }

            view.showDetailMatch(detailMatch.await().match[0],
                    teamHome.await().team[0],
                    teamAway.await().team[0])
            view.hideProgress()
        }
    }
}