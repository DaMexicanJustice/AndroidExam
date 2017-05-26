package dk.cphbusiness.template.user

/**
 * Created by xboxm on 26-05-2017.
 */

import android.app.Application

class App : Application() {
    companion object {
        lateinit var instance: App private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}