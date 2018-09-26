package km.project.movieproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static MainActivity mActivity;
    public ListView listView;
    public CustomAdapter adapter;
    public static ArrayList<ItemModel> list;
    public static ViewPager mPager;
    LinearLayout mLinearLayout;
    CustomPagerAdapter2 mAdapter;
    CustomIndicator mIndicator;
    public static List<Fragment> fragments;
    public ProgressDialog progressDialog;
    private Button btn_alert;
    private String name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        onFindView();
        Util.showToast(MainActivity.this,"토스트 메세지");
        Util.getCurrentTimeMilles();

        Log.e(TAG,"현재시간 : " + Util.getCurrentTimeMilles());

    }

    private void onFindView() {

        list = new ArrayList<ItemModel>();
        adapter = new CustomAdapter(mActivity, list);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        mPager = (ViewPager) findViewById(R.id.viewPager);
        mLinearLayout = (LinearLayout) findViewById(R.id.pagesContainer);
        btn_alert =(Button)findViewById(R.id.btn_alert);

        fragments = new ArrayList<>();
        fragments.add(ContentFragment.newInstance2(R.drawable.network));
        fragments.add(ContentFragment.newInstance2(R.mipmap.ic_launcher_round));
        fragments.add(ContentFragment.newInstance2(R.drawable.meeting));
        fragments.add(ContentFragment.newInstance2(R.drawable.move));
        fragments.add(ContentFragment.newInstance2(R.drawable.search));
        mAdapter = new CustomPagerAdapter2(getSupportFragmentManager(), fragments);
        mPager.setAdapter(mAdapter);
        mIndicator = new CustomIndicator(this, mLinearLayout, mPager, R.drawable.indicator_circle);
        mIndicator.setPageCount(fragments.size());
        mIndicator.show();
        new GetJsonData().execute();
//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new setAutoPager(),2000,6000);
        Event();
    }

    private void Event(){

        btn_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(mActivity);
                alert.setTitle("alarm");
                Log.e(TAG,"name : "+ name);
                String realname = name;
                Log.e(TAG,"realname : "+ realname);
                alert.setMessage("여기에 Json데이터 넣기"+ realname);
                alert.create();
                alert.show();

            }
        });
    }

    public class GetJsonData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mActivity);
            progressDialog.setMessage("Loading Data...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                JSONObject jsonObject = JsonParser.getDataFromWeb();


                if (jsonObject != null) {

                    if (jsonObject.length() > 0) {


                        JSONArray jsonArray = jsonObject.getJSONArray("movies");

                        int lenArray = jsonArray.length();

                        if (lenArray > 0) {

                            for (int i = 0; i < jsonArray.length(); i++) {

                                jsonObject = jsonArray.getJSONObject(i);

                                ItemModel model = new ItemModel();

                                name = jsonObject.getString("movie");
                                String director = jsonObject.getString("director");
                                String image = jsonObject.getString("image");
                                Log.e(TAG, "Json image[" + i + "] : " + image + "\n");
                                model.setTitle(name);
                                model.setDirector(director);
                                model.setImage(image);
                                list.add(model);

                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (list.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(mActivity, "데이터를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
            progresshandler.sendEmptyMessageDelayed(0,1500);
        }
    }

    Handler progresshandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDialog.dismiss();
        }
    };


    private class CustomPagerAdapter2 extends FragmentStatePagerAdapter {

        List<Fragment> mFrags = new ArrayList<>();



        public CustomPagerAdapter2(FragmentManager fm, List<Fragment> mFrags) {
            super(fm);
            this.mFrags = mFrags;
        }

        @Override
        public Fragment getItem(int position) {

            int index = position % mFrags.size();
            return mFrags.get(index);
        }
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }
    }

    public static class setAutoPager extends TimerTask {

        @Override
        public void run() {

            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (mPager.getCurrentItem() < fragments.size() -1 ){

                        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    }else{
                        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    }
                }
            });

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIndicator.cleanup();
        progressDialog.dismiss();
    }

}
