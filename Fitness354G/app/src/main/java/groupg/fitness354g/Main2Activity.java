package groupg.fitness354g;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    private TextView info;

    public void callFitnessGraphView(View view)
    {
        Intent intent= new Intent(Main2Activity.this, FitnessGraphView.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        info=(TextView) findViewById((R.id.textView));
        Intent intent = getIntent();
        String Name= intent.getStringExtra("u_id");
        String Password= intent.getStringExtra("pwd");
        info.setText("Session info: "+Name +" and  "+Password);
    }
}
