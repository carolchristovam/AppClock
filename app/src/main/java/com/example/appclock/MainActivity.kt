package com.example.appclock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.example.appclock.databinding.ActivityMainBinding


class MainActivity() : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val batteryReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent != null && intent.action == Intent.ACTION_BATTERY_CHANGED) {
                    val levelBattery = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
                    val bLevel = levelBattery.toString()
                    Toast.makeText(applicationContext, "Battery Level", Toast.LENGTH_SHORT).show()
                    Toast.makeText(applicationContext, "$bLevel %" , Toast.LENGTH_SHORT).show()
                }
            }
        }

        registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

    }
}

