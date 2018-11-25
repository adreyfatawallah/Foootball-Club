package com.example.adrey.footballclub.model

data class Leagues(val id: Long?, val idLeague: String?, val league: String?) {

    companion object {
        const val TABLE_LEAGUE = "TABLE_LEAGUE"
        const val ID = "ID_"
        const val ID_LEAGUE = "ID_LEAGUE"
        const val LEAGUE = "LEAGUE"
    }
}