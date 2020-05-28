package com.example.grocery.subactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grocery.R;
import com.example.grocery.UI.activity.HomeActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CheckoutActivity extends AppCompatActivity {
    private Button checkout;
    private Intent intent;


    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        intent=getIntent();



    }

    @Override
    protected void onStart() {
        super.onStart();

       checkout=findViewById(R.id.confirm_checkout);
       checkout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               reference.child("Admin").child("invoices").setValue((HashMap<String, String>)intent.getSerializableExtra("inv"));
               startActivity(new Intent(getApplicationContext(), HomeActivity.class));
           }
       });
    }
}
