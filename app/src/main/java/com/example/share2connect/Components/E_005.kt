package com.example.share2connect.Components

import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ace1ofspades.recyclerview.GroupAdapter
import com.ace1ofspades.recyclerview.viewHolders.ViewHolder
import com.example.share2connect.Models.AdvertDataModel
import com.example.share2connect.R
import com.example.share2connect.Utils.AdvertEnum
import com.example.share2connect.Utils.BaseComponentClass
import com.example.share2connect.Utils.Helper
import com.example.share2connect.Utils.Parser
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class E_005: BaseComponentClass() {

    // ViewObjects
    lateinit var title:TextView
    lateinit var ticket:TextView
    lateinit var desc  :TextView
    lateinit var price :TextView
    lateinit var place :TextView
    lateinit var date :TextView
    lateinit var button:Button
    ////
    private var itemModel: AdvertDataModel? = null






    override fun configure() {
        super.configure()
        itemModel = Parser.parse<AdvertDataModel>(model?.data)
        with(itemModel){
            title.text=itemModel?.adNameText
            desc.text=itemModel?.adDescText
            price.setText("Fiyat: "+itemModel?.adPriceText)
            date.setText("Tarih: "+itemModel?.adDateText)
            ticket.setText("Bilet Sayısı : "+itemModel?.adTicketText)
            place.setText("Konum: "+itemModel?.adPlaceText)
        }
    }


    override fun initialize() {
        super.initialize()
        title = findViewById(R.id.titleText)
        place = findViewById(R.id.placeText)
        desc = findViewById(R.id.descText)
        price = findViewById(R.id.priceText)
        date = findViewById(R.id.dateText)
        button = findViewById(R.id.button)
        ticket = findViewById(R.id.ticketText)
    }

    override fun getLayout(): Int = layout

    companion object {
        const val layout = R.layout.card_e005

    }





    }





