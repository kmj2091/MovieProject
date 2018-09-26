package km.project.movieproject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SavedImageActivity extends Activity  {

    private static final String TAG = SavedImageActivity.class.getSimpleName();
    public SavedImageActivity mActivity;

    String imageUrl = "https://t1.daumcdn.net/cfile/tistory/112CA2274C2220D2B4";
    Bitmap mSaveBm;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savedimage);
        mActivity=this;
        ImageView bmImage = (ImageView) findViewById(R.id.image);
        Button btnSave = (Button) findViewById(R.id.btnSave);

        BitmapFactory.Options bmOptions;
        bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;

        OpenHttpConnection opHttpCon = new OpenHttpConnection();
        opHttpCon.execute(bmImage, imageUrl);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OutputStream outStream = null;
                String extStorageDirectory =
                        Environment.getExternalStorageDirectory().toString();

                File file = new File(extStorageDirectory, "downimage.PNG");
                try {
                    outStream = new FileOutputStream(file);
                    mSaveBm.compress(
                            Bitmap.CompressFormat.PNG, 100, outStream);
                    outStream.flush();
                    outStream.close();

                    Toast.makeText(mActivity,
                            "Saved", Toast.LENGTH_LONG).show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(mActivity,
                            e.toString(), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(mActivity,e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });

   }

    private class OpenHttpConnection extends AsyncTask<Object, Void, Bitmap> {

        private ImageView bmImage;

        @Override
        protected Bitmap doInBackground(Object... params) {
            Bitmap mBitmap = null;
            bmImage = (ImageView) params[0];
            String url = (String) params[1];
            InputStream in = null;
            try {
                in = new java.net.URL(url).openStream();
                mBitmap = BitmapFactory.decodeStream(in);
                in.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return mBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bm) {
            super.onPostExecute(bm);
            mSaveBm = bm;
            bmImage.setImageBitmap(bm);
        }
    }

}
