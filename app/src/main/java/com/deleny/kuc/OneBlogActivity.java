package com.deleny.kuc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OneBlogActivity extends AppCompatActivity {


    @BindView(R.id.share)
    ImageView share;


    @BindView(R.id.oneBLogPhoto)
    ImageView oneBLogPhoto;
    @BindView(R.id.oneBlogTitle)
    TextView oneBlogTitle;
    @BindView(R.id.oneBlogDate)
    TextView oneBlogDate;
    @BindView(R.id.oneBlogBody)
    TextView oneBlogBody;
    @BindView(R.id.blogTitle)
    TextView blogTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_blog);
        ButterKnife.bind(this);

        Intent intent = getIntent();


        String title = intent.getStringExtra("BlogTitle");


        blogTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        byte[] decodedString = Base64.decode(intent.getStringExtra("BlogPhoto"), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Glide.with(OneBlogActivity.this).load(decodedByte).fitCenter().into(oneBLogPhoto);

        blogTitle.setText(intent.getStringExtra("BlogTitle"));

        oneBlogTitle.setText(intent.getStringExtra("BlogTitle"));
        oneBlogDate.setText(intent.getStringExtra("BlogDate"));
        oneBlogBody.setText(intent.getStringExtra("BlogBody"));

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
//                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/Share.png";
//                OutputStream out = null;
//                File file=new File(path);
//                try {
//                    out = new FileOutputStream(file);
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//                    out.flush();
//                    out.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                path=file.getPath();
//                Uri bmpUri = Uri.parse("file://"+path);



//                Intent intent = new Intent(Intent.ACTION_SEND);
//
//                Bitmap image=((BitmapDrawable)oneBLogPhoto.getDrawable()).getBitmap();
//                Log.d("TTT", "onClick: "+image);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                byte[] imageInByte = stream.toByteArray();
//                ByteArrayInputStream bis = new ByteArrayInputStream(imageInByte);
//                Log.d("TTT", "onClick: "+bis);
//
//              //  Uri screenshotUri = Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI + "/" + oneBLogPhoto);
//
//                Uri screenshotUri = Uri.parse("android.resource://com.android.test/*");
//                try {
//                    InputStream stream1 = getContentResolver().openInputStream(screenshotUri);
//                } catch (FileNotFoundException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                intent.setType("image/*");
//                intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
//
//
//                Log.d("TTT", "onClick: "+title);
//                intent.putExtra(Intent.EXTRA_TEXT, title);
//
//
//                intent.setType("text/plain");
//
//                startActivity(Intent.createChooser(intent, "Share via"));
                //--------------------------------------------------------------------------------------------

//                View content = findViewById(R.id.oneBLogPhoto);
//                content.setDrawingCacheEnabled(true);
//                Bitmap bitmap = content.getDrawingCache();
//                File root = Environment.getExternalStorageDirectory();
//                File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/image.jpg");
//                try {
//                    cachePath.createNewFile();
//                    FileOutputStream ostream = new FileOutputStream(cachePath);
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
//                    ostream.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("image/*");
//               // Uri phototUri = Uri.parse("/DCIM/Camera/image.jpg");
//               // shareIntent.setData(phototUri);
//                shareIntent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(cachePath));
//                startActivity(Intent.createChooser(shareIntent, "Share Via"));
                //------------------------------------------------------------------------------------------------
//                Drawable drawable = oneBLogPhoto.getDrawable();
//                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
//
//                try {
//                      File file = new File(OneBlogActivity.this.getExternalCacheDir(),"myImage.png");
//                      FileOutputStream fOut = new FileOutputStream(file);
//                      bitmap.compress(Bitmap.CompressFormat.PNG,80,fOut);
//                      fOut.flush();
//                      fOut.close();
//                      file.setReadable(true,false);
//                      Intent intent = new Intent(Intent.ACTION_SEND);
//                      intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
//                      intent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(file));
//                      intent.setType("image/png");
//                      startActivity(Intent.createChooser(intent,"share via"));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                    Toast.makeText(OneBlogActivity.this, "File not found", Toast.LENGTH_SHORT).show();
//                }
//                catch (IOException e) {
//                    e.printStackTrace();
//                }
//                catch (Exception e){
//                    e.printStackTrace();
//                }

                startShare();

            }
        });






    }

    private void startShare() {
        Bitmap bitmap = viewToBitmap(oneBLogPhoto,oneBLogPhoto.getWidth(),oneBLogPhoto.getHeight());
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        File file = new File(Environment.getExternalStorageDirectory()+File.separator+"ImageDemo.jpg");
        try {
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        intent.putExtra(Intent.EXTRA_STREAM,Uri.parse("file:///sdcard/ImageDemo.jpg"));
        startActivity(Intent.createChooser(intent,"Share via"));
    }

    public static Bitmap viewToBitmap(View view,int width, int height){
        Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}
