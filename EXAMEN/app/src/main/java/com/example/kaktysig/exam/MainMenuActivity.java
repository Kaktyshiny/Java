package com.example.kaktysig.exam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kaktysig.exam.Fragments.FragmentFavorite;
import com.example.kaktysig.exam.Fragments.FragmentHeroes;

/**
 * Created by Kaktysig on 21.06.18.
 */

public class MainMenuActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView navigation;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        preferences = getSharedPreferences("LOGIN_DATA", Context.MODE_PRIVATE);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, FragmentHeroes.newInstance());
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        item.setChecked(true);

        Fragment selectedFragment = null;

        switch (item.getItemId()){
            case R.id.navigation_heroes:
                selectedFragment = FragmentHeroes.newInstance();
                break;
            case R.id.navigation_favorites:
                selectedFragment = FragmentFavorite.newInstance();
                break;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment);
        transaction.commit();

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.action_exit:
                preferences.edit().remove("is_auth").apply();
                Intent exit_intent = new Intent(this, LoginActivity.class);
                startActivity(exit_intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
