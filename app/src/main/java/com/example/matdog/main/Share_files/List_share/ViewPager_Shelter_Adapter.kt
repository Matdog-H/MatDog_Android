package com.example.matdog.main.Share_files.List_share

import android.util.Log
import androidx.fragment.app.*

class ViewPager_Shelter_Adapter(fm : FragmentManager, forStatus: Int, search_data: String?): FragmentStatePagerAdapter(fm){
    // List_Activity에서 받아온 상태값
    private var data = forStatus
    private var search_data = search_data

    override fun getItem(position: Int): Fragment {
        Log.v("seardata값_뷰페이저_어댑터",search_data)
        return when (position) {
            0 -> {
                Fragment_Shelter_New(data, search_data)
            }
            else -> {return Fragment_Shelter_Age(data, search_data)
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