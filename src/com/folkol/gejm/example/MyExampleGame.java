package com.folkol.gejm.example;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.VolatileImage;
import java.io.IOException;
import java.util.Random;

import com.folkol.gejm.Game;

public class MyExampleGame extends Game {

    private VolatileImage image;

    @Override
    public void init() {
        super.init();
        image = loadImage("resources/images/test.png");
    };

    @Override
    protected void renderFrame(Rectangle bounds, Graphics g) throws FontFormatException, IOException {
        // Draw scene
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, bounds.width, bounds.height);

        int frame = (int) (System.currentTimeMillis() / 100 % 6);
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            int x = random.nextInt((int) bounds.getWidth() - 50);
            int y = random.nextInt((int) bounds.getHeight() - 100);
            
            g.drawImage(image, x, y, x + 50, y + 100, 51 * frame, 0, 51 + 51 * frame, 100, null);
        }

        g.setColor(Color.ORANGE);
        g.drawString("Hello World!", 50, 50);
        g.drawString("This is some example text", 50, 100);
        g.drawString("lorem ipsum dolore  lorem ipsum dolore  lorem ipsum dolore  lorem"
                + " ipsum dolore  lorem ipsum dolore  lorem ipsum dolore "
                + " lorem ipsum dolore  lorem ipsum dolore !", 50, 120);
        g.drawString("Window size: " + bounds.width + ", " + bounds.height, 50, 140);

        // Draw HUD
    }

    public static void main(String[] args) {

        MyExampleGame myExampleGame = new MyExampleGame();
        myExampleGame.init();
        myExampleGame.run();
        System.out.println("Hej d�!");
    }

}