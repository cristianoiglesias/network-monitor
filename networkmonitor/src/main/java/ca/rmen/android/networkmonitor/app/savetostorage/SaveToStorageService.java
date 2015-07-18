/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 * 
 * Copyright (C) 2013 Benoit 'BoD' Lubek (BoD@JRAF.org)
 * Copyright (C) 2015 Carmen Alvarez (c@rmen.ca)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.rmen.android.networkmonitor.app.savetostorage;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.widget.Toast;

import java.io.File;

import ca.rmen.android.networkmonitor.R;
import ca.rmen.android.networkmonitor.util.IoUtil;
import ca.rmen.android.networkmonitor.util.Log;

public class SaveToStorageService extends IntentService {
    private static final String TAG = SaveToStorageService.class.getSimpleName();

    public SaveToStorageService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v(TAG, "onHandleIntent: intent = " + intent);

        Parcelable extra = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (extra == null || !(extra instanceof Uri)) {
            displayErrorToast();
            return;
        }

        Uri sourceFileUri = (Uri) extra;
        if (!"file".equals(sourceFileUri.getScheme())) {
            displayErrorToast();
            return;
        }

        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            displayErrorToast();
            return;
        }

        File folder = Environment.getExternalStorageDirectory();
        File src = new File(sourceFileUri.getPath());
        File dest = new File(folder, src.getName());
        if (IoUtil.copy(src, dest))
            displaySuccessToast(dest);
        else
            displayErrorToast();
    }

    private void displaySuccessToast(final File dest) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), getString(R.string.export_save_to_external_storage_success, dest.getAbsolutePath()), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void displayErrorToast() {

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), R.string.export_save_to_external_storage_fail, Toast.LENGTH_LONG).show();
            }
        });
    }
}
