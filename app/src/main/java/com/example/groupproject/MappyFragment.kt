package com.example.groupproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import androidx.fragment.app.FragmentManager
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.Marker
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private lateinit var mMap: GoogleMap
private lateinit var mMapView: MapView


/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var mSearchText: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_blank, container, false)
        mSearchText = rootView.findViewById(R.id.input_search)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return rootView
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap





        mMap.setOnMarkerClickListener { marker ->
            val intent = Intent(activity, InspectPostActivity::class.java)
            intent.putExtra("Title", marker.title)
            startActivity(intent)
            true
        }

        var location:LatLng
        var marker: Marker
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
                Toast.makeText(activity, "failed marker db search!", Toast.LENGTH_SHORT).show()
            }
    }


}

