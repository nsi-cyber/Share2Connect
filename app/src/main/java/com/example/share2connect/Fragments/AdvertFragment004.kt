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
import com.example.share2connect.Utils.Helper.Companion.changeFragment
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
 * Use the [AdvertFragment004.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdvertFragment004(var isUpdate:Boolean?=false,var model: BaseComponent?=null) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var filePath: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            firebaseStore = FirebaseStorage.getInstance()
            storageReference = FirebaseStorage.getInstance().reference

        }
    }




    lateinit var advertName: EditText
    lateinit var advertDesc: EditText
    lateinit var descImage: ImageView
    lateinit var placeName: EditText

    lateinit var recyclerView: RecyclerView
    lateinit var advertFee: EditText
    lateinit var inspect: Button
    lateinit var returnFirst: Button

    var cal = Calendar.getInstance()
    private var date: String = "12"
    var gpsCoordinate: String = ""

    var latitudeGPS = "39.782613"
    var longitudeGPS = "30.5104952"
    private var imageUri: Uri? = null
    private var imageHas = false
    lateinit var placeGPSButton: ImageView

    fun placePicker() {
        val locationPickerIntent = LocationPickerActivity.Builder()
            .withLocation(41.4036299, 2.1743558)
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

    fun fillUpdate() {

        advertName.setText(model?.data?.adNameText)
        advertDesc.setText(model?.data?.adDescText)
        placeName.setText(model?.data?.adPlaceText)
        gpsCoordinate = model?.data?.adPlaceGPS.toString()
        advertFee.setText(model?.data?.adPriceText)

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

    fun phoneDate(): String {
        return  SimpleDateFormat("dd.MM.yyyy  h:mm a").format(Calendar.getInstance().getTime())

    }
    private fun imageToBitmap(image: ImageView): ByteArray {
        val bitmap = (image.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

        return stream.toByteArray()
    }

    fun post(bool:Boolean) {
        var paths= "gs://share2connect-93ec8.appspot.com/"+uploadImage()


        changeFragment(
            AdvertShareFragment(

                AdvertDataModel(
                adNameText = advertName.text.toString(),
              //  publishDate = phoneDate(),
                adPlaceGPS = latitudeGPS+","+longitudeGPS,
                adDescText = advertDesc.text.toString(),
                    adImage = paths,
                adPlaceText = placeName.text.toString(),
                adPriceText = advertFee.text.toString(),
            ),"E004",bool
        ),requireActivity().supportFragmentManager)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_advert004, container, false)
        with(view){




            var lay=findViewById<LinearLayout>(R.id.linearLayout3)

            if (isUpdate==true)
                lay.visibility=View.GONE
            placeGPSButton = findViewById(R.id.placeGPS)

            advertName=findViewById(R.id.editTextTitle)
            advertDesc=findViewById(R.id.editTextDesc)
            descImage=findViewById(R.id.imageViewDesc)
            descImage=findViewById(R.id.imageViewDesc)
            placeName=findViewById(R.id.editTextPlaceFirst)
            recyclerView=findViewById(R.id.recyclerView)
            advertFee=findViewById(R.id.editTextFee)
            inspect=findViewById(R.id.button)
            placeGPSButton.setOnClickListener { placePicker() }

        }



        if(isUpdate == true)
            fillUpdate()

        inspect.setOnClickListener {
            isUpdate?.let { it1 -> post(it1) }
        }
        returnFirst = view.findViewById(R.id.button1)
        returnFirst.setOnClickListener {
            activity?.let {
                Helper.changeFragment(
                    ChooseCategoryFragment(),
                    it.supportFragmentManager
                )
            }
        }
        descImage.setOnClickListener { launchGallery() }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdvertFragment004.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdvertFragment004().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}