package km.project.movieproject;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {


    private static final String TAG = Util.class.getSimpleName();

    public static Activity mActivity;


    public static void showToast(Context context, String message){
        Toast toast = Toast.makeText(context,message,Toast.LENGTH_SHORT);
        toast.show();
    }

    public static String getCurrentTimeMilles(){

        String result = "";

        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        result = simpleDateFormat.format(date);

        return result;
    }
}
