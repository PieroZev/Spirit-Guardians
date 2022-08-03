package com.piero.spiritguardians;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.piero.spiritguardians.Game.Room;
import com.piero.spiritguardians.Utility.CustomAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import static android.content.ContentValues.TAG;

public class RoomsActivity extends AppCompatActivity {

    private ArrayList<Room> mDataset = new ArrayList<>();
    private Context context = this;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CustomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        initDataset();

    }

    private void initDataset() {
        try {
            String username = getIntent().getStringExtra("guestName");

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("rooms");

            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for (DataSnapshot roomSnapshot: dataSnapshot.getChildren()) {
                        // TODO: rooms are full then remove
                        Room room = roomSnapshot.getValue(Room.class);
                        String roomKey = roomSnapshot.getKey();
                        if (room.getPlayerNumber() != 1){
                            myRef.child(roomKey).removeValue();
                        }else mDataset.add(room);
                    }

                    // BEGIN_INCLUDE(initializeRecyclerView)
                    mRecyclerView = findViewById(R.id.recyclerView);

                    // LinearLayoutManager is used here, this will layout the elements in a similar fashion
                    // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
                    // elements are laid out.
                    mLayoutManager = new LinearLayoutManager(context);

                    mRecyclerView.setLayoutManager(mLayoutManager);

                    mAdapter = new CustomAdapter(mDataset, context, username);
                    // Set CustomAdapter as the adapter for RecyclerView.
                    mRecyclerView.setAdapter(mAdapter);
                    // END_INCLUDE(initializeRecyclerView)
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}