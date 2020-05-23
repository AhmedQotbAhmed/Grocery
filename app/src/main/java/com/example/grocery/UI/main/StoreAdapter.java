package com.example.grocery.UI.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.model.Products;
import com.example.grocery.prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class StoreAdapter extends FirebaseRecyclerAdapter<Products, StoreAdapter.ItemViewHolder>  {

    private Context context;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public StoreAdapter(@NonNull FirebaseRecyclerOptions<Products> options) {
        super(options);

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       context= parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_store, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull final Products model) {
       String name= model.getName_str();

//        Log.e("name",name);

        holder.product_name.setText(model.getName_str() + "");
        holder.product_price.setText(model.getPrice_str() + " LE");

        Picasso.get().load(model.getUri()).into(holder.product_Image);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Upload_all_PostData(model);
            }
        });

    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView product_Image;
        TextView product_name;
        TextView product_price;
        LinearLayout linearLayout;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.linearLayout_cart);
            product_Image = itemView.findViewById(R.id.product_image_it);
            product_name = itemView.findViewById(R.id.product_name_it);
            product_price = itemView.findViewById(R.id.product_price_it);

        }
    }



    private void Upload_all_PostData(Products product) {


//      product    ,   price_str ,  itemCategory and uri this is our post data
        final String email= Prevalent.userEmail;
        Log.e("email",email+"");

        reference.child("Users").child(email).child("Cart").child(product.getName_str())
                .setValue(product)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Add to Cart", Toast.LENGTH_LONG).show();


                        } else {
                            Toast.makeText(context, "Network Error: please try again...", Toast.LENGTH_LONG).show();


                        }
                    }
                });

    }



}
