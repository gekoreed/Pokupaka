package com.selfach.processor.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * By gekoreed on 9/26/15.
 */
public class FileUtil {

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
}
