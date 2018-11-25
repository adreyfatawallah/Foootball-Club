package com.example.adrey.footballclub.ui.team

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.adrey.footballclub.R
import com.example.adrey.footballclub.R.id.search
import com.example.adrey.footballclub.R.menu.menu_search
import com.example.adrey.footballclub.R.style.AppTheme_AppBarOverlay
import com.example.adrey.footballclub.R.style.AppTheme_PopupOverlay
import com.example.adrey.footballclub.R.string.app_name
import com.example.adrey.footballclub.api.ApiRepository
import com.example.adrey.footballclub.database.favoriteTeamDB
import com.example.adrey.footballclub.database.leaguesDB
import com.example.adrey.footballclub.model.FavoritesTeam
import com.example.adrey.footballclub.model.Leagues
import com.example.adrey.footballclub.model.team.Team
import com.example.adrey.footballclub.ui.detailteam.DetailTeamActivity
import com.example.adrey.footballclub.utils.Utils.gone
import com.example.adrey.footballclub.utils.Utils.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.coroutines.onQueryTextListener
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.themedAppBarLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.*

class TeamFragment : Fragment(), TeamView {

    companion object {
        private const val KEY_TYPE = "type"

        fun newInstance(type: String) : TeamFragment {
            val args = Bundle()
            args.putString(KEY_TYPE, type)
            val teamFragment = TeamFragment()
            teamFragment.arguments = args
            return teamFragment
        }
    }

    private var teams = mutableListOf<FavoritesTeam>()
    private var searchTeams = mutableListOf<FavoritesTeam>()
    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: TeamAdapter

    private lateinit var toolbar: Toolbar
    private lateinit var spinner: Spinner
    private lateinit var textView: TextView
    private lateinit var progress: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

    private var itemLeague = arrayListOf<String?>()
    private var league = ""
    private var type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            UI {
                coordinatorLayout {
                    themedAppBarLayout(AppTheme_AppBarOverlay) {
                        toolbar = toolbar {
                            lparams(width = matchParent)
                            popupTheme = AppTheme_PopupOverlay
                            title = getString(app_name)
                        }
                    }.lparams(width = matchParent)

                    verticalLayout {
                        spinner = spinner().lparams(width = matchParent) {
                            id = R.id.sp_team
                            margin = dip(5)
                        }

                        relativeLayout {
                            lparams(width = matchParent, height = matchParent)

                            textView = textView("List team empty")
                                    .lparams(width = wrapContent) {
                                        margin = dip(5)
                                        centerInParent()
                                    }

                            progress = progressBar().lparams { centerInParent() }

                            swipeRefreshLayout = swipeRefreshLayout {

                                relativeLayout {
                                    lparams(width = matchParent, height = wrapContent)

                                    recyclerView = recyclerView {
                                        id = R.id.rc_team
                                        lparams(width = matchParent, height = wrapContent)
                                        layoutManager = LinearLayoutManager(ctx)
                                    }

                                }
                            }
                        }
                    }.lparams(width = matchParent, height = matchParent) {
                        behavior = AppBarLayout.ScrollingViewBehavior()
                    }
                }
            }.view

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.inflateMenu(menu_search)

        val menu = toolbar.menu
        val searchView = menu.findItem(search).actionView as SearchView
        searchView.onQueryTextListener {
            onQueryTextChange {
                searchTeam(it ?: "")
                false
            }
            onQueryTextSubmit {
                searchTeam(it ?: "")
                false
            }
        }

        val params: AppBarLayout.LayoutParams = toolbar.layoutParams as AppBarLayout.LayoutParams
        params.scrollFlags = 0

        type = arguments?.getString(KEY_TYPE) ?: ""

        val apiRepository = ApiRepository()
        val gson = Gson()
        presenter = TeamPresenter(this, apiRepository, gson)

        context?.leaguesDB?.use {
            val result = select(Leagues.TABLE_LEAGUE)
            val league = result.parseList(classParser<Leagues>())

            league.forEach { itemLeague.add(it.league) }
            val spinAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, itemLeague)
            spinner.adapter = spinAdapter
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (type == "list") {
                    league = itemLeague[p2] ?: ""
                    presenter.getTeamList(league)
                }
            }
        }

        adapter = TeamAdapter(teams) {
            startActivity<DetailTeamActivity>("idTeam" to it.idTeam)
        }
        recyclerView.adapter = adapter

        swipeRefreshLayout.onRefresh {
            if (type == "list")
                presenter.getTeamList(league)
            else
                showFavorite()
        }

        if (type == "favorite") {
            toolbar.gone()
            spinner.gone()
            showFavorite()
        }
    }

    override fun showProgressTeam() {
        progress.visible()
        textView.gone()
        swipeRefreshLayout.gone()
    }

    override fun hideProgressTeam() {
        progress.gone()
    }

    override fun showTeam(team: List<Team>?) {
        swipeRefreshLayout.isRefreshing = false
        teams.clear()
        if (team != null) {
            team.forEach {
                teams.add(FavoritesTeam(0, it.idTeam, it.team, it.imgTeam))
            }
            adapter.notifyDataSetChanged()
            swipeRefreshLayout.visible()
        } else
            showNothing()
    }

    private fun showNothing() {
        textView.visible()
        swipeRefreshLayout.gone()
    }

    private fun searchTeam(string: String) {
        if (string.isNotEmpty()) {
            showSearch()
            presenter.teamSearch(string)
        } else {
            hideSearch()
            adapter.teams = teams
            adapter.notifyDataSetChanged()
        }
    }

    private fun showSearch() {
        spinner.gone()
    }

    private fun hideSearch() {
        spinner.visible()
        textView.gone()
        swipeRefreshLayout.visible()
    }

    override fun showSearch(team: List<Team>?) {
        searchTeams.clear()
        if (team != null) {
            team.forEach {
                searchTeams.add(FavoritesTeam(0, it.idTeam, it.team, it.imgTeam))
            }
            adapter.teams = searchTeams
            adapter.notifyDataSetChanged()
            swipeRefreshLayout.visible()
        } else
            showNothing()
    }

    private fun showFavorite(){
        context?.favoriteTeamDB?.use {
            swipeRefreshLayout.isRefreshing = false
            val result = select(FavoritesTeam.TABLE_FAVORITE_TEAM)
            val favorite = result.parseList(classParser<FavoritesTeam>())
            teams.clear()
            teams.addAll(favorite)
            adapter.notifyDataSetChanged()
        }

        hideProgressTeam()
        if (teams.isNotEmpty())
            textView.gone()
        else
            textView.visible()
    }
}