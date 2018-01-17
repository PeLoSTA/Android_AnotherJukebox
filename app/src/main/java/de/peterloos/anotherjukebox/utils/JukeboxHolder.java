package de.peterloos.anotherjukebox.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;

import de.peterloos.anotherjukebox.Globals;

/**
 * Created by loospete on 16.01.2018.
 */

public class JukeboxHolder {

    private static String downloadUrl = "https://firebasestorage.googleapis.com/v0/b/anotherjukebox-18aad.appspot.com/o/music%2FTrust_Me.mp3?alt=media&token=3be14b2c-a57e-4800-81f6-a85297c18867";


    // implementation of 'singleton pattern'
    private static Context context;
    private static JukeboxHolder instance;

    static {
        instance = null;
    }

    // representing application global data
    private MediaPlayer mediaPlayer;                /* app's media player */
    private ArrayAdapter<String> songsAdapter;      /* song list of selected album */
    private ArrayAdapter<String> playlistAdapter;   /* current playlist */

    // restrict the constructor from being instantiated
    private JukeboxHolder(Context context) {

        this.context = context;
        this.mediaPlayer = new MediaPlayer();
        this.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // create ArrayAdapter for 'songs' list view
        ArrayList<String> empty = new ArrayList<String>();
        this.songsAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, empty);

        // create ArrayAdapter for 'songs' list view
        ArrayList<String> empty2 = new ArrayList<String>();
        this.playlistAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, empty2);
    }

    public static synchronized JukeboxHolder getInstance(Context context) {

        if (instance == null) {
            instance = new JukeboxHolder(context);
        }

        return instance;
    }

    // =====================================================================

    /*
     *  public interface
     */

    public void fetchDownloadUrlFromFirebase() {
        this.fetchDownloadUrlFromFirebase (downloadUrl);
    }

    private void fetchDownloadUrlFromFirebase(String downloadUrl) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(downloadUrl);

        Task<Uri> task = storageRef.getDownloadUrl();
        task.addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    String url = uri.toString();
                    mediaPlayer.setDataSource(url);

                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){

                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            Toast.makeText(context, "Yeah ... things are prepared", Toast.LENGTH_SHORT).show();

                            mediaPlayer.start();
                        }
                    });

                    // wait for media player to be prepared
                    mediaPlayer.prepareAsync();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(Globals.TAG, e.getMessage());
            }
        });
    }

    public void start() {

        this.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){

            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                Toast.makeText(context, "Yeah ... things are prepared", Toast.LENGTH_SHORT).show();

                mediaPlayer.start();
            }
        });

        // wait for media player to be prepared
        this.mediaPlayer.prepareAsync();
    }

    public void stop() {
        this.mediaPlayer.stop();
    }

    public void play() {
        this.mediaPlayer.start();
    }

    public void pause() {
        this.mediaPlayer.pause();
    }

    // =========================================================================================

    /*
     *    adapter of 'Songs' list view
     */

    public void clearSongsAdapter() {
        this.songsAdapter.clear();
    }

    public void addSong(String song) {
        this.songsAdapter.add(song);
    }

    public ArrayAdapter<String> getSongsAdapter() {
        return this.songsAdapter;
    }

    // =========================================================================================

    /*
     *    adapter of 'Playlist' list view
     */

    public void clearPlaylistAdapter() {
        this.playlistAdapter.clear();
    }

    public void addSongToPlaylist(String song) {
        this.playlistAdapter.add(song);
    }

    public ArrayAdapter<String> getPlaylistAdapter() {
        return this.playlistAdapter;
    }
}
