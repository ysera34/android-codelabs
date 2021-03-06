package org.inframincer.nativerecyclerview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class LoadingScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loading_screen, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoadingScreenFragment()
    }
}
