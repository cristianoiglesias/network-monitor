/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2014 Carmen Alvarez (c@rmen.ca)
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
package org.jraf.android.networkmonitor.app.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import org.jraf.android.networkmonitor.Constants;

/**
 * A dialog fragment with a title, message, ok and cancel buttons. This is based on ConfirmDialogFragment from the scrum chatter project.
 */
public class ConfirmDialogFragment extends DialogFragment { // NO_UCD (use default)

    private static final String TAG = Constants.TAG + "/" + ConfirmDialogFragment.class.getSimpleName();

    /**
     * An activity which contains a confirmation dialog fragment should implement this interface to be notified if the user clicks ok on the dialog.
     */
    public interface DialogButtonListener {
        void onOkClicked(int actionId, Bundle extras);

        void onCancelClicked(int actionId, Bundle extras);
    }

    public ConfirmDialogFragment() {
        super();
    }

    /**
     * @return an AlertDialog with a title, message, ok, and cancel buttons.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.v(TAG, "onCreateDialog: savedInstanceState = " + savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle arguments = getArguments();
        builder.setTitle(arguments.getString(DialogFragmentFactory.EXTRA_TITLE)).setMessage(arguments.getString(DialogFragmentFactory.EXTRA_MESSAGE));
        final int actionId = arguments.getInt(DialogFragmentFactory.EXTRA_ACTION_ID);
        final Bundle extras = arguments.getBundle(DialogFragmentFactory.EXTRA_EXTRAS);
        OnClickListener positiveListener = null;
        OnClickListener negativeListener = null;
        if (getActivity() instanceof DialogButtonListener) {
            positiveListener = new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FragmentActivity activity = getActivity();
                    if (activity == null) Log.w(TAG, "User clicked on dialog after it was detached from activity. Monkey?");
                    else
                        ((DialogButtonListener) activity).onOkClicked(actionId, extras);
                }
            };
            negativeListener = new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FragmentActivity activity = getActivity();
                    if (activity == null) Log.w(TAG, "User clicked on dialog after it was detached from activity. Monkey?");
                    else
                        ((DialogButtonListener) activity).onCancelClicked(actionId, extras);
                }
            };
        }
        builder.setNegativeButton(android.R.string.cancel, negativeListener);
        builder.setPositiveButton(android.R.string.ok, positiveListener);
        final AlertDialog dialog = builder.create();
        return dialog;

    }
}