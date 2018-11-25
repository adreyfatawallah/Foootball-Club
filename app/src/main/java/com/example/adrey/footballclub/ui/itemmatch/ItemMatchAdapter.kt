package com.example.adrey.footballclub.ui.itemmatch

import android.content.Context
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.adrey.footballclub.R.drawable.ic_notifications_active_24dp
import com.example.adrey.footballclub.R.id.*
import com.example.adrey.footballclub.utils.Utils.gone
import com.example.adrey.footballclub.model.FavoritesMatch
import com.example.adrey.footballclub.utils.Utils.formatDate
import com.example.adrey.footballclub.utils.Utils.formatTime
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick

class ItemMatchAdapter(private val context: Context,
                       private val matches: List<FavoritesMatch>,
                       private val type: String,
                       private val listener: (FavoritesMatch) -> Unit,
                       private val alert: (title: String, date: String, time: String) -> Unit) : RecyclerView.Adapter<ItemMatchHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) =
            ItemMatchHolder(ItemMacthUI().createView(AnkoContext.create(context, p0)))

    override fun getItemCount() = matches.size

    override fun onBindViewHolder(p0: ItemMatchHolder, p1: Int) {
        p0.bind(matches[p1], type, listener, alert)
    }
}

class ItemMatchHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val imAlert: ImageView = view.find(im_alert)
    private val txDate: TextView = view.find(tx_date)
    private val txHomeTeam: TextView = view.find(tx_home_team)
    private val txHomeScore: TextView = view.find(tx_home_score)
    private val txAwayTeam: TextView = view.find(tx_away_team)
    private val txAwayScore: TextView = view.find(tx_away_score)
    private val txTime: TextView = view.find(tx_time)

    fun bind(match: FavoritesMatch, type: String, listener: (FavoritesMatch) -> Unit,
             alert: (title: String, date: String, time: String) -> Unit) {

        txDate.text = if (match.date != null)
            formatDate(match.date)
        else "Date unknown"

        txHomeTeam.text = match.teamHome
        txHomeScore.text = match.scoreHome
        txAwayTeam.text = match.teamAway
        txAwayScore.text = match.scoreAway

        txTime.text = if (match.time != null)
            formatTime(match.time.split("+")[0])
        else "Time unknown"

        if (type == "favorite")
            imAlert.gone()

        itemView.onClick { listener(match) }
        imAlert.onClick { alert(
                "${match.teamHome} vs ${match.teamAway}",
                match.date ?: "",
                if (match.time != null) match.time.split("+")[0]
                else "")}
    }
}

class ItemMacthUI : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        cardView {
            id = item_match
            lparams(width = matchParent, height = wrapContent) {
                topMargin = dip(10)
                leftMargin = dip(10)
                rightMargin = dip(10)
                bottomMargin = dip(10)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                elevation = 0.5f
            }
            radius = 3f

            verticalLayout {
                lparams(width = matchParent, height = wrapContent) {
                    margin = dip(10)
                }

                relativeLayout {
                    lparams(width = matchParent)

                    textView {
                        id = tx_date
                    }.lparams(width = wrapContent, height = wrapContent) {
                        centerHorizontally()
                    }

                    imageView(ic_notifications_active_24dp) {
                        id = im_alert
                    }.lparams {
                        alignParentRight()
                    }
                }

                linearLayout {
                    lparams(width = matchParent, height = wrapContent) {
                        topMargin = dip(5)
                    }
                    orientation = LinearLayout.HORIZONTAL

                    verticalLayout {
                        lparams(width = 0, height = wrapContent) {
                            weight = 5f
                            rightMargin = dip(5)
                        }

                        textView {
                            id = tx_home_team
                            textSize = 15f
                            gravity = Gravity.CENTER
                        }.lparams(width = matchParent, height = wrapContent)

                        textView {
                            id = tx_home_score
                            textSize = 13f
                            gravity = Gravity.CENTER
                        }.lparams(width = matchParent, height = wrapContent)
                    }

                    textView("vs").lparams(width = wrapContent, height = wrapContent)

                    verticalLayout {
                        lparams(width = 0, height = wrapContent) {
                            weight = 5f
                            leftMargin = dip(5)
                        }

                        textView {
                            id = tx_away_team
                            textSize = 15f
                            gravity = Gravity.CENTER
                        }.lparams(width = matchParent, height = wrapContent)

                        textView {
                            id = tx_away_score
                            textSize = 13f
                            gravity = Gravity.CENTER
                        }.lparams(width = matchParent, height = wrapContent)
                    }
                }

                textView {
                    id = tx_time
                    gravity = Gravity.CENTER
                }.lparams(width = matchParent, height = wrapContent) {
                    topMargin = dip(5)
                }
            }
        }
    }
}