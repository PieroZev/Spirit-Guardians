package com.piero.spiritguardians;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartMenu extends AppCompatActivity {

    private Button join;
    private Button create;
    private Button deck;
    private TextView greeting;

    public String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        join = findViewById(R.id.btn_join);
        create = findViewById(R.id.btn_create);
        deck = findViewById(R.id.btn_deck);
        greeting = findViewById(R.id.txt_greeting);

        username = getIntent().getStringExtra("guestName");
        greeting.setText("Bienvenido " + username);
    }

    public void onClick(View view) {
        try {
            if (view == create) {
                Intent i = new Intent(this, CreateRoomActivity.class);
                i.putExtra("guestName", username);
                startActivity(i);
            }

            if (view == join) {
                Intent i = new Intent(this, RoomsActivity.class);
                i.putExtra("guestName", username);
                startActivity(i);
            }

            if (view == deck) {
                Intent i = new Intent(this, DeckActivity.class);
                startActivity(i);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}