package de.peterloos.anotherjukebox.activities;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import javax.microedition.khronos.opengles.GL;

import de.peterloos.anotherjukebox.Globals;
import de.peterloos.anotherjukebox.R;
import de.peterloos.anotherjukebox.adapters.FragmentsPagerAdapter;
import de.peterloos.anotherjukebox.fragments.FragmentAlbums;
import de.peterloos.anotherjukebox.fragments.FragmentPlayer;

public class JukeboxMainActivity extends AppCompatActivity implements FragmentAlbums.OnAlbumsFragmentListener  {

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
        ViewPager viewPager = this.findViewById(R.id.main_viewpager);
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

    @Override
    public void onSetupSongsList(String artist, String album) {

        String msg = String.format("JukeboxMainActivity::Yeahhh ... I've got a song !!! %s %s", artist, album);

        Log.d(Globals.TAG, msg);

        // FragmentPlayer f = (FragmentPlayer) this.fragmentManager.findFragmentByTag("unique_tag_fragment_player");
        FragmentPlayer f2 = (FragmentPlayer) this.fragmentManager.findFragmentById(R.id.id_fragment_player);

        this.referenceOfPlayerFragment.displayReceivedData (artist, album);
    }

    private FragmentPlayer referenceOfPlayerFragment;

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);



        String msg = String.format("JukeboxMainActivity::onAttachFragment ...ARGHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
        Log.v(Globals.TAG, msg);



        if (fragment.getClass() == FragmentPlayer.class) {
            this.referenceOfPlayerFragment = (FragmentPlayer) fragment;
        }


    }
}
