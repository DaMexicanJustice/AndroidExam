package dk.cphbusiness.template

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        message.text = "A Kotlin Activity"
        toastButton.onClick { toast("Toast button clicked") }
        }

    fun showJavaClicked(view: View) {
        startActivity(Intent(this, JavaActivity::class.java))
        }

    }
