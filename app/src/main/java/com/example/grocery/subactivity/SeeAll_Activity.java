package com.example.grocery.subactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.grocery.R;
import com.example.grocery.UI.adapter.ViewAllAdapter;
import com.example.grocery.model.Products;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SeeAll_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    FirebaseRecyclerOptions<Products> options;

    private DatabaseReference postReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_favourite);

        // for see all

Intent intent= getIntent();
         String childName = intent.getStringExtra("ref");
        postReference = FirebaseDatabase.getInstance().getReference().child("products").child( childName);
        recyclerView=findViewById(R.id.recycler_Favourite);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);

        options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(postReference, Products.class).build();

        ViewAllAdapter adaptor_Fruit = new ViewAllAdapter(options);
        recyclerView.setAdapter(adaptor_Fruit);
        adaptor_Fruit.startListening();
        //end

    }
}
