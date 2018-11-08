package groupg.fitness354g;

import android.preference.PreferenceActivity;
import android.util.Log;

import com.loopj.android.http.*;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class EndoLogin {
    public String email = "e_therou@encs.concordia.ca";
    public String password = "passClass1";
    private static final String BASE_URL = "https://api.mobile.endomondo.com/mobile/";
    private String auth = "";
    public String info;
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public boolean Authenticate(){
        String authUrl = "auth?deviceId=dummy&email=" + email + "&password=" + password + "&country=CA&action=PAIR";
        client.get(getAbsoluteUrl(authUrl), null, new TextHttpResponseHandler(){
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                System.out.print("FUCK");
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                System.out.print(s);
                auth = s.substring(s.indexOf("authToken=") + 10, s.indexOf("measure="));
                auth = auth.trim();
                Log.d("dong", auth);
                GetWorkouts();
            }
            });

        return true;
    }

    public void GetWorkouts(){
        Log.d("dong2", auth + "HI");
        String url = "api/workout/list?authToken=" + auth + "&maxResults=" + 1000;
        client.get(getAbsoluteUrl(url), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("dang", response.toString());
                info = response.toString();
            }
        });
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
