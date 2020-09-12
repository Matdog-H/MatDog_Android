package com.example.matdog.api

import android.content.Context
import java.util.*

object SharedPreferenceController {

    private val TOKEN = "token"

    fun setUserToken(context: Context, token: String) {
        val pref = context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(TOKEN, token)
        editor.apply()
    }

    fun getUserToken(context: Context): String {
        val pref = context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE)
        return pref.getString(TOKEN, "") ?: ""
    }

}