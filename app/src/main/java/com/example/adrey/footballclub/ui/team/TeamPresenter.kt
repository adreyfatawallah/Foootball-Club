package com.example.adrey.footballclub.ui.team

import com.example.adrey.footballclub.api.ApiRepository
import com.example.adrey.footballclub.model.team.TeamResponse
import com.example.adrey.footballclub.utils.CoroutineContextProvider
import com.example.adrey.footballclub.api.Api.getTeams
import com.example.adrey.footballclub.api.Api.searchTeam
import com.google.gson.Gson
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamPresenter(private val view: TeamView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamList(league: String) {
        view.showProgressTeam()

        async(context.main) {
            val team: Deferred<TeamResponse?> = bg {
                gson.fromJson(
                        apiRepository.doRequest(getTeams(league)),
                        TeamResponse::class.java)
            }

            view.showTeam(team.await()?.team)
            view.hideProgressTeam()
        }
    }

    fun teamSearch(string: String) {
        view.showProgressTeam()

        async(context.main) {
            val team: Deferred<TeamResponse?> = bg {
                gson.fromJson(
                        apiRepository.doRequest(searchTeam(string)),
                        TeamResponse::class.java)
            }

            view.showSearch(team.await()?.team)
            view.hideProgressTeam()
        }
    }
}