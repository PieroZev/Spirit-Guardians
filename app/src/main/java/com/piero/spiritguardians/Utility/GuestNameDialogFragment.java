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

public class GuestNameDialogFragment extends DialogFragment {

        private Context context;
        private String message;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            //LayoutInflater inflater = requireActivity().getLayoutInflater();
            //EditText guestName = (EditText) inflater.inflate(R.layout.guestname_layout, null).getParent().removeView(R.layout.guestname_layout);
            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setMessage(message)
                    //.setView(guestName)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // START THE GAME!
                            //Set username as Guest
                            String username = "Invitado" + (int) (10 * Math.random()) + (int) (10 * Math.random()) + (int) (10 * Math.random()) + (int) (10 * Math.random()) + (int) (10 * Math.random());

                            Intent i = new Intent (context, StartMenu.class);
                            i.putExtra("guestName", username);
                            startActivity(i);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            Toast.makeText(context, "You must be logged in to continue", Toast.LENGTH_SHORT).show();
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }

    @Nullable
    @Override
    public Context getContext() {
        return context;
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
