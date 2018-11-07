package groupg.fitness354g;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView info;
    private Button Login;
    private Integer Count=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name =(EditText) findViewById(R.id.etName);
        Password =(EditText) findViewById(R.id.etPassword);
        Login =(Button) findViewById(R.id.btnLogin);
        info=(TextView) findViewById((R.id.tvinfo));
        info.setText("No of attemps remaining: 3");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v) {
                //Intent intent= new Intent(MainActivity.this,SecondActivity.class);
                //   info.setText("No of attemps remaining:"+Password.getText().toString());
                validate(Name.getText().toString(),Password.getText().toString());
            }
        });


    }
    private void validate(String userName, String userPassword)
    {

        if((userName.equals("user"))&& (userPassword.equals("7890")))
        {
            Intent intent= new Intent(MainActivity.this,Main2Activity.class);

            intent.putExtra("u_id",userName);
            intent.putExtra("pwd", userPassword);
            startActivity(intent);

        }
        else
        {
            // Intent intent= new Intent(MainActivity.this,SecondActivity.class);
            //  startActivity(intent);
            Count--;
            info.setText("No of attemps remaining:"+String.valueOf(Count));
            if(Count==0)
            {
                Login.setEnabled(false);
            }
        }
    }
}
