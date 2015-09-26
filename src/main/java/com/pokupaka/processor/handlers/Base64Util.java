package com.pokupaka.processor.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * By gekoreed on 9/26/15.
 */
public class Base64Util {

    public static String encode(String fileName) throws IOException {

        File file = new File(fileName);
        byte[] bytes = loadFile(file);
        byte[] encoded = Base64.getEncoder().encode(bytes);

        return new String(encoded);
    }

    public static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int)length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        is.close();
        return bytes;
    }

    public static byte[] decode(String base) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(base);
        return decoded;
    }
}
