package com.example.share2connect.retrofit

import android.content.Context
import android.content.SharedPreferences
import com.example.share2connect.Models.UserModel
import com.example.share2connect.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class SessionManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "token"
        const val USER_OBJECT = "userObject"
        const val USERNAME = "userName"
        const val USERMAIL = "userMail"
    }


fun clearAll(){
    var st=getUserMail()
    prefs.edit().clear().apply()

    if (st != null) {
        saveUserMail(st)
    }

}
    fun clearAllPrefs(){

    prefs.edit().clear().apply()


}
    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Function to save username
     */
    fun saveUsername(username: String) {
        val editor = prefs.edit()
        editor.putString(USERNAME, username)
        editor.apply()
    }
    /**
     * Function to save username
     */
    fun saveUserMail(usermail: String) {
        val editor = prefs.edit()
        editor.putString(USERMAIL, usermail)
        editor.apply()
    }

    /**
     * Function to save username
     */
    fun saveUserObject(user: UserModel) {
        val gson = Gson()
        val json = gson.toJson(user)
        val editor = prefs.edit()
        editor.putString(USER_OBJECT, json)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }


    /**
     * Function to get username
     */
    fun getUsername(): String? {
        return prefs.getString(USERNAME, null)
    }
    /**
     * Function to get user mail
     */
    fun getUserMail(): String? {
        return prefs.getString(USERMAIL, null)
    }

    /**
     * Function to get user object
     */

    fun getUserObject(): UserModel? {
        val gson = Gson()
        val type: Type = object : TypeToken<UserModel?>() {}.type

        return gson.fromJson(prefs.getString(USER_OBJECT, null), type)

    }

}