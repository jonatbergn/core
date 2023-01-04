package com.jonatbergn.core.iceandfire.android

import android.app.Application
import android.content.Context
import com.jonatbergn.core.iceandfire.android.App.Companion.store
import com.jonatbergn.core.iceandfire.app.AppContext
import com.jonatbergn.core.iceandfire.app.AppModule
import com.jonatbergn.core.iceandfire.app.AppStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class App : Application() {

    private val scope = CoroutineScope(Main)
    private val store by lazy { AppStore(AppContext(AppModule(IO))) }

    override fun onTerminate() {
        super.onTerminate()
        scope.cancel()
    }

    init {
        scope.launch { store.loadNextHousePage() }
    }

    companion object {
        val Context.store get() = (applicationContext as App).store
    }
}
