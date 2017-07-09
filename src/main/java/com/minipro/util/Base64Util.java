package com.minipro.util;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by chuckulu on 2017/7/9.
 */
public class Base64Util {
    private Charset CHARSET;
    public Base64Util(String charset) {
        CHARSET = Charset.forName(charset);
    }
    public Base64Util() {
        CHARSET = Charset.forName("UTF-8");
    }


    public String encodeString(String str) {
        return new String(Base64.encodeBase64(str.getBytes(CHARSET)),CHARSET);
    }

    public String decodeString(String str) {
        return new String(Base64.decodeBase64(str.getBytes(CHARSET)),CHARSET);
    }

    public static void main(String[] args) {
        Base64Util  base = new Base64Util();
        String encodedMessage = base.encodeString("chnologically connected world. Today we may think we have many “friends.” It is true: we do enjoy the ability to be informed and to stay current with what is happening in the lives of many of our acquaintances as well as current and former friends and even people we have not met personally whom we call our friends.");
        System.out.println(encodedMessage);
        System.out.println(base.decodeString(encodedMessage));
    }
}
