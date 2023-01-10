package com.jonatbergn.core.cars.android

import android.app.Application
import android.content.Context
import com.jonatbergn.cars.AppContext
import com.jonatbergn.cars.AppModule
import com.jonatbergn.cars.AppStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class App : Application() {

    private val store = AppStore(AppContext(AppModule(IO)))
    private val scope = CoroutineScope(Main)

    override fun onTerminate() {
        super.onTerminate()
        scope.cancel()
    }

    init {
        scope.launch { store.loadCarStubs() }
        scope.launch { store.loadBookings() }
    }

    companion object {
        val Context.store get() = (applicationContext as App).store
    }
}
