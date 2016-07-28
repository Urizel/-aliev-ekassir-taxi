package test.example.ekassir.taxi.utils.crypto;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 *
 */
public class CryptoSerialization {

    private static final String ALGORITHM_MD_5 = "MD5";

    public static String toHexString(byte[] array){
        if (array == null){
            return "";
        }
        StringBuilder hash = new StringBuilder();
        for (byte anArray : array) {
            hash.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
        }
        return hash.toString();
    }

    public static String toMD5(String source) {
        byte[] bytes = null;
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance(ALGORITHM_MD_5);
            bytes = md.digest(source.getBytes("UTF-8"));
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return toHexString(bytes);
    }
}
