package com.example.groupproject

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import androidx.fragment.app.FragmentManager
import com.google.android.gms.maps.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private lateinit var googleMap: GoogleMap
        lateinit var mMapView: MapView

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_blank, container, false)
        mMapView = rootView.findViewById(R.id.mapView)
        mMapView.onCreate(savedInstanceState)
        mMapView.onResume()
        MapsInitializer.initialize(activity?.applicationContext)
        mMapView.getMapAsync( OnMapReadyCallback {
            googleMap = it

            val sydney = LatLng(-34.0, 151.0)
            googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        })
        return rootView
    }


}