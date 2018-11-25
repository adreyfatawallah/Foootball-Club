package com.example.adrey.footballclub.model.match

import com.google.gson.annotations.SerializedName

class MatchResponse(
        @SerializedName("events")
        val match: List<Match>
)