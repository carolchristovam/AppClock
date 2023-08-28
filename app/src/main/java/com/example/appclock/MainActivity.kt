package com.example.appclock

import android.R.attr.level
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.appclock.databinding.ActivityMainBinding


class MainActivity() : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var isChecked = true
    var batteryLvl = ""


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
                    batteryLvl = bLevel

                }
            }
        }

        registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        binding.checkBoxBattery.setOnClickListener {
            if (isChecked) {
                isChecked = false
                binding.textBattery.visibility = View.GONE

            } else {
                isChecked = true
                binding.textBattery.visibility = View.VISIBLE


                if (intent != null) {
                    batteryLvl
                    binding.textBattery.text = "$batteryLvl %"
                }

            }
            binding.checkBoxBattery.isChecked = isChecked
        }

        binding.icClose.setOnClickListener {
            binding.checkBoxBattery.visibility=View.GONE
            binding.textBatteryLevel.visibility=View.GONE
            binding.icClose.visibility=View.GONE
        }

        binding.icMenu.setOnClickListener {
            binding.checkBoxBattery.visibility=View.VISIBLE
            binding.textBatteryLevel.visibility=View.VISIBLE
            binding.icClose.visibility=View.VISIBLE
        }

    }
}

