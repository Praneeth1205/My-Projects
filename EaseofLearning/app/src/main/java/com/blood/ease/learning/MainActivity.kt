package com.blood.ease.learning

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.blood.ease.learning.fragments.AccountFragment
import com.blood.ease.learning.fragments.EbookFragment
import com.blood.ease.learning.fragments.HomeFragment
import com.blood.ease.learning.fragments.VideoFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {


    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    moveToFragment(HomeFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_ebook -> {
                    moveToFragment(EbookFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_videos -> {
                    moveToFragment(VideoFragment())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_profile -> {
                    moveToFragment(AccountFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        moveToFragment(HomeFragment())
    }
    private fun moveToFragment(fragment: Fragment) {

        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.fragment_container, fragment)
        fragmentTrans.commit()

    }

}