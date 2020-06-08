package com.example.grocery.UI.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.model.Checkout;

import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.RecViewHolder> {

    private List<Checkout> list;

    public CheckoutAdapter(List<Checkout> list) {
        this.list = list;
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_checkout, parent, false);

        return new RecViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecViewHolder holder, int position) {
        Checkout checkout = list.get(position);
        holder.bind(checkout);

        holder.itemView.setOnClickListener(v -> {
            boolean expanded = checkout.isExpanded();
            checkout.setExpanded(!expanded);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class RecViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView sub1;
        private TextView sub2;
        private View subItem;
        private LinearLayout linear;

        public RecViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.item_title);
            sub1 = itemView.findViewById(R.id.sub_item_1);
            sub2 = itemView.findViewById(R.id.sub_item_2);
            subItem = itemView.findViewById(R.id.sub_item);
            linear= itemView.findViewById(R.id.lin_checkout);
        }

        private void bind(Checkout checkout) {
            boolean expanded = checkout.isExpanded();


            subItem.setVisibility(  expanded ? View.VISIBLE : View.GONE   );
            if (expanded){
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0, 0, 0, 0);
                linear.setLayoutParams(lp);
            }
            else
            {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0, 100, 0, 0);
                linear.setLayoutParams(lp);

            }

            subItem.refreshDrawableState();
            title.setText(checkout.getTitle());
            title.setCompoundDrawablesWithIntrinsicBounds(checkout.getIcon_tag(), 0, R.drawable.ic_details_icon, 0);
            sub1.setText(checkout.getSub1());
            sub2.setText( checkout.getSub2());
        }
    }
}