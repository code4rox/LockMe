/***
 Copyright (c) 2012 CommonsWare, LLC
 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License. You may obtain a copy
 of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
 by applicable law or agreed to in writing, software distributed under the
 License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 OF ANY KIND, either express or implied. See the License for the specific
 language governing permissions and limitations under the License.

 Covered in detail in the book _The Busy Coder's Guide to Android Development_
 https://commonsware.com/Android
 */

package com.code4rox.lockme;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

public class LockMeNowActivity extends Activity {


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        requestForDoNotDisturbPermissionOrSetDoNotDisturbForApi23AndUp();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestForDoNotDisturbPermissionOrSetDoNotDisturbForApi23AndUp() {

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // if user granted access else ask for permission
        if (notificationManager != null && notificationManager.isNotificationPolicyAccessGranted()) {
            AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
               if(audioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE || audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT){
                   audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                   finish();
               }else{
                   audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                   finish();
               }
            }
        } else {
            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivityForResult(intent, 211);
        }
    }

}