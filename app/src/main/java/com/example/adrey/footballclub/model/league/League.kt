package com.example.adrey.footballclub.model.league

import com.google.gson.annotations.SerializedName

data class League(
        @SerializedName("idLeague")
        val idLeague: String? = null,
        @SerializedName("strLeague")
        val league: String? = null,
        @SerializedName("strSport")
        val typeLeague: String? = null
)