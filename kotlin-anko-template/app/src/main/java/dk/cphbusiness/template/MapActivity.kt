package dk.cphbusiness.template

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.onClick

class MapActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        backBtn.onClick( { finish() } )
    }
}
