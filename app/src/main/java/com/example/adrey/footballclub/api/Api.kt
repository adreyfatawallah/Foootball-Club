package com.example.adrey.footballclub.api

import com.example.adrey.footballclub.BuildConfig

object Api {

    fun getLeague() =
            "${BuildConfig.BASE_URL}api/v1/json/${BuildConfig.API_KEY}/all_leagues.php"

    fun getMatch(url: String, id: String) = "${BuildConfig.BASE_URL}/api/v1/json/1/$url?id=$id"

    fun getTeams(league: String?): String =
            "${BuildConfig.BASE_URL}api/v1/json/${BuildConfig.API_KEY}/search_all_teams.php?l=$league"

    fun getMatchDetail(string: String) =
            "${BuildConfig.BASE_URL}api/v1/json/${BuildConfig.API_KEY}/lookupevent.php?id=$string"

    fun getTeam(string: String) =
            "${BuildConfig.BASE_URL}api/v1/json/${BuildConfig.API_KEY}/lookupteam.php?id=$string"

    fun getTeamPlayer(string: String) =
            "${BuildConfig.BASE_URL}api/v1/json/${BuildConfig.API_KEY}/lookup_all_players.php?id=$string"

    fun getPlayerDetail(string: String) =
            "${BuildConfig.BASE_URL}api/v1/json/${BuildConfig.API_KEY}/lookupplayer.php?id=$string"

    fun searchTeam(string: String) =
            "${BuildConfig.BASE_URL}api/v1/json/${BuildConfig.API_KEY}/searchteams.php?t=$string"

    fun searchEvent(string: String) =
            "${BuildConfig.BASE_URL}api/v1/json/${BuildConfig.API_KEY}/searchevents.php?e=$string"
}