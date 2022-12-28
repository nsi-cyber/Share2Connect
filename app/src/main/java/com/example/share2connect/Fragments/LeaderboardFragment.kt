package com.example.share2connect.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.ace1ofspades.recyclerview.GroupAdapter
import com.ace1ofspades.recyclerview.viewHolders.ViewHolder
import com.example.share2connect.Components.LeaderboardCard
import com.example.share2connect.Models.BaseModel
import com.example.share2connect.Models.LeaderModel
import com.example.share2connect.Models.UserModel
import com.example.share2connect.R
import com.example.share2connect.Utils.AdvertEnum
import com.example.share2connect.Utils.Helper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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
lateinit var baseModel:ArrayList<LeaderModel>
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

        return view
    }



    fun getData() {
        var data = ""
        data = context?.assets?.open("sampleLeader.json")?.bufferedReader().use {
            it?.readText() ?: ""
        }
        baseModel = Gson().fromJson<ArrayList<LeaderModel>>(data, object : TypeToken<ArrayList<LeaderModel>>() {}.type)
        initializeData()
    }

    fun initializeData() {

        baseModel?.let { componentModels ->
            adapter.clear()
            for (i in componentModels) {

                adapter.add(LeaderboardCard(i.pos,i.name,i.dep,i.point,i.image,i.id))


            }
            adapter.setOnItemClickListener { item, view -> var isn=item.getPosition(item)
            println(isn)
             println(baseModel[isn].id)

                activity?.let { Helper.changeFragment(UserProfileFragment(UserModel()), it.supportFragmentManager) }
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