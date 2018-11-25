package com.example.adrey.footballclub.ui.detailplayer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.adrey.footballclub.R
import com.example.adrey.footballclub.R.layout.activity_detail_player
import com.example.adrey.footballclub.api.ApiRepository
import com.example.adrey.footballclub.model.player.Player
import com.example.adrey.footballclub.utils.Utils.visible
import com.example.adrey.footballclub.utils.Utils.gone
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_player.*

class DetailPlayerActivity : AppCompatActivity(), DetailPlayerView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_detail_player)

        setSupportActionBar(toolbar_detail_player)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val b = intent.extras
        val idPlayer = b?.getString("idPlayer") ?: ""

        val apiRepository = ApiRepository()
        val gson = Gson()
        val presenter = DetailPlayerPresenter(this, apiRepository, gson)
        presenter.getDetailPlayer(idPlayer)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showProgress() {
        progress_detail_player.visible()
        layout_detail_player.gone()
    }

    override fun hideProgress() {
        progress_detail_player.gone()
        layout_detail_player.visible()
    }

    override fun showDetailPlayer(player: List<Player>) {
        title = player[0].name
        Picasso.get().load(player[0].imgHeader).into(im_detail_player)
        tx_weight_detail_player.text = player[0].weight
        tx_height_detail_player.text = player[0].height
        tx_position_detail_player.text = player[0].position
        tx_desc_detail_player.text = player[0].desc
    }
}