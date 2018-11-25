package com.example.adrey.footballclub.ui.match

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import com.example.adrey.footballclub.R.array.item_text_match
import com.example.adrey.footballclub.R.array.item_url_match
import com.example.adrey.footballclub.R.id.search
import com.example.adrey.footballclub.R.layout.fragment_match
import com.example.adrey.footballclub.R.menu.menu_search
import com.example.adrey.footballclub.R.string.app_name
import com.example.adrey.footballclub.api.ApiRepository
import com.example.adrey.footballclub.model.FavoritesMatch
import com.example.adrey.footballclub.model.match.Match
import com.example.adrey.footballclub.ui.detailmatch.DetailMatchActivity
import com.example.adrey.footballclub.ui.itemmatch.ItemMatchAdapter
import com.example.adrey.footballclub.ui.itemmatch.ItemMatchFragment
import com.example.adrey.footballclub.utils.Utils.addReminder
import com.example.adrey.footballclub.utils.Utils.gone
import com.example.adrey.footballclub.utils.Utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_match.*
import org.jetbrains.anko.appcompat.v7.coroutines.onQueryTextListener
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity

class MatchFragment : Fragment(), MatchView {

    private val matchs = mutableListOf<FavoritesMatch>()
    private lateinit var adapter: ItemMatchAdapter
    private lateinit var presenter: MatchPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(fragment_match, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar_match.title = getString(app_name)
        toolbar_match.inflateMenu(menu_search)
        val menu = toolbar_match.menu
        val searchView = menu.findItem(search).actionView as SearchView
        searchView.onQueryTextListener {
            onQueryTextChange {
                searchEvent(it ?: "")
                false
            }
            onQueryTextSubmit {
                searchEvent(it ?: "")
                false
            }
        }

        val apiRepository = ApiRepository()
        val gson = Gson()
        presenter = MatchPresenter(this, apiRepository, gson)
        adapter = ItemMatchAdapter(ctx, matchs, "", {
            startActivity<DetailMatchActivity>("idEvent" to it.idEvent,
                    "idHome" to it.idHome,
                    "idAway" to it.idAway)
        }, { title, date, time -> addReminder(ctx, title, date, time) })
        val layoutManager = LinearLayoutManager(ctx)
        rc_match.layoutManager = layoutManager
        rc_match.adapter = adapter

        val listTitle = resources.getStringArray(item_text_match)
        val listUrl = resources.getStringArray(item_url_match)
        val adapter = MatchAdapter(childFragmentManager)
        listUrl.forEachIndexed { index, _ ->
            adapter.addFragment(ItemMatchFragment.newInstance(listUrl[index], ""), listTitle[index])
        }
        vp_match.adapter = adapter
        tab_match.setupWithViewPager(vp_match)

        hideSearch()
    }

    private fun searchEvent(string: String) {
        if (string.isNotEmpty()) {
            showSearch()
            presenter.eventSearch(string)
        } else
            hideSearch()
    }

    private fun showSearch() {
        tab_match.gone()
        vp_match.gone()
        rl_match.visible()
    }

    private fun hideSearch() {
        tab_match.visible()
        vp_match.visible()
        rl_match.gone()
    }

    override fun showProgress() {
        progress_match.visible()
        tx_nothing_match.gone()
        rc_match.gone()
    }

    override fun hideProgress() {
        progress_match.gone()
        rc_match.visible()
    }

    override fun showSearchMatch(match: List<Match>?) {
        matchs.clear()
        if (match != null) {
            match.forEach {
                matchs.add(FavoritesMatch(0, it.idEvent, it.idHomeTeam, it.idAwayTeam,
                        it.homeTeam, it.awayTeam, it.homeScore, it.awayScore, it.date, it.time))
            }
            adapter.notifyDataSetChanged()
        } else
            tx_nothing_match.visible()
    }
}
