package com.example.share2connect

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.share2connect.Fragments.ChooseCategoryFragment
import com.example.share2connect.Fragments.JoinedFragment
import com.example.share2connect.Fragments.LeaderboardFragment
import com.example.share2connect.Fragments.MyAdsFragment
import com.example.share2connect.Pages.LoginActivity
import com.example.share2connect.Pages.MainFragment
import com.example.share2connect.Pages.ProfileFragment
import com.example.share2connect.Utils.Helper
import com.example.share2connect.retrofit.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.storage.FirebaseStorage


class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var bottomNavigationView: BottomNavigationView

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.navView)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)




        toggle =
            ActionBarDrawerToggle(this@MainActivity, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> changeFragment(MainFragment())
                R.id.joined -> changeFragment(
                    JoinedFragment(
                        // SessionManager(this).getUserObject()!!.id)
                        1
                    )
                )

                R.id.leaderBoard -> changeFragment(LeaderboardFragment())
                R.id.myAds -> changeFragment(MyAdsFragment())
                R.id.profile -> changeFragment(ProfileFragment())
            }
            true
        }



        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.firstItem -> {
                    bottomNavigationView.selectedItemId = R.id.profile
                    drawerLayout.close()
                    changeFragment(ProfileFragment())
                    Toast.makeText(this@MainActivity, "Profil", Toast.LENGTH_SHORT)
                        .show()
                }

                R.id.thirdItem -> {
                    bottomNavigationView.selectedItemId = R.id.home

                    drawerLayout.close()
                    changeFragment(ChooseCategoryFragment())

                    Toast.makeText(this@MainActivity, "İlan Paylaş", Toast.LENGTH_SHORT)
                        .show()
                }
                R.id.fourthItem -> {


                    var intent = Intent(this, LoginActivity().javaClass)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@MainActivity, "Çıkış Yapıldı", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            true
        }


        // Eger başlangıcta fragment cagırmak istersek cagırmak istenilen fragmenti asagıda ki satırda cagırabiliriz
        changeFragment(MainFragment())

        val navigationView: NavigationView = findViewById(R.id.navView)
        val headerView: View = navigationView.getHeaderView(0)
        val navUserImage: ImageView = headerView.findViewById(R.id.navImage)
        val navUserName: TextView = headerView.findViewById(R.id.navUserName)
        val navUserDepartment: TextView = headerView.findViewById(R.id.navUserDepartment)
        navUserName.text = SessionManager(this).getUserObject()?.fullName ?: "UserName"
        navUserDepartment.text = SessionManager(this).getUserObject()?.department ?: "Department"

try {

    val uris=SessionManager(this).getUserObject()?.userImage!!.toString()
    val imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(uris)
    imageRef.getBytes(10 * 1024 * 1024).addOnSuccessListener {
        val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
        navUserImage.setImageBitmap(bitmap)
    }.addOnFailureListener {
        // Handle any errors
    }
}
catch (e:java.lang.Exception){

}



    }

    override fun onBackPressed() {
        changeFragment(MainFragment())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            true
        }
        return super.onOptionsItemSelected(item)
    }

    fun changeFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}

