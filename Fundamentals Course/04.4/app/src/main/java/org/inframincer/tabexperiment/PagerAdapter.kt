package org.inframincer.tabexperiment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class PagerAdapter(fm: FragmentManager?, val numberOfTabs: Int) : FragmentStatePagerAdapter(fm) {

    override fun getItem(p0: Int): Fragment =
        when (p0) {
            0 -> TabFragment1()
            1 -> TabFragment2()
            2 -> TabFragment3()
            else -> BlankFragment()
        }

    override fun getCount(): Int {
        return numberOfTabs
    }
}
