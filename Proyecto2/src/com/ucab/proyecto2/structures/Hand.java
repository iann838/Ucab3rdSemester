package com.ucab.proyecto2.structures;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.ucab.proyecto2.views.GameLetterBtn;
import com.ucab.proyecto2.views.GameView;

public class Hand {
    
    private List<GameLetterBtn> letters;
    private GameView context;
    private int counter;

    public Hand(GameView context) {
        this.context = context;
        letters = new ArrayList<GameLetterBtn>();
        counter = 7;
        for (int i = 0; i < 7; i++) {
            GameLetterBtn letter = new GameLetterBtn(".", context, null);
            letter.setBounds(430 + i*50, 520, 30, 30);
            letters.add(letter);
            context.getContentPane().add(letter);
            context.getContentPane().setComponentZOrder(letter, 1);
        }
    }

    public void generateLetters(boolean brandNew) {
        boolean able = true;
        for (int i = 0; i < counter; i++) {
            if (!letters.get(i).isVisible() && !brandNew) {
                able = false;
                break;
            }
        }
        if (!able) {
            JOptionPane.showMessageDialog(context, "Valide o dehazca la palabra en juego.", "Alerta", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!brandNew) {
            if (counter == 0) {
                JOptionPane.showMessageDialog(context, "Ya se termino las oportunidades de cambio.", "Alerta", JOptionPane.ERROR_MESSAGE);
                return;
            }
            counter--;
            context.getLblCounter().setText("Contador: " + Integer.toString(counter));
        }
        if (brandNew) {
            for (GameLetterBtn letter: letters) {
                if (!(letter.getText() == ".") && letter.isVisible()) continue;
                String newLetter = context.getBag().get();
                if (newLetter == null) {
                    return;
                }
                letter.setText(newLetter);
                letter.setVisible(true);
            }
        } else {
            List<String> putBacks = new ArrayList<String>();
            for (int i = 0; i < counter + 1; i++) {
                String newLetter = context.getBag().get();
                if (newLetter == null) {
                    JOptionPane.showMessageDialog(context, "Ya no hay letras en la bolsa.", "Alerta", JOptionPane.ERROR_MESSAGE);
                }
                putBacks.add(letters.get(i).getText());
                letters.get(i).setText(newLetter);
            }
            for (String putBack: putBacks) {
                context.getBag().put(putBack);
            }
        }
    }

    public GameLetterBtn removeByUuid(int uuid) {
        int index = 0;
        for (GameLetterBtn letter: letters) {
            if (letter.getUuid() == uuid) {
                return letters.remove(index);
            }
            index++;
        }
        return null;
    }

    public boolean isFromHand(int uuid) {
        for (GameLetterBtn letter: letters) {
            if (letter.getUuid() == uuid) {
                return true;
            }
        }
        return false;
    }

    public GameView getContext() {
        return context;
    }

    public int getCounter() {
        return counter;
    }

    public List<GameLetterBtn> getLetters() {
        return letters;
    }

    public void setLetters(List<GameLetterBtn> letters) {
        this.letters = letters;
    }

    public void setCounter(int counter) {
        this.counter = counter;
        context.getLblCounter().setText("Contador: " + Integer.toString(counter));
    }

}
