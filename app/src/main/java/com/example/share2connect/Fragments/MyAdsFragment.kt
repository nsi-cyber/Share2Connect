package com.example.share2connect.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ace1ofspades.recyclerview.GroupAdapter
import com.ace1ofspades.recyclerview.viewHolders.ViewHolder
import com.example.share2connect.Components.EditCard
import com.example.share2connect.Models.AnnouncementsResponse
import com.example.share2connect.Models.BaseModel
import com.example.share2connect.R
import com.example.share2connect.Utils.AdvertEnum
import com.example.share2connect.Utils.Helper
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
 * Use the [MyAdsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyAdsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var adapter = GroupAdapter<ViewHolder>()
    var baseModel: BaseModel? = null
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }fun initializeData() {

        baseModel?.announcements?.let { componentModels ->
            adapter.clear()
            for (i in componentModels) {

                val component = AdvertEnum.get(key = i.category) ?: continue
                val row = component.type.newInstance()
                row.model = i
                println(i.data)
                row.fragment = this
                adapter.add(row)
                val adOnComp = AdvertEnum.get(key = "addOn") ?: continue
                val addOn = adOnComp.type.newInstance()
                addOn.model = i
                addOn.fragment = this

                adapter.add(addOn)

            }}


    }
    fun getDataApi(userId: Int) {

        ApiClient(requireContext())?.getApiService()
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
        var view=inflater.inflate(R.layout.fragment_my_ads, container, false)
        recyclerView=view.findViewById(R.id.recyclerView)

        recyclerView.layoutManager= GridLayoutManager(context, 1)
        recyclerView.adapter=adapter
        SessionManager(requireContext()).getUserObject()?.id?.let { getDataApi(it) }


        adapter.setOnItemClickListener{ item, view ->
            Helper.changeFragment(MyAdsFragment(),requireActivity().supportFragmentManager)}




        return view    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyAdsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyAdsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}