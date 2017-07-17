package ltd.pvt.tagore_6.navigationapp.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ltd.pvt.tagore_6.navigationapp.ElectionDataModel;
import ltd.pvt.tagore_6.navigationapp.JSONParser;
import ltd.pvt.tagore_6.navigationapp.MainActivity;
import ltd.pvt.tagore_6.navigationapp.MyArrayAdapterElection;
import ltd.pvt.tagore_6.navigationapp.R;
import ltd.pvt.tagore_6.navigationapp.controller;
import ltd.pvt.tagore_6.navigationapp.utils.InternetConnection;
import ltd.pvt.tagore_6.navigationapp.utils.Keys;

/**
 * Created by Aman on 7/9/2017.
 */
public class LiveAfterReg extends Fragment {
    View rootView;
    Context context;
    private ArrayList<ElectionDataModel> list;
    private ListView listView;
    private MyArrayAdapterElection adapter;
    String result2=null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.frag_live_after_reg,container,false);
        context=rootView.getContext();
        list=new ArrayList<>();
        listView= (ListView) rootView.findViewById(R.id.listView2);
        adapter=new MyArrayAdapterElection(context,list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.ind_elec=(position+2+"");
                MainActivity.sheet_uid=(list.size()+position+2)+"";
               // String temp,lvl=list.get(position).getLevel();
               // if(lvl.equals("1"))
                if(list.get(position).getLevel().equals("1") || (list.get(position).getLevel().equals("2") && list.get(position).getCredential().equals(RegisterFragment.state))){
                   /* Snackbar snk;
                    snk=Snackbar.make(rootView,"Applicable", Snackbar.LENGTH_LONG);
                    TextView tv= (TextView) snk.getView().findViewById(android.support.design.R.id.snackbar_text);
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    snk.show();*/
                    new UpdateData(view).execute();
                    }
                else {
                Snackbar snk;
                snk=Snackbar.make(rootView,"Not Applicable", Snackbar.LENGTH_LONG);
                TextView tv= (TextView) snk.getView().findViewById(android.support.design.R.id.snackbar_text);
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    snk.show();
                }
            }
        });
        if (InternetConnection.checkConnection(context))
            new GetDataTask().execute();
        return rootView;

    }
    class GetDataTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;
        JSONObject jsonObject;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * Progress Dialog for User Interaction
             */

            x=list.size();

            if(x==0)
                jIndex=0;
            else
                jIndex=x;
            //Toast.makeText(context,"Else",Toast.LENGTH_SHORT).show();
            dialog = new ProgressDialog(context);
            dialog.setTitle("Loading...");
            dialog.setMessage("Getting Live Electons!");
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            /**
             * Getting JSON Object from Web Using okHttp
             */
            jsonObject = JSONParser.getDataFromWeb("1");
            // Toast.makeText(context,"Back",Toast.LENGTH_SHORT).show();

            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject != null) {
                    // Toast.makeText(context,"!null",Toast.LENGTH_SHORT).show();
                    /**
                     * Check Length...
                     */
                    if(jsonObject.length() > 0) {
                        /**
                         * Getting Array named "contacts" From MAIN Json Object
                         */
                        JSONArray array = jsonObject.getJSONArray(Keys.KEY_ELECTIONS);

                        /**
                         * Check Length of Array...
                         */


                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {

                                ElectionDataModel model = new ElectionDataModel();


                                JSONObject innerObject = array.getJSONObject(jIndex);
                                String election = innerObject.getString(Keys.KEY_ELECTIONS_VAR);
                                String credential=innerObject.getString("credential");
                                String level=innerObject.getString("level");
                                String election_id=innerObject.getString("election_id");

                                /**
                                 * Getting Object from Object "phone"
                                 */
                                //JSONObject phoneObject = innerObject.getJSONObject(Keys.KEY_PHONE);
                                //String phone = phoneObject.getString(Keys.KEY_MOBILE);

                                model.setElection(election);
                                model.setCredential(credential);
                                model.setLevel(level);
                                model.setElection_id(election_id);

                                /**
                                 * Adding name and phone concatenation in List...
                                 */
                                list.add(model);
                            }
                        }
                    }
                } else {
                    // Toast.makeText(context,"Else",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException je) {
                Log.i(JSONParser.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(context,list.get(0).getCredential().toString(),Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            /**
             * Checking if List size if more than zero then
             * Update ListView
             */
            if(list.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
                // Snackbar.make(rootView.findViewById(R.id.parentLayout), "No Data Found", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    class UpdateData extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        private CandidatesFragment candidatesFragment;
        View view;

        UpdateData(View view) {
            this.view=view;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            result2=null;

            dialog = new ProgressDialog(context);
            dialog.setTitle("Please Wait...");
            dialog.setMessage("Registering You...");
            dialog.show();

        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            JSONObject jsonObject1= controller.insertData(RegisterFragment.uid.toString());

            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject1 != null) {

                    result2=jsonObject1.getString("result");



                }
                else{
                    result2="You have already Registered";
                }
            } catch (JSONException je) {
                Log.i(controller.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            //if(!result2.equals("You have already voted")) {
            //  new CandidatesFragment().setImage();
            //  }
            if(!result2.equals("You have already Registered")) {
                android.app.FragmentManager fm=getFragmentManager();
                android.app.FragmentTransaction transaction=fm.beginTransaction().replace(R.id.content_frame,new LoginFragment());
                transaction.addToBackStack("Login");
                transaction.commit();



                MainActivity.mtoolbar.setTitle("Login");
            }
            Toast.makeText(context,result2, Toast.LENGTH_LONG).show();
            result2="jj";
        }
    }

    // Change the index of getItem() after adding or removing items from the navigation drawer
    @Override
    public void onStart() {
        MainActivity.navigationView.getMenu().getItem(4).setCheckable(true);
        MainActivity.navigationView.getMenu().getItem(4).setChecked(true);
        MainActivity.navigationView.getMenu().getItem(4).setEnabled(true);

        super.onStart();
    }
}
