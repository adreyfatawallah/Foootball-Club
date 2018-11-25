package com.example.adrey.footballclub.ui.itemmatch

import com.example.adrey.footballclub.TestContextProvider
import com.example.adrey.footballclub.api.Api
import com.example.adrey.footballclub.api.ApiRepository
import com.example.adrey.footballclub.model.match.Match
import com.example.adrey.footballclub.model.match.MatchResponse
import com.google.gson.Gson
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class ItemMatchPresenterTest {

    @Mock
    private
    lateinit var view: ItemMatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private
    lateinit var presenter: ItemMatchPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = ItemMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetMatchList() {
        val match: MutableList<Match> = mutableListOf()
        val response = MatchResponse(match)
        val url = "eventspastleague.php"

        `when`(gson.fromJson(apiRepository.doRequest(Api.getMatch(url, "4328")),
                MatchResponse::class.java)).thenReturn(response)

        presenter.getMatchList(url, "4328")

        verify(view).showProgress()
        verify(view).showMatch(match)
        verify(view).hideProgress()
    }
}