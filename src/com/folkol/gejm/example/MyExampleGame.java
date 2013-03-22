package com.folkol.gejm.example;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;

import com.folkol.gejm.Game;

public class MyExampleGame extends Game {

    @Override
    protected void renderFrame(Rectangle bounds, Graphics g) throws FontFormatException, IOException {
        // Draw scene
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, bounds.width, bounds.height);
        g.setColor(Color.WHITE);
        g.drawString("Hello World!", 50, 50);
        g.drawString("This is some example text", 50, 100);
        g.drawString("lorem ipsum dolore  lorem ipsum dolore  lorem ipsum dolore  lorem"
                + " ipsum dolore  lorem ipsum dolore  lorem ipsum dolore "
                + " lorem ipsum dolore  lorem ipsum dolore !", 50, 120);
        g.drawString("Window size: " + bounds.width + ", " + bounds.height, 50, 140);

        // Draw HUD
    }

    public static void main(String[] args) {
        new MyExampleGame().run();
        System.out.println("Hej då!");
    }

}