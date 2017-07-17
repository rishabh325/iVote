package ltd.pvt.tagore_6.navigationapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
/**
 * Created by Aman on 6/19/2017.
 */
public class MyArrayAdapterElection extends ArrayAdapter<ElectionDataModel> {
    List<ElectionDataModel> modelList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public MyArrayAdapterElection(Context context, List<ElectionDataModel> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        modelList = objects;
    }

    @Override
    public ElectionDataModel getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.live_row_view, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        ElectionDataModel item = getItem(position);

        vh.textViewElection.setText(item.getElection());
      //  vh.textViewParty.setText(item.getParty());

        return vh.rootView;
    }

    /**
     * ViewHolder class for layout.<br />
     * <br />
     * Auto-created on 2016-01-05 00:50:26 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private static class ViewHolder {
        public final RelativeLayout rootView;

        public final TextView textViewElection;
       // public final TextView textViewParty;

        private ViewHolder(RelativeLayout rootView, TextView textViewElection) {
            this.rootView = rootView;
            this.textViewElection = textViewElection;
          //  this.textViewParty = textViewParty;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            TextView textViewElection = (TextView) rootView.findViewById(R.id.textViewElection);
            //TextView textViewParty = (TextView) rootView.findViewById(R.id.textViewParty);
            return new ViewHolder(rootView, textViewElection);
        }
    }
}
