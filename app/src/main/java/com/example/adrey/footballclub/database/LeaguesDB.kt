package com.example.adrey.footballclub.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.adrey.footballclub.model.Leagues
import org.jetbrains.anko.db.*

class LeagueDB(context: Context) : ManagedSQLiteOpenHelper(context, "League.db", null, 1) {

    companion object {
        private var instance: LeagueDB? = null

        fun getInstance(ctx: Context): LeagueDB {
            if (instance == null)
                instance = LeagueDB(ctx.applicationContext)
            return instance as LeagueDB
        }
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.createTable(Leagues.TABLE_LEAGUE, true,
                Leagues.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Leagues.ID_LEAGUE to TEXT + UNIQUE,
                Leagues.LEAGUE to TEXT
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.dropTable(Leagues.TABLE_LEAGUE, true)
    }
}

val Context.leaguesDB: LeagueDB
    get() = LeagueDB.getInstance(applicationContext)