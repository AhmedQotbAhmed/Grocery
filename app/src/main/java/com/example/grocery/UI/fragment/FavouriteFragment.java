package com.example.grocery.UI.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.UI.adapter.FavouriteAdapter;
import com.example.grocery.model.Products;
import com.example.grocery.prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference postReference;
    private FavouriteAdapter adaptor;
    private FrameLayout linearhom;

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        final String email = Prevalent.userEmail;
        linearhom = view.findViewById(R.id.FrameLayout);


        postReference = FirebaseDatabase.getInstance().getReference().child("Users").child(email).child("favourite");
        // Inflate the layout for this fragment
        recyclerView = view.findViewById(R.id.recycler_Favourite);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options_Fruit = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(postReference, Products.class).build();

        adaptor = new FavouriteAdapter(options_Fruit);
        recyclerView.setAdapter(adaptor);
        adaptor.startListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adaptor.getItemCount() == 0) {
            // show my background image
            linearhom.setBackgroundResource(R.drawable.favourite_empty);

            // do stuff
        } else {
            linearhom.setBackgroundColor(Color.parseColor("#52C0C0C0"));
            // RecyclerView stuff
        }
    }


}
