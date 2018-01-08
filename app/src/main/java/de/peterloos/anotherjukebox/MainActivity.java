package de.peterloos.anotherjukebox;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    private MediaPlayer mMediaplayer;

    private String downloadUrl = "https://firebasestorage.googleapis.com/v0/b/anotherjukebox-18aad.appspot.com/o/music%2FTrust_Me.mp3?alt=media&token=3be14b2c-a57e-4800-81f6-a85297c18867";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMediaplayer = new MediaPlayer();
        mMediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        fetchAudioUrlFromFirebase();
    }

    private void fetchAudioUrlFromFirebase() {
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl(downloadUrl);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    // Download url of file
                    final String url = uri.toString();
                    mMediaplayer.setDataSource(url);
                    // wait for media player to get prepare
                    mMediaplayer.setOnPreparedListener(MainActivity.this);
                    mMediaplayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("TAG", e.getMessage());
            }
        });

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mMediaplayer.start();
    }
}
