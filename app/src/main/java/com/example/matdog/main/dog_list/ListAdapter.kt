package com.example.matdog.main.dog_list

import android.hardware.Camera
import androidx.fragment.app.*
import com.example.matdog.R

class ListAdapter(fm : FragmentManager): FragmentStatePagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                fragment_new()}
            else -> {return fragment_age()}
        }

    }
    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> {"최신순" }
            else -> {return "나이순"}
        }

    }
}