package com.example.film_amir;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class OutputSteams {

    public static void saveMovies(List<Movie> listMovie){
        //convert list to ser
        ArrayList<MovieSer> listMovieSer = new ArrayList<>();
        for(Movie m : listMovie){
            listMovieSer.add(Converter.movieToSer(m));
        }

        File dir = new File(utils.file_path);
        if(!dir.exists())
            dir.mkdirs();

        File file = new File(dir, "MovieObject.ser");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            // write object to file
            oos.writeObject(listMovieSer);
            // closing resources
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Movie> loadMovie(){

        File dir = new File(utils.file_path);
        File file = new File(dir, "MovieObject.ser");
        ArrayList<Movie> listMovie = new ArrayList<>();
        try {
            FileInputStream is = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(is);
            ArrayList<MovieSer> movieSer = (ArrayList<MovieSer>) ois.readObject();
            ois.close();
            is.close();

            //deserialize object bitmap
            for(MovieSer m : movieSer){
                listMovie.add(Converter.serToMovie(m));
            }

            return listMovie;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
