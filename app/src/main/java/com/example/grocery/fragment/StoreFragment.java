package com.example.grocery.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.UI.main.StoreItemAdapter;
import com.example.grocery.model.Posts;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends Fragment {


    public StoreFragment() {
        // Required empty public constructor
    }

    private DatabaseReference postReference_Fruit;
    private DatabaseReference postReference_Vegetables;
    private DatabaseReference postReference_Other;

    private RecyclerView recyclerView_Fruit;
    private RecyclerView recyclerView_Vegetables;
    private RecyclerView recyclerView_Other;

//    private CartAdapter adaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_store, container, false);
        // Inflate the layout for this fragment
//        "Fruit", "Vegetables",  "Other"
        postReference_Fruit = FirebaseDatabase.getInstance().getReference().child("products").child( "Fruit");
        postReference_Vegetables = FirebaseDatabase.getInstance().getReference().child("products").child( "Vegetables");
        postReference_Other = FirebaseDatabase.getInstance().getReference().child("products").child( "Other");

        recyclerView_Fruit= view.findViewById(R.id.recycler_store_Fruit);
        recyclerView_Vegetables= view.findViewById(R.id.recycler_Vegetables);
        recyclerView_Other= view.findViewById(R.id.recycler_Other);

        recyclerView_Fruit.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView_Vegetables.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView_Other.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));


//                adaptor = new CartAdapter();
//                recyclerView.setAdapter(adaptor);

//                Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Posts> options_Fruit = new FirebaseRecyclerOptions.Builder<Posts>()
                .setQuery(postReference_Fruit, Posts.class).build();
    FirebaseRecyclerOptions<Posts> options_Vegetables = new FirebaseRecyclerOptions.Builder<Posts>()
                .setQuery(postReference_Vegetables, Posts.class).build();
    FirebaseRecyclerOptions<Posts> options__Other = new FirebaseRecyclerOptions.Builder<Posts>()
                .setQuery(postReference_Other, Posts.class).build();

        StoreItemAdapter adaptor_Fruit = new StoreItemAdapter(options_Fruit);
        recyclerView_Fruit.setAdapter(adaptor_Fruit);
        adaptor_Fruit.startListening();

        StoreItemAdapter adaptor_Vegetables = new StoreItemAdapter(options_Vegetables);
        recyclerView_Vegetables.setAdapter(adaptor_Vegetables);
        adaptor_Vegetables.startListening();

        StoreItemAdapter adaptor_Other = new StoreItemAdapter(options__Other);
        recyclerView_Other.setAdapter(adaptor_Other);
        adaptor_Other.startListening();

    }



}
