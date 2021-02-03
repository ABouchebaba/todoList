package com.example.malistedetches;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;

public class TacheAdapter extends ArrayAdapter {

    private final Context context;
    private final ArrayList<String[]> data;
    private final int layoutResourceId;

    public TacheAdapter(Context context, int layoutResourceId, ArrayList<String[]> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder._id = (TextView)row.findViewById(R.id.entry_id_txt);
            holder.title = (TextView)row.findViewById(R.id.entry_title_txt);

            row.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)row.getTag();
        }

        String[] tache = data.get(position);

        holder._id.setText(tache[0]);
        holder.title.setText(tache[1]);

        return row;
    }

    static class ViewHolder
    {
        TextView _id;
        TextView title;

    }

}
