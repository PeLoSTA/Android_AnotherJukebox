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

public class FragmentAlbums extends Fragment {

    private ListView listviewAlbums;
    private ArrayAdapter<String> albumsAdapter;

    // firebase support
    private FirebaseDatabase database;
    private DatabaseReference reference;

    public FragmentAlbums() {
        // required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_albums, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.v(Globals.TAG, "FragmentNews::onViewCreated");

        // setup controls
        this.listviewAlbums = view.findViewById(R.id.listviewAlbums);

        // connect list view with adapter
        ArrayList<String> empty = new ArrayList<String>();
        Context context = this.getContext();
        this.albumsAdapter =
                new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, empty);
        this.listviewAlbums.setAdapter(this.albumsAdapter);

        // setup firebase
        this.database = FirebaseDatabase.getInstance();
        this.reference = this.database.getReference(Globals.DATABASE_REF_ALBUMS);
        this.reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                FragmentAlbums.this.albumsAdapter.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String albumName = (String) snapshot.child("Name").getValue();
                    String artistOfAlbum = (String) snapshot.child("Artist").getValue();
                    String entry = String.format("%s (%s)", albumName, artistOfAlbum);
                    Log.v(Globals.TAG, entry);
                    FragmentAlbums.this.albumsAdapter.add(entry);
                }

                FragmentAlbums.this.albumsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // getting list of albums failed, log a message
                Log.w(Globals.TAG, "load of albums list", databaseError.toException());
            }
        });
    }
}
