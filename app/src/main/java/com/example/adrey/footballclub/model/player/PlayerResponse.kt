package com.example.adrey.footballclub.model.player

import com.google.gson.annotations.SerializedName

class PlayerResponse(
        @SerializedName("player")
        val player: List<Player>
)