package com.folkol.gejm;

import java.awt.FontFormatException;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Game implements KeyListener {

    public void init() {
        int numBuffers = 2;
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        gc = device.getDefaultConfiguration();
        mainFrame = new Frame(gc);
        mainFrame.setUndecorated(true);
        mainFrame.setIgnoreRepaint(true);
        device.setFullScreenWindow(mainFrame);
        mainFrame.createBufferStrategy(numBuffers);
        mainFrame.addKeyListener(this);
        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent winEv) {
                running = false;
            }
        });
    }

    public VolatileImage loadImage(String string) {
        BufferedImage imageFromFile = null;
        try {
            imageFromFile = ImageIO.read(new File(string));
        } catch (IOException e) {
            e.printStackTrace();
        }
        VolatileImage vImg = gc.createCompatibleVolatileImage(imageFromFile.getWidth(), imageFromFile.getHeight());
        do {
            if (vImg.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE) {
                vImg = gc.createCompatibleVolatileImage(imageFromFile.getWidth(), imageFromFile.getHeight());
            }
            Graphics2D g = vImg.createGraphics();
            g.drawImage(imageFromFile, 0, 0, null);

            g.dispose();
        } while (vImg.contentsLost());
        return vImg;
    }

    public void run() {
        mainFrame.setVisible(true);

        BufferStrategy strategy = mainFrame.getBufferStrategy();
        try {
            while (running) {
                long currentTimeMillis = System.currentTimeMillis();
                do {
                    do {
                        Graphics graphics = strategy.getDrawGraphics();
                        renderFrame(mainFrame.getBounds(), graphics);
                        graphics.dispose();
                    } while (strategy.contentsRestored());

                    strategy.show();
                } while (strategy.contentsLost());
                long elapsed = System.currentTimeMillis() - currentTimeMillis;
                if (elapsed == 0) {
                    elapsed = 1;
                }
                System.out.println("FPS: " + 1 / (elapsed / 1000.0f));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainFrame.setVisible(false);
        mainFrame.dispose();
    }

    protected Frame mainFrame;
    protected boolean running = true;
    private GraphicsConfiguration gc;

    abstract protected void renderFrame(Rectangle bounds, Graphics g) throws FontFormatException, IOException;

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent arg0) {
        if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
            mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
        }
    }
}
