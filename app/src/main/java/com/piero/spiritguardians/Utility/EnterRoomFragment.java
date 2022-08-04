package com.piero.spiritguardians.Utility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.piero.spiritguardians.GameActivity;
import com.piero.spiritguardians.StartMenu;

public class EnterRoomFragment extends CustomDialogFragment {

    private EditText enterPassword;
    private String password;
    private String username;
    private String matchCode;

    public EnterRoomFragment(Context context, String message, String password, String username, String matchCode) {
        super(context, message);
        this.password = password;
        this.username = username;
        this.matchCode = matchCode;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(super.getMessage())
                    .setView(enterPassword)
                    .setPositiveButton("Aceptar", (dialog, id) -> {
                        // START THE GAME!
                        if (enterPassword.getText().toString().equals(password)) {
                            Intent i = new Intent(EnterRoomFragment.super.getContext(), GameActivity.class);
                            i.putExtra("guestName", username);
                            i.putExtra("isPlayer2", true);
                            i.putExtra("matchCode", matchCode);
                            startActivity(i);
                        } else
                            Toast.makeText(EnterRoomFragment.super.getContext(), "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancelar", (dialog, id) -> {
                        // User cancelled the dialog
                        Toast.makeText(EnterRoomFragment.super.getContext(), "Acceso denegado", Toast.LENGTH_SHORT).show();
                    });
            // Create the AlertDialog object and return it
            return builder.create();
    }
}
