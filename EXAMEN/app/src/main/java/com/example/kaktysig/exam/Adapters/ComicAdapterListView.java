package com.example.kaktysig.exam.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kaktysig.exam.R;
import com.example.kaktysig.exam.net.request.comics.model.Comic;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Kaktysig on 22.06.18.
 */

public class ComicAdapterListView extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;

    ArrayList<Comic> comics_data;

    public ComicAdapterListView(Context context, ArrayList<Comic> _comics) {
        ctx = context;
        comics_data = _comics;
        lInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return comics_data.size();
    }

    @Override
    public Object getItem(int position) {
        return comics_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    Comic getComic(int position){
        return((Comic)getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.comics_list_item, parent, false);
        }

        Comic comic = getComic(position);

        TextView namefield = (TextView) view.findViewById(R.id.comic_name);
        TextView descrfield = (TextView) view.findViewById(R.id.comic_description);
        ImageView imagefield = (ImageView) view.findViewById(R.id.comic_image);

        namefield.setText(comic.getTitle());
        descrfield.setText(comic.getDescription());
        String imagePath = comic.getThumbnail().getPath()+ "/standard_xlarge" + ".";
        String imageExtension =  comic.getThumbnail().getExtension();
        String imageUrl = imagePath + imageExtension;
        Picasso.get().load(imageUrl).into(imagefield);

        return view;
    }

}
