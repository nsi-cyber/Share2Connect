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

class E_003: BaseComponentClass() {

    // ViewObjects
    lateinit var title:TextView
    lateinit var seat:TextView
    lateinit var desc  :TextView
    lateinit var price :TextView
    lateinit var start :TextView
    lateinit var end :TextView
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
            end.setText("Bıtıs: "+itemModel?.end)
            seat.setText("Bos Koltuk: "+itemModel?.seat)
            start.setText("Baslangıc: "+itemModel?.start)
        }
    }


    override fun initialize() {
        super.initialize()
        title = findViewById(R.id.titleText)
        end = findViewById(R.id.endText)
        desc = findViewById(R.id.descText)
        price = findViewById(R.id.priceText)
        start = findViewById(R.id.startText)
        button = findViewById(R.id.button)
        seat = findViewById(R.id.seatText)
    }

    override fun getLayout(): Int = layout

    companion object {
        const val layout = R.layout.card_e003

    }


    private data class Model(
        @SerializedName("title")
        var title: String?,
        @SerializedName("start")
        var start: String?,

        @SerializedName("desc")
        val desc: String?,

        @SerializedName("price")
        var price: String,

        @SerializedName("end")
        val end: String?,

        @SerializedName("seat")
        val seat: String?,
    ) : Serializable {



    }

}



