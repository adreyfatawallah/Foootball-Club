package com.example.adrey.footballclub.ui.itemteamplayer

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.adrey.footballclub.R
import com.example.adrey.footballclub.R.id.im_player
import com.example.adrey.footballclub.R.id.tx_name_player
import com.example.adrey.footballclub.R.id.tx_position_player
import com.example.adrey.footballclub.R.color.black
import com.example.adrey.footballclub.model.player.Player
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class ItemPlayerAdapter(private val context: Context,
                        private val players: List<Player>,
                        private val listener: (Player) -> Unit) : RecyclerView.Adapter<ItemPlayerHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemPlayerHolder =
            ItemPlayerHolder(ItemPlayerUI().createView(AnkoContext.create(context, p0)))

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(p0: ItemPlayerHolder, p1: Int) {
        p0.bind(players[p1], listener)
    }
}

class ItemPlayerHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val image: ImageView = view.find(im_player)
    private val txName: TextView = view.find(tx_name_player)
    private val txPosition: TextView = view.find(tx_position_player)

    fun bind(player: Player, listener: (Player) -> Unit) {
        Picasso.get().load(player.image).into(image)
        txName.text = player.name
        txPosition.text = player.position
        itemView.onClick { listener(player) }
    }
}

class ItemPlayerUI : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            padding = dip(16)
            orientation = LinearLayout.HORIZONTAL

            imageView {
                id = im_player
                imageResource = R.drawable.ic_account_circle_24dp
            }.lparams {
                width = dip(60)
                height = dip(60)
            }

            textView {
                id = tx_name_player
                textSize = 15f
                textColor = ContextCompat.getColor(context, black)
            }.lparams(width = 0, height = matchParent) {
                leftMargin = dip(10)
                weight = 1F
            }.gravity = Gravity.CENTER_VERTICAL

            textView {
                id = tx_position_player
            }.lparams(width = wrapContent, height = matchParent).gravity = Gravity.CENTER_VERTICAL
        }
    }
}