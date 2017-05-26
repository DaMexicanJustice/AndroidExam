package dk.cphbusiness.template

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupSupportFragment()

        }

    fun save(u : User) {
        system.save("user", u, this)
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
        val u = User(1,100f,1000f,5)
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
    }

    fun hideStats() {
        supportFragmentManager
                .beginTransaction()
                .hide(statFragment)
                .show(mainFragment)
                .commit()
    }

}
