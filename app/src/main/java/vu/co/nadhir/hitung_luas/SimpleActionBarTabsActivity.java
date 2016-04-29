package vu.co.nadhir.hitung_luas;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class SimpleActionBarTabsActivity
        extends FragmentActivity implements ActionBar.TabListener {

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        Log.d("SimpleActionBarTabsActivity", "tab "
                + String.valueOf(tab.getPosition()) + " clicked");

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        Log.d("SimpleActionBarTabsActivity","tab "
                + String.valueOf(tab.getPosition()) + " re-clicked");
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        Log.d("SimpleActionBarTabsActivity","tab "
                + String.valueOf(tab.getPosition()) + " re-clicked");
    }
}
