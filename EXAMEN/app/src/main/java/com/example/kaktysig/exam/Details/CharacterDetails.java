package com.example.kaktysig.exam.Details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kaktysig.exam.ComicsActivity;
import com.example.kaktysig.exam.R;
import com.example.kaktysig.exam.net.MarvelAPI;
import com.example.kaktysig.exam.net.request.characters.model.CharacterDataWrapper;
import com.example.kaktysig.exam.utils.CredentialsUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.example.kaktysig.exam.utils.CredentialsUtils.public_key;
import static com.example.kaktysig.exam.utils.CredentialsUtils.ts;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class CharacterDetails extends AppCompatActivity implements View.OnClickListener {
    private Subscription subscription;
    private Integer character_id;

    @BindView(R.id.characterImage)
    ImageView characterImage;
    @BindView(R.id.characterName)
    TextView characterName;
    @BindView(R.id.characterDescription)
    TextView characterDescription;
    @BindView(R.id.button_view_comics)
    Button button_view_comics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        character_id = intent.getIntExtra("char_id", 0);
        getData(character_id);

        button_view_comics.setOnClickListener(this);
    }



    private void getData(int character_id){
        ButterKnife.bind(this);

        MarvelAPI marvelApi = MarvelAPI.getInstance();
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
                                       String description = response.getData().getResults().get(0).getDescription();
                                       Picasso.get().load(imageUrl).into(characterImage);
                                       characterDescription.setText(response.getData().getResults().get(0).getDescription());

                                   }catch (NullPointerException e){
                                       Log.e("MainActivity", "NullPointerException  => " + e.getMessage());
                                   }
                               }
                           }
                );
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ComicsActivity.class);
        intent.putExtra("char_id", character_id);
        startActivity(intent);
    }
}
