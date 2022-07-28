package com.piero.spiritguardians;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                    usernames[i] = value.toString().split("=")[i + 1].substring(1).split(",")[1];
                    usercodes[i] = Integer.parseInt(value.toString().split("=")[i + 1].substring(1).split(",")[0].trim());
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
            if (view == create) {
                String roomCode = "R" + (int) (10 * Math.random()) + (int) (10 * Math.random()) + (int) (10 * Math.random()) + (int) (10 * Math.random()) + (int) (10 * Math.random());
                //Create Room
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("room/" + roomCode);

                for (int i = 0; i < usersLength; i++) {
                    if (usernames[i].equals(username)) userCode = usercodes[i];
                    else userCode = 0;
                }
                //Create the match code
                String matchCode = "M" + (int) (10 * Math.random()) + (int) (10 * Math.random()) + (int) (10 * Math.random()) + (int) (10 * Math.random()) + (int) (10 * Math.random());

                room1 = new ArrayList<>();
                room1.add(roomName.getText().toString());
                room1.add(roomCode);
                room1.add(roomPassword.getText().toString());
                room1.add(userCode);
                room1.add(1);
                room1.add(matchCode);

                Map<String, Object> updates = new HashMap<>();
                updates.put("0", room1.get(0));
                updates.put("1", room1.get(1));
                updates.put("2", room1.get(2));
                updates.put("3", room1.get(3));
                updates.put("4", room1.get(4));
                updates.put("5", room1.get(5));

                myRef.setValue(updates);

                //
                Intent i = new Intent(this, GameActivity.class);
                i.putExtra("userCode", userCode);
                i.putExtra("guestName", username);
                i.putExtra("matchCode", matchCode);
                i.putExtra("roomCode", roomCode);
                startActivity(i);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}