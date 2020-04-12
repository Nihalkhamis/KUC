package com.deleny.kuc.ui.alltests;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.artjimlop.altex.AltexImageDownloader;
import com.deleny.kuc.R;
import com.deleny.kuc.adapter.TestImageAdapter;
import com.deleny.kuc.model.PendingRequestModel;
import com.deleny.kuc.model.RequestModel;
import com.deleny.kuc.utils.Connectors;
import com.deleny.kuc.utils.RestClient;
import com.orhanobut.hawk.Hawk;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllTestsFragment extends Fragment {


    @BindView(R.id.tests_tv)
    TextView testsTV;

    Connectors.connectionService connectionService = RestClient.getClient().create(Connectors.connectionService.class);

    ProgressDialog dialog;

    List<PendingRequestModel> pendingRequestModels;

    URL url;
    AsyncTask mMyTask;


    @BindView(R.id.testImageRV)
    RecyclerView testImageRV;

    TestImageAdapter testImageAdapter;
    List<RequestModel> requestModels;
    GridLayoutManager gridLayoutManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_alltests, container, false);

        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dialog = new ProgressDialog(getContext());
        dialog.setTitle(getString(R.string.Loading));

        pendingRequestModels = new ArrayList<>();

        requestModels = new ArrayList<>();

        gridLayoutManager = new GridLayoutManager(getContext(),5);

        testImageRV.setLayoutManager(gridLayoutManager);

        testImageAdapter = new TestImageAdapter(getContext(), requestModels, new TestImageAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {

            }
        });

        testImageRV.setAdapter(testImageAdapter);


        if (!Hawk.isBuilt()) {
            Hawk.init(getContext()).build();
        }


        getPendingRequest();

        getRequest();


    }

    private void getRequest() {
        dialog.show();
        connectionService.getAllRequest(Hawk.get("ID"), 1).enqueue(new Callback<ArrayList<RequestModel>>() {
            @Override
            public void onResponse(Call<ArrayList<RequestModel>> call, Response<ArrayList<RequestModel>> response) {
                dialog.dismiss();
                Log.d("TTT", "onResponse: " + call.request().url());
                Log.d("TTT", "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    Log.d("TTT", "onResponse: " + response.body().get(0).getImagePath());

                   // requestModels.addAll(response.body());

                    for (int i = 0; i < response.body().size(); i++){
                       // AltexImageDownloader.writeToDisk(getContext(), response.body().get(i).getImagePath(), "Images"); //NAME_FOLDER is the name of the folder where you want to save the image.

                      //  mMyTask = new DownloadTask().execute(stringToURL(response.body().get(i).getImagePath()));
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<RequestModel>> call, Throwable t) {
                Log.d("TTT", "onFailure: " + t.getMessage());

            }
        });
    }

    private class DownloadTask extends AsyncTask<URL, Void, Bitmap> {
        protected void onPreExecute() {
            dialog.show();
        }

        protected Bitmap doInBackground(URL... urls) {
            URL url = urls[0];
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                return BitmapFactory.decodeStream(bufferedInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        // When all async task done
        protected void onPostExecute(Bitmap result) {
            // Hide the progress dialog

            dialog.dismiss();
            if (result != null) {
                saveImage(getContext(), result, "my_image.png");

            } else {
                // Notify user that an error occurred while downloading image
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected URL stringToURL(String path) {
        try {
            url = new URL(path);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveImage(Context context, Bitmap b, String imageName) {
        Log.d("TTT", "saveImage: " + "Image Saved");
        FileOutputStream foStream;
        try {
            foStream = context.openFileOutput(imageName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, foStream);
            foStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 2, Something went wrong!");
            e.printStackTrace();
        }

        loadImageBitmap(getContext(), "my_image.png");
    }


    public Bitmap loadImageBitmap(Context context, String imageName) {
        Log.d("TTT", "loadImageBitmap: " + "getting image");
        Bitmap bitmap = null;
        FileInputStream fiStream;
        try {
            fiStream = context.openFileInput(imageName);
            bitmap = BitmapFactory.decodeStream(fiStream);
            // firstImg.setImageBitmap(bitmap);

            fiStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 3, Something went wrong!");
            e.printStackTrace();
        }
        return bitmap;
    }

    private void getPendingRequest() {
        dialog.show();
        Log.d("TTT", "getPendingRequest: " + Hawk.get("ID"));
        connectionService.getRequest(Hawk.get("ID")).enqueue(new Callback<PendingRequestModel>() {
            @Override
            public void onResponse(Call<PendingRequestModel> call, Response<PendingRequestModel> response) {
                dialog.dismiss();

                Log.d("TTT", "onResponse: " + call.request().url());
                Log.d("TTT", "onResponse: " + response.code());

                if (response.isSuccessful()) {
                    Log.d("TTT", "onResponse: " + response.body().getPendingRequest());
                    testsTV.setText(response.body().getPendingRequest());
                }
            }

            @Override
            public void onFailure(Call<PendingRequestModel> call, Throwable t) {
                Log.d("TTT", "onFailure: " + t.getMessage());
            }
        });
    }
}