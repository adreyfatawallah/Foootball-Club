package com.example.adrey.footballclub.model

data class FavoritesTeam(val id: Long?, val idTeam: String?, val name: String?, val image: String?) {

    companion object {
        const val TABLE_FAVORITE_TEAM = "TABLE_FAVORITE_TEAM"
        const val ID = "ID_"
        const val ID_TEAM = "ID_TEAM"
        const val NAME_TEAM = "NAME_TEAM"
        const val IMG_TEAM = "IMG_TEAM"
    }
}