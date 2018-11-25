package com.example.adrey.footballclub.ui.home

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.adrey.footballclub.R
import com.example.adrey.footballclub.R.id.*
import com.example.adrey.footballclub.api.ApiRepository
import com.example.adrey.footballclub.database.leaguesDB
import com.example.adrey.footballclub.model.Leagues
import com.example.adrey.footballclub.model.league.League
import com.example.adrey.footballclub.ui.favorite.FavoriteFragment
import com.example.adrey.footballclub.ui.match.MatchFragment
import com.example.adrey.footballclub.ui.team.TeamFragment
import com.example.adrey.footballclub.utils.Utils.visible
import com.example.adrey.footballclub.utils.Utils.gone
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.design.snackbar

class HomeActivity : AppCompatActivity(), HomeView {

    private lateinit var presenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val gson = Gson()
        val apiRepository = ApiRepository()
        presenter = HomePresenter(this, apiRepository, gson)

        bottomNavigationHome.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                match -> loadFragment(savedInstanceState, 0)
                team -> loadFragment(savedInstanceState, 1)
                favorite -> loadFragment(savedInstanceState, 2)
            }
            true
        }

        cekDatabase()
    }

    private fun cekDatabase() {
        try {
            leaguesDB.use {
                val result = select(Leagues.TABLE_LEAGUE)
                val league = result.parseList(classParser<Leagues>())

                if (league.isEmpty()) {
                    presenter.getLeagueList()
                } else {
                    hideProgress()
                    bottomNavigationHome.selectedItemId = match
                }
            }
        } catch (e: SQLiteConstraintException) {
            snackbar(layoutHome, e.localizedMessage).show()
        }
    }

    private fun loadFragment(savedInstanceState: Bundle?, int: Int) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.frameHome,
                            when (int) {
                                0 -> MatchFragment()
                                1 -> TeamFragment.newInstance("list")
                                else -> FavoriteFragment()
                            })
                    .commit()
        }
    }

    override fun showProgress() {
        progressHome.visible()
        layoutHome.gone()
    }

    override fun hideProgress() {
        progressHome.gone()
        layoutHome.visible()
    }

    override fun saveLeague(leagues: List<League>) {
        try {
            leaguesDB.use {
                leagues.forEach {
                    if (it.typeLeague?.toLowerCase() == "soccer") {
                        insert(Leagues.TABLE_LEAGUE,
                                Leagues.ID_LEAGUE to it.idLeague,
                                Leagues.LEAGUE to it.league)
                    }
                }
            }
            bottomNavigationHome.selectedItemId = match
        } catch (e: SQLiteConstraintException) {
            snackbar(layoutHome, e.localizedMessage).show()
        }
    }
}