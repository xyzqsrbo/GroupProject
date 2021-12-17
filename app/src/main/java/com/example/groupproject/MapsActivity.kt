package com.example.groupproject

import android.content.Intent
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_DOWN
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.groupproject.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.Marker
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var mSearchText:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        mSearchText = findViewById(R.id.input_search)

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

        var location:LatLng
        var marker:Marker
        var markers:ArrayList<Marker> = ArrayList()

        val db = Firebase.firestore
        db.collection("Post")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    location = LatLng(document.getDouble("lat")!!.toDouble(), document.getDouble("long")!!.toDouble())
                    marker = mMap.addMarker(MarkerOptions().position(location).title(document.getString("Name")))!!
                    markers.add(marker)
                }
                mSearchText.doAfterTextChanged {
                    for (post in markers) {
                        if (!post.title?.contains(mSearchText.text.toString(), ignoreCase = true)!!) {
                            post.setVisible(false)
                        } else {
                            post.setVisible(true)
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "failed marker db search!", Toast.LENGTH_SHORT).show()
            }


    }
}