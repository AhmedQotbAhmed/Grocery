package com.example.grocery.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.UI.main.CategoryAdapter;
import com.example.grocery.UI.main.StoreAdapter;
import com.example.grocery.model.Category;
import com.example.grocery.model.Products;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends Fragment {


List<Products>list;


    public StoreFragment() {
        // Required empty public constructor
    }

    private DatabaseReference postReference_Fruit;
    private DatabaseReference postReference_Vegetables;
    private DatabaseReference postReference_Other;

    private RecyclerView recyclerView_Fruit;
    private RecyclerView recyclerView_Vegetables;
    private RecyclerView recyclerView_Other;
    private DatabaseReference Reference;

//    private CartAdapter adaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_store, container, false);
        // Inflate the layout for this fragment
        //   "Fruit", "Vegetables",  "Other"

     Reference = FirebaseDatabase.getInstance().getReference().child("products");

        /////////////////////////////////////////////////////////////////////////////////////
        postReference_Fruit = FirebaseDatabase.getInstance().getReference().child("products").child( "Fruit");
        postReference_Vegetables = FirebaseDatabase.getInstance().getReference().child("products").child( "Vegetables");
        postReference_Other = FirebaseDatabase.getInstance().getReference().child("products").child( "Other");

        recyclerView_Fruit= view.findViewById(R.id.recycler_store_Fruit);
        recyclerView_Vegetables= view.findViewById(R.id.recycler_Vegetables);
        recyclerView_Other= view.findViewById(R.id.recycler_Other);

        recyclerView_Fruit.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView_Vegetables.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView_Other.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();









        FirebaseRecyclerOptions<Products> options_Fruit = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(postReference_Fruit, Products.class).build();
    FirebaseRecyclerOptions<Products> options_Vegetables = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(postReference_Vegetables, Products.class).build();
    FirebaseRecyclerOptions<Products> options__Other = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(postReference_Other, Products.class).build();

        StoreAdapter adaptor_Fruit = new StoreAdapter(options_Fruit);
        recyclerView_Fruit.setAdapter(adaptor_Fruit);
        adaptor_Fruit.startListening();

        StoreAdapter adaptor_Vegetables = new StoreAdapter(options_Vegetables);
        recyclerView_Vegetables.setAdapter(adaptor_Vegetables);
        adaptor_Vegetables.startListening();

        StoreAdapter adaptor_Other = new StoreAdapter(options__Other);
        recyclerView_Other.setAdapter(adaptor_Other);
        adaptor_Other.startListening();


/////////////////////////////////////////////////////






    }



}
