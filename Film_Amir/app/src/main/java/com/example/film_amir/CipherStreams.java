//package com.example.film_amir;
//
//import java.util.List;
//
//public class CipherStreams {
//
//    KeyGenerator keygen = KeyGenerator.getInstance("AES");
//    Key k = keygen.generateKey();
//
//
//    public static void saveMovie(){
//
//        Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        aes.init(Cipher.ENCRYPT_MODE, k);
//        String fileName = "Encrypted.txt";
//        FileOutputStream fs = new FileOutputStream(fileName);
//        CipherOutputStream out = new CipherOutputStream(fs, aes);
//        out.write("[Hello:Okay]\nOkay".getBytes());
//        out.flush();
//        out.close();
//    }
//
//    public static List<Movie> loadMovie(){
//        Cipher aes2 = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        aes2.init(Cipher.DECRYPT_MODE, k);
//
//        FileInputStream fis = new FileInputStream(fileName);
//        CipherInputStream in = new CipherInputStream(fis, aes2);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//        byte[] b = new byte[1024];
//        int numberOfBytedRead;
//        while ((numberOfBytedRead = in.read(b)) >= 0) {
//            baos.write(b, 0, numberOfBytedRead);
//        }
//        System.out.println(new String(baos.toByteArray()));
//    }
//    }
//}

//https://www.programcreek.com/java-api-examples/?api=javax.crypto.CipherOutputStream