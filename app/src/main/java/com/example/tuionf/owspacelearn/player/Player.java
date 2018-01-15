package com.example.tuionf.owspacelearn.player;

import android.media.MediaPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tuionf
 * @date 2018/1/3
 * @email 596019286@qq.com
 * @explain
 */

public class Player implements IPlayback,MediaPlayer.OnBufferingUpdateListener,MediaPlayer.OnCompletionListener,MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener{

    private MediaPlayer player;
    private static volatile Player sInstance;
    private boolean isPaused;
    private String song;
    private List<Callback> mCallbacks = new ArrayList<>(2);

    public Player() {
        player = new MediaPlayer();
        player.setOnCompletionListener(this);
        player.setOnBufferingUpdateListener(this);
        player.setOnPreparedListener(this);
    }

    public static Player getInstance() {
        if (sInstance == null) {
            synchronized (Player.class){
                if (sInstance == null) {
                    sInstance = new Player();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        player.reset();
        notifyComplete(PlayState.COMPLETE);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        player.start();
        notifyPlayStatusChanged(PlayState.PLAYING);
    }

    @Override
    public boolean play() {
        if (isPaused){
            player.start();
            notifyPlayStatusChanged(PlayState.PLAYING);
            return true;
        }
        return false;
    }

    @Override
    public boolean play(String song) {
        try {
            player.reset();
            player.setDataSource(song);
            player.prepare();
            this.song = song;
            notifyPlayStatusChanged(PlayState.PLAYING);
            return true;
        } catch (IOException e) {
            notifyPlayStatusChanged(PlayState.ERROR);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean pause() {
        if (player.isPlaying()) {
            player.pause();
            isPaused = true;
            notifyPlayStatusChanged(PlayState.PAUSE);
            return true;
        }
        return false;
    }

    @Override
    public boolean isPlaying() {
        return player.isPlaying();
    }

    @Override
    public int getProgress() {
        return player.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return player.getDuration();
    }

    @Override
    public boolean seekTo(int progress) {
        player.seekTo(progress);
        return true;
    }

    @Override
    public void registerCallback(Callback callback) {
        mCallbacks.add(callback);
    }

    @Override
    public void unregisterCallback(Callback callback) {
        mCallbacks.remove(callback);
    }

    @Override
    public void removeCallbacks() {
        mCallbacks.clear();
    }

    @Override
    public void releasePlayer() {
        player.reset();
        player.release();
        player = null;
        sInstance = null;
        song = null;
    }

    private void notifyPlayStatusChanged(PlayState status) {
        for (Callback callback : mCallbacks) {
            callback.onPlayStatusChanged(status);
        }
    }

    private void notifyComplete(PlayState state) {
        for (Callback callback : mCallbacks) {
            callback.onComplete(state);
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        notifyPlayStatusChanged(PlayState.ERROR);
        return false;
    }
}
