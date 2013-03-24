package com.folkol.gejm;

import java.awt.Color;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.folkol.gejm.example.AnimatedSprite;

public abstract class Game implements KeyListener {

    private final Map<String, Sprite> images = new HashMap<String, Sprite>();

    public void init() {
        int numBuffers = 3;
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        gc = device.getDefaultConfiguration();
        mainFrame = new Frame(gc);
        mainFrame.setUndecorated(true);
        mainFrame.setIgnoreRepaint(true);
        device.setFullScreenWindow(mainFrame);
        mainFrame.createBufferStrategy(numBuffers);

        System.out.println(mainFrame.getBufferStrategy());
        mainFrame.addKeyListener(this);
        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent winEv) {
                running = false;
            }
        });
    }

    public AnimatedSprite loadAnimation(String name, int x, int y, int width, int height) {
        Sprite i = null;// = images.get(name);
        if (i == null) {
            i = new Sprite(name, x, y, width, height);
            // images.put(name, i);
        }

        AnimatedSprite a = new AnimatedSprite(this, i);
        return a;
    }

    public void run() {
        mainFrame.setVisible(true);

        BufferStrategy strategy = mainFrame.getBufferStrategy();
        try {
            while (running) {
                do {
                    do {
                        Graphics2D graphics = (Graphics2D) strategy.getDrawGraphics();
                        renderFrame(mainFrame.getBounds(), graphics);
                        showFPS(graphics);
                        graphics.dispose();
                    } while (strategy.contentsRestored());
                    strategy.show();
                } while (strategy.contentsLost());
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
    private long lastTime;
    private final double[] fps = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    abstract protected void renderFrame(Rectangle bounds, Graphics2D g) throws FontFormatException, IOException;

    private void showFPS(Graphics g) {
        int x = 30;
        int y = 50;

        double elapsed = (System.currentTimeMillis() - lastTime) / 1000.0;
        if (elapsed == 0)
            elapsed = 1;
        double fps = 1.0 / elapsed;
        for (int i = 0; i < this.fps.length - 1; i++) {
            fps += this.fps[i];
            this.fps[i] = this.fps[i + 1];
        }
        fps /= this.fps.length;
        this.fps[this.fps.length - 1] = fps;

        lastTime = System.currentTimeMillis();

        g.setColor(Color.WHITE);
        g.fillRect(x, y, 75, 20);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, 75, 20);
        g.drawString(String.format("FPS: %3.0f", fps), x + 15, y + 15);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
            mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
        }
    }
}
