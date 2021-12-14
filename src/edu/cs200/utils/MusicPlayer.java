package edu.cs200.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {

    private static MusicPlayer self;
    private Clip clip;
    private String currentSong;
    private int stopFrameOfCurrentSong;
    private String lastSong;
    private int stopFrameOfLastSong;

    public static MusicPlayer getInstance() {
        if (self == null) self = new MusicPlayer();
        return self;
    }

    private MusicPlayer() {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playBump() {
        playSong("bump.wav");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        resumeLastSong();
    }

    public void playClear() {
        playSong("clear.wav");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        resumeLastSong();
    }

    public void playSuccess() {
        playSong("success.wav");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        resumeLastSong();
    }

    public void playGameOver() {
        playSong("gameover.wav");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        resumeLastSong();
    }

    public void playSMB() {
       playSong("smb.wav");
    }

    public void resumeLastSong() {
        String temp = currentSong;
        currentSong = lastSong;
        lastSong = temp;

        int t = stopFrameOfCurrentSong;
        stopFrameOfCurrentSong = stopFrameOfLastSong;
        stopFrameOfLastSong = t;

        playCurrentSong();
    }

    private void playSong(String song) {
        pauseCurrentSong();
        lastSong = currentSong;
        stopFrameOfLastSong = stopFrameOfCurrentSong;
        currentSong = song;
        stopFrameOfCurrentSong = 0;
        playCurrentSong();
    }

    private void pauseCurrentSong() {
        if (!clip.isOpen()) return;
        stopFrameOfCurrentSong = clip.getFramePosition();
        clip.close();
    }

    private void playCurrentSong() {
        clip.close();
        try {
            clip.open(getSound(currentSong));
            clip.setFramePosition(stopFrameOfCurrentSong);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    private AudioInputStream getSound(String sound) throws IOException, UnsupportedAudioFileException {
        return AudioSystem.getAudioInputStream(new File(String.format("assets/sounds/%s", sound)));
    }
}
