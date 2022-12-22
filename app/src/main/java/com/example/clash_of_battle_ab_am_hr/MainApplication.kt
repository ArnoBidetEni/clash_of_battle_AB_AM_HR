package com.example.clash_of_battle_ab_am_hr

import android.app.Application
import com.example.clash_of_battle_ab_am_hr.database.PlayerDatabase

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        PlayerDatabase.getDatabase(this)
    }
}