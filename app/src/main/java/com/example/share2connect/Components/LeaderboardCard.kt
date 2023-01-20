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
import com.example.share2connect.retrofit.SessionManager
import com.google.firebase.storage.FirebaseStorage

class LeaderboardCard(var posS:String,var nameS:String,var depS:String,var pointS:String,var imageUrl:String): Item<ViewHolder, BaseComponent>(){

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
try {

    val imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl)
    imageRef.getBytes(10 * 1024 * 1024).addOnSuccessListener {
        val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
        image.setImageBitmap(bitmap)
    }.addOnFailureListener {
        // Handle any errors
    }
}
catch (e:Exception){}
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



