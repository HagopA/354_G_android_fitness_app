package groupg.fitness354g;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.multidex.MultiDex;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView info;
    private Button Login;
    private Integer Count=3;
    EndoLogin en;
    public AlertDialog.Builder validation;
    public AlertDialog.Builder invalidDialog;
    public AlertDialog.Builder validDialog;
    public AlertDialog alertDialog;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name = findViewById(R.id.etName);
        Password = findViewById(R.id.etPassword);
        Login = findViewById(R.id.btnLogin);
        info = findViewById((R.id.tvinfo));
        info.setText("No of attemps remaining: 3");
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v) {
                //Intent intent= new Intent(MainActivity.this,SecondActivity.class);
                //   info.setText("No of attemps remaining:"+Password.getText().toString());
                validate(Name.getText().toString(),Password.getText().toString());
            }
        });
        en = new EndoLogin();
        en.t = this;

        validation = new AlertDialog.Builder(this);
        validation.setMessage("Awaiting Authentication");
        invalidDialog = new AlertDialog.Builder(this);
        invalidDialog.setNegativeButton("Try Another Username/Password", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Count--;
                info.setText("No of attemps remaining:"+String.valueOf(Count));
                if(Count==0)
                {
                    Login.setEnabled(false);
                }
                alertDialog = validation.create();
            }
        }).setMessage("Authentication Invalid");
        validDialog = new AlertDialog.Builder(this);
        validDialog.setPositiveButton("Go to menu", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(MainActivity.this, GraphView.class);

                intent.putExtra("u_id", Name.getText());
                intent.putExtra("pwd", Password.getText());
                startActivity(intent);
                alertDialog = validation.create();
            }
        });

        alertDialog = validation.create();
        alertDialog.show();
        alertDialog.dismiss();

    }


    public void invalid(){
        alertDialog.dismiss();
            alertDialog = invalidDialog.create();
            alertDialog.show();
    }

    public void valid(){
        alertDialog.dismiss();
        alertDialog = validDialog.create();
        alertDialog.show();
    }

    private void validate(String userName, String userPassword)
    {
        en.email = userName;
            en.password = userPassword;
            en.Authenticate();
        alertDialog.show();
    }
}
