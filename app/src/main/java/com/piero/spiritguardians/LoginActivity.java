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

import java.util.Map;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private EditText email;
    private EditText password;

    private FirebaseAuth mAuth;
    private int usersLength;
    private String[] usernames;
    private String[] emails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        login = findViewById(R.id.btn_login);
        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_password);

        DatabaseReference myUserRef = FirebaseDatabase.getInstance().getReference("users");
        myUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                usersLength = value.size();
                usernames = new String[usersLength];
                emails = new String[usersLength];
                for(int i=0;i<usersLength;i++){
                    usernames[i] = value.toString().split("=")[i + 1].substring(1).split(",")[1];
                    String emailsConCorchete = value.toString().split("=")[i + 1].substring(1).split(",")[2].trim();
                    emails[i] = emailsConCorchete.substring(0, emailsConCorchete.length()-1);
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
            if (view == login) {
                if (!email.getText().toString().trim().isEmpty() && !password.getText().toString().trim().isEmpty()) {

                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        //Set username as Guest or Registered User;
                                        DatabaseReference myUserRef = FirebaseDatabase.getInstance().getReference("users");
                                        String username = "";
                                        for (int i = 0; i < usersLength; i++) {
                                            if (emails[i].equals(email.getText().toString()))
                                                username = usernames[i];
                                            else
                                                username = "Invitado" + (int) (10 * Math.random()) + (int) (10 * Math.random()) + (int) (10 * Math.random()) + (int) (10 * Math.random()) + (int) (10 * Math.random());
                                        }
                                        if (user.getDisplayName() != null)
                                            username = user.getDisplayName();
                                        //Send to Menu
                                        Intent i = new Intent(LoginActivity.this, StartMenu.class);
                                        i.putExtra("guestName", username);
                                        startActivity(i);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    }
                                }
                            });

                } else{
                    Toast.makeText(this, "Usuario o contraseña vacíos", Toast.LENGTH_SHORT).show();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}