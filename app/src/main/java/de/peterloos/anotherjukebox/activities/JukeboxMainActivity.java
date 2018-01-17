package de.peterloos.anotherjukebox.activities;

// AUFHEBEN:
// viewPager.setCurrentItem(idx);   SO TRAVERSIERT MAN DEN VIEWPAGER PROGRAMMATICALLY

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import de.peterloos.anotherjukebox.Globals;
import de.peterloos.anotherjukebox.R;
import de.peterloos.anotherjukebox.adapters.FragmentsPagerAdapter;
import de.peterloos.anotherjukebox.fragments.FragmentAlbums;
import de.peterloos.anotherjukebox.fragments.FragmentArtists;
import de.peterloos.anotherjukebox.utils.JukeboxHolder;

public class JukeboxMainActivity extends AppCompatActivity implements FragmentAlbums.OnAlbumsFragmentListener, FragmentArtists.OnAlbumsOfArtistListener {

    private ViewPager viewPager;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_jukebox_main);

        Log.d(Globals.TAG, "JukeboxMainActivity::onCreate");

        Toolbar toolbar = this.findViewById(R.id.main_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.showOverflowMenu();
        this.setSupportActionBar(toolbar);

        // set adapter of view pager
        this.viewPager = this.findViewById(R.id.main_viewpager);
        this.fragmentManager = this.getSupportFragmentManager();
        FragmentsPagerAdapter adapter = new FragmentsPagerAdapter(fragmentManager, this);
        viewPager.setAdapter(adapter);

        // connect TabLayout and ViewPager
        TabLayout tabLayout = this.findViewById(R.id.main_tablayout);
        tabLayout.setupWithViewPager(viewPager);


        // Original ?!?!?!?
//        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_jukebox_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // implementation of interface 'FragmentAlbums.OnAlbumsFragmentListener'
    @Override
    public void setupSongsList(String artist, String album) {

        String msg = String.format("JukeboxMainActivity::Yeahhh ... I've got a song !!! %s %s", artist, album);
        Log.d(Globals.TAG, msg);

        JukeboxHolder holder = JukeboxHolder.getInstance(this.getApplicationContext());
        ArrayAdapter<String> songsAdapter = holder.getSongsAdapter();
        songsAdapter.add(artist + " " + album);
    }

    // implementation of interface 'FragmentArtists.OnAlbumsOfArtistListener'
    @Override
    public void setupAlbumsOfArtistList(String artist) {

        // test frame
        JukeboxHolder holder = JukeboxHolder.getInstance(this.getApplicationContext());
        ArrayAdapter<String> songsAdapter = holder.getSongsAdapter();

        if (artist.equals("Beatles")) {

            songsAdapter.clear();
            songsAdapter.add("Let it be");
            songsAdapter.add("White Album");
            songsAdapter.add("Abbey Road");

            this.viewPager.setCurrentItem(1, true);
        }
        else if (artist.equals("Toto")) {

            songsAdapter.clear();
            songsAdapter.add("II");
            songsAdapter.add("IV");
            songsAdapter.add("Seven");

            this.viewPager.setCurrentItem(1, true);
        }

    }
}
