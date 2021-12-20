package com.example.groupproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.groupproject.databinding.ActivityMapsBinding
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.GravityCompat
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.core.view.iterator
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.groupproject.profile_page.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import java.lang.Exception

// Activity to hold and swap fragments for both the map and the profile page
class MainPage : AppCompatActivity() {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    lateinit var topBar : ActionBar
    lateinit var tabledrawer : DrawerLayout
    lateinit var drawerToggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(R.layout.main_page)
        var navView : BottomNavigationView = findViewById(R.id.nvView)
        setupDrawerContent(navView)


    }

    /**
     * Sets up the drawer, to call selectDrawerItem when user clicks on the
     * navbar.
     */
    private fun setupDrawerContent(navigationView: BottomNavigationView) {
        selectDrawerItem(MappyFragment(), "World" )
        navigationView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.DorasMap -> selectDrawerItem(MappyFragment(), "World" )
                R.id.personal -> selectDrawerItem(ProfileActivity(), "Profile")
            }
            true
        }
    }

    /**
     * Grabs the fragment and replaces it with the appropiate fragment to load
     */
    private fun selectDrawerItem(fragment: Fragment, title: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmmentlayout, fragment)
        fragmentTransaction.commit()
        if(title !="World") {
            setTitle(title)
        }

    }




}