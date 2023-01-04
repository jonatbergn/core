package com.jonatbergn.core.iceandfire.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.jonatbergn.core.iceandfire.android.house.HouseUi

class Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Theme { HouseUi() } }
    }
}
