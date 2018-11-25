package com.example.adrey.footballclub.model.team

import com.google.gson.annotations.SerializedName

class TeamResponse(
        @SerializedName("teams")
        val team: List<Team>
)