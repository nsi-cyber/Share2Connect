package com.example.share2connect.Pages

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.share2connect.Fragments.JoinedFragment
import com.example.share2connect.Fragments.MyAdsFragment
import com.example.share2connect.MainActivity
import com.example.share2connect.Models.UserModel
import com.example.share2connect.R
import com.example.share2connect.Utils.Helper
import com.example.share2connect.retrofit.SessionManager


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
lateinit var userObject:UserModel
    lateinit  var sessionManager :SessionManager




    lateinit var userImage:ImageView
    lateinit var userName:TextView
    lateinit var editProfile:TextView
    lateinit var userBio:TextView
    lateinit var userDepartment:TextView
    lateinit var userMail:TextView
    lateinit var userPhone:TextView

    lateinit var userAttends:LinearLayout
    lateinit var userAdverts:LinearLayout





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
         sessionManager = SessionManager(this.requireContext())

        if(1==1)
userObject=sessionManager.getUserObject()!!

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_profile, container, false)

        userImage = view.findViewById(R.id.userImage)
        userName = view.findViewById(R.id.nameText)
        editProfile = view.findViewById(R.id.editProfile)
        userBio = view.findViewById(R.id.bioText)
        userDepartment = view.findViewById(R.id.departmentText)
        userMail = view.findViewById(R.id.mailText)
        userPhone = view.findViewById(R.id.phoneText)
        userAttends = view.findViewById(R.id.attends)
        userAdverts = view.findViewById(R.id.adverts)



        if(1==1) {//shared
            userName.text = userObject.fullName
            userBio.text = userObject.about
            userDepartment.text = userObject.department
            userMail.text = userObject.email
            userPhone.text = userObject.phone

            if(userObject.userImage!=null){

            val bmp = BitmapFactory.decodeByteArray(userObject.userImage!!.toByteArray(), 0, userObject.userImage!!.size)
            userImage.setImageBitmap(
                Bitmap.createScaledBitmap(
                    bmp,
                    userImage.width,
                    userImage.height,
                    false
                )
            )
            }
        }

userAdverts.setOnClickListener {
    activity?.let { it1 -> Helper.changeFragment(MyAdsFragment(), it1.supportFragmentManager) } }
userAttends.setOnClickListener { activity?.let { it1 -> Helper.changeFragment(


    JoinedFragment(

       // userObject.id
    1

    ), it1.supportFragmentManager)


} }
editProfile.setOnClickListener {activity?.let { it1 -> Helper.changeFragment(ProfileEditFragment(), it1.supportFragmentManager) }   }


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}