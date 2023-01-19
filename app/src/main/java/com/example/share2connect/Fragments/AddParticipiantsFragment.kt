package com.example.share2connect.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ace1ofspades.recyclerview.GroupAdapter
import com.ace1ofspades.recyclerview.viewHolders.ViewHolder
import com.example.share2connect.Models.BaseComponent
import com.example.share2connect.Models.MessageResponse
import com.example.share2connect.R
import com.example.share2connect.Utils.AdvertEnum
import com.example.share2connect.Utils.Helper
import com.example.share2connect.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddParticipiantsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddParticipiantsFragment(var baseComponent: BaseComponent) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var addUser: Button
    var adapter = GroupAdapter<ViewHolder>()
    lateinit var mailText: EditText
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
        var view = inflater.inflate(R.layout.fragment_add_participiants, container, false)
        mailText = view.findViewById(R.id.editMail)
        recyclerView = view.findViewById(R.id.recyclerView)
        addUser = view.findViewById(R.id.addUser)
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        recyclerView.adapter = adapter
        val component = AdvertEnum.get(key = baseComponent.category)
        var asd = BaseComponent()

        asd.data = baseComponent.data
        asd.category = baseComponent.category
        val row = component!!.type.newInstance()

        row.model = asd
        println(baseComponent.data)
        row.fragment = this
        adapter.add(row)




        addUser.setOnClickListener {
            if (mailText.text.toString()=="")
Toast.makeText(requireContext(),"Lütfen eklemek istediğiniz kullanıcının mail adresini giriniz.",Toast.LENGTH_SHORT)

//todo check is mail valid and get the user id

            var id=2
            var apiClient = context?.let { ApiClient(it) }!!

            baseComponent.data?.participants?.add(id.toString())

            apiClient.getApiService().updatePost(baseComponent)  .enqueue(object : Callback<MessageResponse> {

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    println(t.toString())
                }

                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {
                    val postResponse = response.body()

                    if (postResponse?.status == 200) {
                        Helper.changeFragment(MyAdsFragment(), activity!!.supportFragmentManager)

                    } else {
Toast.makeText(requireContext(),"Geçerli bir mail giriniz",Toast.LENGTH_SHORT)
Toast.makeText(requireContext(),postResponse?.message.toString(),Toast.LENGTH_SHORT)
                    }
                }
            })




        }



        return view
    }


}