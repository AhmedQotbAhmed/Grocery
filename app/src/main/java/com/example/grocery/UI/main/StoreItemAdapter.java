package com.example.grocery.UI.main;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.model.Posts;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class StoreItemAdapter extends FirebaseRecyclerAdapter<Posts, StoreItemAdapter.ItemViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public StoreItemAdapter(@NonNull FirebaseRecyclerOptions<Posts> options) {
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
    protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull Posts model) {
       String name= model.getName_str();

        Log.e("name",name);
        holder.product_name.setText(model.getName_str() + "");
        holder.product_price.setText(model.getPrice_str() + " LE");

        Picasso.get().load(model.getUri()).into(holder.product_Image);

    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView product_Image;
        TextView product_name;
        TextView product_price;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            product_Image = itemView.findViewById(R.id.product_image_it);
            product_name = itemView.findViewById(R.id.product_name_it);
            product_price = itemView.findViewById(R.id.product_price_it);
        }
    }
}
