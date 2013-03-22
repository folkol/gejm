package com.folkol.gejm;

import java.awt.FontFormatException;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;

public abstract class Game implements KeyListener {
    
    public Game() {
        int numBuffers = 2;
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        GraphicsConfiguration gc = device.getDefaultConfiguration();
        mainFrame = new Frame(gc);
        mainFrame.setUndecorated(true);
        mainFrame.setIgnoreRepaint(true);
        device.setFullScreenWindow(mainFrame);
        mainFrame.createBufferStrategy(numBuffers);
        mainFrame.addKeyListener(this);
        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent winEv) {
                running = false;
                mainFrame.setVisible(false);
                mainFrame.dispose();
            }
        });
    }
    
    public void run() {
        try {
            while(running) {
                long before = System.currentTimeMillis();
                BufferStrategy bufferStrategy = mainFrame.getBufferStrategy();
                if(bufferStrategy == null) continue;
                Graphics g = bufferStrategy.getDrawGraphics();
                if (!bufferStrategy.contentsLost()) {
                    renderFrame(mainFrame.getBounds(), g);
                    bufferStrategy.show();
                    long elapsed = System.currentTimeMillis() - before;
                    System.out.println("FPS: " + 1 / (elapsed / 1000.0f));
                    g.dispose();
                } else {
                    System.out.println("Content's lost");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected Frame mainFrame;
    protected boolean running = true;

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
