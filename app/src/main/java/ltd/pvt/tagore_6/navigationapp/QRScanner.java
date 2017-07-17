package ltd.pvt.tagore_6.navigationapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.zxing.Result;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import ltd.pvt.tagore_6.navigationapp.fragments.RegisterFragment;
import me.dm7.barcodescanner.zxing.ZXingScannerView;




public class QRScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
     private ZXingScannerView zXingScannerView;

    public static String uid,name,gender,dateOfBirth,careOf,villageTehsil,postOffice,district,state,postCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);


        zXingScannerView=new ZXingScannerView(this);
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void handleResult(Result result) {
        processScannedData(result.getText());
    }





    protected void processScannedData(String scanData){
        XmlPullParserFactory pullParserFactory;

        try {
            // init the parserfactory
            pullParserFactory = XmlPullParserFactory.newInstance();
            // get the parser
            XmlPullParser parser = pullParserFactory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(scanData));

            // parse the XML
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.START_DOCUMENT) {
                } else if(eventType == XmlPullParser.START_TAG &&  "PrintLetterBarcodeData".equals(parser.getName())) {
                    // extract data from tag
                    //uid
                    uid = parser.getAttributeValue(null,"uid");
                    //name
                    name = parser.getAttributeValue(null,"name");
                    //gender
                    gender = parser.getAttributeValue(null,"gender");
                    // year of birth
                    dateOfBirth = parser.getAttributeValue(null,"dob");
                    // care of
                    // village Tehsil
                    villageTehsil = parser.getAttributeValue(null,"vtc");
                    // Post Office
                    postOffice = parser.getAttributeValue(null,"po");
                    // district
                    district = parser.getAttributeValue(null,"dist");
                    // state
                    state = parser.getAttributeValue(null,"state");
                    // Post Code
                    postCode = parser.getAttributeValue(null,"pc");

                } else if(eventType == XmlPullParser.END_TAG) {
                    Log.d("Rishu","End tag "+parser.getName());

                } else if(eventType == XmlPullParser.TEXT) {
                    Log.d("Rishu","Text "+parser.getText());

                }
                // update eventType
                eventType = parser.next();
            }

            // display the data on screen
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScannedData(scanData);

    }// EO function

    public void displayScannedData(String res) {
        zXingScannerView.stopCameraPreview();
        zXingScannerView.stopCamera();


        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage("Scanned Successfully");
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

        RegisterFragment.tv_sd_nam.setText(name);
        RegisterFragment.tv_sd_uid.setText(uid);
        RegisterFragment.tv_sd_gender.setText(gender);
        RegisterFragment.tv_sd_vtc.setText(villageTehsil+", "+postOffice+", \n"+district+", "+state+", \n"+postCode);
        RegisterFragment.tv_sd_yob.setText(dateOfBirth);
        RegisterFragment.state=state;
        finish();

    }


}

