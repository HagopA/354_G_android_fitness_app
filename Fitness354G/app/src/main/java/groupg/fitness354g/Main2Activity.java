package groupg.fitness354g;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
    private static List<Pair<String, String>> data = new ArrayList();
    private TextView minimumSpeed;
    private TextView maximumSpeed;
    private TextView averageSpeed;


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

        setContentView(R.layout.activity_main2);
        info=(TextView) findViewById((R.id.textView));
        Intent intent = getIntent();
        String summary= "";
        for(int i = 0; i < data.size(); i++){
            summary += data.get(i).first + "  -  " +data.get(i).second + "\n";
        }
        info.setText(summary);
        minimumSpeed =(TextView) findViewById((R.id.minSpeed));
        String minSpeed = dsplayMinSpeed();
        minimumSpeed.setText("Minimum Speed : "+ minSpeed);

        maximumSpeed =(TextView) findViewById((R.id.maxSpeed));
        String maxSpeed = displayMaxSpeed();
        maximumSpeed.setText("Maximum Speed : "+ maxSpeed);

        averageSpeed =(TextView) findViewById((R.id.averageSpeed));
        Float avgSpeed = displayAverageSpeedWorkout();
        averageSpeed.setText("Average Speed : "+ avgSpeed);
       // Log.d("TestText", data.toString());

    }

    // Filter the json file, and store it in another file
    public String loadJsonData(){

        ///String d;
        try {
            FileInputStream inputStream = openFileInput("data.json");
            int content;
            String dataText = "";
            while((content = inputStream.read()) != -1){
                dataText += (char)content;
            }
            inputStream.close();
            JSONString = dataText;
//
//
//            InputStream inputStream = getResources().openRawResource(R.raw.data);
//            int sizeOfJSONFile = inputStream.available();
//
//            //array that will store all the data
//            byte[] bytes = new byte[sizeOfJSONFile];
//
//            //reading data into the array from the file
//            inputStream.read(bytes);
//
//            //close the input stream
//            inputStream.close();
//
//            JSONString = new String(bytes, "UTF-8");
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
                    data.add(new Pair<>(date.substring(0,10), speed));
                }
            }
        }

        sortData();

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

    private void sortData()
    {
        Collections.sort(data, new Comparator<Pair<String, String>>()
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            @Override
            public int compare(Pair<String, String> o1, Pair<String, String> o2)
            {
                Date d1 = null;
                Date d2 = null;

                try
                {
                    d1 = dateFormat.parse(o1.first);
                    d2 = dateFormat.parse(o2.first);
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }

                return d1.compareTo(d2);
            }
        });
    }

    private String dsplayMinSpeed()
    {
        String minimumWorkoutSpeed = Collections.min(speed_data);
        return minimumWorkoutSpeed;
    }

    private String displayMaxSpeed()
    {
        String maximumWorkoutSpeed = Collections.max(speed_data);

        return maximumWorkoutSpeed;
    }

    private float displayAverageSpeedWorkout()
    {
        float averageSpeedWorkouts = 0;

        if(!speed_data.isEmpty()) {
            for (String speed_data: speed_data) {
                averageSpeedWorkouts += Float.parseFloat(speed_data);
            }
            return averageSpeedWorkouts / speed_data.size();
        }
        return averageSpeedWorkouts;
    }
}
