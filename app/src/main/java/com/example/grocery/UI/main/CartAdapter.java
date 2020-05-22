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


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ItemHolder> {
    @NonNull
    @Override
    public CartAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ItemHolder holder, int position) {

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
//List<Article> list ;
//private Context context;
//
//
//
////    Constructor
//public CartAdapter(List<Article> list) {
//    this.list=list;
//
//}
//
//@NonNull
//@Override
//public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//
//    View view= LayoutInflater.from(parent.getContext())
//            .inflate(R.layout.news_layout,parent,false);
//    context=parent.getContext();
//    NewsHolder hold=new NewsHolder(view);
//    return hold;
//}
//
//@Override
//public void onBindViewHolder(@NonNull NewsHolder holder, final int position) {
////
//
//    // بربط الداتا باليو اي بتاعي هنا
//
//    final Article current= list.get(position);
//    //saveData to Use it
//
//    holder.more.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            if (v.getId() == R.id.more) {
//
//                Intent intent = new Intent(context, DescriptionActivity.class);
//
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("Article", current);
//                intent.putExtras(bundle);
//
//
//                context.startActivity(intent);
//
//            }
//
//        }
//    });
//
//    // setData
//    holder.textView.setText(current.getTitle());
//
//    Picasso.get().load(current.getUrlToImage()).into(holder.img);
//
////        Animation
//    holder.img.setAnimation(AnimationUtils.loadAnimation(context,R.anim.rc_transitiion_animation));
//    holder.container.setAnimation(AnimationUtils.loadAnimation(context,R.anim.content_transition_animation));
//    holder.more.setAnimation(AnimationUtils.loadAnimation(context,R.anim.rc_transitiion_animation));
//
//}
//
//@Override
//public int getItemCount() {
//    return list.size();
//}
//
//
//
/////////////////////////////NewsHolder////////////////////////////////////////////////
//class NewsHolder extends RecyclerView.ViewHolder {
//    TextView price_txv;
//    TextView totale_ptice_txv;
//    ImageView img;
//      Button more;
//      RelativeLayout container;
//
//
//    public NewsHolder(@NonNull View itemView) {
//        super(itemView);
//        more=itemView.findViewById(R.id.more);
//        container=itemView.findViewById(R.id.container);
//        textView=itemView.findViewById(R.id.content);
//        img=itemView.findViewById(R.id.imageView);
////
//    }
//
//
//  }
//
//}