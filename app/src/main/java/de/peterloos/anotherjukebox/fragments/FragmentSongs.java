package de.peterloos.anotherjukebox.fragments;

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

import de.peterloos.anotherjukebox.Globals;
import de.peterloos.anotherjukebox.R;
import de.peterloos.anotherjukebox.utils.JukeboxHolder;

public class FragmentSongs extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listviewSongs;
    private ArrayAdapter<String> songsAdapter;

    public FragmentSongs() {
        // required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.v(Globals.TAG, "FragmentSongs::onCreateView");

        // inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_songs, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.v(Globals.TAG, "FragmentArtists::onViewCreated");

        // setup controls
        this.listviewSongs = view.findViewById(R.id.listviewSongs);

        // connect list view with adapter
        JukeboxHolder holder = JukeboxHolder.getInstance(this.getContext());
        this.songsAdapter = holder.getSongsAdapter();
        this.listviewSongs.setAdapter(this.songsAdapter);

        // handle click events on list view
        this.listviewSongs.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        JukeboxHolder holder = JukeboxHolder.getInstance(this.getContext());
        this.songsAdapter = holder.getSongsAdapter();

        ArrayAdapter<String> playlistAdapter = holder.getPlaylistAdapter();
        String song = this.songsAdapter.getItem(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Add to Playlist ?");
        builder.setItems(
            new CharSequence[] {"Yes", "Cancel"} ,
            (dialog, which) -> {

                /* which is an zero-based index */
                Log.v(Globals.TAG, "Dialog =========================> " + which);

                if (which == 0) {

                    playlistAdapter.add(song);
                }
            });
        builder.show();
    }
}
