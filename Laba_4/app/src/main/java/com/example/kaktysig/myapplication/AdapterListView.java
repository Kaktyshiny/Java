package com.example.kaktysig.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kaktysig.myapplication.net.MarvelApi;
import com.example.kaktysig.myapplication.net.request.characters.model.Character;
import com.example.kaktysig.myapplication.net.request.characters.model.CharacterDataWrapper;
import com.example.kaktysig.myapplication.net.request.characters.model.Image;
import com.example.kaktysig.myapplication.utils.CredentialsUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.example.kaktysig.myapplication.utils.CredentialsUtils.public_key;
import static com.example.kaktysig.myapplication.utils.CredentialsUtils.ts;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * Created by Kaktysig on 08.05.18.
 */

public class AdapterListView extends BaseAdapter {

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
            view = lInflater.inflate(R.layout.list_item, parent, false);
        }

        Character character = getCharacter(position);

        TextView namefield = (TextView) view.findViewById(R.id.characterName);
        TextView descrfield = (TextView) view.findViewById(R.id.characterDescription);
        ImageView imagefield = (ImageView) view.findViewById(R.id.characterImage);

        namefield.setText(character.getName());
        descrfield.setText(character.getDescription());
        String imagePath = character.getThumbnail().getPath()+ "/standard_xlarge" + ".";
        String imageExtension =  character.getThumbnail().getExtension();
        String imageUrl = imagePath + imageExtension;
        Picasso.get().load(imageUrl).into(imagefield);

        return view;
    }

}