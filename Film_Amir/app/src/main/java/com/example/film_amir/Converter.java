package com.example.film_amir;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.nio.ByteBuffer;

public class Converter {

    public static byte[] bitmapToByte(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int size = bitmap.getRowBytes() * bitmap.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
        bitmap.copyPixelsToBuffer(byteBuffer);
        byte[] byteArray = byteBuffer.array();

        return byteArray;
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
                bitmapToByte(m.getImage())
        );
    }

    public static Movie serToMovie(MovieSer m){
        return new Movie(
                m.getTitle(),
                m.getYear(),
                m.getDirector(),
                m.getProducer(),
                m.getCost(),
                byteToBitMap(m.getImage())
        );
    }
}
