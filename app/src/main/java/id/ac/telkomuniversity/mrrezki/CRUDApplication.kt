package id.ac.telkomuniversity.mrrezki

import android.app.Application
import timber.log.Timber

class CRUDApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}