package com.example.adrey.footballclub.model.league

import com.google.gson.annotations.SerializedName

class LeagueResponse(
        @SerializedName("leagues")
        val league: List<League>
)