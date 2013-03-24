package com.folkol.gejm.example;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.folkol.gejm.Game;

public class MyExampleGame extends Game {

    private final List<AnimatedSprite> animations = new ArrayList<AnimatedSprite>();
    private final List<AnimatedSprite> tiles = new ArrayList<AnimatedSprite>();

    @Override
    public void init() {
        super.init();
        for (int i = 0; i < 1; i++) {
            animations.add(loadAnimation("hero.png", 0, 0, 50, 100));
        }

        Random random = new Random();
        for (int i = 0; i < 24; i++) {
            for (int y = 0; y < 20; y++) {
                tiles.add(loadAnimation("tiles.png", (random.nextInt(3)) * 50, 0, 50, 50));
            }
        }
    };

    @Override
    protected void renderFrame(Rectangle bounds, Graphics2D g) throws IOException {
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, bounds.width, bounds.height);

        Collections.sort(animations, new Comparator<AnimatedSprite>() {
            @Override
            public int compare(AnimatedSprite a1, AnimatedSprite a2) {
                return a1.y - a2.y;
            }
        });

        for (AnimatedSprite t : tiles) {
            t.draw(g);
        }

        for (AnimatedSprite a : animations) {
            a.update();
            a.draw(g);
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
        System.out.println("Hej då!");
    }

}