package com.example.grocery.subactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grocery.R;
import com.example.grocery.UI.activity.HomeActivity;
import com.example.grocery.model.Invoice;
import com.example.grocery.model.Products;
import com.example.grocery.prevalent.Prevalent;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CheckoutActivity extends AppCompatActivity {
    private Button checkout;
    private Intent intent;
    private HashMap<String, Products> list;
    final String email= Prevalent.userEmail;
    private Invoice invoice;

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        intent=getIntent();

        list= (HashMap<String, Products>) intent.getSerializableExtra("inv");


    }
    private String getTotalPrice() {


         double   total_price = 0;


            for (Products i : list.values()) {

                total_price +=Double.valueOf( i.getTotal_price().replace(" LE",""));


            }



           return total_price - 10 + 30+"";

    }

    @Override
    protected void onStart() {
        super.onStart();


       checkout=findViewById(R.id.confirm_checkout);
       checkout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               invoice=new Invoice();
               invoice.setProductList(list);
               invoice.setTotal_pric(getTotalPrice());
               invoice.setUser(email);

               reference.child("Admin").child("invoices").child((invoice.getTotal_pric().substring(0,invoice.getTotal_pric().indexOf("."))) +invoice.getDate()).setValue(invoice);
               reference.child("Users").child(email).child( "Cart").removeValue();
               startActivity(new Intent(getApplicationContext(), HomeActivity.class));
           }
       });
    }
}
