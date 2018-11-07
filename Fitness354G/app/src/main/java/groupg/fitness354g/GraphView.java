package groupg.fitness354g;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Scanner is = null;
        try
        {
            is = new Scanner(new File("Fitness354G\\app\\src\\data.json"));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }



//        Scanner is = null;
//        try
//        {
//            is = new Scanner(new File("Fitness354G\\app\\src\\data.json"));
//        }
//        catch (FileNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//
//        String text = "cdasvfdbd";
//
////        while(is.hasNext())
////        {
////            text += is.next();
////        }
//
//        System.out.println(text);
//        JSONObject test = null;
//        try
//        {
//            test = new JSONObject(text);
//        }
//        catch (JSONException e)
//        {
//            e.printStackTrace();
//        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
