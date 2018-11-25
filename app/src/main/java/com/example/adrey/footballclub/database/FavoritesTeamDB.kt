package com.example.adrey.footballclub.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.adrey.footballclub.model.FavoritesTeam
import org.jetbrains.anko.db.*

class FavoritesTeamDB(context: Context) : ManagedSQLiteOpenHelper(context, "FavoriteTeam.db", null, 1) {

    companion object {
        private var instance: FavoritesTeamDB? = null

        @Synchronized
        fun getInstance(ctx: Context): FavoritesTeamDB {
            if (instance == null)
                instance = FavoritesTeamDB(ctx.applicationContext)
            return instance as FavoritesTeamDB
        }
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.createTable(FavoritesTeam.TABLE_FAVORITE_TEAM, true,
                FavoritesTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoritesTeam.ID_TEAM to TEXT + UNIQUE,
                FavoritesTeam.NAME_TEAM to TEXT,
                FavoritesTeam.IMG_TEAM to TEXT
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.dropTable(FavoritesTeam.TABLE_FAVORITE_TEAM, true)
    }
}

val Context.favoriteTeamDB: FavoritesTeamDB
    get() = FavoritesTeamDB.getInstance(applicationContext)