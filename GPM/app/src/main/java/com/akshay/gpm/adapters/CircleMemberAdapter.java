package com.akshay.gpm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.akshay.gpm.R;
import com.google.api.services.plusDomains.model.Person;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

/**
 * Created by akshay on 3/16/15.
 */
public class CircleMemberAdapter extends ArrayAdapter<Person> {
    TextView itemView;
    SmartImageView myImage;

    private static class ViewHolder {
        private TextView itemView;
    }

    public CircleMemberAdapter(Context context, int textViewResourceId, ArrayList<Person> items) {
        super(context, textViewResourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.adapter_member_circle, parent, false);

//            viewHolder = new ViewHolder();
            itemView = (TextView) convertView.findViewById(R.id.circle_member_text_view);

            convertView.setTag(itemView);
        } else {
            itemView = (TextView) convertView.getTag();
        }

        Person item = getItem(position);
        if (item!= null) {
            // My layout has only one TextView
            // do whatever you want with your string and long
            itemView.setText(String.format("%s ", item.getDisplayName()));
            myImage = (SmartImageView) convertView.findViewById(R.id.profile_image_view);
            myImage.setImageUrl(item.getImage().getUrl());
        }
        return convertView;
    }
}