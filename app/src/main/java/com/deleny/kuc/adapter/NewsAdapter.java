package com.deleny.kuc.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deleny.kuc.R;
import com.deleny.kuc.model.NewsModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    public Context context;
    private List<NewsModel> newsModels;
    private OnItemClick mOnItemClick;

    public NewsAdapter(Context context, List<NewsModel> newsModels, OnItemClick mOnItemClick) {
        this.context = context;
        this.newsModels = newsModels;
        this.mOnItemClick = mOnItemClick;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card_one_item, parent, false);
        NewsHolder holder = new NewsHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        NewsModel newsModel = newsModels.get(position);
        // Picasso.with(context).load(newsModel.getImagePath()).into(holder.articleImage);
        byte[] decodedString = Base64.decode(newsModel.getImage64(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Glide.with(context).load(decodedByte).fitCenter().into(holder.articleImage);

        holder.titleTV.setText(newsModel.getBlogTitle());
        holder.sectionTV.setText(newsModel.getCategoryName());
        holder.dateTV.setText(newsModel.getPublishDate().substring(0, 10));
//        holder.share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_SEND);
//               intent.setType("text/plain");
//                String shareBody = "Your body here";
//                String shareSub = "Your subject here";
//                intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
//                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
//                context.startActivity(Intent.createChooser(intent, "Share via"));
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.articleImage)
        ImageView articleImage;
        @BindView(R.id.titleTV)
        TextView titleTV;
        @BindView(R.id.sectionTV)
        TextView sectionTV;
        @BindView(R.id.dateTV)
        TextView dateTV;
//        @BindView(R.id.numOFViewsTV)
//        TextView numOFViewsTV;
//        @BindView(R.id.share)
//        ImageView share;


        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mOnItemClick.setOnItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClick {
        void setOnItemClick(int position);
    }
}
