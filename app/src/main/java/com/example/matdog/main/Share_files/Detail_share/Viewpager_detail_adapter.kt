package com.example.matdog.main.Share_files.Detail_share

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.matdog.R

/** @author Aidan Follestad (afollestad) */
class Viewpager_detail_adapter(private val context : Context) : PagerAdapter() {

    private var layoutInflater : LayoutInflater? = null
    val Image = arrayOf(
        R.drawable.taepoong3,
        R.drawable.taepoong2,
        R.drawable.taepoong
    )


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view ===  `object`
    }

    override fun getCount(): Int {
        return Image.size
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = layoutInflater!!.inflate(R.layout.detail_photo_item, null)
        val image = v.findViewById<View>(R.id.imageView) as ImageView

        image.setImageResource(Image[position])
        val vp = container as ViewPager
        vp.addView(v , 0)


        return v

    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val v = `object` as View
        vp.removeView(v)
    }
}