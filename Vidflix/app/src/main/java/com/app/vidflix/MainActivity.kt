package com.app.vidflix

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.app.vidflix.Fragments.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.nav_home ->{
                    moveToFragmnet(HomeFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_search ->{
                    moveToFragmnet(SearchFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_Soon ->{
                    moveToFragmnet(SoonFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_downloads ->{
                    moveToFragmnet(DownloadsFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_wishlist ->{
                    moveToFragmnet(WishlistFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
    }

    private fun moveToFragmnet(fragment: Fragment) {

        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.fragment_container,fragment)
        fragmentTrans.commit()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView : BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        moveToFragmnet(HomeFragment())

    }

}
