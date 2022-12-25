package com.example.share2connect.Components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.widget.ImageView
import android.widget.TextView
import com.ace1ofspades.recyclerview.GroupAdapter
import com.ace1ofspades.recyclerview.items.Item
import com.ace1ofspades.recyclerview.viewHolders.ViewHolder
import com.example.share2connect.Models.BaseComponent
import com.example.share2connect.R
import com.example.share2connect.Utils.BaseComponentClass
import com.example.share2connect.Utils.Parser

class LeaderboardCard(var posS:String,var nameS:String,var depS:String,var pointS:String, var imageB:ByteArray,var id:String): Item<ViewHolder, BaseComponent>(){

    // ViewObjects
    lateinit var pos:TextView
    lateinit var name  :TextView
    lateinit var dep :TextView
    lateinit var image : ImageView
    lateinit var point:TextView
    ////
    var adapter = GroupAdapter<ViewHolder>()






    override fun configure() {
        super.configure()
        val bmp = BitmapFactory.decodeByteArray(imageB, 0, imageB.size)
        image.setImageBitmap(
            Bitmap.createScaledBitmap(
                bmp,
                image.width,
                image.height,
                false
            )
        )
            pos.text=posS
            name.text=nameS
            dep.text=depS
            point.text=pointS

    }


    override fun initialize() {
        super.initialize()
        pos = findViewById(R.id.pos)
        image = findViewById(R.id.image)
        name = findViewById(R.id.name)
        dep = findViewById(R.id.dep)
        point = findViewById(R.id.point)
    }

    override fun getLayout(): Int = layout

    companion object {
        const val layout = R.layout.leader_card

    }




}



