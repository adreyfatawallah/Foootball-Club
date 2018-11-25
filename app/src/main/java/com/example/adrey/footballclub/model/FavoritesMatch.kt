package com.example.adrey.footballclub.model

data class FavoritesMatch(val id: Long?, val idEvent: String?, val idHome: String?, val idAway: String?,
                          val teamHome: String?, val teamAway: String?, val scoreHome: String?,
                          val scoreAway: String?, val date: String?, val time: String?) {

    companion object {
        const val TABLE_FAVORITE_MATCH = "TABLE_FAVORITE_MATCH"
        const val ID = "ID_"
        const val ID_EVENT = "ID_EVENT"
        const val ID_HOME = "ID_HOME"
        const val ID_AWAY = "ID_AWAY"
        const val TEAM_HOME = "TEAM_HOME"
        const val TEAM_AWAY = "TEAM_AWAY"
        const val SCORE_HOME = "SCORE_HOME"
        const val SCORE_AWAY = "SCORE_AWAY"
        const val DATE = "DATE"
        const val TIME = "TIME"
    }
}