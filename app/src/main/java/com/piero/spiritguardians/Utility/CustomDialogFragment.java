package com.piero.spiritguardians.Utility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.piero.spiritguardians.StartMenu;

public abstract class CustomDialogFragment extends DialogFragment {

        private Context context;
        private String message;

        @Override
        public abstract Dialog onCreateDialog(Bundle savedInstanceState);

    @Nullable
    @Override
    public Context getContext() {
        return context;
    }

    public CustomDialogFragment(Context context, String message){
        this.context = context;
        this.message = message;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
