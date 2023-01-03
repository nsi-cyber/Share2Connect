package com.example.share2connect.Pages

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ace1ofspades.recyclerview.GroupAdapter
import com.ace1ofspades.recyclerview.viewHolders.ViewHolder
import com.example.share2connect.Fragments.ChooseCategoryFragment
import com.example.share2connect.Fragments.DetailFragment
import com.example.share2connect.Models.AnnouncementsResponse
import com.example.share2connect.Models.BaseComponent
import com.example.share2connect.Models.BaseModel
import com.example.share2connect.R
import com.example.share2connect.Utils.AdvertEnum
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
lateinit var baseModel: BaseModel
private lateinit var sessionManager: SessionManager
private lateinit var apiClient: ApiClient

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    var filter = 0

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var categoryAll: CardView
    lateinit var category001: CardView
    lateinit var category002: CardView
    lateinit var category003: CardView
    lateinit var category004: CardView
    lateinit var category005: CardView
    lateinit var category006: CardView
    lateinit var searchBox: EditText


    lateinit var recyclerView: RecyclerView
    lateinit var button: Button


    var adapter = GroupAdapter<ViewHolder>()
    var baseModel: BaseModel? = null
    var searchList: BaseModel? = null


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
        apiClient = context?.let { ApiClient(it) }!!
        val view = inflater.inflate(R.layout.fragment_main, container, false)
//getData()
        getDataApi()

        recyclerView = view.findViewById(R.id.recyclerView)
        searchBox = view.findViewById(R.id.searchEditText)
        searchBox.setOnKeyListener { view, i, keyEvent ->
            searchData(searchBox.text.toString().lowercase())
            return@setOnKeyListener true
        }






        category001 = view.findViewById(R.id.catGroup)
        category001.setOnClickListener {
            filter = 1
            initializeData()
        }

        category002 = view.findViewById(R.id.catClub)
        category002.setOnClickListener {
            filter = 2
            initializeData()
        }

        category003 = view.findViewById(R.id.catRoad)
        category003.setOnClickListener {
            filter = 3
            initializeData()
        }

        category004 = view.findViewById(R.id.catRoomie)
        category004.setOnClickListener {
            filter = 4
            initializeData()
        }

        category005 = view.findViewById(R.id.catParty)
        category005.setOnClickListener {
            filter = 5
            initializeData()
        }

        category006 = view.findViewById(R.id.catStudy)
        category006.setOnClickListener {
            filter = 6
            initializeData()
        }

        categoryAll = view.findViewById(R.id.catAll)
        categoryAll.setOnClickListener {
            filter = 0
            initializeData()
        }

        button = view.findViewById(R.id.button2)

        recyclerView.layoutManager = GridLayoutManager(context, 1)
        recyclerView.adapter = adapter


        button.setOnClickListener {
            changeFragment(ChooseCategoryFragment())
        }

        adapter.setOnItemClickListener { item, view ->


            changeFragment(DetailFragment(item.model as BaseComponent))

        }

        return view
    }


    fun getDataApi() {
        /*
        val mProgressDialog = ProgressDialog(requireContext())
        mProgressDialog.setTitle("İlanlar Yükleniyor")
        mProgressDialog.setMessage("Lütfen Bekleyiniz")
        mProgressDialog.show()
        */
        apiClient.getApiService()
            .getHomeData()
            .enqueue(object : Callback<AnnouncementsResponse> {

                override fun onFailure(call: Call<AnnouncementsResponse>, t: Throwable) {
                    println("error= " + t)
                  //  mProgressDialog.hide()
                }

                override fun onResponse(
                    call: Call<AnnouncementsResponse>,
                    response: Response<AnnouncementsResponse>
                ) {
                    if (response.code() == 200) {
                       // mProgressDialog.hide()

                        baseModel = BaseModel(announcements = response.body()!!.data)

                        initializeData()
                    } else {
                        // Error logging in
                        println("error " + response.message().toString())
                      //  mProgressDialog.hide()

                    }
                }

            })
    }


    fun changeFragment(fragment: Fragment) {
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.frameLayout, fragment)
        fragmentTransaction?.commit()
    }

    fun searchData(str: String) {
        adapter.clear()

        for (obj in baseModel?.announcements!!) {

            if (obj.data?.adNameText.toString().lowercase().contains(str)
                ||
                obj.data?.adDescText.toString().lowercase().contains(str)
            ) {
                val component = AdvertEnum.get(key = obj.category) ?: continue
                val row = component.type.newInstance()
                row.model = obj
                println(obj.data)
                row.fragment = this
                adapter.add(row)
            }
        }
    }


    fun getData() {
        var data = ""
        data = context?.assets?.open("sampleData.json")?.bufferedReader().use {
            it?.readText() ?: ""
        }
        baseModel = Gson().fromJson<BaseModel>(data, object : TypeToken<BaseModel>() {}.type)
        initializeData()
    }

    fun initializeData() {

        baseModel?.announcements?.let { componentModels ->
            adapter.clear()
            for (i in componentModels) {
                when (filter) {
                    0 -> {
                        val component = AdvertEnum.get(key = i.category) ?: continue
                        val row = component.type.newInstance()
                        row.model = i
                        println(i.data)
                        row.fragment = this
                        adapter.add(row)
                    }
                    1 -> if (i.category == "E001") {
                        val component = AdvertEnum.get(key = i.category) ?: continue

                        val row = component.type.newInstance()
                        row.model = i
                        println(i.data)
                        row.fragment = this
                        adapter.add(row)
                    }
                    2 -> if (i.category == "E002") {
                        val component = AdvertEnum.get(key = i.category) ?: continue

                        val row = component.type.newInstance()
                        row.model = i
                        println(i.data)
                        row.fragment = this
                        adapter.add(row)
                    }
                    3 -> if (i.category == "E003") {
                        val component = AdvertEnum.get(key = i.category) ?: continue

                        val row = component.type.newInstance()
                        row.model = i
                        println(i.data)
                        row.fragment = this
                        adapter.add(row)
                    }
                    4 -> if (i.category == "E004") {
                        val component = AdvertEnum.get(key = i.category) ?: continue

                        val row = component.type.newInstance()
                        row.model = i
                        println(i.data)
                        row.fragment = this
                        adapter.add(row)
                    }
                    5 -> if (i.category == "E005") {
                        val component = AdvertEnum.get(key = i.category) ?: continue

                        val row = component.type.newInstance()
                        row.model = i
                        println(i.data)
                        row.fragment = this
                        adapter.add(row)
                    }
                    6 -> if (i.category == "E006") {
                        val component = AdvertEnum.get(key = i.category) ?: continue

                        val row = component.type.newInstance()
                        row.model = i
                        println(i.data)
                        row.fragment = this
                        adapter.add(row)
                    }

                }


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
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

