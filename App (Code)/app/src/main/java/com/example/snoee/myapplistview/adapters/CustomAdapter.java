package com.example.snoee.myapplistview.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.snoee.myapplistview.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    ArrayList<SingleRow> list;
    Context c;

    public CustomAdapter(Context c) {
        this.c=c;
        list = new ArrayList<SingleRow>();
        Resources res = c.getResources();
        String[] titles = res.getStringArray(R.array.titles);
        String[] descriptions = res.getStringArray(R.array.description);
        int[] images = {R.drawable.control_panel_icon,R.drawable.statistics_icon};
        for (int i = 0; i < titles.length; i++) {
            list.add(new SingleRow(images[i], titles[i], descriptions[i]));
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(c).inflate(R.layout.categories_listview, null);

            viewHolder = new ViewHolder();

            viewHolder.imageViewCategory = convertView.findViewById(R.id.category_image);
            viewHolder.textViewTitle = convertView.findViewById(R.id.category_title);
            viewHolder.textViewDescription = convertView.findViewById(R.id.category_description);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        SingleRow row = list.get(position);

        viewHolder.textViewTitle.setText(row.title);
        viewHolder.textViewDescription.setText(row.description);
        viewHolder.imageViewCategory.setImageResource(row.image);

        return convertView;

    }
}

class ViewHolder{
    ImageView imageViewCategory;
    TextView textViewTitle;
    TextView textViewDescription;
}