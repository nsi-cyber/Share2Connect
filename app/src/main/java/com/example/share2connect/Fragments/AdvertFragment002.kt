package com.example.share2connect.Fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.location.Address
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.adevinta.leku.*
import com.adevinta.leku.locale.SearchZoneRect
import com.example.share2connect.MainActivity
import com.example.share2connect.Models.AdvertDataModel
import com.example.share2connect.Models.AdvertResponse
import com.example.share2connect.Models.BaseComponent
import com.example.share2connect.Pages.MainFragment
import com.example.share2connect.R
import com.example.share2connect.Utils.Helper
import com.example.share2connect.retrofit.ApiClient
import com.example.share2connect.retrofit.SessionManager
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.IOException
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
class AdvertFragment002(var isUpdate:Boolean?=false,var model: BaseComponent?=null) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
lateinit var returnFirst:Button

    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var filePath: Uri? = null


    var cal = Calendar.getInstance()
    private var date: String = "12"
    var gpsCoordinate: String = ""

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


    var latitudeGPS = "39.782613"
    var longitudeGPS = "30.5104952"
    private var imageUri: Uri? = null
    private var imageHas = false
    lateinit var placeGPSButton: ImageView
    fun placePicker() {
        val locationPickerIntent = LocationPickerActivity.Builder()
            .withLocation(39.7979205, 30.4424081)
            .withGeolocApiKey("AIzaSyBc0uFtvsPaNowF9Ytcvx5lYXupUia6JW8")
            .withGooglePlacesApiKey("AIzaSyBc0uFtvsPaNowF9Ytcvx5lYXupUia6JW8")
            .withSearchZone("tr_TR")
            .withSearchZone(
                SearchZoneRect(
                    LatLng(39.7979205, 30.4424081),
                    LatLng(39.7907312, 30.5579732)
                )
            )
            .withDefaultLocaleSearchZone()
            .shouldReturnOkOnBackPressed()
            .withStreetHidden()
            .withCityHidden()
            .withZipCodeHidden()
            .withSatelliteViewHidden()

            .withGoogleTimeZoneEnabled()
            .withVoiceSearchHidden()
            .withUnnamedRoadHidden()
            .build(requireContext())

        startActivityForResult(locationPickerIntent, 1234)
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        requireActivity().startActivityForResult(intent, 9032)

    }

    val storage = Firebase.storage("gs://share2connect-93ec8.appspot.com")
    private fun uploadImage():String{
        var str="myImages/" + UUID.randomUUID().toString()
        if(filePath != null){
            val ref = storageReference?.child(str)
            val uploadTask = ref?.putFile(filePath!!)

        }else{
            Toast.makeText(requireContext(), "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
        return str
    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 12312)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 12312 && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }

            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, filePath)
                descImage.setImageBitmap(bitmap)
                imageHas=true
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
            Log.d("RESULT****", "OK")
            //if (requestCode == 1) {
            if (1 == 1) {
                val latitude = data.getDoubleExtra(LATITUDE, 0.0)
                Log.d("LATITUDE****", latitude.toString())
                latitudeGPS = latitude.toString()
                val longitude = data.getDoubleExtra(LONGITUDE, 0.0)
                Log.d("LONGITUDE****", longitude.toString())
                longitudeGPS = longitude.toString()

                val address = data.getStringExtra(LOCATION_ADDRESS)
                Log.d("ADDRESS****", address.toString())
                val postalcode = data.getStringExtra(ZIPCODE)
                Log.d("POSTALCODE****", postalcode.toString())
                val bundle = data.getBundleExtra(TRANSITION_BUNDLE)
                if (bundle != null) {
                    bundle.getString("test")?.let { Log.d("BUNDLE TEXT****", it) }
                }
                val fullAddress = data.getParcelableExtra<Address>(ADDRESS)
                if (fullAddress != null) {
                    Log.d("FULL ADDRESS****", fullAddress.toString())
                }
                val timeZoneId = data.getStringExtra(TIME_ZONE_ID)
                if (timeZoneId != null) {
                    Log.d("TIME ZONE ID****", timeZoneId)
                }
                val timeZoneDisplayName = data.getStringExtra(TIME_ZONE_DISPLAY_NAME)
                if (timeZoneDisplayName != null) {
                    Log.d("TIME ZONE NAME****", timeZoneDisplayName)
                }
            } else if (requestCode == 2) {
                val latitude = data.getDoubleExtra(LATITUDE, 0.0)
                Log.d("LATITUDE****", latitude.toString())
                latitudeGPS = latitude.toString()

                val longitude = data.getDoubleExtra(LONGITUDE, 0.0)
                Log.d("LONGITUDE****", longitude.toString())
                longitudeGPS = longitude.toString()

                val address = data.getStringExtra(LOCATION_ADDRESS)
                Log.d("ADDRESS****", address.toString())
                val lekuPoi = data.getParcelableExtra<LekuPoi>(LEKU_POI)
                Log.d("LekuPoi****", lekuPoi.toString())
            }
        }
        if (resultCode == AppCompatActivity.RESULT_CANCELED) {
            Log.d("RESULT****", "CANCELLED")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
    }
    fun fillUpdate() {

        advertName.setText(model?.data?.adNameText)
        advertClub.setText(model?.data?.adClubName)
        placeName.setText(model?.data?.adPlaceText)
        date = model?.data?.adDateText.toString()
        gpsCoordinate = model?.data?.adPlaceGPS.toString()
        advertDesc.setText(model?.data?.adDescText)
        advertFee.setText(model?.data?.adPriceText)
        if(date!="12"){

            selectDate.text=date.split(',')[0]
            selectTime.text=date.split(',')[1]
        }
        val imageRef = model?.data?.adImage?.let {
            FirebaseStorage.getInstance().getReferenceFromUrl(
                it
            )
        }
        if (imageRef != null) {
            imageRef.getBytes(10 * 1024 * 1024).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                descImage.setImageBitmap(bitmap)
            }.addOnFailureListener {
                // Handle any errors
            }
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

            var lay=findViewById<LinearLayout>(R.id.linearLayout3)

            if (isUpdate==true)
                lay.visibility=View.GONE
            placeGPSButton = findViewById(R.id.placeGPS)

            advertName=findViewById(R.id.editTextTitle)
            advertClub=findViewById(R.id.editTextClub)
            advertDesc=findViewById(R.id.editTextDesc)
            selectDate=findViewById(R.id.selectDate)
            selectTime=findViewById(R.id.selectTime)
            descImage=findViewById(R.id.imageViewDesc)
            placeName=findViewById(R.id.editTextPlace)
            recyclerView=findViewById(R.id.recyclerView)
            advertFee=findViewById(R.id.editTextFee)
            inspect=findViewById(R.id.button)
        }
        placeGPSButton.setOnClickListener { placePicker() }

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
        descImage.setOnClickListener { launchGallery() }

        returnFirst.setOnClickListener { changeFragment(ChooseCategoryFragment()) }
        return view
    }

    fun changeFragment(fragment: Fragment) {
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.frameLayout, fragment)
        fragmentTransaction?.commit()
    }
    fun post(bool:Boolean) {
        if(date=="12")
            date=selectDate.text.toString()+" , "+selectTime.text.toString()
        var paths= "gs://share2connect-93ec8.appspot.com/"+uploadImage()
        changeFragment(
            AdvertShareFragment(

                    AdvertDataModel(
                        adNameText = advertName.text.toString(),
                        adClubName = advertClub.text.toString(),

                        //publishDate = phoneDate(),

                        adDescText = advertDesc.text.toString(),
                        adDateText = date,
                        adPlaceGPS=(latitudeGPS+","+longitudeGPS),
                        adImage = paths,
                        adPlaceText = placeName.text.toString(),
                        adPriceText = advertFee.text.toString(),

                )
            ,"E002",bool))

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