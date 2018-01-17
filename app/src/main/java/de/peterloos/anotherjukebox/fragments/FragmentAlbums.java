package de.peterloos.anotherjukebox.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class FragmentAlbums extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listviewAlbums;
    private ArrayAdapter<String> albumsAdapter;

    private OnAlbumsFragmentListener listener;

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

        Log.v(Globals.TAG, "FragmentAlbums::onViewCreated");
        Log.v(Globals.TAG, "FragmentAlbums::tag = " + this.getTag());

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

        this.listviewAlbums.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.w(Globals.TAG, "FragmentAlbums at position " + position);

        if (this.listener != null) {

            this.listener.onSetupSongsList("Ahhhhhhh", "Ohhhhhhhhhhh");
        }
    }

    /**
     * define interaction interface between albums and songs fragment
     */
    public interface OnAlbumsFragmentListener {

        void onSetupSongsList(String artist, String album);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            this.listener = (OnAlbumsFragmentListener) this.getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Internal Error: Parent activity doesn't implement interface 'OnAlbumsFragmentListener'");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        this.listener = null;
    }
}
