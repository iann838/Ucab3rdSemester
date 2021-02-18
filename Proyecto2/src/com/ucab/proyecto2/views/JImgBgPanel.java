package com.ucab.proyecto2.views;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class JImgBgPanel extends JPanel {
    private BufferedImage backgroundImage;

    // Some code to initialize the background image.
    // Here, we use the constructor to load the image. This
    // can vary depending on the use case of the panel.
    public JImgBgPanel(String fileName) { try {
        backgroundImage = ImageIO.read(new File(fileName));
    } catch (IOException e) { e.printStackTrace(); } }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    
        // Draw the background image.
        g.drawImage(backgroundImage, 0, 0, this);
    }
}
