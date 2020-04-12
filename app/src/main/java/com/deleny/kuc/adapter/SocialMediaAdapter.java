package com.deleny.kuc.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.deleny.kuc.R;
import com.deleny.kuc.model.SocialmediaModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SocialMediaAdapter extends RecyclerView.Adapter<SocialMediaAdapter.SocialMediaHolder> {

    public Context context;
    private List<SocialmediaModel> socialmediaModels;
    private OnItemClick mOnItemClick;

    public SocialMediaAdapter(Context context, List<SocialmediaModel> socialmediaModels, OnItemClick mOnItemClick) {
        this.context = context;
        this.socialmediaModels = socialmediaModels;
        this.mOnItemClick = mOnItemClick;
    }

    @NonNull
    @Override
    public SocialMediaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.social_media_one_item, parent, false);
        SocialMediaHolder holder = new SocialMediaHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SocialMediaHolder holder, int position) {
        SocialmediaModel socialmediaModel = socialmediaModels.get(position);
        byte[] decodedString = Base64.decode(socialmediaModel.getIcon(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Glide.with(context).load(decodedByte).fitCenter().into(holder.socialMediaIcon);

    }

    @Override
    public int getItemCount() {
        return socialmediaModels.size();
    }

    public class SocialMediaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.socialMediaIcon)
        ImageView socialMediaIcon;

        public SocialMediaHolder(@NonNull View itemView) {
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
