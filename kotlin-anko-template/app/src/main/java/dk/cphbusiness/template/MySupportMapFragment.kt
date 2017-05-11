package dk.cphbusiness.template

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.my_support_map_fragment.view.*
import org.jetbrains.anko.onClick

/**
 * Created by xboxm on 11-05-2017.
 */

class MySupportMapFragment(val activity: MapActivity) : SupportMapFragment(), OnMapReadyCallback {

    //private var mMap: GoogleMap? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.my_support_map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.vObjBtn.onClick { activity.showObj() }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        /*
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap!!.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        */
    }

}

