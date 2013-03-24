package com.folkol.gejm.example;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import com.folkol.gejm.Game;
import com.folkol.gejm.Sprite;

public class AnimatedSprite {

    private static final int _800 = 800;
    private static final int _1600 = 1600;
    int frame;
    Random random = new Random();
    public int x, y, dx, dy;
    Game game;
    String name;
    Sprite texture;

    public AnimatedSprite(Game game, Sprite texture) {
        this.game = game;
        this.texture = texture;
        x = random.nextInt(_1600);
        y = random.nextInt(_800);
        dx = 1 + random.nextInt(4);
        dy = 1 + random.nextInt(4);
    }

    public void update() {
        frame = random.nextInt(7);
        x += dx;
        y += dy;
        if (x < 0) {
            x = 0;
            dx = 2 + random.nextInt(2);
        }
        if (x > _1600) {
            x = _1600;
            dx = -(2 + random.nextInt(2));
        }
        if (y < 0) {
            y = 0;
            dy = 2 + random.nextInt(2);
        }
        if (y > _800) {
            y = _800;
            dy = -(2 + random.nextInt(2));
        }
    }

    public void draw(Graphics2D g) {
        texture.draw(g, new Rectangle(0, 0, 50, 100), new Rectangle(x, y, x + 50, y + 50));
    }
}
