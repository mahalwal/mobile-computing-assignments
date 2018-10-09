package me.manishmahalwal.android.ass3;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

//    DatabaseManager mDatabase;

    private static final String TAG = "MainActivity";
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Log.d(TAG, "onCreate: Started.");


//        mDatabase = new DatabaseManager(this);

//        initDatabase();


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.containter, new Fragment1(), "NewFragmentTag");
        ft.addToBackStack(null);
        ft.commit();

    }



}
