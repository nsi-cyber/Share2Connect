package com.example.share2connect.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.share2connect.Models.AdvertModel001
import com.example.share2connect.R
import com.example.share2connect.Utils.Helper.Companion.changeFragment
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdvertFragment001 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var advertName:EditText
    lateinit var advertDesc:EditText
    lateinit var selectDate:TextView
    lateinit var selectTime:TextView
    lateinit var descImage:ImageView
    lateinit var placeName:EditText
    lateinit var recyclerView: RecyclerView
    lateinit var advertFee:EditText
    lateinit var inspect: Button
    lateinit var returnFirst: Button
    var gpsCoordinate:String=""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    fun getGps():String{
        return ""
    }

    fun dataToModel(view:View): AdvertModel001 {
        val df = SimpleDateFormat("dd.MM.yyyy  h:mm a")
        val date = df.format(Calendar.getInstance().getTime())
        var model=AdvertModel001(date,true,advertName.text.toString(),advertDesc.text.toString(),advertFee.text.toString(),selectTime.text.toString(),selectDate.text.toString(),"photo",placeName.text.toString(),"")
    return model
    }

fun checkNull():Boolean{
   return true;
}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view=inflater.inflate(R.layout.fragment_advert001, container, false)
        with(view){
            advertName=findViewById(R.id.editTextTitle)
            advertDesc=findViewById(R.id.editTextDesc)
            selectDate=findViewById(R.id.selectDate)
            selectTime=findViewById(R.id.selectTime)
            descImage=findViewById(R.id.imageViewDesc)
            placeName=findViewById(R.id.editTextPlace)
            recyclerView=findViewById(R.id.recyclerView)
            advertFee=findViewById(R.id.editTextFee)
            inspect=findViewById(R.id.button)
        }
        returnFirst=view.findViewById(R.id.button1)
        returnFirst.setOnClickListener { activity?.let { changeFragment(ChooseCategoryFragment(), it.supportFragmentManager) } }






        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdvertFragment001().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}