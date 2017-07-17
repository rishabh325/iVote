package ltd.pvt.tagore_6.navigationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import ltd.pvt.tagore_6.navigationapp.fragments.LoginFragment;

public class UpdateDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new UpdateData().execute();

    }


    class UpdateData extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        String result=null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(UpdateDataActivity.this);
            dialog.setTitle("Please Wait...");
            dialog.setMessage("Submitting Your Vote...");
            dialog.show();

        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = null;
            JSONObject jsonObject1=controller.insertData(MainActivity.uuid.toString());
            try {
                if(!jsonObject1.getString("result").equals("error")) {
                   jsonObject = controller.updateData(MainActivity.upd_party, Integer.parseInt(MainActivity.upd_votes) + 1);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i(controller.TAG, "Json obj ");

            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject != null) {

                    result=jsonObject.getString("result");


                }
                else{
                    result="error";
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
            Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
