package com.android.example.applocksample

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.SwitchCompat
import com.android.example.applocksample.data.AppSettings
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var appSettings: AppSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            startActivity(Intent(this, SecureActivity::class.java))
        }

        findViewById<SwitchCompat>(R.id.lock).apply {
            isChecked = appSettings.isApplicationSecure

            setOnCheckedChangeListener { _, isChecked ->
                appSettings.isApplicationSecure = isChecked
            }
        }

    }
}
