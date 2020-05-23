package com.example.grocery.UI.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.model.Products;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ItemHolder > implements View.OnClickListener {
  private  Context context;
    public static List<Products> list;

    private CartItem cartItem;
    private  ItemHolder holder;

    public CartAdapter(){


    }
    public static void  addItem(Products product){
        list.add(product);


    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.item_cart, parent, false);
        cartItem=new CartItem();
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Products current = list.get(position);
//        holder.constraintLayout.ser;
//        holder.linearLayout;
       this.holder=holder;
        holder.price_Txv.setText(current.getPrice_str());
        Picasso.get().load(current.getUri()).into(holder.product_img);
        holder.product_name.setText(current.getName_str());
        holder.plus_btn.setOnClickListener(this);
        holder.minus_btn.setOnClickListener(this);


    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    @Override
    public void onClick(View v) {
        String quan;
        double price;
        price= Double.valueOf(holder.price_Txv.getText().toString().replace("LE",""));
        switch (v.getId()){
            case R.id.plus_btn_it_c:
                cartItem.setPrice(price);
                quan=cartItem.pluseOprition(holder.quantity_txv.getText().toString());
                holder.quantity_txv.setText(quan);
                holder.total_price.setText(cartItem.getTotalPrice());

                break;

            case R.id.minus_btn_it_c:
                cartItem.setPrice(price);
                quan=cartItem.minusOprition(holder.quantity_txv.getText().toString());
                holder.quantity_txv.setText(quan);
                holder.total_price.setText(cartItem.getTotalPrice());


                break;
        }

    }


    public class ItemHolder extends RecyclerView.ViewHolder {

//        ConstraintLayout constraintLayout;
//        LinearLayout linearLayout;
        TextView price_Txv;
        ImageView product_img;
        TextView total_price;
        TextView product_name;
        ImageView plus_btn;
        ImageView minus_btn;
        TextView quantity_txv;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
//    constraintLayout=itemView.findViewById(R.id.con_lay_it_c);
//    linearLayout=itemView.findViewById(R.id.linearLayout_it_c);
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
