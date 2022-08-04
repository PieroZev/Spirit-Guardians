package com.piero.spiritguardians.Utility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.piero.spiritguardians.StartMenu;

public class WinDefeatDialogFragment extends CustomDialogFragment {

    private String username;

    public WinDefeatDialogFragment(Context context, String message, String username) {
        super(context, message);
        this.username = username;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(super.getMessage())
                //.setView(guestName)
                .setPositiveButton("Aceptar", (dialog, id) -> {
                    // START THE GAME!
                    //Set username as Guest
                    Intent i = new Intent (WinDefeatDialogFragment.super.getContext(), StartMenu.class);
                    i.putExtra("guestName", username);
                    startActivity(i);
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
