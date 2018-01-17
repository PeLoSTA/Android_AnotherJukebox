package de.peterloos.anotherjukebox.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import de.peterloos.anotherjukebox.utils.JukeboxHolder;

public class FragmentArtists extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listviewArtists;
    private ArrayAdapter<String> artistsAdapter;

    // firebase support
    private FirebaseDatabase database;
    private DatabaseReference reference;

    private OnAlbumsOfArtistListener listener;

    public FragmentArtists() {
        // required empty public constructor
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

        // handle click events on list view
        this.listviewArtists.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Search Albums of Artist ?");
        builder.setItems(
            new CharSequence[] {"Yes", "Cancel"} ,
            (dialog, which) -> {

                if (which == 0) {

                    /* which is an zero-based index */
                    Log.v(Globals.TAG, "Dialog =========================> " + which);

                    if (which == 0) {

                        if (listener != null) {

                            String artist = artistsAdapter.getItem(position);
                            listener.setupAlbumsOfArtistList(artist);
                        }
                    }
                }
            });
        builder.show();
    }

    /**
     * define interaction interface between albums and songs fragment
     */
    public interface OnAlbumsOfArtistListener {

        void setupAlbumsOfArtistList(String artist);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            this.listener = (OnAlbumsOfArtistListener) this.getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Internal Error: Parent activity doesn't implement interface 'OnAlbumsOfArtistListener'");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        this.listener = null;
    }
}
