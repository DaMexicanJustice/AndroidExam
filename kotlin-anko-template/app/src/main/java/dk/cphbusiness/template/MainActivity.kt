package dk.cphbusiness.template

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playBtn.onClick { testStartActivity() }
        profileBtn.onClick { toast("Coming soon") }
        }

    fun testStartActivity() {
        startActivity(intentFor<MapActivity>())
    }
}
