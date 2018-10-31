package me.manishmahalwal.android.ass4;


/**
 * Created by Manish on 31-10-2018.
 * manish16054@iiitd.ac.in
 *
 * Credits
 * GPS: https://www.androidhive.info/2012/07/android-gps-location-manager-tutorial
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Looper;
import android.provider.Settings;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import java.text.DateFormat;
import java.util.Date;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private String TAG = "Main";
    SensorManager sensorManager;
    SensorEventListener listener;
    boolean color = false;
    //false -> green
    //true -> red
    boolean gpsbackgroundcolor = false;

    DatabaseManager mDatabase;

    // location last updated time
    private String mLastUpdateTime;

    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

    private static final int REQUEST_CHECK_SETTINGS = 100;


    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;

    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;

    /*
     * accelerometer, gyroscope, orientation, GPS, proximity
     * */

    boolean on = true;
    TextView e1, e2, e3, e4, e5;
    Button b1;
    List<SensorData> sensorDataList;

    long lastUpdate;

    Calendar cal;
    SimpleDateFormat sdf;
    String joiningDate;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = new DatabaseManager(this);
        sensorDataList = new ArrayList<SensorData>();

        ButterKnife.bind(this);

        init();

        e1 = findViewById(R.id.editText1);
        e1.setBackgroundColor(Color.GREEN);

        e2 = findViewById(R.id.editText2);
        e2.setBackgroundColor(Color.GREEN);

        e3 = findViewById(R.id.editText3);
        e3.setText("Portrait");
        e3.setBackgroundColor(Color.GREEN);

        e4 = findViewById(R.id.editText4);
        e4.setText("Lat: " + ", " + "Lng: ");
        e4.setBackgroundColor(Color.GREEN);

        e5 = findViewById(R.id.editText5);
        b1 = findViewById(R.id.button);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (on) {
                    onPause();
                } else {
                    onResume();
                }
                on = !on;
            }
        });



        startLocationButtonClick();

        sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    getAccelerometer(sensorEvent);
                }

                if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                    getGyroscoper(sensorEvent);
                }

                if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                    Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                    if (sensorEvent.values[0] < proximitySensor.getMaximumRange()) {
                        e5.setBackgroundColor(Color.RED);
                        e5.setText("Dist: " + String.valueOf(sensorEvent.values[0]));
                        addSensorData("Dist: " + String.valueOf(sensorEvent.values[0]), String.valueOf(sensorEvent.timestamp), "proximity");
                    } else {
                        e5.setText("Dist: inf");
                        e5.setBackgroundColor(Color.GREEN);
                        addSensorData("Dist: inf", String.valueOf(sensorEvent.timestamp), "proximity");
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };


        sensorManager.registerListener(listener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listener, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listener, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);


    }

    private void addSensorData(String outdata, String mtimestamp, String tableName) {

        cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        joiningDate = sdf.format(cal.getTime());

        //adding the employee with the DatabaseManager instance
        mDatabase.addSensorData(outdata, mtimestamp, tableName);
    }

    private void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                updateLocationUI();
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }
    /**
     * Update the UI displaying the location data
     * and toggling the buttons
     */
    private void updateLocationUI() {
        if (mCurrentLocation != null) {

            if(!gpsbackgroundcolor) {
                e4.setBackgroundColor(Color.RED);
            } else {
                e4.setBackgroundColor(Color.GREEN);
            }
            gpsbackgroundcolor = !gpsbackgroundcolor;
            e4.setText("Lat: " + mCurrentLocation.getLatitude() + ", " + "Lng: " + mCurrentLocation.getLongitude());
            addSensorData("Lat: " + mCurrentLocation.getLatitude() + ", " + "Lng: " + mCurrentLocation.getLongitude(), String.valueOf(mCurrentLocation.getTime()), "gps");
        }
    }

    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        Toast.makeText(getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT).show();

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateLocationUI();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        updateLocationUI();
                    }
                });
    }

    public void startLocationButtonClick() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }


    public void stopLocationUpdates() {
        // Removing location updates
        mRequestingLocationUpdates = false;
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Location updates stopped!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void getGyroscoper(SensorEvent event) {
        float[] values = event.values;
        float x = values[0];
        float y = values[1];
        float z = values[2];

        String out1 = "X: " + String.valueOf(x) + ";    Y: " + String.valueOf(y) + ";    Z: " + String.valueOf(z);
        out1 = out1.replace("\\\n", System.getProperty("line.separator"));//
        e2.setText(out1);
        addSensorData(out1, String.valueOf(event.timestamp), "gyroscope");

        if (event.values[2] > 0.5f) { // anticlockwise
//            e2.setBackgroundColor(Color.RED);
//            addSensorData(out1, String.valueOf(event.timestamp), "gyroscope");
        } else if (event.values[2] < -0.5f) { // clockwise
//            e2.setBackgroundColor(Color.YELLOW);
//            addSensorData(out1, String.valueOf(event.timestamp),"gyroscope");
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        joiningDate = sdf.format(cal.getTime());


        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            e3.setText("Landscape");
            e3.setBackgroundColor(Color.RED);
            addSensorData("Landscape", joiningDate, "orientation");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            e3.setText("Portrait");
            e3.setBackgroundColor(Color.GREEN);
            addSensorData("Portrait", joiningDate, "orientation");
        }
    }

    private void getAccelerometer(SensorEvent event) {

        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        String out1 = "X: " + String.valueOf(x) + ";    Y: " + String.valueOf(y) + ";    Z: " + String.valueOf(z);
        e1.setText(out1);
        addSensorData(out1, String.valueOf(event.timestamp),"accelerometer");

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = event.timestamp;
        if (accelationSquareRoot >= 2) //
        {
            if (actualTime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualTime;
                Toast.makeText(this, "Phone Shaken", Toast.LENGTH_SHORT).show();
                if (color) {
                    e1.setBackgroundColor(Color.GREEN);
                } else {
                    e1.setBackgroundColor(Color.RED);
                }
                color = !color;
        }

//        float[] values = event.values;
//        float x, y, z;
//        x = values[0];
//        y = values[1];
//        z = values[2];
//        float last_x = x, last_y = y, last_z = z;
//
//        String out1 = "X: " + String.valueOf(x) + ";    Y: " + String.valueOf(y) + ";    Z: " + String.valueOf(z);
//        e1.setText(out1);
//        addSensorData(out1, String.valueOf(event.timestamp),"accelerometer");
//        float lastUpdate = 0;
//        long curTime = System.currentTimeMillis();
//
//        if ((curTime - lastUpdate) > 100) {
//            long diffTime = (long) (curTime - lastUpdate);
//            lastUpdate = curTime;
//
//            float speed = Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;
//            Log.e("BITCH", String.valueOf(speed));
//
//            if (speed > 3) {
//                Log.d("sensor", "shake detected w/ speed: " + speed);
//                Toast.makeText(this, "Shaked @ " + speed, Toast.LENGTH_SHORT).show();
//                if (color) {
//                    e1.setBackgroundColor(Color.GREEN);
//                } else {
//                    e1.setBackgroundColor(Color.RED);
//                }
//                color = !color;
//            }
//            last_x = x;
//            last_y = y;
//            last_z = z;
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        sensorManager.registerListener(listener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listener,
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listener,
                sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                SensorManager.SENSOR_DELAY_NORMAL);

        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        }

        updateLocationUI();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(listener);

        if (mRequestingLocationUpdates) {
            // pausing location updates
            stopLocationUpdates();
        }


    }

}
