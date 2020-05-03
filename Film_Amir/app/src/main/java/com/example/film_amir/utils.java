package com.example.film_amir;

import android.os.Environment;

import java.io.File;
import java.util.Random;

public class utils {

    public static String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Storage";

    public static boolean fileExist(){
        File dir = new File(utils.file_path);
        File file = new File(dir, "MovieObject.ser");
        return file.exists();
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
