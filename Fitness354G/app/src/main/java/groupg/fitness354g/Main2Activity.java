package groupg.fitness354g;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity {
    private TextView info;
   // private  JSONObject jsonObj;
    private  String JSONString = null;
   // private String something;
    private JSONArray workouts;
    private List<String> speed_data = new ArrayList();
    private List<String> time_data = new ArrayList();
    private List<Pair<String, String>> data = new ArrayList();

    public void callFitnessGraphView(View view)
    {
        Intent intent= new Intent(Main2Activity.this, FitnessGraphView.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String rawJson = loadJsonData();

        try {
            loadEachData(rawJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //e.printStackTrace();

        setContentView(R.layout.activity_main2);
        info=(TextView) findViewById((R.id.textView));
        Intent intent = getIntent();
        String Name= intent.getStringExtra("u_id");
        String Password= intent.getStringExtra("pwd");
        //loadJsonData();
        info.setText("Session info: "+Name +" and  "+ Password + "  " + data);
       // Log.d("TestText", data.toString());

    }

    // Filter the json file, and store it in another file
    public String loadJsonData(){

         ///String d;
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.data);
            int sizeOfJSONFile = inputStream.available();

            //array that will store all the data
            byte[] bytes = new byte[sizeOfJSONFile];

            //reading data into the array from the file
            inputStream.read(bytes);

            //close the input stream
            inputStream.close();

            JSONString = new String(bytes, "UTF-8");
            //jsonObj = new JSONObject(JSONString);
            //d = jsonObj.getString("data");
            // dataObj = jsonObj.getJSONObject("data");


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONString;
    }

    public void loadEachData(String d) throws JSONException {
        JSONObject w = new JSONObject(d);
        workouts = w.getJSONArray("data");
       // List<String> speed_data = new ArrayList();
       // List<String> time_data = new ArrayList();
       // List<Pair<String, String>> data = new ArrayList();

        for (int i = 0; i< workouts.length(); i++)
        {
            JSONObject c = workouts.getJSONObject(i);

            int sport = c.getInt("sport2");

            //only if the sport is cycling
            if(sport == 2)
            {
                String speed = c.getString("speed_kmh_avg");
                if (speed != null) {
                    speed_data.add(speed);
                    //return speed;
                }
                String date = c.getString("start_time");
                if (date != null){
                    time_data.add(date);
                }
                if(speed != null && date != null) {
                    data.add(new Pair<>(date, speed));
                }
            }
        }

        //print out all the workout results with for loop
        //print lists of strings

        //return speed_data.toString();

        //return w;
/*
            String time = c.getString("start_time");
            if(time != null) {
                time_data.add(time);
                return time;
            }
        }
        return "";
        */
    }

}
