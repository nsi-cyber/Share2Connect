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
import com.example.share2connect.Components.LeaderboardCard
import com.example.share2connect.Models.*
import com.example.share2connect.R
import com.example.share2connect.Utils.AdvertEnum
import com.example.share2connect.Utils.Helper
import com.example.share2connect.retrofit.ApiClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var apiClient: ApiClient

/**
 * A simple [Fragment] subclass.
 * Use the [LeaderboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LeaderboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var recyclerView: RecyclerView
lateinit var baseModel:LeaderboardResponse
    var adapter = GroupAdapter<ViewHolder>()

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
        val view=inflater.inflate(R.layout.fragment_leaderboard, container, false)
        recyclerView=view.findViewById(R.id.recyclerView)

        recyclerView.layoutManager= GridLayoutManager(context, 1)
        recyclerView.adapter=adapter
        getDataApi()
        return view
    }


    fun getDataApi() {
        /*
        val mProgressDialog = ProgressDialog(requireContext())
        mProgressDialog.setTitle("İlanlar Yükleniyor")
        mProgressDialog.setMessage("Lütfen Bekleyiniz")
        mProgressDialog.show()
        */
        apiClient = ApiClient(requireContext())

        apiClient.getApiService().getLeaderboard()
            .enqueue(object : Callback<LeaderboardResponse> {

                override fun onFailure(call: Call<LeaderboardResponse>, t: Throwable) {
                    println("error= " + t)
                    //  mProgressDialog.hide()
                }

                override fun onResponse(
                    call: Call<LeaderboardResponse>,
                    response: Response<LeaderboardResponse>
                ) {
                    if (response.code() == 200) {
                        // mProgressDialog.hide()
                        baseModel = Gson().fromJson(Gson().toJson(response.body()), object : TypeToken<LeaderboardResponse>() {}.type)

                        initializeData()
                    } else {
                        // Error logging in
                        println("error " + response.message().toString())
                        //  mProgressDialog.hide()

                    }
                }

            })
    }



    fun initializeData() {
        var pos=1
        baseModel.data.sortByDescending { it.puan }

        baseModel.data.let { componentModels ->
            adapter.clear()

            for (i in componentModels) {

                adapter.add(LeaderboardCard(pos.toString(),i.user.fullName.toString(),i.user.department.toString(),i.puan.toString(),i.user.userImage.toString()))
pos++

            }
            adapter.setOnItemClickListener {

                    item, view -> var userClicked=((item.model) as LeaderModel).user as UserModel


                activity?.let { Helper.changeFragment(UserProfileFragment(userClicked), it.supportFragmentManager) }
            }







        }
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LeaderboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LeaderboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}