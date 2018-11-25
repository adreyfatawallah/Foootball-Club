package com.example.adrey.footballclub.ui.match

import com.example.adrey.footballclub.api.ApiRepository
import com.example.adrey.footballclub.api.Api.searchEvent
import com.example.adrey.footballclub.model.match.MatchSearchResponse
import com.example.adrey.footballclub.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MatchPresenter(private val view: MatchView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun eventSearch(string: String) {
        view.showProgress()

        async(context.main) {
            val match: Deferred<MatchSearchResponse?> = bg {
                gson.fromJson(apiRepository.doRequest(searchEvent(string)),
                        MatchSearchResponse::class.java)
            }

            view.showSearchMatch(match.await()?.match)
            view.hideProgress()
        }
    }
}