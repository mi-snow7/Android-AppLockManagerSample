package com.android.example.applocksample.lockmanager

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.android.example.applocksample.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.*
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * Lock screen.
 */
class AppLockActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var appLockManager: AppLockManager

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private val biometricAuthCallBack = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            Toast.makeText(applicationContext,
                "Authentication error: $errString",
                Toast.LENGTH_SHORT).show()
            // TODO: Error handling and logout if necessary.
        }

        override fun onAuthenticationFailed() {
            Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT).show()
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            lifecycleScope.launch {

                val appProcessCheck = withContext(Dispatchers.IO) {
                    var r = false
                    for (i in 1..10) {
                        // Wait 10 times(max 1 sec) for the app to return to the foreground.
                        if (appLockManager.isAppForeground) {
                            r = true
                            break
                        }
                        delay(100)
                    }
                    r
                }

                if (appProcessCheck) {
                    Toast.makeText(applicationContext,
                        "Authentication succeeded!",
                        Toast.LENGTH_SHORT).show()
                    finish()
                }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_applock)

        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor, biometricAuthCallBack)
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric authentication")
            .setSubtitle("Unlock using your biometric credential")
            .setDeviceCredentialAllowed(true)
            .build()

        // Prompt appears when user clicks "Authenticate".
        val biometricLoginButton = findViewById<Button>(R.id.button)
        biometricLoginButton.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }

    override fun onBackPressed() {
        // blocking back button
    }
}
