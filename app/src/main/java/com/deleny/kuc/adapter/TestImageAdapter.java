package com.deleny.kuc.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.deleny.kuc.R;
import com.deleny.kuc.model.RequestModel;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TestImageAdapter extends RecyclerView.Adapter<TestImageAdapter.TestImageHolder> {

    public Context context;
    private List<RequestModel> requestModels;
    private OnItemClick mOnItemClick;

    public TestImageAdapter(Context context, List<RequestModel> requestModels, OnItemClick mOnItemClick) {
        this.context = context;
        this.requestModels = requestModels;
        this.mOnItemClick = mOnItemClick;
    }

    @NonNull
    @Override
    public TestImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_image, parent, false);
        TestImageHolder holder = new TestImageHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestImageHolder holder, int position) {
//         RequestModel requestModel = requestModels.get(position);
        Glide.with(context)
                .load(" https://i.ibb.co/4JrS0K4/paris-2017-home.jpg")

                .into(holder.imagePath);
        Log.d("YTTTTT", "onBindViewHolder: "+position);

    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class TestImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.imagePath)
        ImageView imagePath;

        public TestImageHolder(@NonNull View itemView) {
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
