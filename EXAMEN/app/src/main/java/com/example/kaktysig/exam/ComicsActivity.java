package com.example.kaktysig.exam;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kaktysig.exam.Adapters.ComicAdapterListView;
import com.example.kaktysig.exam.Details.ComicDetail;
import com.example.kaktysig.exam.net.MarvelAPI;
import com.example.kaktysig.exam.net.request.comics.model.Image;
import com.example.kaktysig.exam.net.request.comics.model.Comic;
import com.example.kaktysig.exam.net.request.comics.model.ComicDataWrapper;
import com.example.kaktysig.exam.utils.CredentialsUtils;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.example.kaktysig.exam.utils.CredentialsUtils.public_key;
import static com.example.kaktysig.exam.utils.CredentialsUtils.ts;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * Created by Kaktysig on 21.06.18.
 */

public class ComicsActivity extends AppCompatActivity {
    private ListView comic_list;
    private ComicAdapterListView adapter;

    ArrayList<Comic> comics_data = new ArrayList<Comic>();
    ComicAdapterListView adapterListView;

    private Subscription subscription;
    private int char_id;

    public static ComicsActivity newInstance() {
        ComicsActivity fragment = new ComicsActivity();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics);

        Intent coming_intent = getIntent();
        Integer character_id = coming_intent.getIntExtra("char_id", 0);

        adapterListView = new ComicAdapterListView(this, comics_data);

        MarvelAPI marvelApi = MarvelAPI.getInstance();
        subscription = marvelApi.getMarvelComics(ts,public_key, CredentialsUtils.getHash(), character_id)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<ComicDataWrapper>() {
                               @Override
                               public void onCompleted() {
                                   Log.d("MainActivity", "onCompleted");
                               }

                               @Override
                               public void onError(Throwable e) {
                                   Log.e("MainActivity", "onError => " + e.getMessage());
                               }

                               @Override
                               public void onNext(ComicDataWrapper response) {
                                   Log.d("MainActivity", "onNext => " + response);
                                   try{
                                       if(response.getData().getResults().size() > 0){
                                           for (int i = 0; i < response.getData().getResults().size(); i++) {
                                               String name = response.getData().getResults().get(i).getTitle();
                                               Image image =  response.getData().getResults().get(i).getThumbnail();
                                               String description = response.getData().getResults().get(i).getDescription();

                                               Integer pk = response.getData().getResults().get(i).getPk();
                                               Comic comic_item = new Comic(pk, name, image, description);
                                               comics_data.add(comic_item);
                                           }
                                       }

                                       final ListView listOfComics = (ListView) findViewById(R.id.comics_list_view);

                                       listOfComics.setAdapter(adapterListView);

                                       listOfComics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                           @Override
                                           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                               Intent intent = new Intent(getApplicationContext(), ComicDetail.class);
                                               Integer char_id = comics_data.get(position).getPk();
                                               intent.putExtra("comic_id", char_id);
                                               startActivity(intent);
                                           }
                                       });


                                   }catch (NullPointerException e){
                                       Log.e("MainActivity", "NullPointerException  => " + e.getMessage());
                                   }
                               }
                           }
                );
    }


}
