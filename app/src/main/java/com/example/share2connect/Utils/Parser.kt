package com.example.share2connect.Utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Parser {

    companion object {

        inline fun <reified T> parse(src: Any?) = Gson().
        fromJson<T>(
            Gson().toJson(src), object: TypeToken<T>() {}.type)

    }

}