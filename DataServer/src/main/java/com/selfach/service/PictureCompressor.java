package com.selfach.service;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * By gekoreed on 10/2/15.
 */
@Service
public class PictureCompressor {

    private static final int IMG_WIDTH = 530;
    private static final int IMG_HEIGHT = 300;


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
