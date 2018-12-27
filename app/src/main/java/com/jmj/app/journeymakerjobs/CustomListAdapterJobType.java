package com.jmj.app.journeymakerjobs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class CustomListAdapterJobType extends ArrayAdapter<ProductJobType> {

    ArrayList<ProductJobType> products;
    Context context;
    int resource;
    Typeface fontAwesomeFont;

    public CustomListAdapterJobType(Context context, int resource, ArrayList<ProductJobType> products) {
        super(context, resource, products);
        this.products = products;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.jobtype_list_item, null, true);

        }
        ProductJobType product = getItem(position);

        fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fa-solid-900.ttf");

        TextView job = (TextView) convertView.findViewById(R.id.job);
        job.setTypeface(fontAwesomeFont);

        try {
            TextView type = (TextView) convertView.findViewById(R.id.txtName);
            String address = product.getName();
            byte[] b1 = address.getBytes("UTF-8");
            String add = new String(b1, "UTF-8");
            type.setText(add);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
