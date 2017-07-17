package ltd.pvt.tagore_6.navigationapp;

import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ltd.pvt.tagore_6.navigationapp.fragments.LoginFragment;

import static ltd.pvt.tagore_6.navigationapp.MainActivity.*;


public class controller {


    public static final String TAG = "TAG";

    public static final String WAURL = "https://script.google.com/macros/s/AKfycbwfhbPl_GhYEyBw9q220C0944Jfn1lWf5gVLgS6pYTuNi2fyxc/exec?";
    private static Response response;

    public static JSONObject updateData(String party,int votes) {
        try {
           // MainActivity.yoyo=MainActivity.ind_elec+party+votes+request.toString();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(WAURL +"uid="+MainActivity.uid+"&sheet1=Sheet"+ ind_elec+"&sheet2=Sheet"+MainActivity.sheet_uid+"&action=update&party="+party+"&votes=" +votes)
                    .build();

           // request.toString();
            response = client.newCall(request).execute();
            //    Log.e(TAG,"response from gs"+response.body().string());
            return new JSONObject(response.body().string());


        } catch (@NonNull IOException | JSONException e) {
            Log.e(TAG, "recieving null " + e.getLocalizedMessage());
        }
        return null;
    }


    public static JSONObject insertData(String uid) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(WAURL+"action=insert&uid="+uid+"&sheet=Sheet"+MainActivity.sheet_uid)
                    .build();
            response = client.newCall(request).execute();
            //    Log.e(TAG,"response from gs"+response.body().string());
            return new JSONObject(response.body().string());


        } catch (@NonNull IOException | JSONException e) {
            Log.e(TAG, "recieving null " + e.getLocalizedMessage());
        }
        return null;
    }

}