package com.folkol.gejm.example;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import com.folkol.gejm.Animation;
import com.folkol.gejm.Game;
import com.folkol.gejm.Sprite;

public class MyExampleGame extends Game {

    private Sprite grass;
    private Animation hero;
    private long lastTime = System.currentTimeMillis();

    @Override
    public void init() {
        grass = new Sprite("tiles.png", 0, 0, 50, 50);

        hero = new Animation();
        for (int i = 0; i < 7; i++) {
            hero.addFrame(new Sprite("hero.png", i * 51, 0, 50, 100));
        }
    };

    @Override
    protected void renderFrame(Rectangle bounds, Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, bounds.width, bounds.height);

        for (int i = 0; i < 34; i++) {
            for (int j = 0; j < 24; j++) {
                grass.draw(g, i * 50, j * 50);
            }
        }

        Random random = new Random();
        for (int i = 0; i < 500; i++) {
            hero.draw(g, random.nextInt(bounds.width), random.nextInt(bounds.height));
        }

        hero.draw(g, 100, 100);

        g.setColor(Color.ORANGE);
        g.drawString("Hello World!", 50, 50);
        g.drawString("This is some example text", 50, 100);
        g.drawString("lorem ipsum dolore  lorem ipsum dolore  lorem ipsum dolore  lorem"
                + " ipsum dolore  lorem ipsum dolore  lorem ipsum dolore "
                + " lorem ipsum dolore  lorem ipsum dolore !", 50, 120);
        g.drawString("Window size: " + bounds.width + ", " + bounds.height, 50, 140);

        drawFPS(g);
    }

    private void drawFPS(Graphics2D g) {
        long elapsed = System.currentTimeMillis() - lastTime;
        if(elapsed == 0) {
            elapsed = 1;
        }
        double fps = 1000 / (elapsed);

        g.clearRect(5, 5, 65, 30);
        g.setColor(Color.BLACK);
        g.drawRect(5, 5, 65, 30);
        g.drawString(String.format("FPS: %3.0f", fps), 15, 25);
        lastTime = System.currentTimeMillis();
    }

    public static void main(String[] args) {
        new MyExampleGame().run();
        System.out.println("Hej då!");
    }


}