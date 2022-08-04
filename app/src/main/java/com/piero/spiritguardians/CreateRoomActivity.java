package com.piero.spiritguardians;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.piero.spiritguardians.Game.Room;
import com.piero.spiritguardians.Players.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class CreateRoomActivity extends AppCompatActivity {

    private Button create;
    private EditText roomName;
    private EditText roomPassword;

    private int userCode = 0; //0 == null, 1 == admin.
    private int roomLength;
    private int usersLength;
    private List<Object> room1;
    private String[] usernames;
    private int[] usercodes;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        create = findViewById(R.id.btn_create);
        roomName = findViewById(R.id.txt_roomName);
        roomPassword = findViewById(R.id.txt_roomPassword);

        username = getIntent().getStringExtra("guestName");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("room");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                if(value != null)
                roomLength = value.size();
                else roomLength = 0;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        //TODO verificar que pase el valor correcto
        DatabaseReference myUserRef = FirebaseDatabase.getInstance().getReference("users");
        myUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                usersLength = value.size();
                usernames = new String[usersLength];
                usercodes = new int[usersLength];
                for(int i=0;i<usersLength;i++){
                    usernames[i] = value.toString().split("=")[i + 1].substring(1).split(",")[1].trim();
                    usercodes[i] = Integer.parseInt(value.toString().split("=")[i + 1].substring(1).split(",")[0].trim());
                    System.out.println(usernames[i]+" "+usercodes[i]);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void onClick(View view) {
        try {
            if(!roomName.getText().toString().trim().isEmpty()) {
                if (view == create) {
                    //Create Room
                    for (int i = 0; i < usersLength; i++) {
                        if (usernames[i].equals(username)) userCode = usercodes[i];
                    }
                    if(userCode != 0 || roomPassword.getText().toString().trim().isEmpty()) {

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("rooms");
                        //Create the match code
                        String matchCode = "M" + (int) (10 * Math.random()) + (int) (10 * Math.random()) + (int) (10 * Math.random()) + (int) (10 * Math.random()) + (int) (10 * Math.random());
                        String roomCode = myRef.push().getKey();

                        Room room = new Room(roomName.getText().toString(), roomCode, roomPassword.getText().toString(), userCode, 1, matchCode);

                        myRef.child(roomCode).setValue(room);

                        //
                        Intent i = new Intent(this, GameActivity.class);
                        i.putExtra("userCode", userCode);
                        i.putExtra("guestName", username);
                        i.putExtra("matchCode", matchCode);
                        i.putExtra("roomCode", roomCode);
                        startActivity(i);
                    } else {
                        Toast.makeText(this, "Debes estar registrado para colocar una contraseña", Toast.LENGTH_SHORT).show();
                        roomPassword.setText("");
                    }
                }
            }else {
                Toast.makeText(this, "Nombre de la sala vacío", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}