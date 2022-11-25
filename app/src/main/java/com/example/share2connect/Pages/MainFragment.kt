package com.example.share2connect.Pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ace1ofspades.recyclerview.ViewHolder
import com.example.share2connect.R
import com.example.share2connect.Utils.AdvertEnum
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var recyclerView:RecyclerView




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


        return inflater.inflate(R.layout.fragment_main, container, false)
    }
    fun getData() {
        val call = RetrofitClient.retrofitInterface(context).getHomeData()
        call.enqueue(RetrofitCallback(requireContext(), object : Callback<BaseComponentModel?> {
            override fun onResponse(
                call: Call<BaseComponentModel?>,
                response: Response<BaseComponentModel?>
            ) {
                if (response.code() == 200) {
                    baseModel = response.body()
                    configure()
                }
            }

            override fun onFailure(call: Call<BaseComponentModel?>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
            }

        }))
    }

    fun configure() {


        baseModel?.components?.let { componentModels ->
            for (i in componentModels) {
                val component = AdvertEnum.get(key = i.component) ?: return
                val row = component.type.newInstance()
                row.model = i
                println(i.props)
                row.fragment = this
                adapter.add(row)

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