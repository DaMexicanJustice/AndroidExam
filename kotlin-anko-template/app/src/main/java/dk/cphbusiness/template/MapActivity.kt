package dk.cphbusiness.template

import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_map.*
import org.jetbrains.anko.onClick




class MapActivity : FragmentActivity(), OnMapReadyCallback {

    private val cat = "MapA"

    private var mMap: GoogleMap? = null
    lateinit private var locationManager : LocationManager

    var isShowingObj : Boolean = false

    val objxFragment = ObjFragment(this)
    //val mapFragment = MySupportMapFragment(this)
    val mapFragment = SupportMapFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        supportFragmentManager
                .beginTransaction()
                .add(R.id.map_fragment_container, objxFragment)
                //.add(R.id.map_fragment_container, mySupMapFragment)
                .add(R.id.map_fragment_container, mapFragment)
                .hide(objxFragment)
                .commit()

        objBtn.onClick {
            if (!isShowingObj) showObj(mapFragment)
            else hideObj(mapFragment)
        }
        endBtn.onClick { finish() }

        mapFragment.getMapAsync(this)

        //locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    fun showMap(mapFragment : SupportMapFragment) {
        supportFragmentManager
                .beginTransaction()
                //.show(mySupMapFragment)
                .hide(objxFragment)
                .show(mapFragment)
                .commit()
    }

    fun hideMap(mapFragment : SupportMapFragment) {
        supportFragmentManager
                .beginTransaction()
                //.hide(mySupMapFragment)
                .show(objxFragment)
                .hide(mapFragment)
                .commit()
    }


    fun hideObj(mapFragment : SupportMapFragment) {
        supportFragmentManager
                .beginTransaction()
                .hide(objxFragment)
                //.show(mySupMapFragment)
                .show(mapFragment)
                .commit()
        isShowingObj = false
    }

    fun showObj(mapFragment : SupportMapFragment) {
        supportFragmentManager
                .beginTransaction()
                .show(objxFragment)
                //.hide(mySupMapFragment)
                .hide(mapFragment)
                .commit()
        isShowingObj = true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Log.d(cat, "MapReady")
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap!!.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    fun checkNetwork() : Boolean {
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    fun checkGps() : Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

}
