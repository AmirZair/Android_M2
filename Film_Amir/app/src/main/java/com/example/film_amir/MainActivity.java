package com.example.film_amir;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Button;


import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    public Movie[] initdata = new Movie[]{
            new Movie("Film1", "2010","Director1", "Producer1", "999"),
            new Movie("Film2", "2011","Director2", "Producer2", "999"),
            new Movie("Film3", "2012","Director3", "Producer3", "999"),
            new Movie("Film4", "2013","Director4", "Producer4", "999")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Movie> listMovie = new ArrayList<>();
        final MovieAdapter movieAdapter = new MovieAdapter(listMovie, getBaseContext());
        final ListView lv = findViewById(R.id.listViewMovie);
        lv.setAdapter(movieAdapter);


        Button buttonDefaut = (Button) findViewById(R.id.button_defaut);
        Button buttonVide = (Button) findViewById(R.id.button_vide);
        Button buttonAjout = (Button) findViewById(R.id.button_ajout);

        buttonDefaut.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                listMovie.clear();
                listMovie.addAll(Arrays.asList(initdata));
                movieAdapter.notifyDataSetChanged();
            }
        });

        buttonVide.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                listMovie.clear();
                movieAdapter.notifyDataSetChanged();
            }
        });

        buttonAjout.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String title = "titre" + randInt(1, 100);
                String year = ""+randInt(1920, 2020);
                String director = "director"+randInt(1, 100);
                String producer = "producer"+randInt(1, 100);
                String cost = ""+randInt(1000, 10000);
                listMovie.add(new Movie(title, year, director, producer, cost));
                movieAdapter.notifyDataSetChanged();
            }
        });
    }

    public static int randInt(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
