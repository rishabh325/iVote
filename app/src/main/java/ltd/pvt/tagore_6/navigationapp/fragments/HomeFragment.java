package ltd.pvt.tagore_6.navigationapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import ltd.pvt.tagore_6.navigationapp.MainActivity;
import ltd.pvt.tagore_6.navigationapp.R;

/**
 * Created by Aman on 6/3/2017.
 */
public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity.mtoolbar.setTitle("Home");
        View rootView=inflater.inflate(R.layout.fragment_home,container,false);
        return rootView;
    }



    // Change the index of getItem() after adding or removing items from the navigation drawer
    @Override
    public void onStart() {
        MainActivity.navigationView.getMenu().getItem(0).setCheckable(true);
        MainActivity.navigationView.getMenu().getItem(0).setChecked(true);
        MainActivity.navigationView.getMenu().getItem(0).setEnabled(true);

        super.onStart();
    }
}
