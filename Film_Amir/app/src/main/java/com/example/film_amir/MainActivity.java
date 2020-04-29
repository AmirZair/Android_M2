package com.example.film_amir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
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

    private static final int READ_PERMISSION_CODE = 100;
    private static final int WRITE_PERMISSION_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Movie> listMovie = new ArrayList<>();
        final MovieAdapter movieAdapter = new MovieAdapter(listMovie, getBaseContext());
        final ListView lv = findViewById(R.id.listViewMovie);
        final EditText inputCrypt = new EditText(this);
        final EditText inputDecrypt = new EditText(this);
        lv.setAdapter(movieAdapter);

        /*Alert Dialog chiffrer*/
        AlertDialog.Builder builderCrypt = new AlertDialog.Builder(this);
        builderCrypt.setTitle("Mot de passe pour chiffrer le fichier");
        builderCrypt.setMessage("Saisissez votre mot de passe");
        builderCrypt.setView(inputCrypt);

        /*Alert Dialog dechiffrer*/
        AlertDialog.Builder builderDecrypt = new AlertDialog.Builder(this);
        builderCrypt.setTitle("Mot de passe pour dechiffrer le fichier");
        builderCrypt.setMessage("Saisissez votre mot de passe");
        builderCrypt.setView(inputDecrypt);


        Button buttonDefaut = (Button) findViewById(R.id.button_defaut);
        Button buttonVide = (Button) findViewById(R.id.button_vide);
        Button buttonAjout = (Button) findViewById(R.id.button_ajout);
        Button buttonATsk = (Button) findViewById(R.id.button_ATsk);
        Button buttonAutoriser = (Button) findViewById(R.id.button_Autoriser);
        Button buttonSer = (Button) findViewById(R.id.button_Ser);
        Button buttonDeSer = (Button) findViewById(R.id.button_DeSer);
        Button buttonSerCSS = (Button) findViewById(R.id.button_SerCSS);
        Button buttonDeSerCSS = (Button) findViewById(R.id.button_DeSerCSS);

        /*Click listener dialog*/
        builderCrypt.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String key = inputCrypt.getText().toString();

            }
        });

        builderCrypt.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderDecrypt.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String key = inputDecrypt.getText().toString();

            }
        });

        builderDecrypt.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        checkPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                READ_PERMISSION_CODE);

        checkPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                WRITE_PERMISSION_CODE);

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

        buttonAutoriser.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                checkPermission(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        READ_PERMISSION_CODE);

                checkPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        WRITE_PERMISSION_CODE);
            }
        });

        buttonSer.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                OutputSteams.saveMovies(listMovie);
            }
        });

        buttonDeSer.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                listMovie.clear();
                listMovie.addAll(OutputSteams.loadMovie());
                movieAdapter.notifyDataSetChanged();
            }
        });

        final AlertDialog adCrypte = builderCrypt.create();
        buttonSerCSS.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                adCrypte.show();
            }
        });

        final AlertDialog adDecrypte = builderCrypt.create();
        buttonDeSerCSS.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                adDecrypte.show();
            }
        });



    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[] { permission },
                    requestCode);
        }
        else {
            Toast.makeText(MainActivity.this,
                    "Permission deja accordé",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == READ_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,
                        "READ Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(MainActivity.this,
                        "READ Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
        else if (requestCode == WRITE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,
                        "WRITING Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(MainActivity.this,
                        "WRITING Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
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
