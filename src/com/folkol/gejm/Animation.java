package com.folkol.gejm;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Animation {

    long started;
    int speed;
    List<Sprite> frames = new ArrayList<Sprite>();

    public Animation() {
        started = System.currentTimeMillis();
        speed = 100;
    }

    public void addFrame(Sprite frame) {
        frames.add(frame);
    }
    
    public void draw(Graphics2D g, int x, int y) {
        long elapsed = (System.currentTimeMillis() - started);
        int frame = (int) ((elapsed / speed) % frames.size());
        frames.get(frame).draw(g, x, y);
    }
}
