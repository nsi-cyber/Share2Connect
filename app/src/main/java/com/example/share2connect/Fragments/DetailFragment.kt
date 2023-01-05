package com.example.share2connect.Fragments

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.share2connect.Models.AdvertDataModel
import com.example.share2connect.Models.BaseComponent
import com.example.share2connect.Models.LoginResponse
import com.example.share2connect.Models.UserModel
import com.example.share2connect.R
import com.example.share2connect.Utils.Helper
import com.example.share2connect.retrofit.ApiClient
import com.google.android.material.card.MaterialCardView
import com.google.firebase.storage.FirebaseStorage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment(var baseComponent: BaseComponent) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    lateinit var typeImage: ImageView
    lateinit var userProfilePicture: ImageView
    lateinit var typeText: TextView
    lateinit var adNameText: TextView
    lateinit var adDescText: TextView
    lateinit var adImage: ImageView
    lateinit var adClubName: TextView
    lateinit var adDateText: TextView
    lateinit var adDateCard: CardView
    lateinit var reminderButton: TextView
    lateinit var adSeatText: TextView
    lateinit var adTicketText: TextView
    lateinit var adPriceText: TextView
    lateinit var adRouteCard: CardView
    lateinit var adPlaceCard: CardView
    lateinit var adRouteText: TextView
    lateinit var adPlaceText: TextView

    lateinit var userProfileButton: Button
    lateinit var userGender: MaterialCardView
    lateinit var userNameText: TextView
    lateinit var userDepartmentText: TextView
    lateinit var whatsappCard: CardView
    lateinit var mailCard: CardView


    var data: UserModel? = null

    fun makeViewsGone(vararg views: View) {
        views.forEach { view -> view.visibility = View.GONE }

    }

    fun makeViewsVisible(vararg views: View) {
        views.forEach { view -> view.visibility = View.VISIBLE }
    }

    fun disableViews(category: String) {
        makeViewsGone(
            adImage,
            adClubName,
            adDateCard,
            reminderButton,
            adSeatText,
            adTicketText,
            adPriceText,
            adRouteCard,
            adPlaceCard
        )

        when (category) {
            "E001" -> makeViewsVisible(adDateCard, adPlaceCard, adPriceText, adImage)
            "E002" -> makeViewsVisible(adClubName, adPlaceCard, adDateCard, adPriceText, adImage)
            "E003" -> makeViewsVisible(adRouteCard, adDateCard, adPriceText, adSeatText, adImage)
            "E004" -> makeViewsVisible(adPlaceCard, adPriceText, adImage)
            "E005" -> makeViewsVisible(adPlaceCard, adDateCard)
            "E006" -> makeViewsVisible(adPlaceCard, adDateCard, adImage, adPriceText, adTicketText)
        }

    }


    fun bindTextViews(dataObject: AdvertDataModel) {
        val textViewVariables = listOf(
            typeText,
            adNameText,
            adDescText,
            adClubName,
            adDateText,
            adSeatText,
            adTicketText,
            adPriceText,
            adRouteText,
            adPlaceText,
            userNameText,
            userDepartmentText
        )
        val textViewString = listOf(
            "typeText",
            "adNameText",
            "adDescText",
            "adClubName",
            "adDateText",
            "adSeatText",
            "adTicketText",
            "adPriceText",
            "adRouteText",
            "adPlaceText",
            "userNameText",
            "userDepartmentText"
        )
var strr=""
        when (baseComponent.category) {
            "E001" -> strr="Grup İçi Etkinlikler"
            "E002" -> strr="Kulüp Duyuruları"
            "E003" -> strr="Yol Arkadaşlığı"
            "E004" -> strr="Ev, Eşya İlanları"
            "E005" -> strr="Çalışma Grubu İlanları"
            "E006" -> strr="Parti ve Konser İlanları"


               }

        textViewVariables.forEach { textView ->
            when (textView.id) {
                R.id.typeText -> textView.text = strr
                R.id.adNameText -> textView.text = dataObject.adNameText
                R.id.adDescText -> textView.text = dataObject.adDescText
                R.id.adClubName -> textView.text = dataObject.adClubName
                R.id.adDateText -> textView.text = "Tarih: "+ dataObject.adDateText
                R.id.adSeatText -> textView.text = "Koltuk Sayısı: "+dataObject.adSeatText
                R.id.adTicketText -> textView.text = "Bilet Sayısı: "+dataObject.adTicketText
                R.id.adPriceText -> textView.text = "Ücret: "+dataObject.adPriceText
                R.id.adPlaceText -> textView.text = "Konum: "+dataObject.adPlaceText

            }
        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    fun getData(userId: Int) {


        var apiClient = this.context?.let { ApiClient(it) }




        apiClient?.getApiService()
            ?.getUser(userId)?.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    data = response.body()!!.user

                    /*

                    val imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(data!!.userImage!!)
                    imageRef.getBytes(10 * 1024 * 1024).addOnSuccessListener {
                        val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                        userProfilePicture.setImageBitmap(bitmap)
                    }.addOnFailureListener {
                        // Handle any errors
                    }

                    */

                    when(data!!.gender){
                        "Erkek"->userGender.strokeColor=(Color.parseColor("#03A9F4"))
                        "Kadın"->userGender.strokeColor=(Color.parseColor("#C61111"))
                        "Cevaplamıyorum"->userGender.strokeColor=(Color.parseColor("#FF7D54"))
                    }
                    userNameText.text = data?.fullName
                    userDepartmentText.text = data?.department
                    whatsappCard.setOnClickListener { data?.phone?.let { it1 -> openWhatsApp(it1) } }
                    mailCard.setOnClickListener { data?.email?.let { it1 -> openMail(it1) } }
                    userProfileButton.setOnClickListener { Helper.changeFragment(UserProfileFragment(
                        data!!
                    ),requireActivity().supportFragmentManager)}

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
println("error"+ t.toString())                }


            })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
// Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        userProfileButton = view.findViewById(R.id.userProfileButton)
        userProfilePicture = view.findViewById(R.id.userProfilePicture)
        userGender = view.findViewById(R.id.userGender)
        userNameText = view.findViewById(R.id.userNameText)
        userDepartmentText = view.findViewById(R.id.userDepartmentText)
        whatsappCard = view.findViewById(R.id.whatsappCard)
        mailCard = view.findViewById(R.id.mailCard)





        typeImage = view.findViewById(R.id.typeImage)
        typeText = view.findViewById(R.id.typeText)
        adNameText = view.findViewById(R.id.adNameText)
        adDescText = view.findViewById(R.id.adDescText)
        adImage = view.findViewById(R.id.adImage)
        adClubName = view.findViewById(R.id.adClubName)
        adDateText = view.findViewById(R.id.adDateText)
        adDateCard = view.findViewById(R.id.adDateCard)
        reminderButton = view.findViewById(R.id.reminderButton)
        adSeatText = view.findViewById(R.id.adSeatText)
        adTicketText = view.findViewById(R.id.adTicketText)
        adPriceText = view.findViewById(R.id.adPriceText)
        adRouteCard = view.findViewById(R.id.adRouteCard)
        adPlaceCard = view.findViewById(R.id.adPlaceCard)
        adRouteText = view.findViewById(R.id.adRouteText)
        adPlaceText = view.findViewById(R.id.adPlaceText)

        disableViews(baseComponent.category.toString())



        getData(baseComponent.userId!!.toInt())


        baseComponent.data?.let { bindTextViews(it) }




if(baseComponent.data?.adImage ==null)
{
    adImage.visibility=View.GONE
}


        return view
    }

    fun openMail(mail: String) {

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, mail)
        intent.putExtra(Intent.EXTRA_SUBJECT, "Campus uygulamasındaki ilan")

        startActivity(Intent.createChooser(intent, "Email via..."))

    }


    private fun openWhatsApp(smsNumber: String) {
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.type = "text/plain"
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Merhaba, bir ilan için yazmıştım "
        )
        sendIntent.putExtra("jid", "$smsNumber@s.whatsapp.net") //phone number without "+" prefix
        sendIntent.setPackage("com.whatsapp")
        if (activity?.let { sendIntent.resolveActivity(it.packageManager) } == null) {
            Toast.makeText(this.context, "Error/n", Toast.LENGTH_SHORT).show()
            return
        }
        startActivity(sendIntent)
    }


/*
companion object {
/**
* Use this factory method to create a new instance of
* this fragment using the provided parameters.
*
* @param param1 Parameter 1.
* @param param2 Parameter 2.
* @return A new instance of fragment DetailFragment.
*/
// TODO: Rename and change types and number of parameters
@JvmStatic
fun newInstance(param1: String, param2: String) =
DetailFragment(category,postId,userId).apply {
    arguments = Bundle().apply {
        putString(ARG_PARAM1, param1)
        putString(ARG_PARAM2, param2)
    }
}
}
*/


}