package com.piero.spiritguardians;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.piero.spiritguardians.Utility.GuestNameDialogFragment;


public class MainActivity extends AppCompatActivity {

    private Button login;
    private Button start;
    private Button register;
    private Button forgot;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.btn_login);
        start = findViewById(R.id.btn_start);
        register = findViewById(R.id.btn_register);
        forgot = findViewById(R.id.btn_forgot);

        bundle = savedInstanceState;

    }

    public void onClick(View view) {
        try {
            if (view == login) {
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
            }

            if (view == start) {

                GuestNameDialogFragment dialog = new GuestNameDialogFragment(this, "Se le asignará un nombre aleatorio");
                dialog.show(getSupportFragmentManager(), "game");
            }

            if (view == register) {
                Intent i = new Intent(this, RegisterActivity.class);
                startActivity(i);

            }

            if (view == forgot) {
                //TODO
                Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}