package com.minipro.util;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by chuckulu on 2017/7/9.
 */
public class Base64Util {
    private static Charset CHARSET;



    public static String encodeString(String str) {
        return new String(Base64.encodeBase64(str.getBytes(CHARSET)),CHARSET);
    }

    public static String decodeString(String str) {
        return new String(Base64.decodeBase64(str.getBytes(CHARSET)),CHARSET);
    }


}
