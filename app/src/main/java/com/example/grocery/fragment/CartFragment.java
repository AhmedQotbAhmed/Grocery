package com.example.grocery.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.UI.main.CartAdapter;
import com.example.grocery.UI.main.CartItem;
import com.example.grocery.UI.main.StoreAdapter;
import com.example.grocery.model.Products;
import com.example.grocery.prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
        private RecyclerView recyclerView;

        private DatabaseReference postReference;
    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_cart, container, false);
        final String email= Prevalent.userEmail;

        postReference = FirebaseDatabase.getInstance().getReference().child("Users").child(email).child( "Cart");
        // Inflate the layout for this fragment
        recyclerView=view.findViewById(R.id.recycler_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options_Fruit = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(postReference, Products.class).build();

        CartAdapter adaptor = new CartAdapter(options_Fruit);
        recyclerView.setAdapter(adaptor);
        adaptor.startListening();
    }
}
