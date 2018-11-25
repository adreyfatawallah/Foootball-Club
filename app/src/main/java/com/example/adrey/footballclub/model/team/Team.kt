package com.example.adrey.footballclub.model.team

import com.google.gson.annotations.SerializedName

data class Team(
        @SerializedName("idTeam")
        val idTeam: String? = null,

        @SerializedName("strTeam")
        val team: String? = null,

        @SerializedName("intFormedYear")
        val year: String? = null,

        @SerializedName("strStadium")
        val stadium: String? = null,

        @SerializedName("strStadiumThumb")
        val imgStadium: String? = null,

        @SerializedName("strDescriptionEN")
        val desc: String? = null,

        @SerializedName("strTeamBadge")
        val imgTeam: String? = null
)