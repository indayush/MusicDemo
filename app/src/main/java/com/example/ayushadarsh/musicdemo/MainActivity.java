package com.example.ayushadarsh.musicdemo;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    AudioManager  audioManager;


    public void play (View view){

        mediaPlayer.start();
    }

    public void pause (View view){

        mediaPlayer.pause();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar volumeControlSeekbar = (SeekBar)findViewById(R.id.seekBar);
        final SeekBar scrubSeekbar = (SeekBar)findViewById(R.id.seekBarNav);
        audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        // TO get the max value of volume on the android
        // device since all devices have varying Maximum Volume
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        // To get the current volume of device rather tha starting at zero

        mediaPlayer = MediaPlayer.create(this,R.raw.demo);
        // This declaration is done in this manner (inside onCreate method rather than at the top)so
        // that app dosen't crashes when pause button pressed without playing song first

        volumeControlSeekbar.setMax(maxVolume);
        volumeControlSeekbar.setProgress(currentVolume);

        volumeControlSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override

            // Seekbar to change the volume of the audio file playing.
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i("Seekbar Change",Integer.toString(i));
                // By default Tracks changes on seekbar on scale of 0 to 100

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //------------------------------------------------------------------------------------------

        scrubSeekbar.setMax(mediaPlayer.getDuration());
        //To limit the seekbar end till the max duration of audio beyond 100 (if).

        scrubSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Seekbar Values:",Integer.toString(progress));

                mediaPlayer.seekTo(progress);
                //Update song progress together with seekbar change.

                // NOTE:- The method works but audio gets very laggy
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                mediaPlayer.pause();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                mediaPlayer.start();
            }
        });

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                scrubSeekbar.setProgress(mediaPlayer.getCurrentPosition());
            }
        },0,1000);

    }
}
