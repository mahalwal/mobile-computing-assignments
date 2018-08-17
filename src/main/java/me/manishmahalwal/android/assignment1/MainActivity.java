package me.manishmahalwal.android.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import me.manishmahalwal.android.assignment1.R;

public class MainActivity extends AppCompatActivity
{
    Button mSubmitButton, mClearButton;
    EditText e1;

    int create=0, resume=0, pause=0, destroy=0, stop=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if(create==0)
        {
            Log.w("Assignment1", "State of activity MainActivity changed to create");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed to create",
                    Toast.LENGTH_SHORT).show();
            create=1;
        }
        else if(resume==1)
        {
            Log.w("Assignment1", "State of activity MainActivity changed from resume to create");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from resume to create",
                    Toast.LENGTH_SHORT).show();
        }
        else if(pause==1)
        {
            Log.w("Assignment1", "State of activity MainActivity changed from pause to create");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from pause to create",
                    Toast.LENGTH_SHORT).show();
        }
        else if(destroy==1)
        {
            Log.w("Assignment1", "State of activity MainActivity changed from destroy to create");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from destroy to create",
                    Toast.LENGTH_SHORT).show();
        }
        else if(stop==1)
        {
            Log.w("Assignment1", "State of activity MainActivity changed from stop to create");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from stop to create",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.w("Assignment1", "State of activity MainActivity changed from paused to create");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from paused to create",
                    Toast.LENGTH_SHORT).show();
        }

        resume=0; pause=0; destroy=0; stop=0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSubmitButton = (Button) findViewById(R.id.button);
        mClearButton = (Button) findViewById(R.id.button2);

        mSubmitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                e1 = (EditText) findViewById(R.id.editText2);
                String s1 = "Name: ";
                s1 += e1.getText().toString();

                e1 = (EditText) findViewById(R.id.editText3);
                s1 += "\nRoll Number: ";
                s1 += e1.getText().toString();

                e1 = (EditText) findViewById(R.id.editText4);
                s1 += "\nBranch: ";
                s1 += e1.getText().toString();

                e1 = (EditText) findViewById(R.id.editText12);
                s1 += "\nCourse 1: ";
                s1 += e1.getText().toString();

                e1 = (EditText) findViewById(R.id.editText13);
                s1 += "\nCourse 2: ";
                s1 += e1.getText().toString();

                e1 = (EditText) findViewById(R.id.editText14);
                s1 += "\nCourse 3: ";
                s1 += e1.getText().toString();

                e1 = (EditText) findViewById(R.id.editText15);
                s1 += "\nCourse 4: ";
                s1 += e1.getText().toString();


                Log.w("Assignment1", "Submitting and Switching to next Screen");
                Toast.makeText(getBaseContext(), "Submitted",
                        Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(MainActivity.this, finalActivity.class);
                intent.putExtra("key", s1);
                startActivity(intent);
            }
        });

        mClearButton.setOnClickListener(new View.OnClickListener()
        {
              @Override
              public void onClick(View v)
              {
                   //Does nothing

                  Log.w("Assignment1", "Clearing all fields");
                  Toast.makeText(getBaseContext(), "Clear",
                          Toast.LENGTH_SHORT).show();

                   e1 = (EditText) findViewById(R.id.editText2);
                   e1.getText().clear();
                   e1 = (EditText) findViewById(R.id.editText3);
                   e1.getText().clear();
                   e1 = (EditText) findViewById(R.id.editText4);
                   e1.getText().clear();
                   e1 = (EditText) findViewById(R.id.editText12);
                   e1.getText().clear();
                   e1 = (EditText) findViewById(R.id.editText13);
                   e1.getText().clear();
                   e1 = (EditText) findViewById(R.id.editText14);
                   e1.getText().clear();
                   e1 = (EditText) findViewById(R.id.editText15);
                   e1.getText().clear();
              }
        }
        );
    }
    @Override
    public void onResume(){
        super.onResume();
        //////////////////////////////////////

        resume=1;

        if(create==1)
        {
            Log.w("Assignment1", "State of activity MainActivity changed from create to resume");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from create to resume",
                    Toast.LENGTH_SHORT).show();
        }
        else if(pause==1)
        {
            Log.w("Assignment1", "State of activity MainActivity changed from pause to resume");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from pause to resume",
                    Toast.LENGTH_SHORT).show();
        }
        else if(destroy==1)
        {
            Log.w("Assignment1", "State of activity MainActivity changed from destroy to resume");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from destroy to resume",
                    Toast.LENGTH_SHORT).show();
        }
        else if(stop==1)
        {
            Log.w("Assignment1", "State of activity MainActivity changed from stop to resume");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from stop to resume",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
                Log.w("Assignment1", "State of activity MainActivity changed from paused to resumed");
                Toast.makeText(getBaseContext(), "State of activity MainActivity changed from paused to resumed",
                Toast.LENGTH_SHORT).show();
        }

        create=0; pause=0; destroy=0; stop=0;


        //////////////////////////////////////
//        Log.w("Assignment1", "State of activity MainActivity changed from paused to resumed");
//        Toast.makeText(getBaseContext(), "State of activity MainActivity changed from paused to resumed",
//                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause(){
        super.onPause();

        pause=1;

        if(create==1)
        {
            Log.w("Assignment1", "State of activity MainActivity changed from create to pause");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from create to pause",
                    Toast.LENGTH_SHORT).show();
        }
        else if(resume==1)
        {
            Log.w("Assignment1", "State of activity MainActivity changed from resume to pause");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from resume to pause",
                    Toast.LENGTH_SHORT).show();
        }
        else if(destroy==1)
        {
            Log.w("Assignment1", "State of activity MainActivity changed from destroy to pause");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from destroy to pause",
                    Toast.LENGTH_SHORT).show();
        }
        else if(stop==1)
        {
            Log.w("Assignment1", "State of activity MainActivity changed from stop to pause");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from stop to pause",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.w("Assignment1", "State of activity MainActivity changed from paused to resumed");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from paused to resumed",
                    Toast.LENGTH_SHORT).show();
        }

        create=0; resume=0; destroy=0; stop=0;

        ///////////////////
//        Log.w("Assignment1", "State of activity MainActivity paused");
//        Toast.makeText(getBaseContext(), "State of activity MainActivity paused",
//                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();

        destroy=1;

        if(create==1)
        {
            Log.w("Assignment1", "State of activity MainActivity changed from create to destroy");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from create to destroy",
                    Toast.LENGTH_SHORT).show();
        }
        else if(pause==1)
        {
            Log.w("Assignment1", "State of activity MainActivity changed from pause to destroy");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from pause to destroy",
                    Toast.LENGTH_SHORT).show();
        }
        else if(resume==1)
        {
            Log.w("Assignment1", "State of activity MainActivity changed from resume to destroy");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from resume to destroy",
                    Toast.LENGTH_SHORT).show();
        }
        else if(stop==1)
        {
            Log.w("Assignment1", "State of activity MainActivity changed from stop to destroy");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from stop to destroy",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.w("Assignment1", "State of activity MainActivity changed from paused to destroy");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from paused to destroy",
                    Toast.LENGTH_SHORT).show();
        }

        create=0; pause=0; resume=0; stop=0;

        /////////////////////////
//        Log.w("Assignment1", "State of activity MainActivity destroyed");
//        Toast.makeText(getBaseContext(), "State of activity MainActivity destroyed",
//                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onStop(){
        super.onStop();

        stop=1;

        if(create==1)
        {
            Log.w("Assignment1", "State of activity MainActivity changed from create to stop");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from create to stop",
                    Toast.LENGTH_SHORT).show();
        }
        else if(pause==1)
        {
            Log.w("Assignment1", "State of activity MainActivity changed from pause to stop");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from pause to stop",
                    Toast.LENGTH_SHORT).show();
        }
        else if(destroy==1)
        {
            Log.w("Assignment1", "State of activity MainActivity changed from destroy to stop");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from destroy to stop",
                    Toast.LENGTH_SHORT).show();
        }
        else if(resume==1)
        {
            Log.w("Assignment1", "State of activity MainActivity changed from resume to stop");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from resume to stop",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.w("Assignment1", "State of activity MainActivity changed from paused to stop");
            Toast.makeText(getBaseContext(), "State of activity MainActivity changed from paused to stop",
                    Toast.LENGTH_SHORT).show();
        }

        create=0; pause=0; destroy=0; resume=0;
        ////////////////////

//        Log.w("Assignment1", "State of activity MainActivity stopped");
//        Toast.makeText(getBaseContext(), "State of activity MainActivity stopped",
//                Toast.LENGTH_SHORT).show();
    }

}