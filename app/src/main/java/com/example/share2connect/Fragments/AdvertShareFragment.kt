package com.example.share2connect.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ace1ofspades.recyclerview.GroupAdapter
import com.ace1ofspades.recyclerview.viewHolders.ViewHolder
import com.example.share2connect.Models.AdvertDataModel
import com.example.share2connect.Models.AdvertResponse
import com.example.share2connect.Models.BaseComponent
import com.example.share2connect.Models.BaseModel
import com.example.share2connect.Pages.MainFragment
import com.example.share2connect.R
import com.example.share2connect.Utils.AdvertEnum
import com.example.share2connect.Utils.Helper
import com.example.share2connect.retrofit.ApiClient
import com.example.share2connect.retrofit.SessionManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdvertShareFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdvertShareFragment(var advertModel:AdvertDataModel,var category: String) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

lateinit var button:Button
    var adapter = GroupAdapter<ViewHolder>()
    var baseModel: BaseModel? = null
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_advert_share, container, false)
        recyclerView=view.findViewById(R.id.recyclerView)
        button=view.findViewById(R.id.button)
        recyclerView.layoutManager= GridLayoutManager(context, 1)
        recyclerView.adapter=adapter
        val component = AdvertEnum.get(key = category)
        var asd=BaseComponent()

        asd.data=advertModel
        asd.category=category
        val row = component!!.type.newInstance()

        row.model = asd
        println(advertModel)
        row.fragment = this
        adapter.add(row)

        button.setOnClickListener {

                var apiClient = context?.let { ApiClient(it) }!!
                apiClient.getApiService().post(
                asd)  .enqueue(object : Callback<AdvertResponse> {

                    override fun onFailure(call: Call<AdvertResponse>, t: Throwable) {
                        println(t.toString())
                    }

                    override fun onResponse(
                        call: Call<AdvertResponse>,
                        response: Response<AdvertResponse>
                    ) {
                        val postResponse = response.body()

                        if (postResponse?.status == 200) {
                            Helper.changeFragment(MainFragment(), activity!!.supportFragmentManager)

                        } else {
                            // Error logging in
                        }
                    }
                })









println(asd.toString())


        }
        return view
    }

}