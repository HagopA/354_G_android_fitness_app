package groupg.fitness354g;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class FitnessGraphView extends AppCompatActivity {

    private LineGraphSeries<DataPoint> series;
    private LineGraphSeries<DataPoint> series2;

    private  String JSONString = null;
    private JSONArray workouts;
    private List<String> speed_data = new ArrayList();
    private List<String> time_data = new ArrayList();
    private List<Pair<String, String>> data = new ArrayList();
    private GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_graph_view);
        String rawJson = loadJsonData();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try
        {
            loadEachData(rawJson);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        String x;
        double y= 1;

        graph = findViewById(R.id.graph);
        DataPoint[] v = new DataPoint[data.size()];
        Log.d("klkl", String.valueOf(data.size()));

        for(int i = 0; i < data.size(); i++)
        {
            x = data.get(i).first;
            Date d = null;
            try
            {
                d = dateFormat.parse(x);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            y = Double.parseDouble(data.get(i).second);
            v[i] = new DataPoint(d, y);

        }
        series = new LineGraphSeries<>(v);
        Date d1 = null;
        Date d2 = null;
        try
        {
            d1 = dateFormat.parse(data.get(0).first);
            d2 = dateFormat.parse(data.get(data.size()-1).first);
            System.out.println(d1);
            System.out.println(d2);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }


        Calendar c = Calendar.getInstance();
        c.setTime(d2);
        c.add(Calendar.DATE, 7);
        d2 = c.getTime();
        c.setTime(d1);
        c.add(Calendar.DATE, -7);
        d1 = c.getTime();
        v = new DataPoint[2];
        v[0] = new DataPoint(d1, y);
        v[1] = new DataPoint(d2, y);
        series2 = new LineGraphSeries<>(v);

        graph.setTitle("Average speed of the workout");
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getViewport().setMinX(d1.getTime() - 86400 *2);
        graph.getViewport().setMaxX(d2.getTime());
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);
        graph.getGridLabelRenderer().setHumanRounding(false);
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(45);
        graph.getGridLabelRenderer().setVerticalAxisTitle("Avg Speed");
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Date");
        series.setDrawDataPoints(true);
        graph.addSeries(series);
        graph.addSeries(series2);

        series2.setColor(Color.alpha(0));

//        series.setShape(PointsGraphSeries.Shape.POINT);
        series.setColor(Color.MAGENTA);
    }

    public void onClick(View v){
        System.out.print("d");
    }

    public String loadJsonData(){

        ///String d;
        try {
            // FileInputStream inputStream = openFileInput("data.json");
            //int content;
            //String dataText = "";
            //while(content = inputStream.read() != -1){
            //  dataText += (char)content;
            //}
            //inputStream.close;
            //JSONString = new String(bytes, "UTF-8");


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
}
