package com.example.adrey.footballclub.ui.team

import com.example.adrey.footballclub.TestContextProvider
import com.example.adrey.footballclub.api.ApiRepository
import com.example.adrey.footballclub.api.Api.getTeams
import com.example.adrey.footballclub.model.team.Team
import com.example.adrey.footballclub.model.team.TeamResponse
import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class TeamPresenterTest {

    @Mock
    private
    lateinit var view: TeamView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private
    lateinit var presenter: TeamPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getTeamList() {
        val team: MutableList<Team> = mutableListOf()
        val response = TeamResponse(team)

        `when`(gson.fromJson(apiRepository.doRequest(getTeams("4328")),
                TeamResponse::class.java)).thenReturn(response)

        presenter.getTeamList("4328")

        verify(view).showProgressTeam()
        verify(view).showTeam(team)
        verify(view).hideProgressTeam()
    }
}