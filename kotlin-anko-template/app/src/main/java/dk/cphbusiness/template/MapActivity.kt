package dk.cphbusiness.template

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.activity_map.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast
import java.util.*


class MapActivity : FragmentActivity(), OnMapReadyCallback, LocationListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private val cat = "MapA"
    private val errorMsg : String = " signal has been lost"

    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    private var mMap: GoogleMap? = null
    private var isMapReady : Boolean = false
    private val userMarkers = ArrayList<Marker>()

    lateinit private var locationManager : LocationManager
    private val _minDistance: Float = 2.0f
    private val _minTime: Long = 2L
    private var longitude : Double = 0.0
    private var latitude : Double = 0.0

    var isShowingObj : Boolean = false

    val objxFragment = ObjFragment(this)
    //val mapFragment = MySupportMapFragment(this)
    val mapFragment = SupportMapFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        // TODO: This is how to use my objective class
        //val testObj = Objective("test", "simply toast this", 0)

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

        locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (!checkLocationPermission()) {
            //TODO Dialogue box here explaining why we need permission
            ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }

        if ( isNetworkEnabled() ) {
            if ( isGPSEnabled() ) {
                //TODO write code here

            } else {
                toast("GPS${errorMsg}")
            }

        } else {
            toast("Internet${errorMsg}")
        }
    }

    fun getLastKnownLoc() : LatLng {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, _minTime, _minDistance, this)
        var location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        latitude = location.latitude
        longitude = location.longitude
        return LatLng(latitude, longitude)
    }

    fun zoomToLoc(pos : LatLng) {
        if (isMapReady) {
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(pos))
            mMap!!.animateCamera(CameraUpdateFactory.zoomIn());
            mMap!!.animateCamera(CameraUpdateFactory.zoomTo(15f), 2000, null)
        }
    }

    fun isNetworkEnabled() : Boolean {
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    fun isGPSEnabled() : Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
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
        mMap = googleMap
        isMapReady = true
        mMap!!.isMyLocationEnabled = true
        zoomToLoc(getLastKnownLoc())
    }

    fun checkNetwork() : Boolean {
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    fun checkGps() : Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    override fun onLocationChanged(p0: Location?) {

    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(p0: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(p0: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }// other 'case' lines to check for other
        // permissions this app might request
    }

    fun checkLocationPermission(): Boolean {
        val permission = "android.permission.ACCESS_FINE_LOCATION"
        val res = this.checkCallingOrSelfPermission(permission)
        return res == PackageManager.PERMISSION_GRANTED
    }
}
