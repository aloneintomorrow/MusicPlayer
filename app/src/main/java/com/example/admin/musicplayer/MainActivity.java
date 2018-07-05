package com.example.admin.musicplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.Annotation;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {

    TextView txtTitle, txtTimeSong, txtTimeTotal;
    SeekBar skSong;
    SeekBar skVolume;
    ImageButton btnPrev, btnPlay, btnStop, btnNext;
    ImageView imgHinh;

    ArrayList<Song> arraySong;
    int position = 0;
    MediaPlayer mediaPlayer;
    Animation animation;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        AnhXa();
        AddSong();

        animation= AnimationUtils.loadAnimation(this, R.anim.disc_rotate);

        KhoiTaoMediaPlayer();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;
                if(position > arraySong.size() - 1) {
                    position = 0;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pasue2);
                SetTimeTotal();
                UpdateTimeSong();
            }

        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;
                if(position < 0) {
                    position = arraySong.size() - 1;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pasue2);
                SetTimeTotal();
                UpdateTimeSong();
            }

        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                btnPlay.setImageResource(R.drawable.play);
                KhoiTaoMediaPlayer();

            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);
                } else {
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pasue2);
                    SetTimeTotal();
                }
                SetTimeTotal();
                UpdateTimeSong();
                imgHinh.startAnimation(animation);


            }

        });
        skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(skSong.getProgress());

            }
        });
        audioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);


        skVolume.setMax(maxVolume);
        skVolume.setProgress(curVolume);

        skVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        
    }


    private void UpdateTimeSong() {
        final android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
                //update proress skSong
                txtTimeSong.setText(dinhDangGio.format(mediaPlayer.getCurrentPosition()));

                skSong.setProgress(mediaPlayer.getCurrentPosition());

                //kiem tra thoi gian bai hat->neu ket thuc -> next
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if(position > arraySong.size() - 1) {
                            position = 0;
                        }
                        if (mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        KhoiTaoMediaPlayer();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.pasue2);
                        SetTimeTotal();
                        UpdateTimeSong();

                    }
                });

                handler.postDelayed(this, 500);


            }
        },100);
    }









    private void SetTimeTotal() {
        SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
        txtTimeTotal.setText(dinhDangGio.format(mediaPlayer.getDuration()));
        skSong.setMax(mediaPlayer.getDuration());
    }

    private void KhoiTaoMediaPlayer()
    {
        mediaPlayer = MediaPlayer.create(MainActivity.this,arraySong.get(position).getFile());
        txtTitle.setText(arraySong.get(position).getFile());
    }

    private void AddSong() {
        arraySong = new ArrayList<>();
        arraySong.add(new Song("Cho em trong dem", R.raw.choemtrongdem));
        arraySong.add(new Song("Em luon trong tam tri anh", R.raw.emluontrongtamtrianh));
        arraySong.add(new Song("Mot thoi da xa", R.raw.motthoidaxa));
        arraySong.add(new Song("Goi ten em trong dem", R.raw.goitenemtrongdem));
        arraySong.add(new Song("Thang tu la loi noi doi cua em", R.raw.thangtulaloinoidoicuaem));
        arraySong.add(new Song("Van", R.raw.van));

    }


    private void AnhXa() {
        txtTimeSong     =(TextView) findViewById(R.id.textViewTimeSong);
        txtTimeTotal    =(TextView) findViewById(R.id.textViewTotalSong);
        txtTitle        =(TextView) findViewById(R.id.textviewTitle);
        skSong          =(SeekBar ) findViewById(R.id.seekBarSong);
        btnPlay         =(ImageButton) findViewById(R.id.imageButtonPlay);
        btnNext         =(ImageButton) findViewById(R.id.imageButtonNext);
        btnStop         =(ImageButton) findViewById(R.id.imageButtonStop);
        btnPrev         =(ImageButton) findViewById(R.id.imageButtonPre);
        imgHinh         =(ImageView) findViewById(R.id.imageViewDisc);
        skVolume        =(SeekBar) findViewById(R.id.seekBarVolume);

    }
}
