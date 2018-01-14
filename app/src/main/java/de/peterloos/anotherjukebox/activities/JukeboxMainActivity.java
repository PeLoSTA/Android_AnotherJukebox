package de.peterloos.anotherjukebox.activities;

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

import de.peterloos.anotherjukebox.Globals;
import de.peterloos.anotherjukebox.R;
import de.peterloos.anotherjukebox.adapters.FragmentsPagerAdapter;

public class JukeboxMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_jukebox_main);

        Log.d(Globals.TAG, "JukeboxMainActivity::onCreate");

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.main_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.showOverflowMenu();
        this.setSupportActionBar(toolbar);

        // set adapter of view pager
        ViewPager viewPager = (ViewPager) this.findViewById(R.id.main_viewpager);
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentsPagerAdapter adapter = new FragmentsPagerAdapter(fragmentManager, this);
        viewPager.setAdapter(adapter);

        // connect TabLayout and ViewPager
        TabLayout tabLayout = (TabLayout) this.findViewById(R.id.main_tablayout);
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
}
