package com.example.adrey.footballclub.model.player

import com.google.gson.annotations.SerializedName

class Player(
        @SerializedName("idPlayer")
        val idPlayer: String? = null,

        @SerializedName("strPlayer")
        val name: String? = null,

        @SerializedName("strDescriptionEN")
        val desc: String? = null,

        @SerializedName("strPosition")
        val position: String? = null,

        @SerializedName("strCutout")
        val image: String? = null,

        @SerializedName("strFanart1")
        val imgHeader: String? = null,

        @SerializedName("strHeight")
        val height: String? = null,

        @SerializedName("strWeight")
        val weight: String? = null
)