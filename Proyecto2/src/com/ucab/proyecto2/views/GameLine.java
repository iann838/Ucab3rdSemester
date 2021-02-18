package com.ucab.proyecto2.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class GameLine extends JLabel {

    private boolean left;
    private int level;
    private int index;

    public GameLine(int level, int index, boolean left) {
        super();
        this.left = left;
        this.level = level;
        this.index = index;
        setBounds(getIndexX(), getIndexY(), (int) getLevelWidth(), getLevelHeight());
    }

    public int getLevelHeight() {
        if (level == 1)
            return 40;
        if (level == 2)
            return 50;
        if (level == 3)
            return 60;
        if (level == 4)
            return 80;
        return 0;
    }

    public int getLevelHeight(int level) {
        if (level == 1)
            return 40;
        if (level == 2)
            return 50;
        if (level == 3)
            return 60;
        if (level == 4)
            return 80;
        return 0;
    }

    public double getLevelWidth() {
        if (level == 1)
            return 260;
        if (level == 2)
            return 130;
        if (level == 3)
            return 65;
        if (level == 4)
            return 32.5;
        return 0;
    }

    public int getIndexX() {
        double pilot = getLevelWidth();
        return (int) (pilot + pilot * (index + (index / 2) * 2));
    }

    public int getIndexY() {
        int pilot = 0;
        for (int i = level-1; i > 0; i--) {
            pilot += getLevelHeight(i);
        }
        return 120 + pilot + 15;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        if (left) {
            g.drawLine(0, getHeight(), getWidth(), 0);
        } else {
            g.drawLine(0, 0, getWidth(), getHeight());
        }
        g.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getWidth(), getHeight());
    }

}
