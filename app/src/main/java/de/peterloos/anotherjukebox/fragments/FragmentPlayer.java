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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import de.peterloos.anotherjukebox.Globals;
import de.peterloos.anotherjukebox.R;
import de.peterloos.anotherjukebox.utils.MediaPlayerHolder;

public class FragmentPlayer extends Fragment implements View.OnClickListener {

    private Context context;

    private Button b1, b2, b3, b4;
    private TextView tx1, tx2, tx3;
    private SeekBar seekbar;

    private double startTime = 0;
    private double finalTime = 0;

    public FragmentPlayer() {
        // required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.v(Globals.TAG, "FragmentPlayer::onCreateView");

        // inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_player, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        this.context = this.getContext();

        // retrieve references of controls
        this.b1 = view.findViewById(R.id.button1);
        this.b2 = view.findViewById(R.id.button2);
        this.b3 = view.findViewById(R.id.button3);
        this.b4 = view.findViewById(R.id.button4);

        this.tx1 = view.findViewById(R.id.textView2);
        this.tx2 = view.findViewById(R.id.textView3);
        this.tx3 = view.findViewById(R.id.textView4);

        this.seekbar = view.findViewById(R.id.seekBar);
        this.seekbar.setClickable(false);

        // connect controls with event handler
        this.b1.setOnClickListener (this);
        this.b2.setOnClickListener (this);
        this.b3.setOnClickListener (this);
        this.b4.setOnClickListener (this);

        Log.v(Globals.TAG, "FragmentPlayer::onViewCreated");
    }

    boolean isStarted = false;

    @Override
    public void onClick(View view) {

        if (view == this.b1) {
            Toast.makeText(this.context, "Button 1", Toast.LENGTH_SHORT).show();
        }
        else if (view == this.b2) {

            Toast.makeText(this.context, "Button 2 - Pausing sound", Toast.LENGTH_SHORT).show();

            MediaPlayerHolder holder = MediaPlayerHolder.getInstance(this.getContext());
            holder.pause();

            this.b2.setEnabled(false);
            this.b3.setEnabled(true);

        }
        else if (view == this.b3) {
            Toast.makeText(this.context, "Button 3 - Playing sound", Toast.LENGTH_SHORT).show();

            if (! this.isStarted)
            {
                MediaPlayerHolder holder = MediaPlayerHolder.getInstance(this.getContext());
                holder.fetchDownloadUrlFromFirebase();

                this.isStarted = true;
            }
            else
            {
                MediaPlayerHolder holder = MediaPlayerHolder.getInstance(this.getContext());
                holder.play();
            }



//            finalTime = this.mediaPlayer.getDuration();
//            startTime = this.mediaPlayer.getCurrentPosition();

            // ??????????????????????
//            if (oneTimeOnly == 0) {
//                seekbar.setMax((int) finalTime);
//                oneTimeOnly = 1;
//            }

            this.tx2.setText(String.format(Locale.getDefault(),"%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                    finalTime)))
            );

            this.tx1.setText(String.format(Locale.getDefault(),"%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                    startTime)))
            );

            this.seekbar.setProgress((int) startTime);


            // myHandler.postDelayed(UpdateSongTime, 100);

            this.b2.setEnabled(true);
            this.b3.setEnabled(false);
        }
        else if (view == this.b4) {
            Toast.makeText(this.context, "Button 4", Toast.LENGTH_SHORT).show();
        }

    }

//    private void fetchAudioUrlFromFirebase() {
//
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReferenceFromUrl(downloadUrl);
//        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                try {
//                    // download url of file
//                    final String url = uri.toString();
//                    mediaPlayer.setDataSource(url);
//
//                    // wait for media player to be prepared
//                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener  () {
//
//                        @Override
//                        public void onPrepared(MediaPlayer mp) {
//                            Toast.makeText(FragmentPlayer.this.context, "Yeah ... things are prepared", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    mediaPlayer.prepareAsync();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.i("TAG", e.getMessage());
//            }
//        });
//
//    }
}
