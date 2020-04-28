package com.example.film_amir;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageDownloaderThread implements Runnable {
    private String imageUrl;
    private Movie movie;
    private MovieAdapter movieAdapter;

    public ImageDownloaderThread(Movie movie, MovieAdapter movieAdapter, String imageUrl) {
        this.movie = movie;
        this.movieAdapter = movieAdapter;
        this.imageUrl = imageUrl;
    }

    @Override
    public void run() {
        try {
            URL ImageUrl = new URL(imageUrl);
            URLConnection conn = ImageUrl.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            Bitmap bitmap = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
            movie.setImage(bitmap);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    movieAdapter.notifyDataSetChanged();
                }
            });
        }
        catch (IOException e) {
            System.out.println(e.toString());
        }
    }

}
