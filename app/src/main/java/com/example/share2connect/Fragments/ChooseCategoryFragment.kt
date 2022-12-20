package com.example.share2connect.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.share2connect.R
import com.google.android.material.card.MaterialCardView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChooseCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChooseCategoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

lateinit var frag1:MaterialCardView
lateinit var frag2:MaterialCardView
lateinit var frag3:MaterialCardView
lateinit var frag4:MaterialCardView
lateinit var frag5:MaterialCardView
lateinit var frag6:MaterialCardView
lateinit var frag7:MaterialCardView

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

        val view=inflater.inflate(R.layout.fragment_choose_category, container, false)

frag1=view.findViewById(R.id.frag1)
frag2=view.findViewById(R.id.frag2)
frag3=view.findViewById(R.id.frag3)
frag4=view.findViewById(R.id.frag4)
frag5=view.findViewById(R.id.frag5)
frag6=view.findViewById(R.id.frag6)
frag7=view.findViewById(R.id.frag7)


        frag1.setOnClickListener { changeFragment(AdvertFragment001()) }
        frag2.setOnClickListener { changeFragment(AdvertFragment002()) }
        frag3.setOnClickListener { changeFragment(AdvertFragment003()) }
        frag4.setOnClickListener { changeFragment(AdvertFragment004()) }
        frag5.setOnClickListener { changeFragment(AdvertFragment005()) }
        frag6.setOnClickListener { changeFragment(AdvertFragment006()) }
       // frag7.setOnClickListener { changeFragment(AdvertFragment007()) }






        return view
    }

    fun changeFragment(fragment: Fragment) {
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.frameLayout, fragment)
        fragmentTransaction?.commit()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChooseCategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChooseCategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}