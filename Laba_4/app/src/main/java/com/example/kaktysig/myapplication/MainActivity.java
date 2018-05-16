package com.example.kaktysig.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kaktysig.myapplication.net.MarvelApi;
import com.example.kaktysig.myapplication.net.request.characters.model.Character;
import com.example.kaktysig.myapplication.net.request.characters.model.CharacterDataWrapper;
import com.example.kaktysig.myapplication.net.request.characters.model.Image;
import com.example.kaktysig.myapplication.utils.CredentialsUtils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.example.kaktysig.myapplication.utils.CredentialsUtils.public_key;
import static com.example.kaktysig.myapplication.utils.CredentialsUtils.ts;
import static rx.android.schedulers.AndroidSchedulers.mainThread;


public class MainActivity extends AppCompatActivity {
    ArrayList<Character> characters_data = new ArrayList<Character>();
    AdapterListView adapterListView;

    private Subscription subscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();
    }

    private void getData(){
        ButterKnife.bind(this);

        MarvelApi marvelApi = MarvelApi.getInstance();
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
                                           for (int i = 0; i < 10; i++) {
                                               String name = response.getData().getResults().get(i).getName();
                                               Image image =  response.getData().getResults().get(i).getThumbnail();
                                               String description = response.getData().getResults().get(i).getDescription();
                                               Integer pk = response.getData().getResults().get(i).getPk();
                                               Character character_item = new Character(pk, name, image, description);
                                               characters_data.add(character_item);
                                           }
                                       }

                                       initList();
                                   }catch (NullPointerException e){
                                       Log.e("MainActivity", "NullPointerException  => " + e.getMessage());
                                   }
                               }
                           }
                );
    }

    private void initList(){
        adapterListView = new AdapterListView(this,characters_data);
        final ListView listOfRating = (ListView)findViewById(R.id.ItemView);
        listOfRating.setAdapter(adapterListView);

        listOfRating.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), CharacterDetail.class);
                Integer char_id = characters_data.get(position).getPk();
                Log.e("XXXX", char_id.toString());
                intent.putExtra("char_id", char_id);
                startActivity(intent);
            }


        });
    }
}