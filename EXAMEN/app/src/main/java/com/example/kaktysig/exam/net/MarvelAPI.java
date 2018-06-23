package com.example.kaktysig.exam.net;

import android.util.Log;

import com.example.kaktysig.exam.net.request.characters.model.CharacterDataWrapper;
import com.example.kaktysig.exam.net.request.comics.model.ComicDataWrapper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.Version;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public class MarvelAPI {
    private static final String HOST = "https://gateway.marvel.com:443/v1/public/";
    private static final String CHARACTERS = "characters";
    private static final String COMICS = "comics";

    private Retrofit retrofit;
    private String char_id = "0";

    private MarvelAPI() {
        init();
    }

    private void init(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request requestWithUserAgent = originalRequest.newBuilder()
                            .removeHeader("User-Agent")
                            .header("User-Agent", Version.userAgent() + "/" + "android")
                            .build();
                    return chain.proceed(requestWithUserAgent);
                })
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static MarvelAPI getInstance(){
        return MarvelAPI.MarvelApiHolder.getInstance();
    }

    public Observable<CharacterDataWrapper> getMarvel(String ts, String apikey, String hash) {
        MarvelApiEndPoint request = retrofit.create(MarvelApiEndPoint.class);
        return request.getCharacters(ts, apikey, hash);
    }

    public Observable<CharacterDataWrapper> getMarvelCharacter(String ts, String apikey, String hash, Integer index) {
        MarvelApiEndPoint request = retrofit.create(MarvelApiEndPoint.class);
        return request.getCharactersById(index.toString(), ts, apikey, hash);
    }

    public Observable<ComicDataWrapper> getMarvelComics(String ts, String apikey, String hash, Integer index) {
        MarvelApiEndPoint request = retrofit.create(MarvelApiEndPoint.class);
        return request.getComics(index.toString(), ts, apikey, hash);
    }

    public Observable<ComicDataWrapper> getMarvelComic(String ts, String apikey, String hash, Integer index) {
        MarvelApiEndPoint request = retrofit.create(MarvelApiEndPoint.class);
        return request.getMarvelComicById(index.toString(), ts, apikey, hash);
    }

    private static class MarvelApiHolder {
        private static MarvelAPI instance;

        static MarvelAPI getInstance(){
            if(instance == null) {
                instance = new MarvelAPI();
            }
            else {
                instance.init();
            }

            return instance;
        }
    }

    private String GET_ID (int char_id){
        Integer char_str = char_id;
        String host = "characters/" + char_str.toString() + "?";
        return host;
    }

    public interface MarvelApiEndPoint{
        @GET(CHARACTERS + "?")
        Observable<CharacterDataWrapper> getCharacters(@Query("ts") String ts, @Query("apikey") String apikey, @Query("hash") String hash );

        @GET(CHARACTERS + "/{char_id}?")
        Observable<CharacterDataWrapper> getCharactersById(@Path("char_id") String char_id, @Query("ts") String ts, @Query("apikey") String apikey, @Query("hash") String hash);

        @GET(CHARACTERS + "/{char_id}/" + COMICS + "?")
        Observable<ComicDataWrapper> getComics(@Path("char_id") String char_id, @Query("ts") String ts, @Query("apikey") String apikey, @Query("hash") String hash );

        @GET(COMICS + "/{comic_id}?")
        Observable<ComicDataWrapper> getMarvelComicById(@Path("comic_id") String comic_id, @Query("ts") String ts, @Query("apikey") String apikey, @Query("hash") String hash);

    }
}
