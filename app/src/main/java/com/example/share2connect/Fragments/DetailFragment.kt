package com.example.share2connect.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.share2connect.R
import com.example.share2connect.retrofit.ApiClient
import com.example.share2connect.retrofit.SessionManager
import com.google.android.material.card.MaterialCardView
import okhttp3.ResponseBody
import org.w3c.dom.Text
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
class DetailFragment(var category: String,var postId:String) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    lateinit var typeImage:ImageView
    lateinit var typeText:TextView
    lateinit var adNameText:TextView
    lateinit var adDescText:TextView
    lateinit var adImage:ImageView
    lateinit var adClubName:TextView
    lateinit var adDateText:TextView
    lateinit var adDateCard:CardView
    lateinit var reminderButton:TextView
    lateinit var adSeatText:TextView
    lateinit var adTicketText:TextView
    lateinit var adPriceText:TextView
    lateinit var adRouteCard:CardView
    lateinit var adPlaceCard:CardView
    lateinit var adRouteText:TextView
    lateinit var adPlaceText:TextView

    lateinit var userProfileButton:Button
    lateinit var userGender:MaterialCardView
    lateinit var userNameText:TextView
    lateinit var userDepartmentText:TextView
    lateinit var whatsappCard:CardView
    lateinit var mailCard:CardView



lateinit var data:Map<*,*>
    fun makeViewsGone(vararg views: View) {
        views.forEach { view -> view.visibility = View.GONE }
    }
    fun makeViewsVisible(vararg views: View) {
        views.forEach { view -> view.visibility = View.VISIBLE }
    }

fun disableViews(category:String) {
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
        "001" -> makeViewsVisible(adDateCard, adPlaceCard, adPriceText, adImage)
        "002" -> makeViewsVisible(adClubName, adPlaceCard, adDateCard, adPriceText, adImage)
        "003" -> makeViewsVisible(adRouteCard, adDateCard, adPriceText, adSeatText, adImage)
        "004" -> makeViewsVisible(adPlaceCard, adPriceText, adImage)
        "005" -> makeViewsVisible(adPlaceCard, adDateCard)
        "006" -> makeViewsVisible(adPlaceCard, adDateCard, adImage, adPriceText, adTicketText)
    }

}

fun initializeData(data:Map<*,*>){
    val textViewVariables = listOf(typeText, adNameText, adDescText, adClubName, adDateText, adSeatText, adTicketText, adPriceText, adRouteText, adPlaceText, userNameText, userDepartmentText)

    textViewVariables.forEach { textView ->
        textView.text = data[textView.id.toString()]?.toString() ?: "error"
    }
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    fun getData(postId:String){


        var apiClient = this.context?.let { ApiClient(it) }
        var sessionManager = this.context?.let { SessionManager(it) }




        apiClient?.getApiService()
?.getPost(postId)
            ?.enqueue(object : Callback<Map<*, *>> {
                override fun onResponse(call: Call<Map<*, *>>, response: Response<Map<*, *>>) {

data= response.body()!!

                }

                override fun onFailure(call: Call<Map<*, *>>, t: Throwable) {
                    TODO("Not yet implemented")
                }


            })
}









override fun onCreateView(
inflater: LayoutInflater, container: ViewGroup?,
savedInstanceState: Bundle?
): View? {
// Inflate the layout for this fragment
val view = inflater.inflate(R.layout.fragment_detail, container, false)

    userProfileButton = view.findViewById(R.id.userProfileButton)
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

    disableViews(category)



    getData(postId)








return view
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