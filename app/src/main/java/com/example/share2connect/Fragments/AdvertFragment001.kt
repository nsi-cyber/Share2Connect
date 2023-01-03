package com.example.share2connect.Fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.share2connect.MainActivity
import com.example.share2connect.Models.*
import com.example.share2connect.R
import com.example.share2connect.Utils.Helper
import com.example.share2connect.Utils.Helper.Companion.changeFragment
import com.example.share2connect.retrofit.ApiClient
import com.example.share2connect.retrofit.SessionManager
import java.io.ByteArrayOutputStream
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

class AdvertFragment001(var isUpdate: Boolean? = false, var model: BaseComponent? = null) : Fragment() {

    var cal = Calendar.getInstance()
    private var date: String = "12"
    var gpsCoordinate: String = ""


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var imageUri: Uri? = null
    private var imageHas = false
    lateinit var advertName: EditText
    lateinit var advertDesc: EditText
    lateinit var selectDate: TextView
    lateinit var selectTime: TextView
    lateinit var descImage: ImageView
    lateinit var placeName: EditText
    lateinit var recyclerView: RecyclerView
    lateinit var advertFee: EditText
    lateinit var inspect: Button
    lateinit var returnFirst: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }


    fun fillUpdate() {

        advertName.setText(model?.data?.adNameText)
        advertDesc.setText(model?.data?.adDescText)
        placeName.setText(model?.data?.adPlaceText)
        date = model?.data?.adDateText.toString()
        gpsCoordinate = model?.data?.adPlaceGPS.toString()
        advertDesc.setText(model?.data?.adDescText)
        advertFee.setText(model?.data?.adPriceText)
        descImage.setImageBitmap(model?.data?.adImage?.let { Helper.toBitmap(it) })

    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        requireActivity()
            .startActivityForResult(intent, 9032)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == 9032) {

            imageUri = data?.data
            imageHas = true
            val bitmap =
                MediaStore.Images.Media.getBitmap(MainActivity().contentResolver, imageUri)
            descImage.setImageBitmap(bitmap)
        }
    }


    fun getGps(): String {
        return ""
    }

    fun phoneDate(): String {
        return SimpleDateFormat("dd.MM.yyyy  h:mm a").format(Calendar.getInstance().time)

    }

    fun checkNull(): Boolean {

        return true
    }

    private fun imageToBitmap(image: ImageView): ByteArray {
        val bitmap = (image.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

        return stream.toByteArray()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_advert001, container, false)
        with(view) {
var lay=findViewById<LinearLayout>(R.id.linearLayout3)

            if (isUpdate==true)
                lay.visibility=View.GONE

            advertName = findViewById(R.id.editTextTitle)
            advertDesc = findViewById(R.id.editTextDesc)
            selectDate = findViewById(R.id.selectDate)
            selectTime = findViewById(R.id.selectTime)
            descImage = findViewById(R.id.imageViewDesc)
            placeName = findViewById(R.id.editTextPlace)
            recyclerView = findViewById(R.id.recyclerView)
            advertFee = findViewById(R.id.editTextFee)
            inspect = findViewById(R.id.button)

        }


        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "MM/dd/yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                selectDate!!.text = sdf.format(cal.getTime())
            }
        }

        val mTimePicker: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)

        mTimePicker = TimePickerDialog(requireContext(), object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                selectTime.setText(String.format("%d : %d", hourOfDay, minute))
            }
        }, hour, minute, true)

        selectTime.setOnClickListener({ v ->
            mTimePicker.show()
        })

        selectDate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(requireContext(),
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })

        if(isUpdate == true)
            fillUpdate()

        inspect.setOnClickListener {
            isUpdate?.let { it1 -> post(it1) }
        }
        descImage.setOnClickListener { pickImageFromGallery() }
        returnFirst = view.findViewById(R.id.button1)


        returnFirst.setOnClickListener {
            activity?.let {
                changeFragment(
                    ChooseCategoryFragment(),
                    it.supportFragmentManager
                )
            }
        }






        return view
    }


    fun post(bool:Boolean) {
        if(date!="12")
            date=selectDate.text.toString()+selectTime.text.toString()

        changeFragment(
            AdvertShareFragment(
                AdvertDataModel(
                    adNameText = advertName.text.toString(),
                    //publishDate = phoneDate(),
                    adDescText = advertDesc.text.toString(),
                    adDateText = date,
                  //  adImage = imageToBitmap(descImage),
                    adPlaceText = placeName.text.toString(),
                    adPlaceGPS=gpsCoordinate,
                    adPriceText = advertFee.text.toString(),
                ), "E001",bool
            ), requireActivity().supportFragmentManager
        )


    }

}