package com.example.adrey.footballclub.ui.team

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.adrey.footballclub.R.id.team_badge
import com.example.adrey.footballclub.R.id.team_name
import com.example.adrey.footballclub.model.FavoritesTeam
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class TeamAdapter(var teams: List<FavoritesTeam>, private val listener: (FavoritesTeam) -> Unit)
    : RecyclerView.Adapter<TeamHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TeamHolder =
            TeamHolder(TeamUI().createView(AnkoContext.create(p0.context, p0)))

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(p0: TeamHolder, p1: Int) {
        p0.bindTeam(teams[p1], listener)
    }
}

class TeamUI : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>): View =
            with(ui) {
                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    padding = dip(16)
                    orientation = LinearLayout.HORIZONTAL

                    imageView {
                        id = team_badge
                    }.lparams {
                        height = dip(50)
                        width = dip(50)
                    }

                    textView {
                        id = team_name
                        textSize = 16f
                    }.lparams {
                        margin = dip(15)
                    }
                }
            }
}

class TeamHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val teamBadge: ImageView = view.find(team_badge)
    private val teamName: TextView = view.find(team_name)

    fun bindTeam(team: FavoritesTeam, listener: (FavoritesTeam) -> Unit) {
        Picasso.get().load(team.image).into(teamBadge)
        teamName.text = team.name
        itemView.onClick { listener(team) }
    }
}
