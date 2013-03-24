package com.folkol.gejm.example;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.folkol.gejm.Game;
import com.folkol.gejm.Sprite;

public class MyExampleGame extends Game {

    private Sprite grass;
    private Sprite hero;

    @Override
    public void init() {
        grass = new Sprite("tiles.png", 0, 0, 50, 50);
        hero = new Sprite("hero.png", 0, 0, 50, 100);
    };

    @Override
    protected void renderFrame(Rectangle bounds, Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, bounds.width, bounds.height);

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                grass.draw(g, i * 50, j * 50);
            }
        }

        hero.draw(g, 300, 300);

        g.setColor(Color.ORANGE);
        g.drawString("Hello World!", 50, 50);
        g.drawString("This is some example text", 50, 100);
        g.drawString("lorem ipsum dolore  lorem ipsum dolore  lorem ipsum dolore  lorem"
                + " ipsum dolore  lorem ipsum dolore  lorem ipsum dolore "
                + " lorem ipsum dolore  lorem ipsum dolore !", 50, 120);
        g.drawString("Window size: " + bounds.width + ", " + bounds.height, 50, 140);
    }

    public static void main(String[] args) {
        new MyExampleGame().run();
        System.out.println("Hej då!");
    }

}