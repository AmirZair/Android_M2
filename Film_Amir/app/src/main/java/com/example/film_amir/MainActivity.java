package com.example.film_amir;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Button;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    public String URLIMAGE = "https://picsum.photos/75/75";
    public Movie[] initdata = new Movie[]{
            new Movie("Film1", "2010", "Director1", "Producer1", "999", null),
            new Movie("Film2", "2011", "Director2", "Producer2", "999", null),
            new Movie("Film3", "2012", "Director3", "Producer3", "999", null),
            new Movie("Film4", "2013", "Director4", "Producer4", "999", null)
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
        Button buttonATsk = (Button) findViewById(R.id.button_ATsk);
        Button buttonATskExec = (Button) findViewById(R.id.button_ATskExec);
        Button buttonThreads = (Button) findViewById(R.id.button_Threads);
        Button buttonExecutor = (Button) findViewById(R.id.button_Executor);
        Button buttonExecWR = (Button) findViewById(R.id.button_ExecWR);


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
                String year = "" + randInt(1920, 2020);
                String director = "director" + randInt(1, 100);
                String producer = "producer" + randInt(1, 100);
                String cost = "" + randInt(1000, 10000);
                listMovie.add(new Movie(title, year, director, producer, cost, null));
                movieAdapter.notifyDataSetChanged();
            }
        });

        buttonATsk.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                for (Movie m : listMovie) {
                    ImageDownloader imageDownloader = new ImageDownloader(m, movieAdapter);
                    imageDownloader.execute(URLIMAGE);
                }
            }
        });

        buttonATskExec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Movie m : listMovie) {
                    ImageDownloader asyncTask = new ImageDownloader(m, movieAdapter);
                    asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, URLIMAGE);
                }
            }
        });

        buttonThreads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Movie m : listMovie) {
                    Runnable threadDownloader = new ImageDownloaderThread(m, movieAdapter, URLIMAGE);
                    Thread thread = new Thread(threadDownloader);
                    thread.start();
                }

                movieAdapter.notifyDataSetChanged();
            }
        });

        buttonExecutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutorService executor = Executors.newFixedThreadPool(5);
                for (Movie m : listMovie) {

                    Runnable threadDownloader = new ImageDownloaderThread(m, movieAdapter, URLIMAGE);
                    executor.execute(threadDownloader);

                }
                executor.shutdown();
                //wait until thread finished
                while (!executor.isTerminated()) {

                }
            }
        });

        buttonExecWR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutorService executor = Executors.newFixedThreadPool(5);

                for (Movie m : listMovie) {

                    Runnable threadDownloader = new ImageDownloaderThread(m, movieAdapter, URLIMAGE);
                    executor.execute(threadDownloader);

                }
                executor.shutdown();
                //wait until thread finished
                while (!executor.isTerminated()) {

                }
            }
        });
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        private Movie movie;
        private MovieAdapter movieAdapter;
        ProgressDialog p;

        public ImageDownloader(Movie movie, MovieAdapter movieAdapter) {
            this.movie = movie;
            this.movieAdapter = movieAdapter;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(MainActivity.this);
            p.setMessage("downloading in async");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String urldisplay = url[0];
            URL ImageUrl = null;
            InputStream is = null;
            Bitmap bitmap = null;
            try {
                ImageUrl = new URL(urldisplay);
                URLConnection conn = ImageUrl.openConnection();
                conn.connect();
                is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bitmap = BitmapFactory.decodeStream(bis);
                bis.close();
                return bitmap;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                movie.setImage(bitmap);
                movieAdapter.notifyDataSetChanged();
                p.hide();
            } else {
                p.show();
            }
        }
    }

}
