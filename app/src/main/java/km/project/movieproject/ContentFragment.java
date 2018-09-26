package km.project.movieproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class ContentFragment extends Fragment {

    private static final String TAG = ContentFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";

    private int mParam1;

    public ContentFragment() {
        // Required empty public constructor
    }

    public static ContentFragment newInstance(String result) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, result);
        fragment.setArguments(args);
        return fragment;
    }

    public static ContentFragment newInstance2(int result) {

        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, result);
        fragment.setArguments(args);
        return fragment;
    }

    public static ContentFragment newInstance3(String list){

        ContentFragment contentFragment = new ContentFragment();
        Bundle args2 = new Bundle();
        args2.putString(ARG_PARAM1,list);
        contentFragment.setArguments(args2);

        return contentFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(mParam1);

        return view;
    }



    public int getmParam1() {
        return mParam1;
    }

}

