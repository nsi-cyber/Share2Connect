package com.example.share2connect.Components

import android.widget.LinearLayout
import com.example.share2connect.Fragments.EditAdDetailFragment
import com.example.share2connect.Fragments.EditAdFragment
import com.example.share2connect.Fragments.ParticipiantsEditFragment
import com.example.share2connect.Models.*
import com.example.share2connect.Pages.LoginActivity
import com.example.share2connect.R
import com.example.share2connect.Utils.BaseComponentClass
import com.example.share2connect.Utils.Helper
import com.example.share2connect.Utils.Parser
import com.example.share2connect.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditCard() : BaseComponentClass() {

    // ViewObjects
    private var itemModel: BaseComponent? = null

    lateinit var edit: LinearLayout
    lateinit var list: LinearLayout
    lateinit var delete: LinearLayout

    override fun configure() {
        super.configure()
        itemModel = Parser.parse<BaseComponent>(model)

    }

    override fun initialize() {
        super.initialize()
        list = findViewById(R.id.listParti)
        delete = findViewById(R.id.delete)
        edit = findViewById(R.id.edit)

        list.setOnClickListener {
            //fragment.fragmentManager.let {  }
            EditAdFragment().fragmentManager?.let { it1 ->
            Helper.changeFragment(ParticipiantsEditFragment(),
                it1
            )
        }

        //todo update methodu üzerinden kullanıcı ekle
        }





        delete.setOnClickListener {


                ApiClient(context!!.applicationContext).getApiService()
                    ?.deletePost(itemModel!!.postId!!)
                    ?.enqueue(object : Callback<MessageResponse> {
                        override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                            println("error= "+ t )
                        }

                        override fun onResponse(
                            call: Call<MessageResponse>,
                            response: Response<MessageResponse>
                        ) {
                            if (response.code() == 200) {


                            } else {
                                // Error logging in
                            }


                    }})
            }






        edit.setOnClickListener {
            EditAdFragment().fragmentManager?.let { it1 ->
            Helper.changeFragment(EditAdDetailFragment(itemModel),
                it1
            )


        }
        }
    }

    override fun getLayout(): Int = layout

    companion object {
        const val layout = R.layout.edit_card

    }

}



