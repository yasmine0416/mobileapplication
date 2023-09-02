/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tensorflow.lite.examples.audio

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import org.tensorflow.lite.examples.audio.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            // Workaround for Android Q memory leak issue in IRequestFinishCallback$Stub.
            // (https://issuetracker.google.com/issues/139738913)
            finishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }

    // Declare MyService as a static inner class using companion object
    companion object {
        class MyService : Service() {
            override fun onBind(intent: Intent?): IBinder? {
                // Not used in this example
                return null
            }

            override fun onCreate() {
                super.onCreate()

                // Create and show a foreground notification
                val notification = NotificationCompat.Builder(this, "my_channel")
                    .setContentTitle("My Service")
                    .setContentText("Running in the foreground")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .build()

                startForeground(1, notification)
            }

            override fun onDestroy() {
                super.onDestroy()

                // Stop the foreground service
                stopForeground(true)

                // Stop the service
                stopSelf()
            }
        }
    }
}

