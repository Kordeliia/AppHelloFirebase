package com.example.holafirebase

import android.app.Application
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        //Activando modo off-line del firebase
        Firebase.database.setPersistenceEnabled(true)
    }
}