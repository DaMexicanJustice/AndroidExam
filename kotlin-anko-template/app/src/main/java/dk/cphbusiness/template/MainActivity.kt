package dk.cphbusiness.template

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import org.jetbrains.anko.intentFor

class MainActivity : FragmentActivity() {

    val statFragment = StatFragment(this)
    val mainFragment = MainFragment(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupSupportFragment()

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
