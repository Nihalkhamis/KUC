package com.deleny.kuc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deleny.kuc.adapter.NewsAdapter;
import com.deleny.kuc.model.NewsModel;
import com.deleny.kuc.service.BlogDatabase;
import com.deleny.kuc.utils.Connectors;
import com.deleny.kuc.utils.RestClient;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {

    NewsAdapter newsAdapter;
    List<NewsModel> newsModels;
    LinearLayoutManager linearLayoutManager;

    @BindView(R.id.news_tv)
    TextView newsTv;
//    @BindView(R.id.share)
//    ImageView share;
    @BindView(R.id.newsCardRV)
    RecyclerView newsCardRV;

    ProgressDialog dialog;

    boolean connected = false;


    Integer blogID;

    Connectors.connectionService connectionService = RestClient.getClient().create(Connectors.connectionService.class);
    @BindView(R.id.articleName)
    TextView articleName;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.sectionName)
    TextView sectionName;
    @BindView(R.id.backgroundImg)
    ImageView backgroundImg;

    BlogDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        dialog = new ProgressDialog(this);
        dialog.setTitle(getString(R.string.Loading));

        database = BlogDatabase.getInstance(this);

        newsModels = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        newsAdapter = new NewsAdapter(this, newsModels, new NewsAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {
                Intent intent = new Intent(NewsActivity.this, OneBlogActivity.class);
                intent.putExtra("BlogPhoto", newsModels.get(position).getImagePath());
                intent.putExtra("BlogTitle", newsModels.get(position).getBlogTitle());
                intent.putExtra("BlogDate", newsModels.get(position).getPublishDate().substring(0, 10));
                intent.putExtra("BlogBody", newsModels.get(position).getBlogBody());
                startActivity(intent);
            }
        });


        newsCardRV.setAdapter(newsAdapter);

        if (!Hawk.isBuilt()){
        Hawk.init(this).build();
        }

        if (Hawk.get("BlogID") != null) {
            blogID = Hawk.get("BlogID");
        } else {
            blogID = 0;
        }

        newsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("text/plain");
//                String shareBody = "Your body here";
//                String shareSub = "Your subject here";
//                intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
//                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
//                startActivity(Intent.createChooser(intent, "Share via"));
//            }
//        });


        //check for the internet connection
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            Log.d("TTT", "onCreate: " + "the internet connection is : " + connected);

            getBlogs(blogID);



        } else {
            getBLogsFromDB();

        }
    }

    public void getBLogsFromDB() {
        //newsModels.clear();
        connected = false;
        Log.d("TTT", "onCreate: " + "the internet connection is : " + connected);

        newsModels.addAll(database.blogDao().getBlogs());
       // Log.d("TTT", "onCreate: " + blogs.get(0).getBlogID());


        byte[] decodedString = Base64.decode(newsModels.get(newsModels.size() - 1).getImagePath(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Glide.with(NewsActivity.this).load(decodedByte).fitCenter().into(backgroundImg);

        sectionName.setText(newsModels.get(newsModels.size() - 1).getCategoryName());
        articleName.setText(newsModels.get(newsModels.size() - 1).getBlogTitle());
        date.setText(newsModels.get(newsModels.size() - 1).getPublishDate().substring(0, 10));


        newsAdapter.notifyDataSetChanged();
    }

    private void getBlogs(int blogid) {
        dialog.show();

        connectionService.getBlogs(blogid).enqueue(new Callback<ArrayList<NewsModel>>() {
            @Override
            public void onResponse(Call<ArrayList<NewsModel>> call, Response<ArrayList<NewsModel>> response) {
                dialog.dismiss();

                Log.d("TTT", "onResponse: " + call.request().url());
                Log.d("TTT", "onResponse: " + response.code());

                if (response.isSuccessful()) {
                    Log.d("TTT", "onResponse: " + response.body().get(0).getBlogID());
                    Log.d("TTT", "onResponse: " + response.body().get(0).getBlogBody());

                    newsModels.addAll(response.body());


                    //save data in roomDB
                    database.blogDao().deleteAllBlogs();

                    for (int i = 0; i < newsModels.size(); i++) {
                        database.blogDao().addBlog(newsModels.get(i));
                    }


                    //set the last blog info in the upper cardview
                    byte[] decodedString = Base64.decode(newsModels.get(newsModels.size() - 1).getImagePath(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    Glide.with(NewsActivity.this).load(decodedByte).fitCenter().into(backgroundImg);

                    sectionName.setText(newsModels.get(newsModels.size() - 1).getCategoryName());
                    articleName.setText(newsModels.get(newsModels.size() - 1).getBlogTitle());
                    date.setText(newsModels.get(newsModels.size() - 1).getPublishDate().substring(0, 10));

                    Log.d("TTT", "onResponse: " + newsModels.size());


                    if (blogID == 0) {
                        Log.d("TTT", "onResponse: " + "into if condition");
                        for (int i = 0; i < response.body().size(); i++) {
                            if (response.body().get(i).getBlogID() > blogID) {
                                blogID = response.body().get(i).getBlogID();
                            }
                        }
                    }

                    Log.d("TTT", "onResponse: " + blogID);
                    Hawk.put("BlogID", blogID);


                    newsAdapter.notifyDataSetChanged();

                } else {
                    getBLogsFromDB();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<NewsModel>> call, Throwable t) {
                Log.d("TTT", "onFailure: " + t.getMessage());
            }
        });
    }

}
