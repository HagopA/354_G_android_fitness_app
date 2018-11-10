package groupg.fitness354g;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;

import cz.msebera.android.httpclient.Header;

public class EndoLogin {
    public String email = "e_therou@encs.concordia.ca";
    public String password = "passClass1";
    private static final String BASE_URL = "https://api.mobile.endomondo.com/mobile/";
    private String auth = "";
    public String info;
    private static AsyncHttpClient client = new AsyncHttpClient();
    public MainActivity t;


    RequestParams params = null;

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public void AccessEndo(String method, MainActivity nt){
        t = nt;
        if(method.equals("Authenticate")){
            Authenticate();
        }
        else if(method.equals("Get")){
            GetWorkouts();
        }
    }

    public boolean Authenticate(){
        final String authUrl = "auth?deviceId=dummy&email=" + email + "&password=" + password + "&country=CA&action=PAIR";
        client.get(getAbsoluteUrl(authUrl), null, new TextHttpResponseHandler(){
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Log.d("dong", s);
                t.invalid();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                if(s.substring(0, 2).equals("OK")) {
                    auth = s.substring(s.indexOf("authToken=") + 10, s.indexOf("measure="));
                    auth = auth.trim();
                    Log.d("dongash", auth);
                    GetWorkouts();
                }
                else{
                    Log.d("dongish", s);
                    t.invalid();
                }
            }
            });
        Log.d("dong", "later");

        return true;
    }

    public void GetWorkouts(){
        Log.d("dong2", auth + "HI");
        String url = "api/workout/list?authToken=" + auth + "&maxResults=" + 1000;
        client.get(getAbsoluteUrl(url), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("dong3", response.toString());
                try {
                    JSONArray r = response.getJSONArray("data");
                    for(int i = 0; i< r.length();i++){
                        Log.d("dong4", r.getString(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    t.invalid();
                }
                try {
                    FileOutputStream fos = t.getBaseContext().openFileOutput("data.json", Context.MODE_PRIVATE);
                    fos.write(response.toString().getBytes());
                    fos.close();
                } catch (Exception e) {
                    Log.d("jj", e.getMessage());
                }

                info = response.toString();
                t.valid();
            }
        });
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
