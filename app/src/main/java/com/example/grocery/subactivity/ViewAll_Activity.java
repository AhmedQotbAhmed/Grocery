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

import java.util.HashMap;


public class ViewAll_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HashMap<String, Products> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_favourite);

        // for see all
        Intent intent= getIntent();

        list= (HashMap<String, Products>) intent.getSerializableExtra("products");

        recyclerView=findViewById(R.id.recycler_Favourite);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);


        ViewAllAdapter adaptor = new ViewAllAdapter(list);
        recyclerView.setAdapter(adaptor);




        //end

    }
}
