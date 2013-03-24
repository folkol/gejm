package com.folkol.gejm;

import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public abstract class Game {

    private static final int NUM_BUFFERS = 3;

    protected Frame mainFrame;
    protected boolean running = true;

    abstract protected void renderFrame(Rectangle bounds, Graphics2D g);

    abstract protected void init();

    public Game() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        mainFrame = new Frame(device.getDefaultConfiguration());
        mainFrame.setUndecorated(true);
        mainFrame.setIgnoreRepaint(true);
        device.setFullScreenWindow(mainFrame);
        mainFrame.createBufferStrategy(NUM_BUFFERS);

        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent winEv) {
                running = false;
            }
        });

    }

    final public void run() {
        init();

        BufferStrategy strategy = mainFrame.getBufferStrategy();
        try {
            while (running) {
                // Read input ?
                // Update world
                // Render frame
                do {
                    do {
                        Graphics2D graphics = (Graphics2D) strategy.getDrawGraphics();
                        renderFrame(mainFrame.getBounds(), graphics);
                        graphics.dispose();
                    } while (strategy.contentsRestored());
                    strategy.show();
                } while (strategy.contentsLost());
            }
        } catch (Exception e) {
            System.err.println("GEJM failed :-(");
        }

        mainFrame.setVisible(false);
        mainFrame.dispose();
    }

}
