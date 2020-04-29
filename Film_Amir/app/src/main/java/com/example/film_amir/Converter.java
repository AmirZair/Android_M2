package com.example.film_amir;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class Converter {

    public static byte[] bitmapToByte(Bitmap bitmap){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();

    }

    public static Bitmap byteToBitMap(byte[] array){
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }

    public static MovieSer movieToSer(Movie m){
        return new MovieSer(
                m.getTitle(),
                m.getYear(),
                m.getDirector(),
                m.getProducer(),
                m.getCost(),
                m.getImage() != null ? bitmapToByte(m.getImage()) : null
        );
    }

    public static Movie serToMovie(MovieSer m){
        return new Movie(
                m.getTitle(),
                m.getYear(),
                m.getDirector(),
                m.getProducer(),
                m.getCost(),
                m.getImage() != null ? byteToBitMap(m.getImage()) : null
        );
    }
}
