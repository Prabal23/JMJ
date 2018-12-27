package com.jmj.app.journeymakerjobs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Visit website http://www.whats-online.info
 **/

public class dataAdapter extends ArrayAdapter<Contact> {

    Context context;
    ArrayList<Contact> mcontact;
    Typeface fontAwesomeFont;


    public dataAdapter(Context context, ArrayList<Contact> contact) {
        super(context, R.layout.job_lists, contact);
        this.context = context;
        this.mcontact = contact;
    }

    public class Holder {
        TextView exp, time, date, eye, save, post, organization, last_date, experience, nature;
        ImageView pic;
        LinearLayout savedpage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fa-solid-900.ttf");

        Contact data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        Holder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.job_lists, parent, false);

            viewHolder.exp = (TextView) convertView.findViewById(R.id.exp);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.eye = (TextView) convertView.findViewById(R.id.apply);
            viewHolder.save = (TextView) convertView.findViewById(R.id.save);
            viewHolder.pic = (ImageView) convertView.findViewById(R.id.p1);
            viewHolder.exp.setTypeface(fontAwesomeFont);
            viewHolder.time.setTypeface(fontAwesomeFont);
            viewHolder.date.setTypeface(fontAwesomeFont);
            viewHolder.eye.setTypeface(fontAwesomeFont);
            viewHolder.save.setTypeface(fontAwesomeFont);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Holder) convertView.getTag();
        }

        String title = data.getTitle();
        viewHolder.post = (TextView) convertView.findViewById(R.id.post);
        viewHolder.post.setText(Html.fromHtml(title));

        String org = data.getCompany();
        viewHolder.organization = (TextView) convertView.findViewById(R.id.org);
        viewHolder.organization.setText(Html.fromHtml(org));

        String date = data.getLast_date();
        viewHolder.last_date = (TextView) convertView.findViewById(R.id.last_date);
        viewHolder.last_date.setText(Html.fromHtml(date));

        String exp_ratio = data.getExperience();
        viewHolder.experience = (TextView) convertView.findViewById(R.id.exp_ratio);
        viewHolder.experience.setText(Html.fromHtml(exp_ratio));

        String nat = data.getNature();
        viewHolder.nature = (TextView) convertView.findViewById(R.id.full_part);
        viewHolder.nature.setText(Html.fromHtml(nat));

        viewHolder.pic.setImageBitmap(convertToBitmap(data.getImage()));

        /*viewHolder.savedpage = (LinearLayout) convertView.findViewById(R.id.r6);
        viewHolder.savedpage.setVisibility(View.VISIBLE);
        viewHolder.savedpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
        // Return the completed view to render on screen
        return convertView;
    }
    //get bitmap image from byte array

    private Bitmap convertToBitmap(byte[] b) {

        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }
}

