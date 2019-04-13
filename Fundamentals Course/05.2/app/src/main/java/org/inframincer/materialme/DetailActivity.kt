package org.inframincer.materialme

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

const val SPORT_TITLE = "sport title"
const val SPORT_SUB_TITLE = "sport sub title"
const val SPORT_IMAGE_RESOURCE = "sport image resource"

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val intent = intent
        val title = intent.getStringExtra(SPORT_TITLE)
        val info = intent.getStringExtra(SPORT_SUB_TITLE)
        val imageResourceId = intent.getIntExtra(SPORT_IMAGE_RESOURCE, 0)

        titleDetailTextView.text = title
        subTitleDetailTextView.text = info
        Glide.with(this@DetailActivity)
            .load(imageResourceId)
            .into(sportDetailImageView)
    }
}
