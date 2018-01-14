package de.peterloos.anotherjukebox.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import de.peterloos.anotherjukebox.Globals;
import de.peterloos.anotherjukebox.R;
import de.peterloos.anotherjukebox.fragments.FragmentAlbums;
import de.peterloos.anotherjukebox.fragments.FragmentArtists;

/**
 * Created by loospete on 14.01.2018.
 */

public class FragmentsPagerAdapter extends FragmentStatePagerAdapter {

    // Hint: check discussion "FragmentPagerAdapter vs FragmentStatePagerAdapter"

    private Context context;
    private String tabTitles[];

    public FragmentsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);

        Log.v(Globals.TAG, "c'tor FragmentsPagerAdapter (...)");

        this.context = context;

        Resources resources = context.getResources();
        this.tabTitles = new String[]{
                resources.getString(R.string.tab_title_artists),
                resources.getString(R.string.tab_title_albums),
                resources.getString(R.string.tab_title_player)
        };
    }

    @Override
    public Fragment getItem(int position) {

        Log.v(Globals.TAG, "FragmentsPagerAdapter ==> " + Integer.toString(position));

        switch (position) {
            case 0:
                return new FragmentArtists();
            case 1:
                return new FragmentAlbums();
            case 2:
                return new FragmentAlbums();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.tabTitles[position];
    }
}
