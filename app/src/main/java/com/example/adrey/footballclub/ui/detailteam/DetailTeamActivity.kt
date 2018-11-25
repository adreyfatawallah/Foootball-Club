package com.example.adrey.footballclub.ui.detailteam

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.adrey.footballclub.R
import com.example.adrey.footballclub.R.drawable.ic_star_24dp
import com.example.adrey.footballclub.R.drawable.ic_star_border_24dp
import com.example.adrey.footballclub.api.ApiRepository
import com.example.adrey.footballclub.database.favoriteTeamDB
import com.example.adrey.footballclub.model.FavoritesTeam
import com.example.adrey.footballclub.model.team.Team
import com.example.adrey.footballclub.ui.itemteamplayer.ItemPlayerFragment
import com.example.adrey.footballclub.ui.itemteamreview.ItemReviewFragment
import com.example.adrey.footballclub.ui.match.MatchAdapter
import com.example.adrey.footballclub.utils.Utils.visible
import com.example.adrey.footballclub.utils.Utils.gone
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class DetailTeamActivity : AppCompatActivity(), DetailTeamView {

    private lateinit var team: Team

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private var idTeam = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        setSupportActionBar(toolbar_detail_team)
        title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val b = intent.extras
        idTeam = b?.getString("idTeam") ?: ""

        val adapter = MatchAdapter(supportFragmentManager)
        adapter.addFragment(ItemReviewFragment.newInstance(idTeam), "Review")
        adapter.addFragment(ItemPlayerFragment.newInstance(idTeam), "Player")
        vp_team_detail.adapter = adapter
        tab_detail_team.setupWithViewPager(vp_team_detail)

        val apiRepository = ApiRepository()
        val gson = Gson()
        val presenter = DetailTeamPresenter(this, apiRepository, gson)
        presenter.getTeamDetail(idTeam)

        favoriteState()
    }

    override fun showProgress() {
        progress_detail_team.visible()
        appbar_detail_team.gone()
        vp_team_detail.gone()
    }

    override fun hideProgress() {
        progress_detail_team.gone()
        appbar_detail_team.visible()
        vp_team_detail.visible()
    }

    override fun showDetailTeam(teams: List<Team>) {
        team = teams[0]
        Picasso.get().load(teams[0].imgTeam).into(im_detail_team)
        tx_name_detail_team.text = teams[0].team
        tx_year_detail_team.text = teams[0].year
        tx_stadium_detail_team.text = teams[0].stadium
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
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
            R.id.favorite -> {
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
            favoriteTeamDB.use {
                insert(FavoritesTeam.TABLE_FAVORITE_TEAM,
                        FavoritesTeam.ID_TEAM to team.idTeam,
                        FavoritesTeam.NAME_TEAM to team.team,
                        FavoritesTeam.IMG_TEAM to team.imgTeam)
            }
            snackbar(vp_team_detail, "Added to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(vp_team_detail, e.localizedMessage).show()
        }
    }

    private fun removeFavorite() {
        try {
            favoriteTeamDB.use {
                delete(FavoritesTeam.TABLE_FAVORITE_TEAM,
                        "(${FavoritesTeam.ID_TEAM} = {id})",
                        "id" to idTeam)
            }
            snackbar(vp_team_detail, "Removed to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(vp_team_detail, e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_star_24dp)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_star_border_24dp)
    }

    private fun favoriteState() {
        favoriteTeamDB.use {
            val result = select(FavoritesTeam.TABLE_FAVORITE_TEAM)
                    .whereArgs("(${FavoritesTeam.ID_TEAM} = {id})",
                            "id" to idTeam)
            val favorite = result.parseList(classParser<FavoritesTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }
}