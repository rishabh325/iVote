package ltd.pvt.tagore_6.navigationapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ltd.pvt.tagore_6.navigationapp.MainActivity;
import ltd.pvt.tagore_6.navigationapp.R;

/**
 * Created by Aman on 6/3/2017.
 */
public class AboutFragment extends Fragment  {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity.mtoolbar.setTitle("Live Elections");



        View rootView=inflater.inflate(R.layout.fragment_about,container,false);
        return rootView;
    }
}
