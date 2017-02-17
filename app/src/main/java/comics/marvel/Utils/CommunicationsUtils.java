package comics.marvel.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by albert on 16/02/17.
 */

public class CommunicationsUtils {

    // VARIABLES
    static String PUBLIC_KEY = "6a7ed890b4b941a925202a5630d5b162";
    static String PRIVATE_KEY = "0f1d0fdf46a0bf32f962b0b9997233c0395cdf8e";
    static String CAPITAN_ID = "1009220";

    // URLS
    static String BASE_URL = "http://gateway.marvel.com:80/";
    static String COMIC_LIST = "v1/public/characters/"+CAPITAN_ID+"/comics?format=comic&formatType=comic&orderBy=title&";
    static String URL_COMIC_LIST = BASE_URL+""+COMIC_LIST;

    String TAG = "CommunicationsUtils";
    //EXAMPLE Authentication
    // http://gateway.marvel.com/v1/comics?ts=1&apikey=6a7ed890b4b941a925202a5630d5b162&hash=5def722ec801d40824af072eeec86266

    // CAPTAIN QUERY
    // http://gateway.marvel.com:80/v1/public/characters/1009220/comics?format=comic&formatType=comic&orderBy=title&apikey=

    public CommunicationsUtils(){}

    private HttpURLConnection urlConnection = null;
    private HttpURLConnection urlConnection_img;

    public String getCapitanComics(String url){
        String stringResult = null;
        URL request_url = null;

        try {

            if (urlConnection != null) {
                urlConnection.disconnect();
                urlConnection = null;
            }

            request_url = new URL(url);
            urlConnection = (HttpURLConnection) request_url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(95 * 1000);
            urlConnection.setConnectTimeout(95 * 1000);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("X-Environment", "android");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            Log.w(TAG,"getCapitanComics inputStream: "+inputStream);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            stringResult = buffer.toString();



        } catch (MalformedURLException e) {
            //e.printStackTrace();
            stringResult = "1. ERROR : "+e.getMessage();
        } catch (IOException e) {
            //e.printStackTrace();
            stringResult = "2. ERROR : "+e.getMessage();
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        Log.w("CommunicationsUtils","getCapitanComics stringResult: "+stringResult);
        return stringResult;
    }


    public Bitmap getImageComics(String url){
        URL request_url = null;
        Bitmap bitmap = null;
        BufferedInputStream bufferedInputStream = null;


        try {
            request_url = new URL(url);

            if (urlConnection_img != null) {
                urlConnection_img.disconnect();
                urlConnection_img = null;
            }

            urlConnection_img = (HttpURLConnection) request_url.openConnection();
            urlConnection_img.setRequestMethod("GET");
            urlConnection_img.setReadTimeout(95 * 1000);
            urlConnection_img.setConnectTimeout(95 * 1000);
            urlConnection_img.setDoInput(true);
            urlConnection_img.setRequestProperty("X-Environment", "android");
            urlConnection_img.setRequestProperty("Content-Type", "image/jpeg");
            urlConnection_img.setRequestProperty("Accept", "application/json");
            urlConnection_img.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection_img.getInputStream();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }

            bufferedInputStream = new BufferedInputStream(inputStream, 8192); // 8192
            bitmap = BitmapFactory.decodeStream(bufferedInputStream);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            bitmap = null;
        } catch (IOException e) {
            e.printStackTrace();
            bitmap = null;
        } finally {
            urlConnection_img.disconnect();
        }

        return bitmap;

    }

    public static String ts(){
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        Log.w("CommunicationsUtils","getCapitanComics ts: "+ts);
        return  ts;
    }


    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {

            String api_key = s;

            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(api_key.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }



}
