package com.example.share2connect.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.share2connect.Models.BaseComponent
import com.example.share2connect.R
import com.example.share2connect.Utils.Helper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditAdDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditAdDetailFragment(var model: BaseComponent?=null) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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





        when(model?.category)
        {
            "E001"-> Helper.changeFragment(AdvertFragment001(isUpdate = true, model = model),requireActivity().supportFragmentManager)
            "E002"-> Helper.changeFragment(AdvertFragment002(isUpdate = true, model = model),requireActivity().supportFragmentManager)
            "E003"-> Helper.changeFragment(AdvertFragment003(isUpdate = true, model = model),requireActivity().supportFragmentManager)
            "E004"-> Helper.changeFragment(AdvertFragment004(isUpdate = true, model = model),requireActivity().supportFragmentManager)
            "E005"-> Helper.changeFragment(AdvertFragment006(isUpdate = true, model = model),requireActivity().supportFragmentManager)
            "E006"-> Helper.changeFragment(AdvertFragment005(isUpdate = true, model = model),requireActivity().supportFragmentManager)

        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_ad_detail, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditAdDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditAdDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}