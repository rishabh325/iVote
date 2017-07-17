package ltd.pvt.tagore_6.navigationapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ltd.pvt.tagore_6.navigationapp.MainActivity;
import ltd.pvt.tagore_6.navigationapp.R;

/**
 * Created by Aman on 6/3/2017.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    Button buttonLogin;
    EditText login_uid;
    EditText login_pass;
    Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_login,container,false);
        context=rootView.getContext();


        MainActivity.mtoolbar.setTitle("Login");


        login_uid= (EditText) rootView.findViewById(R.id.login_uid);
        login_pass= (EditText) rootView.findViewById(R.id.login_pass);
        buttonLogin=(Button) rootView.findViewById(R.id.butt_login);
        buttonLogin.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        MainActivity.uuid=login_uid.getText();
        MainActivity.pass=login_pass.getText();
        if(/*MainActivity.uuid.toString().length()==12&&*/!MainActivity.uuid.toString().equals("")&&!MainActivity.pass.toString().equals("")) {
           // MainActivity.headerUid.setText(MainActivity.uuid.toString());
            MainActivity.voted=false;
            android.app.FragmentManager fm = getFragmentManager();
            android.app.FragmentTransaction transaction=fm.beginTransaction().replace(R.id.content_frame,new LiveElectionsFragment());
            transaction.addToBackStack("Live");
            transaction.commit();


            MainActivity.mtoolbar.setTitle("Live Elections");
        }
        else{
            Toast.makeText(context,"Please enter both uid and password",Toast.LENGTH_SHORT).show();
        }
    }

    // Change the index of getItem() after adding or removing items from the navigation drawer
    @Override
    public void onStart() {
        MainActivity.navigationView.getMenu().getItem(2).setCheckable(true);
        MainActivity.navigationView.getMenu().getItem(2).setChecked(true);
        MainActivity.navigationView.getMenu().getItem(2).setEnabled(true);

        super.onStart();
    }
}
