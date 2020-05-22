package com.example.grocery.UI.main;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ItemHolder >{

    public CartAdapter(){


    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }




    public class ItemHolder extends RecyclerView.ViewHolder {

        ConstraintLayout constraintLayout;
        LinearLayout linearLayout;
        TextView price_Txv;
        ImageView product_img;
        TextView total_price;
        TextView product_name;
        ImageView plus_btn;
        ImageView minus_btn;
        TextView quantity_txv;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
    constraintLayout=itemView.findViewById(R.id.con_lay_it_c);
    linearLayout=itemView.findViewById(R.id.linearLayout_it_c);
    price_Txv=itemView.findViewById(R.id.price_it_c);
    product_img=itemView.findViewById(R.id.product_image_it_c);
    total_price=itemView.findViewById(R.id.total_price_it_c);
    product_name=itemView.findViewById(R.id.name_it_c);
    plus_btn=itemView.findViewById(R.id.plus_btn_it_c);
    minus_btn=itemView.findViewById(R.id.minus_btn_it_c);
    quantity_txv=itemView.findViewById(R.id.quantity_it_c);

          }
    }
}
