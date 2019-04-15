package org.inframincer.materialmeresource

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridColumnCount = resources.getInteger(R.integer.grid_column_count)
        val sports = initializeData()
        val sportsAdapter = SportsAdapter(this@MainActivity, sports)
        recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, gridColumnCount)
            adapter = sportsAdapter
        }

        val swipeDirs = if (gridColumnCount > 1) {
            0
        } else {
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        }
        val helper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            swipeDirs
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition
                Collections.swap(sports, from, to)
                sportsAdapter.notifyItemMoved(from, to)
                return true
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                sports.removeAt(viewHolder.adapterPosition)
                sportsAdapter.notifyItemRemoved(viewHolder.adapterPosition)
            }
        })
        helper.attachToRecyclerView(recyclerView)

        refreshFloatingActionButton.setOnClickListener {
            sportsAdapter.sports = initializeData()
            sportsAdapter.notifyDataSetChanged()
        }
    }

    private fun initializeData(): MutableList<Sport> {
        val sportsTitles = resources.getStringArray(R.array.sports_titles)
        val sportsInfo = resources.getStringArray(R.array.sports_info)
        val sportsImageResources = resources.obtainTypedArray(R.array.sports_images)

        return mutableListOf<Sport>().apply {
            for (i in 0 until sportsTitles.size) {
                add(Sport(sportsTitles[i], sportsInfo[i], sportsImageResources.getResourceId(i, 0)))
            }
            sportsImageResources.recycle()
        }
    }
}
