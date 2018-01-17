package de.peterloos.anotherjukebox.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.peterloos.anotherjukebox.Globals;
import de.peterloos.anotherjukebox.R;

public class FragmentSongs extends Fragment {

    public FragmentSongs() {
        // required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.v(Globals.TAG, "FragmentSongs::onCreateView");
        Log.v(Globals.TAG, "FragmentSongs::tag = " + this.getTag());

        // inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_songs, container, false);
    }
}
