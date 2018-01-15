package de.peterloos.anotherjukebox.fragments;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

import de.peterloos.anotherjukebox.R;
import de.peterloos.anotherjukebox.activities.MediaPlayerActivity;

public class FragmentPlayer extends Fragment implements View.OnClickListener {

    private MediaPlayer mediaPlayer;
    private Context context;
    private Button buttonStart;

    private String downloadUrl = "https://firebasestorage.googleapis.com/v0/b/anotherjukebox-18aad.appspot.com/o/music%2FTrust_Me.mp3?alt=media&token=3be14b2c-a57e-4800-81f6-a85297c18867";

    public FragmentPlayer() {
        // required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_player, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        this.context = this.getContext();

//        this.buttonStart = view.findViewById(R.id.buttonStart);
//        this.buttonStart.setOnClickListener(this);

        this.mediaPlayer = new MediaPlayer();
        this.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        this.fetchAudioUrlFromFirebase();
    }

    @Override
    public void onClick(View v) {

        Toast.makeText(this.context, "Started ...", Toast.LENGTH_SHORT).show();
        this.mediaPlayer.start();
    }

    private void fetchAudioUrlFromFirebase() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(downloadUrl);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    // Download url of file
                    final String url = uri.toString();
                    mediaPlayer.setDataSource(url);

                    // wait for media player to be prepared
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener  () {

                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            Toast.makeText(FragmentPlayer.this.context, "Yeah ... things are prepared", Toast.LENGTH_SHORT).show();
                        }
                    });
                    mediaPlayer.prepareAsync();
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
}
