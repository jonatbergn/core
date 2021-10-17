package com.jonatbergn.core.iceandfire.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.jonatbergn.core.iceandfire.android.house.HouseUi
import com.jonatbergn.core.iceandfire.app.iceAndFireContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class Activity : AppCompatActivity() {

    private val model by lazy { iceAndFireContext(CoroutineScope(Dispatchers.IO)).model }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Theme { HouseUi(model) } }
    }
}
