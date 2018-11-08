package groupg.fitness354g;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity {
    private TextView info;
    private  JSONObject jsonObj;
    private  String JSONString = null;

    public void callFitnessGraphView(View view)
    {
        Intent intent= new Intent(Main2Activity.this, FitnessGraphView.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadJsonData();
        setContentView(R.layout.activity_main2);
        info=(TextView) findViewById((R.id.textView));
        Intent intent = getIntent();
        String Name= intent.getStringExtra("u_id");
        String Password= intent.getStringExtra("pwd");
        loadJsonData();
        info.setText("Session info: "+Name +" and  "+Password + "  " + jsonObj);

    }

    // Filter the json file, and store it in another file
    public JSONObject loadJsonData()
    {
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
            jsonObj = new JSONObject(JSONString);

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        catch (JSONException x) {
            x.printStackTrace();
            return null;
        }
        return jsonObj;
    }
}
