package com.example.grocery.subactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.grocery.R;
import com.example.grocery.UI.activity.HomeActivity;
import com.example.grocery.UI.adapter.CheckoutAdapter;
import com.example.grocery.model.Invoice;
import com.example.grocery.model.Checkout;
import com.example.grocery.model.Products;
import com.example.grocery.prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
    private Button checkout;
    private Intent intent;
    private HashMap<String, Products> list;
    final String email= Prevalent.userEmail;
    private Invoice invoice;

    private String fname="";
    private String lname="";
    private String email_string="";
    private String mobile="";
    private String address="";

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        intent=getIntent();

        list= (HashMap<String, Products>) intent.getSerializableExtra("inv");


        List<Checkout> movieList = new ArrayList<>();


        reference.child("Users").child(email).child("profile_inf").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                fname = dataSnapshot.child("fname").getValue().toString();
                lname = dataSnapshot.child("lname").getValue().toString();
                email_string = dataSnapshot.child("email").getValue().toString();
                mobile = dataSnapshot.child("mobile").getValue().toString();
                address=dataSnapshot.child("address").getValue().toString()+"";
                movieList.add(new Checkout("Recipient details","Name:   "+fname+lname ,"Email:   " +email_string,R.drawable.ic_recipients_prof_icon));
                movieList.add(new Checkout("Delivery or Pickup info", "address:   "+address,"mobile:   " +mobile,R.drawable.ic_delivery_address));
                movieList.add(new Checkout("Payment Method", "Payment card number **************:   ","" +mobile,R.drawable.ic_pyment_icon));
                CheckoutAdapter adapter = new CheckoutAdapter(movieList);

                RecyclerView recyclerView = findViewById(R.id.recview);

                ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });









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

               reference.child("Admin").child("invoices").child((invoice.getTotal_pric()
                       .substring(0, invoice.getTotal_pric().indexOf("."))) + invoice.getDate()).setValue(invoice);
               reference.child("Users").child(email).child( "Cart").removeValue();
               startActivity(new Intent(getApplicationContext(), HomeActivity.class));
           }
       });
    }




}
