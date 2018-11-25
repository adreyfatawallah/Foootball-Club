package com.example.adrey.footballclub.ui.detailmatch

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.adrey.footballclub.R
import com.example.adrey.footballclub.R.menu.menu_detail
import com.example.adrey.footballclub.R.drawable.ic_star_border_24dp
import com.example.adrey.footballclub.R.drawable.ic_star_24dp
import com.example.adrey.footballclub.R.id.favorite
import com.example.adrey.footballclub.api.ApiRepository
import com.example.adrey.footballclub.database.favoriteMatchDB
import com.example.adrey.footballclub.model.FavoritesMatch
import com.example.adrey.footballclub.model.match.Match
import com.example.adrey.footballclub.model.team.Team
import com.example.adrey.footballclub.utils.Utils.formatDate
import com.example.adrey.footballclub.utils.Utils.formatTime
import com.example.adrey.footballclub.utils.Utils.visible
import com.example.adrey.footballclub.utils.Utils.gone
import com.example.adrey.footballclub.utils.Utils.split
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.toast

class DetailMatchActivity : AppCompatActivity(), DetailMatchView {

    private lateinit var presenter: DetailMatchPresenter
    private lateinit var match: Match

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private var idEvent = ""
    private var idHome = ""
    private var idAway = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)

        setSupportActionBar(toolbar_detail)
        supportActionBar?.title = "Detail Match"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val b = intent.extras
        if (b != null) {
            idEvent = b.getString("idEvent") ?: ""
            idHome = b.getString("idHome") ?: ""
            idAway = b.getString("idAway") ?: ""

            favoriteState()

            val apiRepository = ApiRepository()
            val gson = Gson()
            presenter = DetailMatchPresenter(this, apiRepository, gson)

            presenter.getDetailMatch(idEvent, idHome, idAway)
        } else {
            toast("Failure to get detail match")
            finish()
        }
    }

    override fun showProgress() {
        progressBar.visible()
        scrollView.gone()
    }

    override fun hideProgress() {
        progressBar.gone()
        scrollView.visible()
    }

    override fun showDetailMatch(detailMatch: Match, teamHome: Team, teamAway: Team) {
        match = detailMatch

        Picasso.get().load(teamHome.imgStadium).into(im_stadium)
        tx_stadium.text = teamHome.stadium

        Picasso.get().load(teamHome.imgTeam).into(im_home_team)
        Picasso.get().load(teamAway.imgTeam).into(im_away_team)

        tx_home_team.text = detailMatch.homeTeam
        tx_away_team.text = detailMatch.awayTeam

        tx_home_score.text = detailMatch.homeScore
        tx_away_score.text = detailMatch.awayScore

        tx_date.text = if (detailMatch.date != null) formatDate(detailMatch.date) else "Date unknown"
        tx_time.text = if (detailMatch.time != null) formatTime(detailMatch.time.split("+")[0]) else "Time unknown"

        tx_home_goals.text = split(detailMatch.homeGoal)
        tx_away_goals.text = split(detailMatch.awayGoal)

        tx_home_keeper.text = split(detailMatch.homeGoalKeeper)
        tx_away_keeper.text = split(detailMatch.awayGoalKeeper)

        tx_home_defense.text = split(detailMatch.homeDefense)
        tx_away_defense.text = split(detailMatch.awayDefense)

        tx_home_midfield.text = split(detailMatch.homeMidfield)
        tx_away_midfield.text = split(detailMatch.awayMidfield)

        tx_home_forward.text = split(detailMatch.homeForward)
        tx_away_forward.text = split(detailMatch.awayForward)

        tx_home_subtitures.text = split(detailMatch.homeSubtitutes)
        tx_away_subtitutes.text = split(detailMatch.awaySubtitutes)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(menu_detail, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            favorite -> {
                if (isFavorite) removeFavorite() else addFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addFavorite() {
        try {
            favoriteMatchDB.use {
                insert(FavoritesMatch.TABLE_FAVORITE_MATCH,
                        FavoritesMatch.ID_EVENT to match.idEvent,
                        FavoritesMatch.ID_HOME to match.idHomeTeam,
                        FavoritesMatch.ID_AWAY to match.idAwayTeam,
                        FavoritesMatch.TEAM_HOME to match.homeTeam,
                        FavoritesMatch.TEAM_AWAY to match.awayTeam,
                        FavoritesMatch.SCORE_HOME to match.homeScore,
                        FavoritesMatch.SCORE_AWAY to match.awayScore,
                        FavoritesMatch.DATE to match.date,
                        FavoritesMatch.TIME to match.time)
            }
            snackbar(scrollView, "Added to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(scrollView, e.localizedMessage).show()
        }
    }

    private fun removeFavorite() {
        try {
            favoriteMatchDB.use {
                delete(FavoritesMatch.TABLE_FAVORITE_MATCH,
                        "(${FavoritesMatch.ID_EVENT} = {id})",
                        "id" to idEvent)
            }
            snackbar(scrollView, "Removed to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(scrollView, e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_star_24dp)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_star_border_24dp)
    }

    private fun favoriteState() {
        favoriteMatchDB.use {
            val result = select(FavoritesMatch.TABLE_FAVORITE_MATCH)
                    .whereArgs("(${FavoritesMatch.ID_EVENT} = {id})",
                            "id" to idEvent)
            val favorite = result.parseList(classParser<FavoritesMatch>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }
}