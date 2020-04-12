package com.deleny.kuc.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deleny.kuc.R;
import com.deleny.kuc.model.ContractsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ContractsAdapter extends RecyclerView.Adapter<ContractsAdapter.ContractsHolder> {

    public Context context;
    private List<ContractsModel> contractsModels;
    private OnItemClick mOnItemClick;

    public ContractsAdapter(Context context, List<ContractsModel> contractsModels, OnItemClick mOnItemClick) {
        this.context = context;
        this.contractsModels = contractsModels;
        this.mOnItemClick = mOnItemClick;
    }

    @NonNull
    @Override
    public ContractsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contract_one_item, parent, false);
        ContractsHolder holder = new ContractsHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContractsHolder holder, int position) {
            ContractsModel contractsModel = contractsModels.get(position);
            holder.contractName.setText(contractsModel.getTitle());

        byte[] decodedString = Base64.decode(contractsModel.getImage64(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Glide.with(context).load(decodedByte).fitCenter().into(holder.contractImg);
    }

    @Override
    public int getItemCount() {
        return contractsModels.size();
    }

    public class ContractsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.contractImg)
        CircleImageView contractImg;
        @BindView(R.id.contractName)
        TextView contractName;

        public ContractsHolder(@NonNull View itemView) {
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
