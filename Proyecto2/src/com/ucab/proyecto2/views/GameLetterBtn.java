package com.ucab.proyecto2.views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.ucab.proyecto2.structures.Stage.AlreadyExist;

@SuppressWarnings("serial")
public class GameLetterBtn extends JLabel {

    private Image backgroundImage;
    private GameLine line;
    private GameView context;
    private int level;
    private int uuid;
    private boolean isSpecial;

    public GameLetterBtn(String letter, GameView context, GameLine line) {
        super(letter);
        this.context = context;
        this.line = line;
        this.isSpecial = false;
        uuid = (int) (Math.random() * Integer.MAX_VALUE);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setForeground(Color.WHITE);
        GameLetterBtn this_ = this;
        addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
                if (getText().equals("*")) {
                    String str = JOptionPane.showInputDialog(context, "Letra a cambiar");
                    if (str == null) {
                        return;
                    }
                    str = str.toUpperCase();
                    if (!context.getBag().getRegistry().contains(str)) {
                        JOptionPane.showMessageDialog(context, "No es una letra valida a cambiar.", "Alerta", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    isSpecial = true;
                    setText(str);
                }
                try {
                    getContext().getStage().add(this_);
                } catch (AlreadyExist er) {
                    JOptionPane.showMessageDialog(context, "Ya esta letra está en juego.", "Alerta", JOptionPane.ERROR_MESSAGE);
                }
			}
		});
        setFont(new Font("Gill Sans", Font.BOLD, 14));
    try {
        backgroundImage = ImageIO.read(new File("letterbg.png"));
    } catch (IOException e) { e.printStackTrace(); } }

    public void paintComponent(Graphics g) {
        backgroundImage = backgroundImage.getScaledInstance(getWidth(), getHeight(), BufferedImage.SCALE_DEFAULT);
        g.drawImage(backgroundImage, 0, 0, this);
        super.paintComponent(g);
    }

    public int getUuid() {
        return uuid;
    }

    public GameView getContext() {
        return context;
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (line != null)
            line.setVisible(aFlag);
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        if (context != null && context.getHand().isFromHand(uuid)) return;
        if (text.length() == 0) {
            setVisible(false);
        } else {
            setVisible(true);
        }
    }

    public static Image getIcon(String path, int w, int h) {
        try {
            Image img = ImageIO.read(new File(path));
            return img.getScaledInstance(w, h, BufferedImage.SCALE_DEFAULT);
        } catch (IOException e) { e.printStackTrace(); }
        return null;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean getIsSpecial() {
        return isSpecial;
    }

    public void setSpecial(boolean isSpecial) {
        this.isSpecial = isSpecial;
    }

}
