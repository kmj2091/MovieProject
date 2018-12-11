package km.project.movieproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class BitmapActivity extends Activity {

    private static final String TAG = BitmapActivity.class.getSimpleName();
    public BitmapActivity mActivity;
    private ImageView img_download;
    private Button btn_download;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bitmap);
        mActivity = this;
        onFindView();
        Event();
    }

    private void onFindView() {

        img_download = (ImageView) findViewById(R.id.img_download);
        btn_download = (Button) findViewById(R.id.btn_download);
    }

    private void Event() {

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DownloadImageTask().execute("https://t1.daumcdn.net/cfile/tistory/112CA2274C2220D2B4");
            }
        });
    }

    private class DownloadImageTask extends AsyncTask<String,Void,Bitmap> {

        Bitmap bitmap = null;
        ProgressDialog progressDialog = new ProgressDialog(mActivity);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog.show(mActivity,"","다운로드 중입니다...");
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];

            try {
                InputStream inputStream = new URL(urldisplay).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap bitmap) {
           img_download.setImageBitmap(bitmap);
           progressDialog.dismiss();

        }
    }

//    private Bitmap GetImageFromUrl(String imageurl) {
//
//        Bitmap bitmap = null;
//
//        try {
//            URL url = new URL(imageurl);
//            URLConnection connection = url.openConnection();
//            connection.connect();
//
//            int Size = connection.getContentLength();
//            BufferedInputStream bis = new BufferedInputStream(connection.getInputStream(), Size);
//            bitmap = BitmapFactory.decodeStream(bis);
//            bis.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return bitmap;
//    }


}
