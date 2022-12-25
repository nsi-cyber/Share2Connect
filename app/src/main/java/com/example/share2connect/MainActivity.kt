package com.example.share2connect

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var bottomNavigationView: BottomNavigationView


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
                    Toast.makeText(this@MainActivity, "First Item Clicked", Toast.LENGTH_SHORT)
                        .show()
                }
                R.id.secondtItem -> {
                    drawerLayout.close()
                    Toast.makeText(this@MainActivity, "Second Item Clicked", Toast.LENGTH_SHORT)
                        .show()
                }
                R.id.thirdItem -> {
                    changeFragment(ChooseCategoryFragment())
                    bottomNavigationView.selectedItemId = R.id.home

                    drawerLayout.close()

                    Toast.makeText(this@MainActivity, "third Item Clicked", Toast.LENGTH_SHORT)
                        .show()
                }
                R.id.fourthItem -> {


                    var intent = Intent(this, LoginActivity().javaClass)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@MainActivity, "third Item Clicked", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            true
        }


        // Eger başlangıcta fragment cagırmak istersek cagırmak istenilen fragmenti asagıda ki satırda cagırabiliriz
        changeFragment(MainFragment())


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

