package com.example.adrey.footballclub.ui.itemmatch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.adrey.footballclub.R
import com.example.adrey.footballclub.api.ApiRepository
import com.example.adrey.footballclub.database.favoriteMatchDB
import com.example.adrey.footballclub.database.leaguesDB
import com.example.adrey.footballclub.model.FavoritesMatch
import com.example.adrey.footballclub.model.Leagues
import com.example.adrey.footballclub.model.match.Match
import com.example.adrey.footballclub.ui.detailmatch.DetailMatchActivity
import com.example.adrey.footballclub.utils.Utils
import com.example.adrey.footballclub.utils.Utils.addReminder
import com.example.adrey.footballclub.utils.Utils.visible
import com.example.adrey.footballclub.utils.Utils.gone
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.*

class ItemMatchFragment : Fragment(), ItemMatchView {

    companion object {
        private const val KEY_URL = "url"
        private const val KEY_TYPE = "type"

        fun newInstance(url: String, type: String): ItemMatchFragment {
            val matchFragment = ItemMatchFragment()
            val args = Bundle()
            args.putString(KEY_URL, url)
            args.putString(KEY_TYPE, type)
            matchFragment.arguments = args
            return matchFragment
        }
    }

    private lateinit var textView: TextView
    private lateinit var spinner: Spinner
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var progress: ProgressBar

    private var matches: MutableList<FavoritesMatch> = mutableListOf()
    private lateinit var presenter: ItemMatchPresenter
    private lateinit var adapter: ItemMatchAdapter

    private var itemLeague = arrayListOf<String?>()
    private var itemIdLeague = arrayListOf<String?>()
    private var idLeague = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            UI {
                verticalLayout {
                    id = R.id.layout_match
                    lparams(width = matchParent, height = wrapContent)

                    spinner = spinner().lparams(width = matchParent, height = wrapContent) {
                        id = R.id.sp_league_match
                        margin = dip(5)
                    }

                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        textView = textView("List match empty")
                                .lparams(width = wrapContent, height = wrapContent) {
                                    margin = dip(5)
                                    centerInParent()
                                }

                        progress = progressBar().lparams { centerInParent() }

                        swipeRefreshLayout = swipeRefreshLayout {
                            id = R.id.refresh

                            relativeLayout {
                                lparams(width = matchParent, height = wrapContent)

                                recyclerView = recyclerView {
                                    id = R.id.listMatch
                                    lparams(width = matchParent, height = wrapContent)
                                    layoutManager = LinearLayoutManager(ctx)
                                }

                            }
                        }
                    }
                }
            }.view

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val b = arguments
        val url = b?.getString(KEY_URL, "") ?: ""
        val type = b?.getString(KEY_TYPE, "") ?: ""

        val apiRepository = ApiRepository()
        val gson = Gson()
        presenter = ItemMatchPresenter(this, apiRepository, gson)

        context?.leaguesDB?.use {
            val result = select(Leagues.TABLE_LEAGUE)
            val league = result.parseList(classParser<Leagues>())

            league.forEach {
                itemLeague.add(it.league)
                itemIdLeague.add(it.idLeague)
            }
            val spinAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, itemLeague)
            spinner.adapter = spinAdapter
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (url.isNotEmpty()) {
                    idLeague = itemIdLeague[p2] ?: ""
                    presenter.getMatchList(url, idLeague)
                }
            }
        }

        adapter = ItemMatchAdapter(ctx, matches, type, {
            startActivity<DetailMatchActivity>("idEvent" to it.idEvent,
                    "idHome" to it.idHome,
                    "idAway" to it.idAway)
        }, { title, date, time ->
            addReminder(ctx, title, date, time)
        })
        recyclerView.adapter = adapter

        if (url.isEmpty()) {
            spinner.visibility = View.GONE
            showFavorite()
        }

        swipeRefreshLayout.onRefresh {
            if (url.isNotEmpty())
                presenter.getMatchList(url, idLeague)
            else
                showFavorite()
        }
    }

    override fun showProgress() {
        progress.visible()
        textView.gone()
        swipeRefreshLayout.gone()
    }

    override fun hideProgress() {
        progress.gone()
        swipeRefreshLayout.visible()
    }

    override fun showMatch(match: List<Match>?) {
        swipeRefreshLayout.isRefreshing = false
        this.matches.clear()
        if (match != null) {
            textView.gone()
            swipeRefreshLayout.visible()

            match.forEach {
                this.matches.add(FavoritesMatch(0, it.idEvent, it.idHomeTeam, it.idAwayTeam,
                        it.homeTeam, it.awayTeam, it.homeScore, it.awayScore, it.date, it.time))
            }
            adapter.notifyDataSetChanged()
        } else
            textView.visible()
    }

    private fun showFavorite(){
        context?.favoriteMatchDB?.use {
            swipeRefreshLayout.isRefreshing = false
            val result = select(FavoritesMatch.TABLE_FAVORITE_MATCH)
            val favorite = result.parseList(classParser<FavoritesMatch>())
            matches.clear()
            matches.addAll(favorite)
            adapter.notifyDataSetChanged()
        }

        hideProgress()
        if (matches.isNotEmpty())
            textView.gone()
        else
            textView.visible()
    }
}