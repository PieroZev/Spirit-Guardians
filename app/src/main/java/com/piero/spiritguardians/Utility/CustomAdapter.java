package com.piero.spiritguardians.Utility;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.piero.spiritguardians.Game.Room;
import com.piero.spiritguardians.GameActivity;
import com.piero.spiritguardians.R;
import com.piero.spiritguardians.RoomsActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<Room> localDataSet;
    private Context context;
    private LayoutInflater inflator;
    private String username;
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final TextView textViewPlayerNumber;
        private final Button join;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.txt_roomName);
            textViewPlayerNumber = (TextView) view.findViewById(R.id.txt_playerNumber);
            join = (Button) view.findViewById(R.id.btn_join);
        }

        public TextView getTextView() {
            return textView;
        }

        public Button getJoin() {
            return join;
        }

        public TextView getTextViewPlayerNumber() {
            return textViewPlayerNumber;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CustomAdapter(ArrayList<Room> dataSet, Context context, String username) {
        this.context = context;
        this.localDataSet = dataSet;
        this.inflator = LayoutInflater.from(context);
        this.username = username;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = inflator.inflate(R.layout.room_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            final Room room = localDataSet.get(position);
            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            viewHolder.getTextView().setText(room.getRoomName());
            viewHolder.getTextViewPlayerNumber().setText(room.getPlayerNumber() + "/2");
            viewHolder.getJoin().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (room.getPlayerNumber() < 2) {
                        room.setPlayerNumber(room.getPlayerNumber() + 1);
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("rooms").child(room.getRoomCode());

                        myRef.child("playerNumber").setValue(room.getPlayerNumber());

                        Intent i = new Intent(context, GameActivity.class);
                        i.putExtra("userCode", room.getUserCode());
                        i.putExtra("guestName", username);
                        i.putExtra("roomCode", room.getRoomCode());
                        i.putExtra("isPlayer2", true);
                        i.putExtra("matchCode", room.getMatchCode());
                        i.putExtra("password", room.getPassword());
                        i.putExtra("roomName", room.getRoomName());
                        i.putExtra("playerNumber", room.getPlayerNumber());
                        context.startActivity(i);
                    } else {
                        Toast.makeText(context, "The room has already been occupied", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount () {
            if (localDataSet != null) return localDataSet.size();
            else return 0;
        }

}
