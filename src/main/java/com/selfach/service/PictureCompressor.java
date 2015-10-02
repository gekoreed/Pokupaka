package com.selfach.service;

import org.springframework.stereotype.Service;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

/**
 * By gekoreed on 10/2/15.
 */
@Service
public class PictureCompressor {

    private static final int IMG_WIDTH = 530;
    private static final int IMG_HEIGHT = 300;

    public boolean compress(File img) {

        File compressedImageFile = new File("pictures/c/" + img.getName());

        InputStream is = null;
        try {
            is = new FileInputStream(img);

            OutputStream os = new FileOutputStream(compressedImageFile);

            float quality = 0.3f;
            BufferedImage image = ImageIO.read(is);

            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");

            if (!writers.hasNext())
                throw new IllegalStateException("No writers found");

            ImageWriter writer = writers.next();
            ImageOutputStream ios = ImageIO.createImageOutputStream(os);
            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);

            writer.write(null, new IIOImage(image, null, null), param);

            is.close();
            os.close();
            ios.close();
            writer.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    public BufferedImage resizeImage(File img) {
        BufferedImage resizedImage = null;
        try {
            BufferedImage image = ImageIO.read(img);
            resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(image, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
            g.dispose();

            ImageIO.write(resizedImage, "jpg", new File("pictures/c/"+img.getName()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resizedImage;
    }

}
