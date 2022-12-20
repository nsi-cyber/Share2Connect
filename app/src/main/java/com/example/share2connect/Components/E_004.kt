package com.example.share2connect.Components

import android.widget.Button
import android.widget.TextView
import com.ace1ofspades.recyclerview.GroupAdapter
import com.ace1ofspades.recyclerview.viewHolders.ViewHolder
import com.example.share2connect.R
import com.example.share2connect.Utils.BaseComponentClass
import com.example.share2connect.Utils.Parser
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class E_004 : BaseComponentClass() {

    // ViewObjects
    lateinit var title: TextView
    lateinit var desc: TextView
    lateinit var price: TextView
    lateinit var button: Button

    ////
    var adapter = GroupAdapter<ViewHolder>()
    private var itemModel: Model? = null


    override fun configure() {
        super.configure()
        itemModel = Parser.parse<Model>(model?.data)
        with(itemModel) {
            title.text = itemModel?.title
            desc.text = itemModel?.desc
            price.text = "Fiyat: " + itemModel?.price
        }
    }


    override fun initialize() {
        super.initialize()
        title = findViewById(R.id.titleText)

        desc = findViewById(R.id.descText)
        price = findViewById(R.id.priceText)


        button = findViewById(R.id.button)
    }

    override fun getLayout(): Int = layout

    companion object {
        const val layout = R.layout.card_e004

    }


    private data class Model(
        @SerializedName("title")
        var title: String?,


        @SerializedName("desc")
        val desc: String?,

        @SerializedName("price")
        var price: String,


        ) : Serializable

}



