package com.example.grocery.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.subactivity.CheckoutActivity;
import com.example.grocery.R;
import com.example.grocery.UI.adapter.CartAdapter;
import com.example.grocery.model.Products;
import com.example.grocery.prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
        private HashMap<String,Products> list;
        private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        private CartAdapter adaptor;
        private RecyclerView recyclerView;
        private ConstraintLayout cart_summary;
        private BottomSheetBehavior bottomSheetBehavior;
        private TextView itemCount ;
        private TextView totalprice ;
        private TextView totalprice_payment ;
        private Button paynow;
        private Button cancel;


        private DatabaseReference postReference;
    public CartFragment() {
        // Required empty public constructor
    }

    private double total_price = 0;
    private int size=0;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cart_summary=view.findViewById(R.id.cart_summary);
        bottomSheetBehavior= BottomSheetBehavior.from(cart_summary);

        paynow=view.findViewById(R.id.paynow_btn);
        cancel=view.findViewById(R.id.cancel_btn);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState==BottomSheetBehavior.STATE_HIDDEN)
                { bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                    set_cart_summary();}

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                set_cart_summary();


            }});


        itemCount =view.findViewById(R.id.subtotal_item);
        totalprice =view.findViewById(R.id.subtotal_price_p);
        totalprice_payment =view.findViewById(R.id.total_price_payment_p);

        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!totalprice_payment.getText().toString().equals(+ 0.0+"  EL" ))
                {  upload_Invoices ();

                startActivity(new Intent(getContext(), CheckoutActivity.class).putExtra("inv",list));

                }
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(list ==null) &&!list.isEmpty())
                list.clear();

                final String email= Prevalent.userEmail;
                reference.child("Users").child(email).child("invoices").child(((int)total_price)+"-"+size+"-"+((int)(total_price - 10 + 30))).removeValue();

                reference.child("Users").child(email).child( "Cart").removeValue();

                itemCount.setText("Subtotal (" + 0 + " item)");
                totalprice.setText(  0.0+"  EL");
                totalprice_payment.setText(  0.0+"  EL");

            }
        });
    }


    @SuppressLint("SetTextI18n")
    private void set_cart_summary() {
        
        if (!adaptor.getTotal_price().isEmpty()) {
             total_price = 0;
             size=0;
            list = adaptor.getTotal_price();
            for (Products i : list.values()) {

                total_price +=Double.valueOf( i.getTotal_price().replace(" LE",""));
                    size++;

            }

            itemCount.setText("Subtotal (" + size + " item)");
            totalprice.setText( total_price+"  LE" );
            totalprice_payment.setText((total_price - 10 + 30)+"  LE"  );

        }
    }
    void upload_Invoices (){
        final String email= Prevalent.userEmail;
        Log.e("email",email+"");

        reference.child("Users").child(email).child("invoices").child(((int)total_price)+"-"+size+"-"+((int)(total_price - 10 + 30)))
                .setValue(list)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
//                            Toast.makeText(getContext(), "Add to Cart", Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(getContext(), "Network Error: please try again...", Toast.LENGTH_LONG).show();


                        }
                    }
                });



    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options_Fruit = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(postReference, Products.class).build();

        adaptor = new CartAdapter(options_Fruit);
        recyclerView.setAdapter(adaptor);
        adaptor.startListening();

    }















}
