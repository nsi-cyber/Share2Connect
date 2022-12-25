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
 * Use the [AdvertFragment002.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdvertFragment002 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
lateinit var returnFirst:Button


    lateinit var advertName: EditText
    lateinit var advertClub: EditText
    lateinit var advertDesc: EditText
    lateinit var selectDate: TextView
    lateinit var selectTime: TextView
    lateinit var descImage: ImageView
    lateinit var placeName: EditText
    lateinit var recyclerView: RecyclerView
    lateinit var advertFee: EditText
    lateinit var inspect: Button



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
        val view=  inflater.inflate(R.layout.fragment_advert002, container, false)
        returnFirst=view.findViewById(R.id.button1)
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
        inspect.setOnClickListener { post() }

        returnFirst.setOnClickListener { changeFragment(ChooseCategoryFragment()) }
        return view
    }

    fun changeFragment(fragment: Fragment) {
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.frameLayout, fragment)
        fragmentTransaction?.commit()
    }

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
                        adClubName = advertClub.text.toString(),

                        publishDate = phoneDate(),

                        adDescText = advertDesc.text.toString(),
                        adDateText = "12",
                        adImage = imageToBitmap(descImage),
                        adPlaceText = placeName.text.toString(),
                        adPriceText = advertFee.text.toString(),
                        adCategory = "E002"

                )))

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdvertFragment002.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdvertFragment002().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}