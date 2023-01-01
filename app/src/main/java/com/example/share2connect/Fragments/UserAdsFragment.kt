package com.example.share2connect.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ace1ofspades.recyclerview.GroupAdapter
import com.ace1ofspades.recyclerview.viewHolders.ViewHolder
import com.example.share2connect.Models.AnnouncementsResponse
import com.example.share2connect.Models.BaseComponent
import com.example.share2connect.Models.BaseModel
import com.example.share2connect.Models.LoginResponse
import com.example.share2connect.R
import com.example.share2connect.Utils.AdvertEnum
import com.example.share2connect.Utils.Helper.Companion.changeFragment
import com.example.share2connect.retrofit.ApiClient
import com.example.share2connect.retrofit.SessionManager
import com.google.gson.Gson
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
 * Use the [UserAdsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserAdsFragment(var userId:Int?,var userName:String?) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var adapter = GroupAdapter<ViewHolder>()
    var baseModel: BaseModel? = null
    var searchList: BaseModel? = null
    lateinit var recyclerView: RecyclerView
    lateinit var userNameText: TextView
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    fun initializeData() {

        baseModel?.announcements?.let { componentModels ->
            adapter.clear()
            for (i in componentModels) {

                val component = AdvertEnum.get(key = i.category) ?: continue
                val row = component.type.newInstance()
                row.model = i
                println(i.data)
                row.fragment = this
                adapter.add(row)
            }}}
    fun getDataApi(userId: Int) {

        apiClient?.getApiService()
            ?.getUserPosts(userId)
            ?.enqueue(object : Callback<AnnouncementsResponse> {

                override fun onFailure(call: Call<AnnouncementsResponse>, t: Throwable) {
                    println("error= "+ t )
                }

                override fun onResponse(
                    call: Call<AnnouncementsResponse>,
                    response: Response<AnnouncementsResponse>
                ) {
                    if (response.code() == 200) {

                        baseModel=BaseModel(announcements = response.body()!!.data)

                        initializeData()
                    } else {
                        // Error logging in
                    }
                }

            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view=inflater.inflate(R.layout.fragment_user_ads, container, false)
        recyclerView=view.findViewById(R.id.recyclerView)
        apiClient= ApiClient(requireContext())
userNameText=view.findViewById(R.id.userNameText)
        userNameText.text=userName+" kullanıcısının ilanları"
        recyclerView.layoutManager= GridLayoutManager(context, 1)
        recyclerView.adapter=adapter

        userId?.let { getDataApi(it) }


        adapter.setOnItemClickListener { item, view ->


            changeFragment(DetailFragment(item.model as BaseComponent),requireActivity().supportFragmentManager)

        }


        return view
    }

}