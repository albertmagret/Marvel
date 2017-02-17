package comics.marvel;

/**
 * Created by albert on 17/02/17.
 */

import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CommunicationsUtils {

    public static final String PUBLIC_KEY = "6a7ed890b4b941a925202a5630d5b162";
    public static final String PRIVATE_KEY = "0f1d0fdf46a0bf32f962b0b9997233c0395cdf8e";
    public static final String TS = "1487363956";
    private static final String hash = "957b569e820f8b2521afdc1fada8da97";

    public CommunicationsUtils(){}


    // http://gateway.marvel.com:80/v1/public/characters/1009220/comics?format=comic&formatType=comic&orderBy=title&ts=1487363956&apikey=6a7ed890b4b941a925202a5630d5b162&hash=957b569e820f8b2521afdc1fada8da97

    @Test
    public static String md5_mock() {
        final String MD5 = "MD5";
        try {

            String api_key = TS+""+PRIVATE_KEY+""+PUBLIC_KEY;

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
