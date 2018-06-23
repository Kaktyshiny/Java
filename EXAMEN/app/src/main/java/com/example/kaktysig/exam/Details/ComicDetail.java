package com.example.kaktysig.exam.Details;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaktysig.exam.R;
import com.example.kaktysig.exam.net.MarvelAPI;
import com.example.kaktysig.exam.net.request.comics.model.ComicDataWrapper;
import com.example.kaktysig.exam.utils.CredentialsUtils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.example.kaktysig.exam.utils.CredentialsUtils.public_key;
import static com.example.kaktysig.exam.utils.CredentialsUtils.ts;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class ComicDetail extends AppCompatActivity {
    private Subscription subscription;
    private Gson gson;
    private String comic_cashe;

    @BindView(R.id.comicImage)
    ImageView comicImage;
    @BindView(R.id.comicName)
    TextView comicName;
    @BindView(R.id.comicDescription)
    TextView comicDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);

        Intent intent = getIntent();
        Integer comic_id = intent.getIntExtra("comic_id", 0);
        gson = new Gson();

        getData(comic_id);
    }



    private void getData(int comic_id){
        ButterKnife.bind(this);

        MarvelAPI marvelApi = MarvelAPI.getInstance();
        subscription = marvelApi.getMarvelComic(ts,public_key, CredentialsUtils.getHash(), comic_id)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<ComicDataWrapper>() {
                               @Override
                               public void onCompleted() {
                                   Log.d("MainActivity", "onCompleted");
                               }

                               @Override
                               public void onError(Throwable e) {
                                   Log.w("MainActivity", "onError => " + e.getMessage());

                                   SharedPreferences preferences = getSharedPreferences("COMICS_DATA", Context.MODE_PRIVATE);
                                   comic_cashe = preferences.getString("comic_cashe_" + comic_id, "");

                                   if (comic_cashe == ""){
                                       comicName.setText("Ошибка при соединении с интернетом");
                                   }
                                   else{
                                       ComicDataWrapper response = gson.fromJson(comic_cashe, ComicDataWrapper.class);

                                       Toast.makeText(getApplicationContext(), "Не удалось загрузить, загружено из кэша", Toast.LENGTH_LONG).show();
                                       comicName.setText(response.getData().getResults().get(0).getTitle());
                                       String imagePath = response.getData().getResults().get(0).getThumbnail().getPath()+ "/standard_xlarge" + ".";
                                       String imageExtension =  response.getData().getResults().get(0).getThumbnail().getExtension();
                                       String imageUrl = imagePath + imageExtension;
                                       String description = response.getData().getResults().get(0).getDescription();
                                       Picasso.get().load(imageUrl).into(comicImage);
                                       comicDescription.setText(response.getData().getResults().get(0).getDescription());
                                   }
                               }

                               @Override
                               public void onNext(ComicDataWrapper response) {
                                   Log.d("MainActivity", "onNext => " + response);
                                   try{
                                       comicName.setText(response.getData().getResults().get(0).getTitle());
                                       String imagePath = response.getData().getResults().get(0).getThumbnail().getPath()+ "/standard_xlarge" + ".";
                                       String imageExtension =  response.getData().getResults().get(0).getThumbnail().getExtension();
                                       String imageUrl = imagePath + imageExtension;
                                       String description = response.getData().getResults().get(0).getDescription();
                                       Picasso.get().load(imageUrl).into(comicImage);
                                       comicDescription.setText(response.getData().getResults().get(0).getDescription());

                                       comic_cashe = gson.toJson(response);
                                       SharedPreferences preferences = getSharedPreferences("COMICS_DATA", Context.MODE_PRIVATE);
                                       SharedPreferences.Editor editor = preferences.edit();
                                       editor.putString("comic_cashe_" + comic_id, comic_cashe);
                                       editor.apply();


                                   }catch (NullPointerException e){
                                       Log.e("MainActivity", "NullPointerException  => " + e.getMessage());
                                   }
                               }
                           }
                );
    }
}
