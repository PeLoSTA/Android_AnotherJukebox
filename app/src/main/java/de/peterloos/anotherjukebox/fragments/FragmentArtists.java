package de.peterloos.anotherjukebox.fragments;


import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.peterloos.anotherjukebox.Globals;
import de.peterloos.anotherjukebox.R;
import de.peterloos.anotherjukebox.dtos.ArtistDTO;

public class FragmentArtists extends Fragment {

    // firebase support
    private FirebaseDatabase database;
    private DatabaseReference reference;

    public FragmentArtists() {
        // required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_artists, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.v(Globals.TAG, "FragmentNews::onViewCreated");
        this.setupView();
    }

    // private helper methods
    private void setupView() {

        // setup firebase
        this.database = FirebaseDatabase.getInstance();
        this.reference = this.database.getReference(Globals.DATABASE_REF_ARTISTS);
        this.reference.addValueEventListener(artistslistener);
        // this.reference.addListenerForSingleValueEvent(artistslistener);
    }

    ValueEventListener artistslistener = new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.v(Globals.TAG, "jaaaaaaaaaaaaaaaaaaaaaaaaaa " + dataSnapshot.getChildrenCount());

            for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                String sKey = (String) snapshot.getKey();
                Log.v(Globals.TAG, sKey);

                // GEHT !!!!
                String s = (String) snapshot.child("Name").getValue();
                Log.v(Globals.TAG, s);
            }
        }



        @Override
        public void onCancelled(DatabaseError databaseError) {
            // getting list of artists failed, log a message
            Log.w(Globals.TAG, "load of artists list", databaseError.toException());

        }
    };
}
