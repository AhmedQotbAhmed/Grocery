package com.example.grocery.UI.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.model.Category;
import com.example.grocery.model.Products;
import com.example.grocery.subactivity.ViewAll_Activity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StoreAdapter extends FirebaseRecyclerAdapter<Category, StoreAdapter.ItemViewHolder> {

    private Context context;


    private List<Products> list = new ArrayList<>();

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public StoreAdapter(@NonNull FirebaseRecyclerOptions<Category> options) {
        super(options);

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    protected void onBindViewHolder(@NonNull final ItemViewHolder holder, int position, @NonNull final Category model) {


        holder.category_name.setText(" Grocery " + model.getCategory());
        holder.category_recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.category_recycler.setAdapter(new CategoryAdapter(getData(model.getProducts_list())));


        holder.viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(new Intent(context, ViewAll_Activity.class).putExtra("products", getData(model.getProducts_list())));


            }
        });


    }

    private HashMap<String, Products> getData(HashMap<String, Products> product) {

        HashMap<String, Products> product_list = new HashMap<>();

        int i = 0;

        for (Products product_item : product.values()) {
            if (!list.contains(product_item)) {
                list.add(product_item);
            }
            product_list.put(i + "", product_item);
            i++;

        }
        return product_list;
    }

    public HashMap<String, Products> getFilter(CharSequence constraint) {
        int count = 0;
        HashMap<String, Products> list_p = new HashMap<>();
        for (Products i : list) {
            list_p.put(count + "", i);
            count++;
        }
        HashMap<String, Products> filteredList = new HashMap<>();
        String charString = constraint.toString();

        if (charString.isEmpty()) {
        } else {
            count = 0;
//            Log.e("siz",list.size()+"");
            for (Products product : list_p.values()) {

                if (product.getName_str().toLowerCase().contains(charString.toLowerCase())) {

                    filteredList.put(count + "", product);
                    count++;

                }
            }
            if (!filteredList.isEmpty()) {
//                Log.e("sizdsfe",list_p.size()+"");
                return filteredList;

            }

        }

        return filteredList;
    }


//


    class ItemViewHolder extends RecyclerView.ViewHolder {


        TextView viewAll;
        TextView category_name;
        RecyclerView category_recycler;
        LinearLayout linearLayout;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            viewAll = itemView.findViewById(R.id.viewAll_Vegetables2);
            linearLayout = itemView.findViewById(R.id.item_category);
            category_name = itemView.findViewById(R.id.Category_name);
            category_recycler = itemView.findViewById(R.id.recycler_category);
        }
    }


}
