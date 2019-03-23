package org.inframincer.nativerecyclerview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager



class RecyclerViewFragment : Fragment() {

    private var recyclerViewItems = mutableListOf<Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        recyclerViewItems = (activity as MainActivity).getRecyclerViewItems()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false)
        val mRecyclerView = rootView.findViewById(R.id.recycler_view) as RecyclerView

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView.
        mRecyclerView.setHasFixedSize(true)

        // Specify a linear layout manager.
        val layoutManager = LinearLayoutManager(context)
        mRecyclerView.layoutManager = layoutManager

        // Specify an adapter.
        val adapter = RecyclerViewAdapter(activity!!, recyclerViewItems)
        mRecyclerView.adapter = adapter

        return rootView
    }


}
