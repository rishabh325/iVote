package ltd.pvt.tagore_6.navigationapp.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import ltd.pvt.tagore_6.navigationapp.MainActivity;
import ltd.pvt.tagore_6.navigationapp.QRScanner;
import ltd.pvt.tagore_6.navigationapp.R;
import ltd.pvt.tagore_6.navigationapp.controller;

/**
 * Created by Aman on 6/3/2017.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener {
    public static  TextView tv_sd_uid,tv_sd_nam,tv_sd_gender,tv_sd_yob,tv_sd_vtc,tv_sd_password,tv_sd_conpass,tv_sd_mob;
    public static String uid,state,country;
    Button buttonScan,buttonSubmit;
    Context context;
    CheckBox undertaking;

   // Toolbar mToolbar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView=inflater.inflate(R.layout.fragment_register,container,false);
        MainActivity.mtoolbar.setTitle("Register");
        tv_sd_uid = (TextView)rootView.findViewById(R.id.editUid);
        tv_sd_nam = (TextView)rootView.findViewById(R.id.editNam);
        tv_sd_gender = (TextView)rootView.findViewById(R.id.editGender);
        tv_sd_yob = (TextView)rootView.findViewById(R.id.editDob);
        tv_sd_vtc = (TextView)rootView.findViewById(R.id.editAddress);
        tv_sd_password= (TextView) rootView.findViewById(R.id.editPass);
        tv_sd_conpass= (TextView) rootView.findViewById(R.id.editConfirmPass);
        tv_sd_mob= (TextView) rootView.findViewById(R.id.editMobile);
        undertaking= (CheckBox) rootView.findViewById(R.id.undertaking);

        context=rootView.getContext();
        buttonScan=(Button)rootView.findViewById(R.id.buttonScan);
        buttonScan.setOnClickListener(this);

        buttonSubmit=(Button)rootView.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonScan:

                Intent intent=new Intent(context,QRScanner.class);
                startActivity(intent);
                break;
            case R.id.buttonSubmit:

                if(undertaking.isChecked() && !tv_sd_uid.getText().toString().equals("") && tv_sd_password.getText().toString().equals(tv_sd_conpass.getText().toString()) && (!tv_sd_password.getText().toString().equals(""))){
               // Toast.makeText(context,"Registered Successfully",Toast.LENGTH_SHORT).show();

               // new UpdateData().execute();
                    uid=tv_sd_uid.getText().toString();
                    MainActivity.uid=tv_sd_uid.getText().toString();

               android.app.FragmentManager fm=getFragmentManager();
               android.app.FragmentTransaction transaction=fm.beginTransaction().replace(R.id.content_frame,new LiveAfterReg());
               transaction.addToBackStack("Live");
                    transaction.commit();

                MainActivity.mtoolbar.setTitle("Live Elections");}
                else if(tv_sd_uid.getText().toString().equals(""))
                    Toast.makeText(context,"Please scan a AADHAR QR",Toast.LENGTH_SHORT).show();
                else if(tv_sd_password.getText().toString().equals(""))
                    Toast.makeText(context,"Please enter the password",Toast.LENGTH_SHORT).show();
                else if( !tv_sd_password.getText().toString().equals(tv_sd_conpass.getText().toString()) )
                    Toast.makeText(context,"Password and Confirm password don't match",Toast.LENGTH_SHORT).show();
                else if(!undertaking.isChecked())
                    Toast.makeText(context,"Please check the undertaking",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    // Change the index of getItem() after adding or removing items from the navigation drawer
    @Override
    public void onStart() {
        MainActivity.navigationView.getMenu().getItem(1).setCheckable(true);
        MainActivity.navigationView.getMenu().getItem(1).setChecked(true);
        MainActivity.navigationView.getMenu().getItem(1).setEnabled(true);

        super.onStart();
    }
}
