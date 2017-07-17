package ltd.pvt.tagore_6.navigationapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Aman on 6/19/2017.
 */
public class MyArrayAdapterCandidate extends ArrayAdapter<CandidateDataModel> {
    List<CandidateDataModel> modelList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public MyArrayAdapterCandidate(Context context, List<CandidateDataModel> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        modelList = objects;
    }

    @Override
    public CandidateDataModel getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.candidate_row_view, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        CandidateDataModel item = getItem(position);

        vh.textViewName.setText(item.getName());
        vh.textViewParty.setText(item.getParty());


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

        public final TextView textViewName;
        public final TextView textViewParty;

        private ViewHolder(RelativeLayout rootView, TextView textViewName, TextView textViewParty) {
            this.rootView = rootView;
            this.textViewName = textViewName;
            this.textViewParty = textViewParty;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            TextView textViewName = (TextView) rootView.findViewById(R.id.textViewName);
            TextView textViewParty = (TextView) rootView.findViewById(R.id.textViewParty);
            return new ViewHolder(rootView, textViewName, textViewParty);
        }
    }
}
