package de.peterloos.anotherjukebox.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.peterloos.anotherjukebox.Globals;
import de.peterloos.anotherjukebox.R;

public class FragmentArtists extends Fragment {

    private ListView listviewArtists;
    private ArrayAdapter<String> artistsAdapter;

    // firebase support
    private FirebaseDatabase database;
    private DatabaseReference reference;

    public FragmentArtists() {
        // required empty public constructor


    }

    @Override
    public void onResume() {
        super.onResume();

        Log.v(Globals.TAG, "onResume - FragmentArtists >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(Globals.TAG, "FragmentArtists::tag2 = " + this.getTag());
        // inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_artists, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.v(Globals.TAG, "FragmentArtists::onViewCreated");

        // setup controls
        this.listviewArtists = view.findViewById(R.id.listviewArtists);

        // connect list view with adapter
        ArrayList<String> empty = new ArrayList<String>();
        Context context = this.getContext();
        this.artistsAdapter =
                new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, empty);
        this.listviewArtists.setAdapter(this.artistsAdapter);

        // setup firebase
        this.database = FirebaseDatabase.getInstance();
        this.reference = this.database.getReference(Globals.DATABASE_REF_ARTISTS);
        this.reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                FragmentArtists.this.artistsAdapter.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String s = (String) snapshot.child("Name").getValue();
                    FragmentArtists.this.artistsAdapter.add(s);
                }

                FragmentArtists.this.artistsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // getting list of artists failed, log a message
                Log.w(Globals.TAG, "load of artists list", databaseError.toException());
            }
        });
    }
}
