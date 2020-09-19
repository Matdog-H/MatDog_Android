package com.example.matdog.main.Share_files.List_share

import androidx.fragment.app.*

class ViewPager_Shelter_Adapter(fm : FragmentManager, forStatus: Int): FragmentStatePagerAdapter(fm){
    // List_Activity에서 받아온 상태값
    private var data = forStatus

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                Fragment_Shelter_New(data)
            }
            else -> {return Fragment_Shelter_Age(data)
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