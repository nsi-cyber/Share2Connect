package com.example.share2connect.Components

import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ace1ofspades.recyclerview.GroupAdapter
import com.ace1ofspades.recyclerview.viewHolders.ViewHolder
import com.example.share2connect.R
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
    var adapter = GroupAdapter<ViewHolder>()
    private var itemModel: Model? = null






    override fun configure() {
        super.configure()
        itemModel = Parser.parse<Model>(model?.data)
        with(itemModel){
            title.text=itemModel?.title
            desc.text=itemModel?.desc
            price.setText("Fiyat: "+itemModel?.price)
            date.setText("Tarih: "+itemModel?.date)
            ticket.setText("Bilet Sayısı : "+itemModel?.ticket)
            place.setText("Konum: "+itemModel?.place)
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


    private data class Model(
        @SerializedName("title")
        var title: String?,
        @SerializedName("date")
        var date: String?,

        @SerializedName("desc")
        val desc: String?,

        @SerializedName("price")
        var price: String,

        @SerializedName("place")
        val place: String?,

        @SerializedName("ticket")
        val ticket: String?,
    ) : Serializable {



    }

}



