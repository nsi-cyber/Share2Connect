package com.example.share2connect.Components

import android.widget.LinearLayout
import com.example.share2connect.Fragments.EditAdFragment
import com.example.share2connect.Fragments.ParticipiantsEditFragment
import com.example.share2connect.Models.BaseComponent
import com.example.share2connect.Models.BaseModel
import com.example.share2connect.Pages.LoginActivity
import com.example.share2connect.R
import com.example.share2connect.Utils.BaseComponentClass
import com.example.share2connect.Utils.Helper

class EditCard(var baseModel:BaseComponent) : BaseComponentClass() {

    // ViewObjects

    lateinit var edit: LinearLayout
    lateinit var list: LinearLayout
    lateinit var delete: LinearLayout


    override fun initialize() {
        super.initialize()
        list = findViewById(R.id.listParti)
        delete = findViewById(R.id.delete)
        edit = findViewById(R.id.edit)

        list.setOnClickListener { EditAdFragment().fragmentManager?.let { it1 ->
            Helper.changeFragment(ParticipiantsEditFragment(),
                it1
            )
        } }
        delete.setOnClickListener {
            //Todo delete post retrofit
        }
        edit.setOnClickListener {
            //todo verileri yeni oluşturur gibi dataya gönder sonrasında returnünde updatei çalıştır enes.
            EditAdFragment().fragmentManager?.let { it1 ->
            Helper.changeFragment(ParticipiantsEditFragment(),
                it1
            )
        } }
    }

    override fun getLayout(): Int = layout

    companion object {
        const val layout = R.layout.edit_card

    }

}



