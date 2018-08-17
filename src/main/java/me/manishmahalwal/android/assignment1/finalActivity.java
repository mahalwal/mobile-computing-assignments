package me.manishmahalwal.android.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class finalActivity extends AppCompatActivity {

    int create=0, resume=0, pause=0, destroy=0, stop=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        if(create==0)
        {
            Log.w("Assignment1", "State of activity finalActivity changed to create");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed to create",
                    Toast.LENGTH_SHORT).show();
            create=1;
        }
        else if(resume==1)
        {
            Log.w("Assignment1", "State of activity finalActivity changed from resume to create");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from resume to create",
                    Toast.LENGTH_SHORT).show();
        }
        else if(pause==1)
        {
            Log.w("Assignment1", "State of activity finalActivity changed from pause to create");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from pause to create",
                    Toast.LENGTH_SHORT).show();
        }
        else if(destroy==1)
        {
            Log.w("Assignment1", "State of activity finalActivity changed from destroy to create");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from destroy to create",
                    Toast.LENGTH_SHORT).show();
        }
        else if(stop==1)
        {
            Log.w("Assignment1", "State of activity finalActivity changed from stop to create");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from stop to create",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.w("Assignment1", "State of activity finalActivity changed from paused to create");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from paused to create",
                    Toast.LENGTH_SHORT).show();
        }

        resume=0; pause=0; destroy=0; stop=0;


        //////////////////////////
        String value = getIntent().getExtras().getString("key");
        TextView t1 = (TextView) findViewById(R.id.textView);
        t1.setText(value);
    }

    @Override
    public void onResume(){
        super.onResume();

        resume=1;

        if(create==1)
        {
            Log.w("Assignment1", "State of activity finalActivity changed from create to resume");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from create to resume",
                    Toast.LENGTH_SHORT).show();
        }
        else if(pause==1)
        {
            Log.w("Assignment1", "State of activity finalActivity changed from pause to resume");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from pause to resume",
                    Toast.LENGTH_SHORT).show();
        }
        else if(destroy==1)
        {
            Log.w("Assignment1", "State of activity finalActivity changed from destroy to resume");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from destroy to resume",
                    Toast.LENGTH_SHORT).show();
        }
        else if(stop==1)
        {
            Log.w("Assignment1", "State of activity finalActivity changed from stop to resume");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from stop to resume",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.w("Assignment1", "State of activity finalActivity changed from paused to resumed");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from paused to resumed",
                    Toast.LENGTH_SHORT).show();
        }

        create=0; pause=0; destroy=0; stop=0;
    }

    @Override
    public void onPause(){
        super.onPause();

        pause=1;

        if(create==1)
        {
            Log.w("Assignment1", "State of activity finalActivity changed from create to pause");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from create to pause",
                    Toast.LENGTH_SHORT).show();
        }
        else if(resume==1)
        {
            Log.w("Assignment1", "State of activity finalActivity changed from resume to pause");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from resume to pause",
                    Toast.LENGTH_SHORT).show();
        }
        else if(destroy==1)
        {
            Log.w("Assignment1", "State of activity finalActivity changed from destroy to pause");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from destroy to pause",
                    Toast.LENGTH_SHORT).show();
        }
        else if(stop==1)
        {
            Log.w("Assignment1", "State of activity finalActivity changed from stop to pause");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from stop to pause",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.w("Assignment1", "State of activity finalActivity changed from paused to resumed");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from paused to resumed",
                    Toast.LENGTH_SHORT).show();
        }

        create=0; resume=0; destroy=0; stop=0;

    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        destroy=1;

        if(create==1)
        {
            Log.w("Assignment1", "State of activity finalActivity changed from create to destroy");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from create to destroy",
                    Toast.LENGTH_SHORT).show();
        }
        else if(pause==1)
        {
            Log.w("Assignment1", "State of activity finalActivity changed from pause to destroy");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from pause to destroy",
                    Toast.LENGTH_SHORT).show();
        }
        else if(resume==1)
        {
            Log.w("Assignment1", "State of activity finalActivity changed from resume to destroy");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from resume to destroy",
                    Toast.LENGTH_SHORT).show();
        }
        else if(stop==1)
        {
            Log.w("Assignment1", "State of activity finalActivity changed from stop to destroy");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from stop to destroy",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.w("Assignment1", "State of activity finalActivity changed from paused to destroy");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from paused to destroy",
                    Toast.LENGTH_SHORT).show();
        }

        create=0; pause=0; resume=0; stop=0;


    }
    @Override
    public void onStop(){
        super.onStop();

        stop=1;

        if(create==1)
        {
            Log.w("Assignment1", "State of activity finalActivity changed from create to stop");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from create to stop",
                    Toast.LENGTH_SHORT).show();
        }
        else if(pause==1)
        {
            Log.w("Assignment1", "State of activity finalActivity changed from pause to stop");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from pause to stop",
                    Toast.LENGTH_SHORT).show();
        }
        else if(destroy==1)
        {
            Log.w("Assignment1", "State of activity finalActivity changed from destroy to stop");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from destroy to stop",
                    Toast.LENGTH_SHORT).show();
        }
        else if(resume==1)
        {
            Log.w("Assignment1", "State of activity finalActivity changed from resume to stop");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from resume to stop",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.w("Assignment1", "State of activity finalActivity changed from paused to stop");
            Toast.makeText(getBaseContext(), "State of activity finalActivity changed from paused to stop",
                    Toast.LENGTH_SHORT).show();
        }

        create=0; pause=0; destroy=0; resume=0;

    }

//    @Override
//    public void onBackPressed() {
//        Log.w("Assignment1", "Going to Main Screen");
//        Toast.makeText(getBaseContext(), "Going to Main Screen",
//                Toast.LENGTH_SHORT).show();
//
//
//        Intent intent = new Intent(finalActivity.this, MainActivity.class);
//        startActivity(intent);
//    }
}