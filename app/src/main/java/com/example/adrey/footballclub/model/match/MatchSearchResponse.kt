package com.example.adrey.footballclub.model.match

import com.google.gson.annotations.SerializedName

class MatchSearchResponse(
        @SerializedName("event")
        val match: List<Match>
)