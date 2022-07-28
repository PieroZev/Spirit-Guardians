package com.piero.spiritguardians;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.piero.spiritguardians.Players.User;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText username;
    private EditText password;
    private EditText confirmPassword;
    private Button register;

    private List<Object> user1;
    private int usersLength = 0;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_password);
        confirmPassword = findViewById(R.id.txt_password2);
        register = findViewById(R.id.btn_register);
        username = findViewById(R.id.txt_username);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                usersLength = value.size();
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
            if (view == register && password.getText().toString().equals(confirmPassword.getText().toString()) && !username.getText().toString().trim().isEmpty()) {
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    //updateUI(user);
                                    //Create user in DB
                                    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

                                    //Set username as Guest or Registered User;
                                    if (username.getText().toString().isEmpty())
                                        username.setText("Invitado" + (int) (10 * Math.random()) + (int) (10 * Math.random()) + (int) (10 * Math.random()) + (int) (10 * Math.random()) + (int) (10 * Math.random()));

                                    user1 = new ArrayList<>();
                                    user1.add(usersLength + 1);
                                    user1.add(username);
                                    user1.add(email.getText().toString());

                                    database.child("users").child("user" + (usersLength + 1)).setValue(user1);
                                    //Finish create user
                                    Intent i = new Intent(RegisterActivity.this, StartMenu.class);
                                    i.putExtra("guestName", username.getText().toString());
                                    startActivity(i);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                }
                            }
                        });
            } else {
                Toast.makeText(this, "Please confirm your password", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}