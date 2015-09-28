package com.selfach.processor.handlers;

import java.io.*;
import java.util.Base64;

/**
 * By gekoreed on 9/26/15.
 */
public class Base64Util {

    public static String encode(String fileName) {

        File file = new File(fileName);
        byte[] bytes = loadFile(file);
        byte[] encoded = Base64.getEncoder().encode(bytes);

        return new String(encoded);
    }

    public static byte[] loadFile(File file) {
        byte[] bytes = null;
        try {
            InputStream is = new FileInputStream(file);

            long length = file.length();

            bytes = new byte[(int) length];

            int offset = 0;
            int numRead;
            while (offset < bytes.length
                    && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            if (offset < bytes.length) {
                throw new IOException("Could not completely read file " + file.getName());
            }

            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    public static byte[] decode(String base) throws Exception {
        return Base64.getDecoder().decode(base);
    }
}
