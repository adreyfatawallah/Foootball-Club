package com.example.adrey.footballclub.model.player

import com.google.gson.annotations.SerializedName

class PlayerDetailResponse(
        @SerializedName("players")
        val player: List<Player>
)