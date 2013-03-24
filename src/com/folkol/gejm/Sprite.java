package com.folkol.gejm;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

    private VolatileImage image;
    private final String name;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public Sprite(String filename, int x, int y, int width, int height) {
        this.name = filename;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        loadImage();
    }

    public void draw(Graphics2D g, Rectangle src, Rectangle dest) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        GraphicsConfiguration gc = device.getDefaultConfiguration();

        do {
            if (image.validate(gc) != VolatileImage.IMAGE_OK) {
                loadImage();
            }
            g.setComposite(AlphaComposite.Src);
            // g.drawImage(image, dest.x, dest.y, dest.x + dest.width, dest.y +
            // dest.height, src.x, src.y, src.x
            // + src.width, src.y + src.height, null);
            g.drawImage(image, dest.x, dest.y, null);

        } while (image.contentsLost());
    }

    // public static VolatileImage createVolatileImage(int width, int height,
    // int transparency) {
    // GraphicsEnvironment ge =
    // GraphicsEnvironment.getLocalGraphicsEnvironment();
    // GraphicsConfiguration gc =
    // ge.getDefaultScreenDevice().getDefaultConfiguration();
    // VolatileImage image = null;
    //
    // image = gc.createCompatibleVolatileImage(width, height, transparency);
    //
    // int valid = image.validate(gc);
    //
    // if (valid == VolatileImage.IMAGE_INCOMPATIBLE) {
    // image = createVolatileImage(width, height, transparency);
    // }
    // // System.out.println(ImageBank.class.getSimpleName() +
    // // ": created new VolatileImage");
    // return image;
    // }
    //
    // public static VolatileImage createTransparentVolatileImage(int width, int
    // height) {
    // VolatileImage image = createVolatileImage(width, height,
    // Transparency.OPAQUE);
    // Graphics2D g = (Graphics2D) image.getGraphics();
    // g.setColor(new Color(0, 0, 0, 0));
    // g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OUT));
    // g.fillRect(0, 0, image.getWidth(), image.getHeight());
    // return image;
    // }

    public void loadImage() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        GraphicsConfiguration gc = device.getDefaultConfiguration();

        BufferedImage imageFromFile = null;
        try {
            imageFromFile = ImageIO.read(new File("resources/images/" + name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        VolatileImage vImg = gc.createCompatibleVolatileImage(width, height);
        do {
            if (vImg.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE) {
                vImg = gc.createCompatibleVolatileImage(imageFromFile.getWidth(), imageFromFile.getHeight());
            }

            if (vImg.contentsLost()) {
                System.out.println("Content's lost");
            }
            Graphics2D g = vImg.createGraphics();
            g.setColor(Color.CYAN);
            g.clearRect(0, 0, 50, 50);
            g.drawLine(0, 0, 50, 50);
            // g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
            // 0.7f));
            g.drawImage(imageFromFile, 0, 0, width, height, x, y, x + width, height, null);
            // g.drawImage(imageFromFile, 0, 0, null);

            g.dispose();
        } while (vImg.contentsLost());
        image = vImg;
    }
}