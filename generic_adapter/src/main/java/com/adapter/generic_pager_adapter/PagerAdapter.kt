package com.adapter.generic_pager_adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.util.*
import kotlin.collections.ArrayList


/**
 * The PagerAdapter class
 */
class PagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var fragments: MutableList<Fragment> = ArrayList()
    var titles: MutableList<String> = ArrayList()

    /**
     * Add a title for the ViewPager tab
     * @param title - Tab title
     */
    fun addTitle(title: String) {
        titles.add(title)
    }

    /**
     * Add a Fragment to the ViewPager
     * @param fragment - Instance of the fragment
     */
    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }

    /**
     * Add a Fragment to the ViewPager
     * @param fragment - Instance of the Fragment
     * @param title - Title of the Tab
     */
    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        titles.add(title.toUpperCase(Locale.getDefault()))
    }

    /**
     * Add several Fragments
     * @param frag - Array of fragments
     */
    fun addAllFragments(vararg frag: Fragment) {
        fragments.addAll(frag)
    }

    /**
     * Add several titles
     * @param title - Array of titles
     */
    fun addAllTitles(vararg title: String) {
        titles.addAll(title)
    }

    /**
     * Remove Fragment using index
     * @param index - Index of the Fragment
     */
    fun removeFragment(index: Int) {
        fragments.removeAt(index)
        titles.removeAt(index)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }
}