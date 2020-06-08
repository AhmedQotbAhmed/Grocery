package com.example.grocery.UI.adapter;

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


public class FavouriteAdapter extends FirebaseRecyclerAdapter<Products, FavouriteAdapter.ItemHolder> {
    private Context context;

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    private CartItem cartItem;
    private ItemHolder holder;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FavouriteAdapter(@NonNull FirebaseRecyclerOptions<Products> options) {
        super(options);

    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite, parent, false);
        cartItem = new CartItem();
        return new ItemHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull final ItemHolder holder, int position, @NonNull final Products model) {

//        holder.constraintLayout.ser;
//        holder.linearLayout;

        this.holder = holder;
        holder.linearLayout.setAnimation(AnimationUtils.loadAnimation(context, R.anim.content_transition_animation));
        holder.product_price.setText(model.getPrice_str() + " LE");
        Picasso.get().load(model.getUri()).into(holder.product_Image);
        holder.product_name.setText(model.getName_str());


        holder.product_favourite_fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView product_favourite_fa = v.findViewById(R.id.product_favourite_fa);
                product_favourite_fa.setImageResource(R.drawable.ic_not__favorite);
                delete_data(model.getName_str());
            }
        });


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Add_to_cart_PostData(model);

            }
        });


    }


    private void delete_data(String name) {
        final String email = Prevalent.userEmail;
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(email).child("favourite");
        reference.child(name).removeValue();


    }


    private void Add_to_cart_PostData(Products product) {


//      product    ,   price_str ,  itemCategory and uri this is our post data
        final String email = Prevalent.userEmail;
        Log.e("email", email + "");
        reference = FirebaseDatabase.getInstance().getReference();
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

    public class ItemHolder extends RecyclerView.ViewHolder {


        public ImageView product_favourite_it;
        ImageView product_Image;
        ImageView product_favourite_fa;
        TextView product_name;
        TextView product_price;
        LinearLayout linearLayout;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            product_favourite_fa = itemView.findViewById(R.id.product_favourite_fa);
            linearLayout = itemView.findViewById(R.id.linearLayout_favorite);
            product_Image = itemView.findViewById(R.id.product_image_fa);
            product_name = itemView.findViewById(R.id.product_name_fa);
            product_price = itemView.findViewById(R.id.product_price_fa);

        }
    }
}
