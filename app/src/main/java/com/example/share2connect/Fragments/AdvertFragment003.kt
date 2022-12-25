package com.example.share2connect.Fragments

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.share2connect.Models.AdvertDataModel
import com.example.share2connect.Models.AdvertResponse
import com.example.share2connect.Pages.MainFragment
import com.example.share2connect.R
import com.example.share2connect.Utils.Helper
import com.example.share2connect.Utils.Helper.Companion.changeFragment
import com.example.share2connect.retrofit.ApiClient
import com.example.share2connect.retrofit.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdvertFragment003.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdvertFragment003 : Fragment() {
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







    lateinit var advertName: EditText
    lateinit var advertDesc: EditText
    lateinit var selectDate: TextView
    lateinit var selectTime: TextView
    lateinit var descImage:ImageView
    lateinit var advertSeat:EditText
    lateinit var placeFirstName: EditText
    lateinit var placeLastName: EditText

    lateinit var recyclerView: RecyclerView


    lateinit var advertFee: EditText

    lateinit var inspect: Button
    lateinit var returnFirst: Button




    fun phoneDate(): String {
        return  SimpleDateFormat("dd.MM.yyyy  h:mm a").format(Calendar.getInstance().getTime())

    }
    private fun imageToBitmap(image: ImageView): ByteArray {
        val bitmap = (image.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

        return stream.toByteArray()
    }
    fun post(){

        changeFragment(
            AdvertShareFragment(

                AdvertDataModel(
                adNameText = advertName.text.toString(),
                adSeatText = advertSeat.text.toString(),
                publishDate = phoneDate(),
                adDescText = advertDesc.text.toString(),
                adDateText = "12",
                adImage = imageToBitmap(descImage),
                adRouteEndText = placeLastName.text.toString(),
                adRouteStartText = placeFirstName.text.toString(),
                adPriceText = advertFee.text.toString(),
                adCategory = "E003"
            )
        ),requireActivity().supportFragmentManager)


    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var view=inflater.inflate(R.layout.fragment_advert003, container, false)
        with(view){
            advertName=findViewById(R.id.editTextTitle)
            advertSeat=findViewById(R.id.editTextSeat)
            advertDesc=findViewById(R.id.editTextDesc)
            selectDate=findViewById(R.id.selectDate)
            selectTime=findViewById(R.id.selectTime)
            descImage=findViewById(R.id.imageViewDesc)
            placeFirstName=findViewById(R.id.editTextPlaceFirst)
            placeLastName=findViewById(R.id.editTextPlaceLast)
            recyclerView=findViewById(R.id.recyclerView)
            advertFee=findViewById(R.id.editTextFee)
            inspect=findViewById(R.id.button)
        }
        inspect.setOnClickListener { post() }

        returnFirst=view.findViewById(R.id.button1)
        returnFirst.setOnClickListener { activity?.let {
            Helper.changeFragment(
                ChooseCategoryFragment(),
                it.supportFragmentManager
            )
        } }






        return view    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdvertFragment003.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdvertFragment003().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}