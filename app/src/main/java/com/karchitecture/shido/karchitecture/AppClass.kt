package com.karchitecture.shido.karchitecture

import android.app.Application
import android.arch.persistence.room.Room

/**
 * Created by Shido on 01/10/2017.
 */
val db by lazy { //Only set this value when somone uses this, and then evaluates this statement
    AppClass.db!!
}

class AppClass: Application() {
    companion object {
        var db: AppDatabase? = null
    }
    override fun onCreate() {
        db = Room.databaseBuilder(this, AppDatabase::class.java, "GDAX").build()
        super.onCreate()
    }
}