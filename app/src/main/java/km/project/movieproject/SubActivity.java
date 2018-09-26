package km.project.movieproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class SubActivity extends AppCompatActivity {

    private static final String TAG = SubActivity.class.getSimpleName();

    ViewPager mPager;
    LinearLayout mLinearLayout;
    CustomPagerAdapter2 mAdapter;
    CustomIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_activity);
        mPager = (ViewPager) findViewById(R.id.pager);
        mLinearLayout = (LinearLayout) findViewById(R.id.pagesContainer);

        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(ContentFragment.newInstance2(R.drawable.network));
//        fragments.add(ContentFragment.newInstance2(R.drawable.meeting));
//        fragments.add(ContentFragment.newInstance2(R.drawable.search));
//        fragments.add(ContentFragment.newInstance2(R.drawable.move));
        mAdapter = new CustomPagerAdapter2(getSupportFragmentManager(),fragments);
        mPager.setAdapter(mAdapter);
        mIndicator = new CustomIndicator(this, mLinearLayout, mPager, R.drawable.indicator_circle);
        mIndicator.setPageCount(fragments.size());
        mIndicator.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIndicator.cleanup();
    }



    private class CustomPagerAdapter2 extends FragmentStatePagerAdapter {

        List<Fragment> mFrags = new ArrayList<>();

        public CustomPagerAdapter2(FragmentManager fm,List<Fragment> mFrags) {
            super(fm);
            this.mFrags=mFrags;
        }

        @Override
        public Fragment getItem(int position) {

            int index = position % mFrags.size();
            Log.e(TAG,"position  : " + position);
            Log.e(TAG,"Frag Size : " + mFrags.size());
            Log.e(TAG,"index     : " + index);
            return mFrags.get(index);
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

    }
}

