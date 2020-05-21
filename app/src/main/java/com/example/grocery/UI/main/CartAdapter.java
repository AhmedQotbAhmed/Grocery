//package com.example.grocery.UI.main;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.AnimationUtils;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.grocery.R;
//import com.example.news.R;
//import com.example.news.pojo.Article;
//import com.squareup.picasso.Picasso;
//
//import java.util.List;
//// PICASSO FOR DISPLAY image
//
//public class CartAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
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