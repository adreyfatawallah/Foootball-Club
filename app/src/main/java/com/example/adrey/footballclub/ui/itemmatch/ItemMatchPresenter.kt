package com.example.adrey.footballclub.ui.itemmatch

import com.example.adrey.footballclub.api.Api.getMatch
import com.example.adrey.footballclub.api.ApiRepository
import com.example.adrey.footballclub.model.match.MatchResponse
import com.example.adrey.footballclub.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class ItemMatchPresenter(private val view: ItemMatchView,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson,
                         private val context: CoroutineContextProvider = CoroutineContextProvider())  {

    fun getMatchList(url: String, id: String) {
        view.showProgress()
//        doAsync {
//            val match = gson.fromJson(
//                    apiRepository.doRequest(getMatch(url)),
//                    MatchResponse::class.java)
//
//            uiThread {
//                view.showMatch(match.match)
//                view.hideProgress()
//            }
//        }

        async(context.main) {
            val match: Deferred<MatchResponse?> = bg {
                gson.fromJson(
                        apiRepository.doRequest(getMatch(url, id)),
                        MatchResponse::class.java)
            }

            view.showMatch(match.await()?.match)
            view.hideProgress()
        }
    }
}