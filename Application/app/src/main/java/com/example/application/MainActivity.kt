package com.example.application

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.application.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header.*
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var drawerLayout: DrawerLayout? = null
    private var toggle: ActionBarDrawerToggle? = null
    private var navigationView: NavigationView? = null

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_play_quiz -> {
                    moveToFragment(PlayQuizFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_cricket -> {
                    moveToFragment(CricketFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_predict -> {
                    moveToFragment(PredictionFragment())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_exam_prep -> {
                    moveToFragment(ExamPrepFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_image_quiz -> {
                    moveToFragment(ImageQuizFragment())
                    return@OnNavigationItemSelectedListener true
                }

            }
            false
        }

    private fun moveToFragment(fragment: Fragment) {

        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.fragment_container, fragment)
        fragmentTrans.commit()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocate()
        setContentView(R.layout.activity_main)

        username_main.text = intent.getStringExtra("username")

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.nav_drawer)

        val actionBar = supportActionBar
        actionBar?.title = resources.getString(R.string.app_name)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close)

        drawerLayout?.addDrawerListener(toggle!!)
        toggle!!.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.menu_icon)

        navigationView?.setNavigationItemSelectedListener(this)
       // var userName = findViewById<TextView>(R.id.username)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        moveToFragment(PlayQuizFragment())

        relative_wallet.setOnClickListener {
            val intent = Intent(this,EarnCoinActivity::class.java)
            startActivity(intent)
        }

        relative_free_coins.setOnClickListener {
            val intent = Intent(this,FreeCoinsActivity::class.java)
            startActivity(intent)
        }

        timer?.setOnClickListener {
            val dialog = ReminderFragment()

            dialog.show(supportFragmentManager,"customDialog")
        }

        recent_winners?.setOnClickListener {
            val intent = Intent(this,RecentWinnersActivity::class.java)
            startActivity(intent)
        }

        refer_earn?.setOnClickListener {
            val dialog = ReferEarnFragment()

            dialog.show(supportFragmentManager,"customDialog")
        }

        invite_friends?.setOnClickListener {
            val dialog = InviteFriendsFragment()

            dialog.show(supportFragmentManager,"customDialog")
        }

        val bundle = intent.extras
        val userName =bundle?.getString("username")
        username_main?.text = userName.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle?.onOptionsItemSelected(item) == true) {
            return true
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile_image->{
                val intent = Intent(this,AccountActivity::class.java)
                startActivity(intent)
            }
            R.id.manage_alarm -> {
                val dialog = AlarmDialogFragment()

                dialog.show(supportFragmentManager,"customDialog")
            }
            R.id.timings -> {
                val dialog = ShowTimingsFragment()

                dialog.show(supportFragmentManager,"customDialog")
            }
            R.id.free_coins -> {
                val intent = Intent(this,FreeCoinsActivity::class.java)
                startActivity(intent)
            }
            R.id.share_app ->{
                val intent = Intent().apply {
                    this.action = Intent.ACTION_SEND
                    this.putExtra(Intent.EXTRA_TEXT,"Share App to:")
                    this.type = "text/plain"
                }
                startActivity(intent)
            }
            R.id.security_measures -> {
                val dialog = SecurityMeasuresFragment()

                dialog.show(supportFragmentManager,"customDialog")
            }
            R.id.language -> {
                //val dialog = LanguageFragment()
                changeLanguage()
               // dialog.show(supportFragmentManager,"customDialog")
            }
            R.id.feedback -> {
                val dialog = FeedBackFragment()

                dialog.show(supportFragmentManager,"customDialog")
            }
            R.id.facebook -> {
                gotoUrl("https://www.facebook.com/")
            }
            R.id.twitter -> {
                gotoUrl("https://www.twitter.com/")
            }
            R.id.instagram -> {
                gotoUrl("https://www.instagram.com/")
            }
            R.id.youtube -> {
                gotoUrl("https://www.youtube.com/")
            }
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()

                val intent = Intent(this,WelcomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

        }
        return true
    }

    private fun changeLanguage() {
        val listItems = arrayOf("English","हिंदी","తెలుగు")

        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle("Change Language")
        mBuilder.setSingleChoiceItems(listItems,-1){dialog,which->
            when (which) {
                0 -> {
                    setLocate("en")
                    recreate()
                }
                1 -> {
                    setLocate("hi")
                    recreate()
                }
                2 -> {
                    setLocate("te")
                    recreate()
                }
            }
            dialog.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    private fun setLocate(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", lang)
        editor.apply()
    }

    private fun loadLocate() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        setLocate(language!!)
    }

    private fun gotoUrl(s: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(s))
        startActivity(intent)
    }
}