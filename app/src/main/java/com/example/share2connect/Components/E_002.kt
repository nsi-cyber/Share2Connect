package com.example.share2connect.Components

import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ace1ofspades.recyclerview.GroupAdapter
import com.ace1ofspades.recyclerview.viewHolders.ViewHolder
import com.example.share2connect.Models.AdvertDataModel
import com.example.share2connect.R
import com.example.share2connect.Utils.BaseComponentClass
import com.example.share2connect.Utils.Helper
import com.example.share2connect.Utils.Parser
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class E_002: BaseComponentClass() {

    // ViewObjects
    lateinit var title:TextView
    lateinit var club:TextView
    lateinit var desc  :TextView
    lateinit var price :TextView
    lateinit var place :TextView
    lateinit var button:Button
    ////
    var adapter = GroupAdapter<ViewHolder>()
    private var itemModel: AdvertDataModel? = null






    override fun configure() {
        super.configure()
        itemModel = Parser.parse<AdvertDataModel>(model?.data)
        with(itemModel){
            title.text=itemModel?.adNameText
            club.text=itemModel?.adClubName
            desc.text=itemModel?.adDescText
            price.setText("Fiyat: "+itemModel?.adPriceText)
            place.setText("Konum: "+itemModel?.adPlaceText)
        }
    }


    override fun initialize() {
        super.initialize()
        title = findViewById(R.id.titleText)
        club = findViewById(R.id.clubText)
        desc = findViewById(R.id.descText)
        price = findViewById(R.id.priceText)
        place = findViewById(R.id.placeText)
        button = findViewById(R.id.button)
    }

    override fun getLayout(): Int = layout

    companion object {
        const val layout = R.layout.card_e002

    }


    private data class Model(
        @SerializedName("title")
        var title: String?,
        @SerializedName("club")
        var club: String?,

        @SerializedName("desc")
        val desc: String?,

        @SerializedName("price")
        var price: String,

        @SerializedName("place")
        val place: String?,

        @SerializedName("info")
        val info: String?,
    ) : Serializable {



    }

}



