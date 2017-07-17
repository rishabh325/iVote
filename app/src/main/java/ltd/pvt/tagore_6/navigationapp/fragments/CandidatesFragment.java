package ltd.pvt.tagore_6.navigationapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.hdodenhof.circleimageview.CircleImageView;
import ltd.pvt.tagore_6.navigationapp.MainActivity;
import ltd.pvt.tagore_6.navigationapp.R;



import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import ltd.pvt.tagore_6.navigationapp.MyArrayAdapterCandidate;
import ltd.pvt.tagore_6.navigationapp.CandidateDataModel;
import ltd.pvt.tagore_6.navigationapp.JSONParser;
import ltd.pvt.tagore_6.navigationapp.UpdateDataActivity;
import ltd.pvt.tagore_6.navigationapp.controller;
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
public class CandidatesFragment extends Fragment {
    private ListView listView;
    private ArrayList<CandidateDataModel> list;
    private MyArrayAdapterCandidate adapter;
    Context context;
    View rootView;
    String result2=null;
    CircleImageView circleImageView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity.mtoolbar.setTitle("Candidates");
        rootView=inflater.inflate(R.layout.fragment_candidates,container,false);
        context=rootView.getContext();
        Fresco.initialize(rootView.getContext());

        list = new ArrayList<>();
        /**
         * Binding that List to Adapter
         */
        adapter = new MyArrayAdapterCandidate(rootView.getContext(), list);

        /**
         * Getting List and Setting List Adapter
         */
        listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.upd_votes=list.get(position).getVotes();
                MainActivity.upd_party=list.get(position).getParty();
                if((MainActivity.uuid==null)) {
                    Toast.makeText(context, "Please Login to Submit your vote", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (InternetConnection.checkConnection(context)) {

                        new UpdateData(new CandidatesFragment(),view).execute();


                          // Toast.makeText(context,result2, Toast.LENGTH_LONG).show();


                    }

                }

               // if(list.get(position).getParty().equals("BJP"))
               // Toast.makeText(context,"+1 BJP",Toast.LENGTH_SHORT).show();
                //Snackbar.make(rootView.findViewById(R.id.parentLayout), list.get(position).getName() + " => " + list.get(position).getPhone(), Snackbar.LENGTH_LONG).show();
            }
        });
        if (InternetConnection.checkConnection(context))
            new GetDataTask().execute();
        return rootView;

    }
    public  void setImage(View view){
        circleImageView = (CircleImageView) view.findViewById(R.id.imgParty);
       circleImageView.setBackgroundResource(R.drawable.ic_voted);
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
            dialog.setMessage("Loading Candidates' List...");
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            /**
             * Getting JSON Object from Web Using okHttp
             */
            jsonObject = JSONParser.getDataFromWeb(MainActivity.ind_elec);
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
                        JSONArray array = jsonObject.getJSONArray("Sheet"+MainActivity.ind_elec);

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
                                CandidateDataModel model = new CandidateDataModel();

                                /**
                                 * Getting Inner Object from contacts array...
                                 * and
                                 * From that We will get Name of that Contact
                                 *
                                 */
                                JSONObject innerObject = array.getJSONObject(jIndex);
                                String name = innerObject.getString(Keys.KEY_NAME);
                                String country = innerObject.getString(Keys.KEY_PARTY);
                                String votes=innerObject.getString("votes");

                                /**
                                 * Getting Object from Object "phone"
                                 */
                                //JSONObject phoneObject = innerObject.getJSONObject(Keys.KEY_PHONE);
                                //String phone = phoneObject.getString(Keys.KEY_MOBILE);

                                model.setName(name);
                                model.setParty(country);
                                model.setVotes(votes);

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

        UpdateData(CandidatesFragment candidatesFragment,View view) {
            this.candidatesFragment=candidatesFragment;
            this.view=view;
        }

            @Override
        protected void onPreExecute() {

            super.onPreExecute();
            result2=null;

            dialog = new ProgressDialog(context);
            dialog.setTitle("Please Wait...");
            dialog.setMessage("Submitting Your Vote...");
            dialog.show();

        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = null;
            jsonObject = controller.updateData(MainActivity.upd_party, Integer.parseInt(MainActivity.upd_votes) + 1);

            Log.i(controller.TAG, "Json obj ");

            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject != null) {

                    result2=jsonObject.getString("result");



                }
                else{
                    result2="You have already voted";
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
            if(result2.equals("Successfully Voted"))
            candidatesFragment.setImage(view);
            Toast.makeText(context,result2, Toast.LENGTH_LONG).show();
            result2="jj";
        }
    }
}
