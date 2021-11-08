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
import com.google.android.material.navigation.NavigationView
import java.lang.Exception


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    lateinit var topBar : Toolbar
    lateinit var tabledrawer : DrawerLayout
    lateinit var drawerToggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView : NavigationView = findViewById(R.id.nvView)
        topBar = findViewById(R.id.toolbar)
        setSupportActionBar(topBar)
        tabledrawer = findViewById(R.id.drawer_layout)
        drawerToggle = setupDrawerToggle()
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerToggle.syncState()
        tabledrawer.addDrawerListener(drawerToggle)
        setupDrawerContent(navView)


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener {
            it.isChecked = true

            when(it.itemId){
                R.id.DorasMap -> selectDrawerItem(BlankFragment(), "fuck you" )
                R.id.cheapSkate -> selectDrawerItem(Terms(), "Also fuck you" )
            }
            true
        }
    }
    private fun selectDrawerItem(fragment: Fragment, title: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
        setTitle(title)

    }






    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.home -> tabledrawer.openDrawer(GravityCompat.START)

        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupDrawerToggle(): ActionBarDrawerToggle{
        return ActionBarDrawerToggle(this, tabledrawer, topBar , R.string.drawer_open,  R.string.drawer_close)

    }
}