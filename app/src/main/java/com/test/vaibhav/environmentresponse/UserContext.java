package com.test.vaibhav.environmentresponse;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class UserContext {

    private static String id;
    private static String email;
    private static String displayName;
    private static String profileImageURL;
    private static Bitmap profileImage;

    public static String getId(){
        return id;
    }
    public static String getDisplayName(){
        return displayName;
    }
    public static String getEmail(){
        return email;
    }
    public static String getProfileImageURL(){
        return profileImageURL;
    }
    public static Bitmap getProfileImage(){
        return profileImage;
    }
    public static void setId(String s){
        id =s;
    }
    public static void setEmail(String s){
        email =s;
    }
    public static void setProfileImageURL(String s){
        profileImageURL =s;
    }
    public static void setDisplayName(String s){
        displayName =s;
    }
    public static Bitmap downloadImageusingHTTPGetRequest(String urlString) {
        Bitmap image=null, line;

        try {
            URL url = new URL(urlString);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = httpConnection.getInputStream();
                image = getImagefromStream(stream);
            }
            httpConnection.disconnect();
        }  catch (UnknownHostException e1) {
            Log.d("MyDebugMsg", "UnknownHostexception in sendHttpGetRequest");
            e1.printStackTrace();
        } catch (Exception ex) {
            Log.d("MyDebugMsg", "Exception in sendHttpGetRequest");
            ex.printStackTrace();
        }
        profileImage= image;
        return image;
    }
    private static Bitmap getImagefromStream(InputStream stream) {
        Bitmap bitmap = null;
        if(stream!=null) {
            bitmap = BitmapFactory.decodeStream(stream);
            try {
                stream.close();
            }catch (IOException e1) {
                Log.d("MyDebugMsg", "IOException in getImagefromStream()");
                e1.printStackTrace();
            }
        }
        return bitmap;
    }

}
