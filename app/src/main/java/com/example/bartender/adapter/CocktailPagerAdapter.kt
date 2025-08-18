package com.example.bartender.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bartender.fragment.CocktailListFragment

class CocktailPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CocktailListFragment.newInstance(CocktailListFragment.TYPE_ALL)
            1 -> CocktailListFragment.newInstance(CocktailListFragment.TYPE_POPULAR)
            2 -> CocktailListFragment.newInstance(CocktailListFragment.TYPE_FAVORITES)
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}