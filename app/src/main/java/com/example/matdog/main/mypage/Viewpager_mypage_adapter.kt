package com.example.matdog.main.mypage


import androidx.fragment.app.*

class Viewpager_mypage_adapter(fm : FragmentManager): FragmentStatePagerAdapter(fm){
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