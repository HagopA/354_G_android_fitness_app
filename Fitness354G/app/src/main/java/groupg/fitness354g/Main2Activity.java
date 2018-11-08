package groupg.fitness354g;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class Main2Activity extends AppCompatActivity {
    private TextView info;
    private  String jsonObj = null;

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
        info.setText("Session info: "+Name +" and  "+Password + "  " + jsonObj);

    }

    // Filter the json file, and store it in another file
    public String loadJsonData()
    {
        try {
            InputStream is = Main2Activity.this.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonObj = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return jsonObj;
    }
}
