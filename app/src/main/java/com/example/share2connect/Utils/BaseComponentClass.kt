package com.example.share2connect.Utils

import com.ace1ofspades.recyclerview.items.Item
import com.ace1ofspades.recyclerview.viewHolders.ViewHolder
import com.example.share2connect.Models.BaseComponent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

abstract class BaseComponentClass : Item<ViewHolder, BaseComponent>() {
    fun <T> parse(src: Any?): T {
        return Gson().fromJson(
            Gson().toJson(src),
            object : TypeToken<T>() {}.type
        )
    }

}