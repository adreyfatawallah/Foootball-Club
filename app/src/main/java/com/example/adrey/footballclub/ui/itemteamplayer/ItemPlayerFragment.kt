package com.example.adrey.footballclub.ui.itemteamplayer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adrey.footballclub.api.ApiRepository
import com.example.adrey.footballclub.model.player.Player
import com.example.adrey.footballclub.ui.detailplayer.DetailPlayerActivity
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity

class ItemPlayerFragment : Fragment(), ItemPlayerView {

    companion object {
        fun newInstance(idTeam: String) : ItemPlayerFragment {
            val args = Bundle()
            args.putString("idTeam", idTeam)
            val fragment = ItemPlayerFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            UI {
                verticalLayout {
                    lparams(width = matchParent, height = wrapContent)

                    recyclerView = recyclerView {
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }
                }
            }.view

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val b = arguments
        val idTeam = b?.getString("idTeam") ?: ""

        val apiRepository = ApiRepository()
        val gson = Gson()
        val presenter = ItemPlayerPresenter(this, apiRepository, gson)
        presenter.getPlayer(idTeam)
    }

    override fun showPlayer(player: List<Player>) {
        val adapter = ItemPlayerAdapter(ctx, player) {
            startActivity<DetailPlayerActivity>("idPlayer" to it.idPlayer)
        }
        recyclerView.adapter = adapter
    }
}