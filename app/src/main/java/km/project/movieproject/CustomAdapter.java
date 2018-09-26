package km.project.movieproject;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private static final String TAG = CustomAdapter.class.getSimpleName();
    public Activity mActivity;
    public ArrayList<ItemModel> list = new ArrayList<ItemModel>();

    public CustomAdapter(Activity mActivity, ArrayList<ItemModel> list) {
        this.mActivity = mActivity;
        this.list = list;
    }

    public class ViewHolder{

        public ImageView imageView;
        public TextView txt_title;
        public TextView txt_director;
        public CheckBox checkbox;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {

        ViewHolder holder;

        if (convertview == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertview = inflater.inflate(R.layout.list_item,parent,false);
            convertview.setTag(holder);

            holder.imageView = (ImageView)convertview.findViewById(R.id.imageView);
            holder.txt_title = (TextView)convertview.findViewById(R.id.txt_title);
            holder.txt_director = (TextView)convertview.findViewById(R.id.txt_director);
            holder.checkbox = (CheckBox)convertview.findViewById(R.id.checkbox);
        }else{
            holder = (ViewHolder)convertview.getTag();
        }

        ItemModel model = (ItemModel)getItem(position);

        Glide.with(mActivity).load(model.getImage()).into(holder.imageView);
        holder.txt_title.setText(model.getTitle());
        holder.txt_director.setText(model.getDirector());



        return convertview;
    }
}
