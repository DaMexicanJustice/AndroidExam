package dk.cphbusiness.template

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import dk.cphbusiness.template.system.SaveLoad
import dk.cphbusiness.template.user.User
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class MainActivity : FragmentActivity() {

    val statFragment = StatFragment(this)
    val mainFragment = MainFragment(this)
    //val uc : UserController = UserController()
    lateinit var user : User
    val system = SaveLoad()
    lateinit var mediaPlayer : MediaPlayer
    var isPlaying : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Load gets user data, but if that data does not exist, it creates a new
        load()
        //createNewUser()
        //save(user)
        setupSupportFragment()

        if (!checkLocationPermission()) {
            //TODO Dialogue box here explaining why we need permission
            ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }

        }

    // Stop the music if doing something else on the phone
    override fun onPause() {
        super.onPause()
        playStopMusic()
    }

    override fun onResume() {
        super.onResume()
        playStopMusic()
    }

    fun checkLocationPermission(): Boolean {
        val permission = "android.permission.ACCESS_FINE_LOCATION"
        val res = this.checkCallingOrSelfPermission(permission)
        return res == PackageManager.PERMISSION_GRANTED
    }

    fun playStopMusic() {
        if (!isPlaying) {
            mediaPlayer = MediaPlayer.create(this, R.raw.sunsetbridge)
            mediaPlayer.start()
        } else {
            mediaPlayer.stop()
        }
        isPlaying = !isPlaying
    }

    fun startOver() {
        createNewUser()
        save(user)
    }

    fun createNewUser() {
        user = User(1,0f,0f,0)
    }

    fun save(u : User) {
        system.save("user", u, this)
        user = u
    }

    fun load () {
        user = system.load("user", this)
    }

    fun testLoad() {
        user = system.load("user", this)
        toast("Discover Events Completed: ${user.markersDiscovered}, Best sprint event PB: ${user.bestSprint}" +
                " and you have walked: ${user.totalMWalked}m in total ")
    }

    fun testSave() {
        val u = User(1,1000f,300f,5)
        system.save("user", u, this)
    }

    fun startMapActivity() {
        startActivity(intentFor<MapActivity>())
    }

    fun setupSupportFragment() {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.main_fragment_container, statFragment)
                .add(R.id.main_fragment_container, mainFragment)
                .hide(statFragment)
                .show(mainFragment)
                .commit()
    }

    fun showStats() {
        supportFragmentManager
                .beginTransaction()
                .hide(mainFragment)
                .show(statFragment)
                .commit()
        load()
        statFragment.updateStats(statFragment.view)
    }

    fun hideStats() {
        supportFragmentManager
                .beginTransaction()
                .hide(statFragment)
                .show(mainFragment)
                .commit()
    }

}
