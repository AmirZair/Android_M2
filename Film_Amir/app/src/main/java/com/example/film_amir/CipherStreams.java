package com.example.film_amir;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class CipherStreams {

    final static String fileName = "MovieObjectCrypte.ser";

    public static void saveMovie(String pass, List<Movie> listMovie){

        byte[] keyByte = Arrays.copyOfRange(getHash(pass).getBytes(), 0, 16);
        //convert list to ser
        ArrayList<MovieSer> listMovieSer = new ArrayList<>();
        for(Movie m : listMovie){
            listMovieSer.add(Converter.movieToSer(m));
        }

        File dir = new File(utils.file_path);
        if(!dir.exists())
            dir.mkdirs();

        File file = new File(dir, fileName);

        try {
            SecretKeySpec key = new SecretKeySpec(keyByte, "AES");
            Cipher aes = Cipher.getInstance("AES");
            aes.init(Cipher.ENCRYPT_MODE, key);

            FileOutputStream fos = new FileOutputStream(file);
            CipherOutputStream crypter = new CipherOutputStream(fos, aes);
            ObjectOutputStream out = new ObjectOutputStream(crypter);

            out.writeObject(listMovieSer);
            // closing resources
            out.close();
            fos.close();
        }
        catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

    }

    public static List<Movie> loadMovie(String pass){
        byte[] keyByte = Arrays.copyOfRange(getHash(pass).getBytes(), 0, 16);


        File dir = new File(utils.file_path);
        File file = new File(dir, fileName);
        ArrayList<Movie> listMovie = new ArrayList<>();

        try {
            SecretKeySpec key = new SecretKeySpec(keyByte, "AES");
            Cipher aes = Cipher.getInstance("AES");
            aes.init(Cipher.DECRYPT_MODE, key);

            FileInputStream is = new FileInputStream(file);
            CipherInputStream decrypter = new CipherInputStream(is, aes);
            ObjectInputStream out = new ObjectInputStream(decrypter);
            ArrayList<MovieSer> movieSer = (ArrayList<MovieSer>) out.readObject();
            // closing resources
            out.close();
            is.close();

            //deserialize object bitmap
            for(MovieSer m : movieSer){
                listMovie.add(Converter.serToMovie(m));
            }

            return listMovie;

        }
        catch ( NoSuchPaddingException |
                NoSuchAlgorithmException  |
                IOException |
                InvalidKeyException |
                ClassNotFoundException e) {

                e.printStackTrace();
                return null;
        }
        }

    public static String getHash(String input)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            String hashtext = no.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}


