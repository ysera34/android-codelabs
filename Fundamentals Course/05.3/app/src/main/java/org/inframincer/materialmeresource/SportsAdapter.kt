package org.inframincer.materialmeresource

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item.view.*
import org.jetbrains.anko.startActivity

class SportsAdapter(
    private val context: Context,
    var sports: MutableList<Sport>
) : RecyclerView.Adapter<SportsAdapter.SportViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SportViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, p0, false)
        return SportViewHolder(view)
    }

    override fun getItemCount() = sports.size

    override fun onBindViewHolder(p0: SportViewHolder, p1: Int) {
        val sport = sports[p1]
        p0.bindSport(sport)
    }

    inner class SportViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private val titleTextView = view.titleTextView
        private val subTitleTextView = view.subTitleTextView
        private val sportImageView = view.sportImageView
        lateinit var sport: Sport

        init {
            view.setOnClickListener(this)
        }

        fun bindSport(sport: Sport) {
            this.sport = sport
            titleTextView.text = sport.title
            subTitleTextView.text = sport.info
            Glide.with(context)
                .load(sport.imageResourceId)
                .into(sportImageView)
        }

        override fun onClick(v: View?) {
            context.startActivity<DetailActivity>(
                SPORT_TITLE to this.sport.title,
                SPORT_SUB_TITLE to this.sport.info,
                SPORT_IMAGE_RESOURCE to this.sport.imageResourceId
            )
        }
    }
}
