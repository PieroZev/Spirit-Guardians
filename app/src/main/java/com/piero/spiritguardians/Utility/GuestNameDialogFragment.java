package com.piero.spiritguardians.Utility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.piero.spiritguardians.R;
import com.piero.spiritguardians.StartMenu;

public class GuestNameDialogFragment extends CustomDialogFragment {

    public GuestNameDialogFragment(Context context, String message) {
        super(context, message);
    }

    @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage(super.getMessage())
                    //.setView(guestName)
                    .setPositiveButton("Aceptar", (dialog, id) -> {
                        // START THE GAME!
                        //Set username as Guest
                        String username = "Invitado" + (int) (10 * Math.random()) + (int) (10 * Math.random()) + (int) (10 * Math.random()) + (int) (10 * Math.random()) + (int) (10 * Math.random());

                        Intent i = new Intent (GuestNameDialogFragment.super.getContext(), StartMenu.class);
                        i.putExtra("guestName", username);
                        startActivity(i);
                    })
                    .setNegativeButton("Cancelar", (dialog, id) -> {
                        // User cancelled the dialog
                        Toast.makeText(GuestNameDialogFragment.super.getContext(), "Registrate para continuar", Toast.LENGTH_SHORT).show();
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }
}
