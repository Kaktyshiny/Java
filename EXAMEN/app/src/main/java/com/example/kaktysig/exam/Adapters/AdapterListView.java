package com.example.kaktysig.exam.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kaktysig.exam.R;
import com.example.kaktysig.exam.net.request.characters.model.Character;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Kaktysig on 22.06.18.
 */

public class AdapterListView extends BaseAdapter{

    Context ctx;
    LayoutInflater lInflater;

    ArrayList<Character> characters_data;

    public AdapterListView(Context context, ArrayList<Character> _characters) {
        ctx = context;
        characters_data = _characters;
        lInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return characters_data.size();
    }

    @Override
    public Object getItem(int position) {
        return characters_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    Character getCharacter(int position){
        return((Character)getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.heroes_list_item, parent, false);
        }

        Character character = getCharacter(position);

        TextView namefield = (TextView) view.findViewById(R.id.hero_name);
        TextView descrfield = (TextView) view.findViewById(R.id.hero_description);
        ImageView imagefield = (ImageView) view.findViewById(R.id.hero_image);

        namefield.setText(character.getName());
        descrfield.setText(character.getDescription());
        String imagePath = character.getThumbnail().getPath()+ "/standard_xlarge" + ".";
        String imageExtension =  character.getThumbnail().getExtension();
        String imageUrl = imagePath + imageExtension;
        Picasso.get().load(imageUrl).into(imagefield);

        return view;
    }

}
