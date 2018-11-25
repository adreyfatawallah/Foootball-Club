package com.example.adrey.footballclub.ui.itemteamreview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.adrey.footballclub.api.ApiRepository
import com.example.adrey.footballclub.model.team.Team
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.nestedScrollView

class ItemReviewFragment : Fragment(), ItemReviewView {

    companion object {
        fun newInstance(idTeam: String) : ItemReviewFragment {
            val args = Bundle()
            args.putString("idTeam", idTeam)
            val fragment = ItemReviewFragment()
            fragment.arguments = args;
            return fragment
        }
    }

    private lateinit var textView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            UI {
                nestedScrollView {
                    lparams(width = matchParent, height = wrapContent)

                    verticalLayout {
                        lparams(width = matchParent, height = wrapContent) {
                            margin = dip(10)
                        }

                        textView = textView()
                    }
                }
            }.view

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val b = arguments
        val idTeam = b?.getString("idTeam") ?: ""

        val apiRepository = ApiRepository()
        val gson = Gson()
        val presenter = ItemReviewPresenter(this, apiRepository, gson)
        presenter.getReview(idTeam)
    }

    override fun showReview(team: List<Team>) {
        textView.text = team[0].desc
    }
}