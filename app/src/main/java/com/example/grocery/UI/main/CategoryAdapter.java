package com.example.grocery.UI.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.model.Category;
import com.example.grocery.model.Products;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class CategoryAdapter extends FirebaseRecyclerAdapter<List<Products>, CategoryAdapter.CategoryViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CategoryAdapter(@NonNull FirebaseRecyclerOptions<List<Products>> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull List<Products> model) {

    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        TextView viewAll;
        RecyclerView recyclerView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName=itemView.findViewById(R.id.category_name);
            viewAll=itemView.findViewById(R.id.category_viewAll);
            recyclerView=itemView.findViewById(R.id.recycler_category_item_recy);
        }
    }
}
