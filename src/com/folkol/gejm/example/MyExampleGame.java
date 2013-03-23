package com.folkol.gejm.example;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.folkol.gejm.Game;

public class MyExampleGame extends Game {

    private List<Animation> animations = new ArrayList<Animation>();

    @Override
    public void init() {
        super.init();
        for (int i = 0; i < 5; i++) {
            animations.add(loadAnimation("hero"));
        }
    };

    @Override
    protected void renderFrame(Rectangle bounds, Graphics2D g) throws FontFormatException, IOException {
        // Draw scene
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, bounds.width, bounds.height);

        for (Animation a : animations) {
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