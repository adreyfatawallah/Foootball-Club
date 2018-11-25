package com.example.adrey.footballclub.ui.favorite

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adrey.footballclub.R.array.*
import com.example.adrey.footballclub.R.id.search
import com.example.adrey.footballclub.R.layout.fragment_match
import com.example.adrey.footballclub.R.menu.menu_search
import com.example.adrey.footballclub.R.string.app_name
import com.example.adrey.footballclub.ui.itemmatch.ItemMatchFragment
import com.example.adrey.footballclub.ui.match.MatchAdapter
import com.example.adrey.footballclub.ui.team.TeamFragment
import com.example.adrey.footballclub.utils.Utils.gone
import com.example.adrey.footballclub.utils.Utils.visible
import kotlinx.android.synthetic.main.fragment_match.*
import org.jetbrains.anko.support.v4.toast

class FavoriteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(fragment_match, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar_match.title = getString(app_name)

        val listTitle = resources.getStringArray(item_text_favorite)
        val adapter = MatchAdapter(childFragmentManager)
        adapter.addFragment(ItemMatchFragment.newInstance("", "favorite"), listTitle[0])
        adapter.addFragment(TeamFragment.newInstance("favorite"), listTitle[1])
        vp_match.adapter = adapter
        tab_match.setupWithViewPager(vp_match)

        hideSearch()
    }

    private fun hideSearch() {
        tab_match.visible()
        vp_match.visible()
        rl_match.gone()
    }
}