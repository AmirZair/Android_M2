package com.example.film_amir;

import android.os.Environment;

import java.io.File;

public class utils {

    public static String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Storage";

    public static boolean fileExist(){
        File dir = new File(utils.file_path);
        File file = new File(dir, "MovieObject.ser");
        return file.exists();
    }
}
