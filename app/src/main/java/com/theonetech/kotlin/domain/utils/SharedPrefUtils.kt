package com.theonetech.kotlin.domain.utils

import android.content.Context
/*
 Created by Amit on 30,July,2020
*/
object SharedPrefUtils
{
    //method to store string value
    fun setStr(context: Context,key:String,value:String)
    {
        val prefs=context.getSharedPreferences(Utils.LOGIN_PREF,0)
        val editor=prefs.edit()
        editor.putString(key,value)
        editor.apply()
    }
    //method to get string value
    fun getStr(context: Context,key:String):String?
    {
        val prefs=context.getSharedPreferences(Utils.LOGIN_PREF,0)
        return prefs.getString(key,"")

    }
    //method to store boolean value
    fun setBool(context: Context,key:String,value:Boolean)
    {
        val prefs=context.getSharedPreferences(Utils.LOGIN_PREF,0)
        val editor=prefs.edit()
        editor.putBoolean(key,value)
        editor.apply()
    }
    //method to get boolean value
    fun getBool(context: Context,key:String):Boolean?
    {
        val prefs=context.getSharedPreferences(Utils.LOGIN_PREF,0)
        return prefs.getBoolean(key,false)

    }
}