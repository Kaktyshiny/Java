package com.example.kaktysig.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kaktysig.myapplication.net.MarvelApi;
import com.example.kaktysig.myapplication.net.request.characters.model.Character;
import com.example.kaktysig.myapplication.net.request.characters.model.CharacterDataWrapper;
import com.example.kaktysig.myapplication.net.request.characters.model.Image;
import com.example.kaktysig.myapplication.utils.CredentialsUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.example.kaktysig.myapplication.utils.CredentialsUtils.public_key;
import static com.example.kaktysig.myapplication.utils.CredentialsUtils.ts;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * Created by Kaktysig on 16.05.18.
 */

public class CharacterDetail  extends AppCompatActivity {

    private Subscription subscription;

    @BindView(R.id.characterImage)
    ImageView characterImage;
    @BindView(R.id.characterName)
    TextView characterName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        Intent intent = getIntent();
        Integer character_id = intent.getIntExtra("char_id", 0);
        getData(character_id);
    }



    private void getData(int character_id){
        ButterKnife.bind(this);



        MarvelApi marvelApi = MarvelApi.getInstance();
        subscription = marvelApi.getMarvelCharacter(ts,public_key, CredentialsUtils.getHash(), character_id)
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
                                               characterName.setText(response.getData().getResults().get(0).getName());
                                               String imagePath = response.getData().getResults().get(0).getThumbnail().getPath()+ "/standard_xlarge" + ".";
                                               String imageExtension =  response.getData().getResults().get(0).getThumbnail().getExtension();
                                               String imageUrl = imagePath + imageExtension;
                                               Picasso.get().load(imageUrl).into(characterImage);

                                   }catch (NullPointerException e){
                                       Log.e("MainActivity", "NullPointerException  => " + e.getMessage());
                                   }
                               }
                           }
                );
    }
}
