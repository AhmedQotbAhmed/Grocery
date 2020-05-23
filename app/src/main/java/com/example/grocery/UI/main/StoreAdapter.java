package com.example.grocery.UI.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.model.Products;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class StoreAdapter extends FirebaseRecyclerAdapter<Products, StoreAdapter.ItemViewHolder> implements View.OnClickListener {
    Products product;

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
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_store, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull Products model) {
       String name= model.getName_str();
        this.product =model;

//        Log.e("name",name);

        holder.product_name.setText(model.getName_str() + "");
        holder.product_price.setText(model.getPrice_str() + " LE");

        Picasso.get().load(model.getUri()).into(holder.product_Image);

        holder.linearLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        case R.id.linearLayout_cart:
            CartAdapter.addItem(product);
            break;

        }
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
}
