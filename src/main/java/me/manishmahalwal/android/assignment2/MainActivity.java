package me.manishmahalwal.android.assignment2;

import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//References:
//https://developer.android.com/guide/topics/media/mediaplayer
//https://www.sitepoint.com/a-step-by-step-guide-to-building-an-android-audio-player-app/
//https://medium.com/androiddevelopers/building-a-simple-audio-app-in-android-part-1-3-c14d1a66e0f1


public class MainActivity extends FragmentActivity {

    private MusicPlayerService player;
    boolean serviceBound = false;

    Button playButton, stopButton, downloadButton, playDownloadButton;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicPlayerService.LocalBinder binder = (MusicPlayerService.LocalBinder) service;
            player = binder.getService();
            serviceBound = true;

            Toast.makeText(MainActivity.this, "Service Bound", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    private void playAudio(String media) {
        if (!serviceBound) {
            Toast.makeText(MainActivity.this, "Ye lo", Toast.LENGTH_SHORT).show();
            Intent playerIntent = new Intent(this, MusicPlayerService.class);
            playerIntent.putExtra("media", media);
            startService(playerIntent);
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);

        } else {

            Toast.makeText(MainActivity.this, "Else mein pahoch gaya bhai", Toast.LENGTH_SHORT).show();
            Intent playerIntent = new Intent(this, MusicPlayerService.class);
            playerIntent.putExtra("media", media);
            stopService(playerIntent);
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = (Button) findViewById(R.id.button10);
        downloadButton = (Button) findViewById(R.id.button8);
        playDownloadButton = (Button) findViewById(R.id.button7);


        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio("http://s6.faz-dl3.ir/user1/album/june2018/Drake%20-%20Scorpion%20-%20MP3%20320/2-09%20In%20My%20Feelings.mp3");
            }
        });


        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("download", "lol");
                download("http://faculty.iiitd.ac.in/~mukulika/s1.mp3", "s1.mp3");
            }
        });
        playDownloadButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                playAudio("http://faculty.iiitd.ac.in/~mukulika/s1.mp3");
            }
        });

    }

//    https://stackoverflow.com/questions/33020815/download-files-using-download-manager
    public void download(String url, String sem) {


        Uri Download_Uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle("Downloading");
        request.setDescription("Downloading File");
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, sem);

//        refid = downloadManager.enqueue(request);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("ServiceState", serviceBound);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        serviceBound = savedInstanceState.getBoolean("ServiceState");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceBound) {
            unbindService(serviceConnection);
            //service is active
            player.stopSelf();
        }
    }
}
