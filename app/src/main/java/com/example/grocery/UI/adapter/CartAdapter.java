package com.example.grocery.UI.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.model.CartItem;
import com.example.grocery.model.Products;
import com.example.grocery.prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class CartAdapter extends FirebaseRecyclerAdapter<Products, CartAdapter.ItemHolder> {
    @SuppressLint("UseSparseArrays")
    HashMap<String, Products> total_price = new HashMap<>();
    private Context context;
    private DatabaseReference reference;

    private CartItem cartItem;

    private ItemHolder holder;


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CartAdapter(@NonNull FirebaseRecyclerOptions<Products> options) {
        super(options);

    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        cartItem = new CartItem();
        return new ItemHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull final ItemHolder holder, final int position, @NonNull final Products model) {

//        holder.constraintLayout.ser;
//        holder.linearLayout;


        this.holder = holder;
        holder.price_Txv.setText(model.getPrice_str() + " LE");
        Picasso.get().load(model.getUri()).into(holder.product_img);
        holder.product_name.setText(model.getName_str());

        holder.quantity_txv.setText(model.getQuantity() + " k");
        holder.total_price.setText(model.getTotal_price() + " LE");


//        holder.quantity_txv.setText(quan);
        holder.linearLayout.setAnimation(AnimationUtils.loadAnimation(context, R.anim.content_transition_animation));

        final double price = Double.valueOf(model.getPrice_str());

        holder.chBx_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                total_price.remove(holder.product_name.getText().toString());
                delete_data(model.getName_str());


            }
        });

        holder.plus_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String quan;
                cartItem.setPrice(price);


                quan = cartItem.pluseOprition(holder.quantity_txv.getText().toString());
                holder.quantity_txv.setText(quan + " k");
                holder.total_price.setText(cartItem.getTotalPrice() + " LE");

                model.setQuantity(quan);
                model.setTotal_price(cartItem.getTotalPrice());

                total_price.put(holder.product_name.getText().toString(), model);
                Add_to_cart_PostData(model);


            }
        });
        holder.minus_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String quan;
                cartItem.setPrice(price);


                quan = cartItem.minusOprition(holder.quantity_txv.getText().toString());
                holder.quantity_txv.setText(quan + " k");
                holder.total_price.setText(cartItem.getTotalPrice() + " LE");

                model.setTotal_price(cartItem.getTotalPrice());
                model.setQuantity(quan);

                total_price.put(holder.product_name.getText().toString(), model);
                Add_to_cart_PostData(model);

            }
        });


    }


    private void delete_data(String name) {

        final String email = Prevalent.userEmail;
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(email).child("Cart");
        reference.child(name).removeValue();


    }


    public HashMap<String, Products> getTotal_price() {
        return total_price;
    }


    private void Add_to_cart_PostData(Products product) {

        reference = FirebaseDatabase.getInstance().getReference();
//      product    ,   price_str ,  itemCategory and uri this is our post data
        final String email = Prevalent.userEmail;
        Log.e("email", email + "");

        reference.child("Users").child(email).child("Cart").child(product.getName_str())
                .setValue(product)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                        } else {
                            Toast.makeText(context, "Network Error: please try again...", Toast.LENGTH_LONG).show();


                        }
                    }
                });
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        //        ConstraintLayout constraintLayout;
        LinearLayout linearLayout;

        TextView price_Txv;
        ImageView product_img;
        TextView total_price;
        TextView product_name;
        ImageView plus_btn;
        ImageView minus_btn;
        TextView quantity_txv;
        TextView chBx_Delete;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
//    constraintLayout=itemView.findViewById(R.id.con_lay_it_c);

            linearLayout = itemView.findViewById(R.id.linearLayout_it);
            chBx_Delete = itemView.findViewById(R.id.delete_cart);
            price_Txv = itemView.findViewById(R.id.price_it_c);
            product_img = itemView.findViewById(R.id.product_image_it_c);
            total_price = itemView.findViewById(R.id.total_price_it_c);
            product_name = itemView.findViewById(R.id.name_it_c);
            plus_btn = itemView.findViewById(R.id.plus_btn_it_c);
            minus_btn = itemView.findViewById(R.id.minus_btn_it_c);
            quantity_txv = itemView.findViewById(R.id.quantity_it_c);

        }
    }
}
