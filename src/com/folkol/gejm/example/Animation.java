package com.folkol.gejm.example;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Animation {

    private static final int _800 = 800;
    private static final int _1600 = 1600;
    VolatileImage image;
    int frame;
    Random random = new Random();
    private int x, y, dx, dy;
    private String name;
    
    public Animation(String name) {
        this.name = name;
        VolatileImage image = loadImage();
        this.image = image;
        x = random.nextInt(_1600);
        y = random.nextInt(_800);
        dx = random.nextInt(20) - 10;
        dy = random.nextInt(20) - 10;
    }
    
    public VolatileImage loadImage() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        GraphicsConfiguration gc = device.getDefaultConfiguration();

        BufferedImage imageFromFile = null;
        try {
            imageFromFile = ImageIO.read(new File("resources/images/" + name + "/test.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        VolatileImage vImg = gc.createCompatibleVolatileImage(imageFromFile.getWidth(), imageFromFile.getHeight(), VolatileImage.TRANSLUCENT);
        do {
            if (vImg.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE) {
                System.out.println("Image incompatible");
                vImg = gc.createCompatibleVolatileImage(imageFromFile.getWidth(), imageFromFile.getHeight());
            }
            
            Graphics2D g = vImg.createGraphics();
            
            g.setComposite(AlphaComposite.Src);
            g.setColor(Color.BLACK);
            g.clearRect(0, 0, vImg.getWidth(), vImg.getHeight()); // Clears the image.
         
            g.drawImage(imageFromFile, 0, 0, null);

            g.dispose();
        } while (vImg.contentsLost());
        return vImg;
    }

    
    public void update() {
        frame = random.nextInt(7); 
        x += dx;
        y += dy;
        if (x < 0) {
            x = 0;
            dx = 1 + random.nextInt(10);
        }
        if(x > _1600) {
            x = _1600;
            dx = -(1 + random.nextInt(10));
        }
        if(y < 0) {
            y = 0;
            dy = 1 + random.nextInt(10);
        }
        if(y > _800) {
            y = _800;
            dy = -(1 + random.nextInt(10));
        }
    }
    
    public void draw(Graphics2D g) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        GraphicsConfiguration gc = device.getDefaultConfiguration();
        
        do {
            if(image.validate(gc) != VolatileImage.IMAGE_OK) {
                image = loadImage();
            }
            g.setComposite(AlphaComposite.Src);
            g.drawImage(image, x, y, x + 50, y + 100, 51 * frame, 0, 51 + 51 * frame, 100, null);
        } while (image.contentsLost());
    }

}
