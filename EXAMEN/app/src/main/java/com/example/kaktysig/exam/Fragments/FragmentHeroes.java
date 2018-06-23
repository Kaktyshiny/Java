package com.example.kaktysig.exam.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kaktysig.exam.Adapters.AdapterListView;
import com.example.kaktysig.exam.Details.CharacterDetails;
import com.example.kaktysig.exam.R;
import com.example.kaktysig.exam.net.MarvelAPI;
import com.example.kaktysig.exam.net.request.characters.model.Character;
import com.example.kaktysig.exam.net.request.characters.model.CharacterDataWrapper;
import com.example.kaktysig.exam.net.request.characters.model.Image;
import com.example.kaktysig.exam.utils.CredentialsUtils;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.example.kaktysig.exam.utils.CredentialsUtils.public_key;
import static com.example.kaktysig.exam.utils.CredentialsUtils.ts;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * Created by Kaktysig on 22.06.18.
 */

public class FragmentHeroes extends Fragment{

    private ListView hero_list;
    private AdapterListView adapter;

    ArrayList<Character> characters_data = new ArrayList<Character>();
    AdapterListView adapterListView;

    private Subscription subscription;

    public static FragmentHeroes newInstance() {
        FragmentHeroes fragment = new FragmentHeroes();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_heroes, container, false);

        adapterListView = new AdapterListView(getActivity().getApplicationContext(), characters_data);

        MarvelAPI marvelApi = MarvelAPI.getInstance();
        subscription = marvelApi.getMarvel(ts,public_key, CredentialsUtils.getHash())
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<CharacterDataWrapper>() {
                               @Override
                               public void onCompleted() {
                                   Log.d("MainActivity", "onCompleted");
                               }

                               @Override
                               public void onError(Throwable e) {
                                   Log.e("MainActivity", "onError => " + e.getMessage());
                               }

                               @Override
                               public void onNext(CharacterDataWrapper response) {
                                   Log.d("MainActivity", "onNext => " + response);
                                   try{
                                       if(response.getData().getResults().size() > 0){
                                           for (int i = 0; i < response.getData().getResults().size(); i++) {
                                               String name = response.getData().getResults().get(i).getName();
                                               Image image =  response.getData().getResults().get(i).getThumbnail();
                                               String description = response.getData().getResults().get(i).getDescription();
                                               Integer pk = response.getData().getResults().get(i).getPk();
                                               Character character_item = new Character(pk, name, image, description);
                                               characters_data.add(character_item);
                                           }
                                       }

                                       final ListView listOfHeroes = v.findViewById(R.id.hero_list_view);

                                       listOfHeroes.setAdapter(adapterListView);

                                       listOfHeroes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                           @Override
                                           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                               Intent intent = new Intent(getActivity().getApplicationContext(), CharacterDetails.class);
                                               Integer char_id = characters_data.get(position).getPk();
                                               intent.putExtra("char_id", char_id);
                                               startActivity(intent);
                                           }
                                       });


                                   }catch (NullPointerException e){
                                       Log.e("MainActivity", "NullPointerException  => " + e.getMessage());
                                   }
                               }
                           }
                );

        return v;
    }

}
