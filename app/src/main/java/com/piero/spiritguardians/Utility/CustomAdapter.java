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

    private Map<String, List<Object>> localDataSet;
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
    public CustomAdapter(Map<String, List<Object>> dataSet, Context context, String username) {
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

        String dataString = localDataSet.toString().split("=")[position + 1];
        String[] parsedString = dataString.substring(1).split(",");

        String playerNumber1 = parsedString[4].trim();
        final int playerNumber = Integer.parseInt(playerNumber1);
        if (playerNumber == 1) {
            String roomName = parsedString[0];
            String roomCode = parsedString[1];
            String password = parsedString[2];
            final int userCode = Integer.parseInt(parsedString[3].trim());

            String preMatchCode = parsedString[5].trim();
            final String matchCode = preMatchCode.substring(0, preMatchCode.length() - 1).split("]")[0];
        /*dataSet[0] = "Sala";
        dataSet[1] = "R10010";
        dataSet[2] = "Sala123";
        dataSet[3] = 0;
        dataSet[4] = 0;*/

            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            viewHolder.getTextView().setText(roomName);
            viewHolder.getTextViewPlayerNumber().setText(playerNumber + "/2");
            viewHolder.getJoin().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (playerNumber < 2) {
                        int newPlayerNumber = playerNumber + 1;
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("room/" + roomCode);

                        List<Object> room1 = new ArrayList<>();
                        room1.add(roomName);
                        room1.add(roomCode);
                        room1.add(password);
                        room1.add(userCode);
                        room1.add(newPlayerNumber);
                        room1.add(matchCode);

                        Map<String, Object> updates = new HashMap<>();
                        updates.put("0", room1.get(0));
                        updates.put("1", room1.get(1));
                        updates.put("2", room1.get(2));
                        updates.put("3", room1.get(3));
                        updates.put("4", room1.get(4));
                        updates.put("5", room1.get(5));
                        myRef.updateChildren(updates);

                        Intent i = new Intent(context, GameActivity.class);
                        i.putExtra("userCode", userCode);
                        i.putExtra("guestName", username);
                        i.putExtra("roomCode", roomCode);
                        i.putExtra("isPlayer2", true);
                        i.putExtra("matchCode", matchCode);
                        i.putExtra("password", password);
                        i.putExtra("roomName", roomName);
                        i.putExtra("playerNumber", playerNumber);
                        context.startActivity(i);
                    } else {
                        Toast.makeText(context, "The room has already been occupied", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else{
            ViewGroup layout = (ViewGroup) viewHolder.getJoin().getParent();
            //viewHolder.getTextView().setText("Sala llena");
            layout.removeAllViews();
        }
    }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount () {
            if (localDataSet != null) return localDataSet.size();
            else return 0;
            //return 2;
        }

}
