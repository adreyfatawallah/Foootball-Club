package com.example.adrey.footballclub.model.match

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Match(
        @SerializedName("idEvent")
        val idEvent: String? = null,

        @SerializedName("strHomeTeam")
        val homeTeam: String? = null,

        @SerializedName("strAwayTeam")
        val awayTeam: String? = null,

        @SerializedName("intHomeScore")
        val homeScore: String? = null,

        @SerializedName("intAwayScore")
        val awayScore: String? = null,

        @SerializedName("strHomeGoalDetails")
        val homeGoal: String? = null,

        @SerializedName("strHomeLineupGoalkeeper")
        val homeGoalKeeper: String? = null,

        @SerializedName("strHomeLineupDefense")
        val homeDefense: String? = null,

        @SerializedName("strHomeLineupMidfield")
        val homeMidfield: String? = null,

        @SerializedName("strHomeLineupForward")
        val homeForward: String? = null,

        @SerializedName("strHomeLineupSubstitutes")
        val homeSubtitutes: String? = null,

        @SerializedName("strHomeFormation")
        val homeFormation: String? = null,

        @SerializedName("strAwayGoalDetails")
        val awayGoal: String? = null,

        @SerializedName("strAwayLineupGoalkeeper")
        val awayGoalKeeper: String? = null,

        @SerializedName("strAwayLineupDefense")
        val awayDefense: String? = null,

        @SerializedName("strAwayLineupMidfield")
        val awayMidfield: String? = null,

        @SerializedName("strAwayLineupForward")
        val awayForward: String? = null,

        @SerializedName("strAwayLineupSubstitutes")
        val awaySubtitutes: String? = null,

        @SerializedName("strAwayFormation")
        val awayFormation: String? = null,

        @SerializedName("intHomeShots")
        val homeShot: String? = null,

        @SerializedName("intAwayShots")
        val awayShot: String? = null,

        @SerializedName("strDate")
        val date: String? = null,

        @SerializedName("strTime")
        val time: String? = null,

        @SerializedName("idHomeTeam")
        val idHomeTeam: String? = null,

        @SerializedName("idAwayTeam")
        val idAwayTeam: String? = null
) : Parcelable