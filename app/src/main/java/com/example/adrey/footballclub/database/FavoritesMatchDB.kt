package com.example.adrey.footballclub.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.adrey.footballclub.model.FavoritesMatch
import org.jetbrains.anko.db.*

class FavoriteMatchDB(context: Context) : ManagedSQLiteOpenHelper(context, "FavoriteMatch.db", null, 1) {

    companion object {
        private var instance: FavoriteMatchDB? = null

        @Synchronized
        fun getInstance(ctx: Context): FavoriteMatchDB {
            if (instance == null)
                instance = FavoriteMatchDB(ctx.applicationContext)
            return instance as FavoriteMatchDB
        }
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.createTable(FavoritesMatch.TABLE_FAVORITE_MATCH, true,
                FavoritesMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoritesMatch.ID_EVENT to TEXT + UNIQUE,
                FavoritesMatch.ID_HOME to TEXT,
                FavoritesMatch.ID_AWAY to TEXT,
                FavoritesMatch.TEAM_HOME to TEXT,
                FavoritesMatch.TEAM_AWAY to TEXT,
                FavoritesMatch.SCORE_HOME to TEXT,
                FavoritesMatch.SCORE_AWAY to TEXT,
                FavoritesMatch.DATE to TEXT,
                FavoritesMatch.TIME to TEXT
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.dropTable(FavoritesMatch.TABLE_FAVORITE_MATCH, true)
    }
}

val Context.favoriteMatchDB: FavoriteMatchDB
    get() = FavoriteMatchDB.getInstance(applicationContext)