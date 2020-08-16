package com.example.matdog.main.Share_files.List_share

import androidx.fragment.app.*

class ViewPager_Shelter_Adapter(fm : FragmentManager): FragmentStatePagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                Fragment_Shelter_New()
            }
            else -> {return Fragment_Shelter_Age()
            }
        }

    }
    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> {"등록일순" }
            else -> {return "나이순"}
        }

    }
}