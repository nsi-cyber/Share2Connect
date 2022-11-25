package com.example.share2connect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.share2connect.Pages.MainFragment
import com.example.share2connect.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout:DrawerLayout
    lateinit var navView:NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout=findViewById(R.id.drawerLayout)
        navView=findViewById(R.id.navView)


        toggle = ActionBarDrawerToggle(this@MainActivity, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.firstItem -> {

                    Toast.makeText(this@MainActivity, "First Item Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.secondtItem -> {
                    Toast.makeText(this@MainActivity, "Second Item Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.thirdItem -> {
                    Toast.makeText(this@MainActivity, "third Item Clicked", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }


        // Eger başlangıcta fragment cagırmak istersek cagırmak istenilen fragmenti asagıda ki satırda cagırabiliriz
            changeFragment(MainFragment())



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
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

