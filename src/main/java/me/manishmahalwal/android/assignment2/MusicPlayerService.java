package me.manishmahalwal.android.assignment2;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

public class MusicPlayerService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener

{
    MediaPlayer musicPlayer;
    String pathToMusicFile;

    public class LocalBinder extends Binder {
        public MusicPlayerService getService() {
            return MusicPlayerService.this;
        }
    }

    public void initMusicPlayer()
    {
        musicPlayer = new MediaPlayer();
        musicPlayer.setOnCompletionListener(this);
        musicPlayer.setOnPreparedListener(this);
        musicPlayer.reset();
        musicPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            musicPlayer.setDataSource(pathToMusicFile);
        } catch (IOException e) {
            e.printStackTrace();
            stopSelf();
        }
        musicPlayer.prepareAsync();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        stopMedia();
        stopSelf();

    }


    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        playMedia();

    }

    private void playMedia() {
        if (!musicPlayer.isPlaying()) {
            musicPlayer.start();
        }
    }

    public void stopMedia() {
        if (musicPlayer == null) return;
        if (musicPlayer.isPlaying()) {
            musicPlayer.stop();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            pathToMusicFile = intent.getExtras().getString("media");
        } catch (NullPointerException e) {
            stopSelf();
        }

        if (pathToMusicFile != null && pathToMusicFile != "")
            initMusicPlayer();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (musicPlayer != null) {
            stopMedia();
            musicPlayer.release();
        }
    }
}
