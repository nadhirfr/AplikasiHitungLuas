package vu.co.nadhir.hitung_luas;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.view.ActionBarPolicy;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Button leftButton;
    List judul;
    List tautan;
    private String tabTitles[] = new String[] { "Tab1", "Tab2", "Tab3" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ActionBarPolicy.get(this).showsOverflowMenuButton();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        leftButton = (Button) findViewById(R.id.leftButton);
        //mainButton = (Button) findViewById(R.id.mainButton);

       mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
         R.string.drawer_open, R.string.drawer_close) {

           @Override
           public void onDrawerOpened(View drawerView) {
               Toast.makeText(MainActivity.this, "Drawer Opened",
                       Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onDrawerClosed(View drawerView) {
               Toast.makeText(MainActivity.this, "Drawer Closed",
                       Toast.LENGTH_SHORT).show();
           }
       };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        leftButton.setOnClickListener(this);
        //mainButton.setOnClickListener(this);

        //mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        SampleFragmentPagerAdapter pagerAdapter =
                new SampleFragmentPagerAdapter(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            //tab.setText(tabTitles[i]);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }
        //tabs();
       // rss();

    }

public void rss(){
    /* ini coba rss start*/
    judul = new ArrayList();
    tautan = new ArrayList();
    ListView list ;

    try {
        // tentukan alamat file xml atau rss
        URL url = new URL("http://serambi.blankonlinux.or.id/rss20.xml");

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(false);
        XmlPullParser xpp = factory.newPullParser();

        // mendapatkan berkas XML dari variabel url
        xpp.setInput(getInputStream(url), "UTF_8");

        boolean insideItem = false;

        // Returns the type of current event: START_TAG, END_TAG, etc..
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {

                if (xpp.getName().equalsIgnoreCase("item")) {
                    insideItem = true;
                } else if (xpp.getName().equalsIgnoreCase("title")) {
                    if (insideItem)
                        judul.add(xpp.nextText()); // mengambil tag <title> dari berkas xml
                } else if (xpp.getName().equalsIgnoreCase("link")) {
                    if (insideItem)
                        tautan.add(xpp.nextText()); // mengambil tag <link> dari berkas xml
                }
            } else if (eventType == XmlPullParser.END_TAG
                    && xpp.getName().equalsIgnoreCase("item")) {
                insideItem = false;
            }

            eventType = xpp.next(); // menuju berita selanjutnya
        }

    } catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (XmlPullParserException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }

   //list = (ListView)findViewById(R.id.list);
    // menyisipkan data ke dalam adapter
    ArrayAdapter adapter = new ArrayAdapter(this,
            android.R.layout.simple_list_item_1, judul);

    // menampilkan adapter ke dalam listView
    //list.setAdapter(adapter);


        /*ini coba rss end*/
}
    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 4;
        private String tabTitles[] = new String[] { "Berita Utama", "Populer", "Terbaru","Top Komentar"};
        private Context context;

        public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return PageFragment.newInstance(position-1);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }

        private int[] layoutTab = { R.layout.fragment_page, R.layout.tab_title, R.layout.tab_title};
        //private int[] imageResId = { R.drawable.ic_one, R.drawable.ic_two };

        public View getTabView(int position) {
            //ini hanya untuk memberi judul tab
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View v = LayoutInflater.from(context).inflate(R.layout.tab_title, null);
            //ini judul tab nya bukan isinya
            TextView tt = (TextView)  v.findViewById(R.id.textView);
            tt.setText(tabTitles[position]);
            //ImageView img = (ImageView) v.findViewById(R.id.imgView);
            //img.setImageResource(imageResId[position]);
            return v;
        }
    }

    private void tabs(){

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        //tabLayout.setupWithViewPager(findViewById(R.id.viewpager));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 4"));



    }


    private ShareActionProvider mShareActionProvider;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        //Set up search menu
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        // Set up ShareActionProvider's default share intent
        MenuItem shareItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider)
                MenuItemCompat.getActionProvider(shareItem);
        mShareActionProvider.setShareIntent(getDefaultIntent());
        return true;
    }

    //controller share button actionbar
    private Intent getDefaultIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        //
        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }


        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        if (id == R.id.action_close) {
            this.finish();
            System.exit(id);
            return true;
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

        @Override
    public void onClick(View v) {
        /*if (v == mainButton) {
            Toast.makeText(this, "Main Button", Toast.LENGTH_SHORT).show();
        } else*/
        if (v == leftButton) {
            Toast.makeText(this, "Left Button", Toast.LENGTH_SHORT).show();
        }
    }
}

