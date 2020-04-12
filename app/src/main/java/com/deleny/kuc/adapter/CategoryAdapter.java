package com.deleny.kuc.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deleny.kuc.R;
import com.deleny.kuc.model.CategoryModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    public Context context;
    private ArrayList<CategoryModel> categoryModels;
    private OnItemClick mOnItemClick;

    public CategoryAdapter(Context context, ArrayList<CategoryModel> categoryModels, OnItemClick mOnItemClick) {
        this.context = context;
        this.categoryModels = categoryModels;
        this.mOnItemClick = mOnItemClick;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_one_item, parent, false);
        CategoryHolder holder = new CategoryHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        CategoryModel categoryModel = categoryModels.get(position);
        holder.category.setText(categoryModel.getTitle());
        Picasso.with(context).load(categoryModel.getIcon()).into(holder.circleImg);

    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.circle_img)
        CircleImageView circleImg;
        @BindView(R.id.category)
        TextView category;

        public CategoryHolder(@NonNull View itemView) {
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
