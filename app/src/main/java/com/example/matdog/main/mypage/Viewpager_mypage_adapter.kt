package com.example.matdog.main.mypage


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.ListFragment
import androidx.viewpager.widget.PagerAdapter

class Viewpager_mypage_adapter(fm : FragmentManager): FragmentStatePagerAdapter(fm){
        // MyPageActivity에서 받아온 상태값
        //private var data = Status
    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> {
               fragment_my()
            }
            else -> {return fragment_like()
            }
        }

    }
    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "내 공고"
            else -> {return "찜"}
        }

    }


}