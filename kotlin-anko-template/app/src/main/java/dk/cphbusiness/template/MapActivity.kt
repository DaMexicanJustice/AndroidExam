package dk.cphbusiness.template

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_map.*
import org.jetbrains.anko.onClick

class MapActivity : FragmentActivity(), OnMapReadyCallback {
    val objxFragment = ObjFragment(this)
    val mapFragment = SupportMapFragment()
    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        supportFragmentManager
                .beginTransaction()
                .add(R.id.map_fragment_container, objxFragment)
                .add(R.id.map_fragment_container, mapFragment)
                .hide(objxFragment)
                .commit()

        objBtn.onClick { showObj() }
        backBtn.onClick {finish()}
    }

    fun hideObj() {
        supportFragmentManager
                .beginTransaction()
                .hide(objxFragment)
                .show(mapFragment)
                .commit()
    }

    fun showObj() {
        supportFragmentManager
                .beginTransaction()
                .show(objxFragment)
                .hide(mapFragment)
                .commit()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap!!.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

}
