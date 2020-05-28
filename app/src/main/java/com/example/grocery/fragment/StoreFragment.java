package com.example.grocery.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.subactivity.ViewAll_Activity;
import com.example.grocery.UI.adapter.StoreAdapter;
import com.example.grocery.model.Products;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends Fragment {


    private List<Products>list;
    private  TextView product_seeAll_f;
    private  TextView product_seeAll_v;
    private  TextView product_seeAll_o;

    public StoreFragment() {
        // Required empty public constructor
    }

    private DatabaseReference postReference_Fruit;
    private DatabaseReference postReference_Vegetables;
    private DatabaseReference postReference_Other;


    private RecyclerView recyclerView_Fruit;
    private RecyclerView recyclerView_Vegetables;
    private RecyclerView recyclerView_Other;
    private FirebaseRecyclerOptions<Products> options_Fruit;
    private FirebaseRecyclerOptions<Products> options_Vegetables;
    private FirebaseRecyclerOptions<Products> options__Other;

//    private CartAdapter adaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_store, container, false);


        // Inflate the layout for this fragment
        //   "Fruit", "Vegetables",  "Other"

        // for all references

        postReference_Fruit = FirebaseDatabase.getInstance().getReference().child("products").child( "Fruit");
        postReference_Vegetables = FirebaseDatabase.getInstance().getReference().child("products").child( "Vegetables");
        postReference_Other = FirebaseDatabase.getInstance().getReference().child("products").child( "Other");
        //end



        /////////////////////////////////////////////////////////////////////////////////////
        product_seeAll_f= view.findViewById(R.id.viewAll_fruit);
        product_seeAll_v= view.findViewById(R.id.viewAll_Vegetables);
        product_seeAll_o= view.findViewById(R.id.viewAll_Other);

        recyclerView_Fruit= view.findViewById(R.id.recycler_store_Fruit);
        recyclerView_Vegetables= view.findViewById(R.id.recycler_Vegetables);
        recyclerView_Other= view.findViewById(R.id.recycler_Other);

        recyclerView_Fruit.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView_Vegetables.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView_Other.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));




        product_seeAll_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), ViewAll_Activity.class).putExtra("ref", "Fruit" ));



            }
        });
        product_seeAll_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ViewAll_Activity.class).putExtra("ref","Vegetables" ));


            }
        });
        product_seeAll_o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ViewAll_Activity.class).putExtra("ref", "Other"));




            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


        // GET THE Data from fireBase
        options_Fruit = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(postReference_Fruit, Products.class).build();

        options_Vegetables = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(postReference_Vegetables, Products.class).build();

        options__Other = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(postReference_Other, Products.class).build();

        //set adapter with the data and recyclerView

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


    @Override
    public void onStop() {
        super.onStop();

    }



}
