package com.example.grocery.subactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grocery.R;
import com.example.grocery.UI.activity.HomeActivity;
import com.example.grocery.prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener {
    private final String email = Prevalent.userEmail;
    private TextView curr_ps_txv;
    private TextView new_ps_txv;
    private TextView new_confirm_ps_txv;
    private Button update_ps_btn;
    private DatabaseReference Reference = FirebaseDatabase.getInstance().getReference().child("Users").child(email).child("profile_inf").child("password");
    private String new_ps;
    private String new_confirm_ps;
    private String curr_ps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        curr_ps_txv = findViewById(R.id.curr_ps);
        new_ps_txv = findViewById(R.id.new_ps);
        new_confirm_ps_txv = findViewById(R.id.new_confirm_ps);
        update_ps_btn = findViewById(R.id.update_btn_ps);


        update_ps_btn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        get_curr_ps();

    }

    @SuppressLint("CommitPrefEdits")
    private void valid(String pass) {

        new_ps = new_ps_txv.getText().toString();
        new_confirm_ps = new_confirm_ps_txv.getText().toString();
        curr_ps = curr_ps_txv.getText().toString();


        if (new_ps.length() < 8) {
            new_ps_txv.setError(" Minimum length of Password is should be 8 ");
            new_ps_txv.requestFocus();
        } else {
            if (curr_ps.equals(pass)) {
                if (new_ps.equals(pass)) {

                    new_ps_txv.setError(" New password can't be an old one.");

                } else {
                    if (new_confirm_ps.equals(new_ps)) {

                        Reference.setValue(new_ps);
                        Toast.makeText(this, "Password change Successfully", Toast.LENGTH_LONG).show();
                        SharedPreferences sp;

                        sp = getSharedPreferences("userLogin", MODE_PRIVATE);
                        sp.edit().putString("Psw", new_ps);
                        startActivity(new Intent(this, HomeActivity.class));


                    } else {

                        new_ps_txv.setError("password don't match");
                    }
                }

            } else {

                curr_ps_txv.setError("password incorrect");

            }

        }
    }

    private void get_curr_ps() {

        Reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                valid((String) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
