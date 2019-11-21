package com.apms_user;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class HistoryAdapter extends ArrayAdapter<HistoryHelper> {

    Context tempContext;

    public HistoryAdapter(@NonNull Context context, int resource, @NonNull List<HistoryHelper> objects) {
        super(context, resource, objects);
        tempContext = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if (convertView == null)
        {
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.custom_history_listview, parent, false);
        }


        TextView date = convertView.findViewById(R.id.date);
        TextView time = convertView.findViewById(R.id.timePark);

        HistoryHelper historyHelper = getItem(position);

        date.setText(historyHelper.getParkStatus());
        time.setText(historyHelper.getTime());

        return convertView;
    }
}