/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2016 Carmen Alvarez (c@rmen.ca)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ca.rmen.android.networkmonitor.app;

import android.content.Context;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatDelegate;
import android.widget.TextView;

import ca.rmen.android.networkmonitor.app.prefs.NetMonPreferences;

public final class Theme {

    private Theme() {
        // prevent instantiation
    }

    public static void setThemeFromSettings(Context context) {
        NetMonPreferences.NetMonTheme theme = NetMonPreferences.getInstance(context).getTheme();
        AppCompatDelegate.setDefaultNightMode(theme == NetMonPreferences.NetMonTheme.DAY ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES);
    }

    /**
     * Modify the compound drawables of the given text view, based on the current theme.
     * If the current theme is
     * {@link ca.rmen.android.networkmonitor.app.prefs.NetMonPreferences.NetMonTheme#DAY}, the
     * drawables will be dark.  If the current theme is {@link ca.rmen.android.networkmonitor.app.prefs.NetMonPreferences.NetMonTheme#NIGHT},
     * the drawables will be light.
     */
    public static void tintCompoundDrawables(Context context, TextView textView) {
        Drawable[] compoundDrawables = textView.getCompoundDrawables();
        int colorFilter = 0x0;
        if (NetMonPreferences.getInstance(context).getTheme() == NetMonPreferences.NetMonTheme.NIGHT) {
            colorFilter = 0xFFFFFF;
        }
        for (Drawable compoundDrawable : compoundDrawables) {
            if (compoundDrawable != null) {
                compoundDrawable.setColorFilter(new LightingColorFilter(0, colorFilter));
            }
        }
    }
}
