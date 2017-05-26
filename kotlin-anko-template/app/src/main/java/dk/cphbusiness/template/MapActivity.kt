package dk.cphbusiness.template

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
import com.google.android.gms.maps.model.MarkerOptions
import dk.cphbusiness.template.system.SaveLoad
import dk.cphbusiness.template.user.User
import kotlinx.android.synthetic.main.activity_map.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast
import java.util.*


class MapActivity() : FragmentActivity(), OnMapReadyCallback, LocationListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private val cat = "MapA"
    private val errorMsg : String = " signal has been lost"

    //private val LOCATION_PERMISSION_REQUEST_CODE = 1

    private var mMap: GoogleMap? = null
    private var isMapReady : Boolean = false

    lateinit private var locationManager : LocationManager
    private val _minDistance: Float = 2.0f
    private val _minTime: Long = 2L
    private var longitude : Double = 0.0
    private var latitude : Double = 0.0

    private var isShowingObj : Boolean = false

    private val objxFragment = ObjFragment(this)
    //val mapFragment = MySupportMapFragment(this)
    private val mapFragment = SupportMapFragment()

    private val random = Random()
    var currentObjective : Any? = null
    private var isSprintEvent : Boolean = false
    private var isDiscoverEvent : Boolean = false
    private var dMarker : Marker? = null

    private var timestamp : Long = 0
    private var eventDuraton : Long = 120000

    private lateinit var origin : LatLng

    private val system = SaveLoad()

    //TODO: Entity class variables
    val user : User = User(0,0f,0f,1)
    var metersWalked = 0f
    var markersDiscovered = 0
    var bestSprint = 0f

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
        endBtn.onClick { endSession() }

        mapFragment.getMapAsync(this)

        locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if ( isNetworkEnabled() ) {
            if ( isGPSEnabled() ) {
                //TODO write code here

            } else {
                //toast("GPS${errorMsg}")
            }

        } else {
            //toast("Internet${errorMsg}")
        }

        loadUserInfo()

    }

    fun loadUserInfo() {
        val u = system.load("user", this)
        metersWalked = u.totalMWalked
        //markersDiscovered = u.markersDiscovered
        bestSprint = u.bestSprint
    }

    fun endSession() {
        user.markersDiscovered += markersDiscovered
        if (bestSprint > user.bestSprint) user.bestSprint = bestSprint
        user.totalMWalked += metersWalked
        system.save("user", user, this)
        finish()
    }


    fun newEvent() {
        val r : Int = random.nextInt(2)
        when (r) {
            0 -> {
                // TODO: Generate a random LatLng, create objective
                val prevLoc =  getLastKnownLoc()
                val target = calcNewDistance(prevLoc)
                val objLat = target.latitude
                val objLong = target.longitude
                currentObjective = newDiscoverObjective(objLat, objLong)
            }
            1 -> {
                // 120 seconds is the time limit
                currentObjective = newSprintObjective(120000)
            }
        }

        if (currentObjective != null) {
            when (currentObjective) {
                is SprintObjective -> {}
                is DiscoverObjective -> {
                    // Smart cast is not possible on mutable type (currentObjective)
                    val obj = currentObjective as DiscoverObjective
                    addMarkerToMap(obj.latitude, obj.longitude)
                }
            }
            objxFragment.updateObjective()
        } else { }

    }

    private fun calcNewDistance(prevLoc : LatLng) : LatLng {
        // Calculate new LatLng from previous + n meters using radius_earth, pi, 180 degrees
        // Approximately 400 meters away from player
        val r_earth = 6378
        val dx : Double = (random.nextInt(2) + 1.0) / 4.0
        val dy : Double = (random.nextInt(2) + 1.0) / 4.0
        val new_latitude  = prevLoc.latitude  + (dy / r_earth) * (180 / Math.PI)
        val new_longitude = prevLoc.longitude + (dx / r_earth) * (180 / Math.PI) / Math.cos(latitude * Math.PI/180)
        return LatLng(new_latitude, new_longitude)
    }

    fun newSprintObjective(timeLimit: Long) : SprintObjective {
        toast("Sprint Event Has Started")
        val name = "Sprint"
        val goal = "Walk as far as possible!"
        isSprintEvent = true
        timestamp = System.currentTimeMillis() + eventDuraton
        return SprintObjective(timeLimit, name, goal)
    }

    fun newDiscoverObjective(latitude : Double, longitude : Double) : DiscoverObjective {
        toast("Discover Event Has Started")
        val name = "Discover"
        val goal = "Walk to the marker"
        isDiscoverEvent = true
        return DiscoverObjective(latitude, longitude, name, goal)
    }

    fun addMarkerToMap(latitude: Double, longitude: Double) {
        dMarker = mMap!!.addMarker(MarkerOptions().position(LatLng(latitude, longitude)).title("Test Marker"))
        zoomToLoc(LatLng(latitude,longitude))
    }

    fun getLastKnownLoc() : LatLng {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, _minTime, _minDistance, this)
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        latitude = location.latitude
        longitude = location.longitude
        return LatLng(latitude, longitude)
    }

    fun zoomToLoc(pos : LatLng) {
        if (isMapReady) {
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(pos))
            mMap!!.animateCamera(CameraUpdateFactory.zoomIn())
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
        origin = getLastKnownLoc()
        newEvent()
    }

    fun checkNetwork() : Boolean {
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    fun checkGps() : Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    override fun onLocationChanged(p0: Location?) {
        if (isSprintEvent) {
            val startLoc = getLastKnownLoc()
            if (timestamp <= System.currentTimeMillis())
                toast("Sprint Event: Time is up")
                //Do something after 12 seconds (final build: 120 seconds)
                val endLoc = getLastKnownLoc()
                val score = FloatArray(1)
                Location.distanceBetween(startLoc.latitude, startLoc.longitude, endLoc.latitude, endLoc.longitude, score)
                // TODO: Update user entity class personal best for distance if the new score is better than the last one
                if (score[0] > bestSprint) {
                    bestSprint = score[0]
                }
                isSprintEvent = false
        } else if (isDiscoverEvent) {
            if (dMarker != null) {
                val location = getLastKnownLoc()
                val loclat = location.latitude
                val loclong = location.longitude
                val maplat = dMarker!!.position.latitude
                val maplong = dMarker!!.position.longitude
                val dist = FloatArray(1)
                Location.distanceBetween(loclat, loclong, maplat, maplong, dist)
                if (dist[0] <= 1.0) {
                    // TODO: Access user entity class and increment number of discover events completed here
                    toast("Completed a discover event")
                    markersDiscovered++
                    dMarker!!.remove()
                    isDiscoverEvent = false
                }
            }
        } else {
            // Might as well do event detection at run-time (location updates)
            newEvent()
        }
        // When location changes, update how far we have walked. We use Math.abs to ignore negative/positive number issues

        val distanceWalked = FloatArray(1)
        Location.distanceBetween(origin.latitude, origin.longitude, getLastKnownLoc().latitude, getLastKnownLoc().longitude,
                distanceWalked)
        //metersWalked += Math.abs(distanceWalked[1])
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
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }// other 'case' lines to check for other
        // permissions this app might request
    }
}
