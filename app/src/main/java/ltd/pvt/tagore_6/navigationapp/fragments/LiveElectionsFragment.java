package ltd.pvt.tagore_6.navigationapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ltd.pvt.tagore_6.navigationapp.ElectionDataModel;
import ltd.pvt.tagore_6.navigationapp.MainActivity;
import ltd.pvt.tagore_6.navigationapp.MyArrayAdapterElection;
import ltd.pvt.tagore_6.navigationapp.R;



import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import ltd.pvt.tagore_6.navigationapp.MyArrayAdapterCandidate;
import ltd.pvt.tagore_6.navigationapp.JSONParser;
import ltd.pvt.tagore_6.navigationapp.utils.InternetConnection;
import ltd.pvt.tagore_6.navigationapp.utils.Keys;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Aman on 6/5/2017.
 */
public class LiveElectionsFragment extends Fragment {
    private ListView listView;
    private ArrayList<ElectionDataModel> list;
    private MyArrayAdapterElection adapter;
    Context context;
    View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity.mtoolbar.setTitle("Live Elections");
        rootView=inflater.inflate(R.layout.fragment_live,container,false);
        context=rootView.getContext();
        Fresco.initialize(rootView.getContext());
        /**
         * Array List for Binding Data from JSON to this List
         */
        list = new ArrayList<>();
        /**
         * Binding that List to Adapter
         */
        adapter = new MyArrayAdapterElection(rootView.getContext(), list);

        /**
         * Getting List and Setting List Adapter
         */
        listView = (ListView) rootView.findViewById(R.id.listViewElections);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.ind_elec=(position+2+"");
                MainActivity.sheet_uid=(list.size()+position+2)+"";
                android.app.FragmentManager fm=getFragmentManager();
                android.app.FragmentTransaction transaction=fm.beginTransaction().replace(R.id.content_frame,new CandidatesFragment());
                transaction.addToBackStack("Candidates");
                transaction.commit();



                MainActivity.mtoolbar.setTitle("Candidates");
                //Snackbar.make(rootView.findViewById(R.id.parentLayout), list.get(position).getName() + " => " + list.get(position).getPhone(), Snackbar.LENGTH_LONG).show();
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
                                // Toast tst= Toast.makeText(context,"For loop",Toast.LENGTH_SHORT);
                                // tst.setGravity(Gravity.BOTTOM,0,0);
                                //tst.show();
                                /**
                                 * Creating Every time New Object
                                 * and
                                 * Adding into List
                                 */
                                ElectionDataModel model = new ElectionDataModel();

                                /**
                                 * Getting Inner Object from contacts array...
                                 * and
                                 * From that We will get Name of that Contact
                                 *
                                 */
                                JSONObject innerObject = array.getJSONObject(jIndex);
                                String election = innerObject.getString(Keys.KEY_ELECTIONS_VAR);
                                String credential=innerObject.getString("credential");


                                /**
                                 * Getting Object from Object "phone"
                                 */
                                //JSONObject phoneObject = innerObject.getJSONObject(Keys.KEY_PHONE);
                                //String phone = phoneObject.getString(Keys.KEY_MOBILE);

                                model.setElection(election);
                                model.setCredential(credential);

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

    // Change the index of getItem() after adding or removing items from the navigation drawer
    @Override
    public void onStart() {
        MainActivity.navigationView.getMenu().getItem(4).setCheckable(true);
        MainActivity.navigationView.getMenu().getItem(4).setChecked(true);
        MainActivity.navigationView.getMenu().getItem(4).setEnabled(true);

        super.onStart();
    }
}
