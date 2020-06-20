package com.example.matdog.main.dog_list

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import com.example.matdog.R
import com.example.matdog.main.dog_lost.DetailActivity
import com.example.matdog.main.dog_camera.CameraActivity
import com.example.matdog.main.dog_lost.WriteActivity
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_sign_up.*


class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        var count=1 //버튼 클릭 횟수

        val ic_back: ImageButton = findViewById(R.id.ic_back)
        ic_back.setOnClickListener{
            finish()
        }

        btn_search.setOnClickListener {
            val search_edt = EditText(applicationContext)
            val p = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            if(count>1 && count%2==0){ //버튼을 두번 클릭 했을 시
                //검색 기능 추가

                searchview.removeAllViews();
                count++

            } else{ //버튼을 눌렀을 때
                search_edt.layoutParams = p
                search_edt.setHint("검색어를 입력하세요.")
                search_edt.setPadding(50, 0, 50, 0)
                search_edt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
//                search_edt.background(txt_box)
                //search_edt.setPadding(5)
                search_edt.setBackgroundDrawable(getResources().getDrawable(R.drawable.txt_box))

                searchview.addView(search_edt)
                count++
            }

        }

        // 공고 등록 플로팅 버튼 클릭이벤트
        btn_write.setOnClickListener{
            val intent = Intent(this@ListActivity, WriteActivity::class.java)
            startActivity(intent)
        }

        val fragmentAdapter = ListAdapter(supportFragmentManager)
        list_viewPager.adapter = fragmentAdapter

        list_tablayout.setupWithViewPager(list_viewPager)


        imsibutton.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }

//        //이미지 버튼 클릭시
//        btn_tab_new.setOnClickListener {
//            //이미지 변경
//            btn_tab_age.setBackgroundResource(R.drawable.tap_age_unselect)
//            btn_tab_new.setBackgroundResource(R.drawable.tap_new_select)
//
//
//            //화면 변경
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_view, fragment_new())
//                .commit()
//        }
//
//        btn_tab_age.setOnClickListener {
//            //이미지 변경
//            btn_tab_new.setBackgroundResource(R.drawable.tap_new_unselect)
//            btn_tab_age.setBackgroundResource(R.drawable.tap_age_select)
//
//
//            //화면 변경
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_view, fragment_age())
//                .commit()
//        }

    }





}
