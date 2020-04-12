package com.deleny.kuc.ui.home;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.deleny.kuc.AboutUsActivity;
import com.deleny.kuc.ContactUsActivity;
import com.deleny.kuc.ContractsActivity;
import com.deleny.kuc.HomeActivity;
import com.deleny.kuc.NewsActivity;
import com.deleny.kuc.R;
import com.deleny.kuc.ServicesActivity;
import com.deleny.kuc.adapter.CategoryAdapter;
import com.deleny.kuc.model.CategoryModel;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    ArrayList<CategoryModel> categoryModels;
    GridLayoutManager gridLayoutManager;


    @BindView(R.id.menu_image)
    ImageView menuImage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, root);


        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        menuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).menu();

            }
        });

        categoryModels = new ArrayList<>();

        categoryModels.add(new CategoryModel("عن المركز", R.drawable.about));
        categoryModels.add(new CategoryModel("خدماتنا", R.drawable.service));
        categoryModels.add(new CategoryModel("التعاقدات", R.drawable.ensurance));
        categoryModels.add(new CategoryModel("الاخبار", R.drawable.news));
        categoryModels.add(new CategoryModel("اتصل بنا", R.drawable.contacts));
        categoryModels.add(new CategoryModel("اطلبنا الان", R.drawable.call_now));


        recyclerView = view.findViewById(R.id.categoryRV);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayoutManager = new GridLayoutManager(getContext(), 3);
        } else {
            gridLayoutManager = new GridLayoutManager(getContext(), 2);
        }


        recyclerView.setLayoutManager(gridLayoutManager);

        categoryAdapter = new CategoryAdapter(getContext(), categoryModels, new CategoryAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getActivity(), AboutUsActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), ServicesActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), ContractsActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), NewsActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(getActivity(), ContactUsActivity.class));
                        break;
                    case 5:
                        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +"034207755"));
                        startActivity(i);
                        break;
                }
            }
        });

        recyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

    }
}