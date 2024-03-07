package com.example.startwarsdemo

import android.app.Application
import com.example.startwarsdemo.di.component.AppComponent
import com.example.startwarsdemo.di.component.DaggerAppComponent
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application()
