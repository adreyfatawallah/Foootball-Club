package com.example.adrey.footballclub.ui.home

import com.example.adrey.footballclub.api.Api.getLeague
import com.example.adrey.footballclub.api.ApiRepository
import com.example.adrey.footballclub.model.league.LeagueResponse
import com.example.adrey.footballclub.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class HomePresenter(private val view: HomeView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getLeagueList() {
        view.showProgress()

        async(context.main) {
            val league = bg {
                gson.fromJson(apiRepository.doRequest(getLeague()),
                        LeagueResponse::class.java)
            }

            view.saveLeague(league.await().league)
            view.hideProgress()
        }
    }
}