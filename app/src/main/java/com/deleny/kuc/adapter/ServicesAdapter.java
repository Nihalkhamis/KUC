package com.deleny.kuc.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deleny.kuc.R;
import com.deleny.kuc.model.CategoryModel;
import com.deleny.kuc.model.ServicesModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServicesHolder> {

    public Context context;
    private List<ServicesModel> servicesModels;
    private OnItemClick mOnItemClick;

//    public void setServicesModels(List<ServicesModel> servicesModels) {
//        this.servicesModels = servicesModels;
//    }

    public ServicesAdapter(Context context, List<ServicesModel> servicesModels, OnItemClick mOnItemClick) {
        this.context = context;
        this.servicesModels = servicesModels;
        this.mOnItemClick = mOnItemClick;
    }

    @NonNull
    @Override
    public ServicesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_one_item, parent, false);
        ServicesHolder holder = new ServicesHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesHolder holder, int position) {
        ServicesModel servicesModel = servicesModels.get(position);

        Log.d("TTTTTTT", "onBindViewHolder: "+position);
        holder.category.setText(servicesModel.getTitle());

        byte[] decodedString = Base64.decode(servicesModel.getImage64(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Glide.with(context).load(decodedByte).fitCenter().into(holder.circleImg);


    }

    @Override
    public int getItemCount() {
        return servicesModels.size();
    }

    public class ServicesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.circle_img)
        CircleImageView circleImg;
        @BindView(R.id.category)
        TextView category;

        public ServicesHolder(@NonNull View itemView) {
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
