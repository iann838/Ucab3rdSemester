package com.ucab.proyecto2.structures;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.ucab.proyecto2.views.GameLetterBtn;
import com.ucab.proyecto2.views.GameView;

public class Stage {

    private List<GameLetterBtn> letters;
    private Node<GameLetterBtn> lastNode;
    private Node<GameLetterBtn> firstNode;
    private List<String> validated;
    private int points;
    private GameView context;

    @SuppressWarnings("serial")
    public static class AlreadyExist extends Exception {
        public AlreadyExist(String errorMessage) {
            super(errorMessage);
        }
    }

    public static void main(String[] args) {
        System.out.println(spellcheck("hola"));
        System.out.println(spellcheck("holau"));
    }

    public Stage(GameView context) {
        letters = new ArrayList<GameLetterBtn>();
        this.context = context;
        this.validated = new ArrayList<String>();
        this.points = 0;
    }

    
	public static void preorderGet(List<Node<GameLetterBtn>> assign, GameLetterBtn letter, Node<GameLetterBtn> node, int level) {
		if (node == null)
            return;
        node.getData().setLevel(level);
        if (assign.size() == 0 && node.getData().getUuid() == letter.getUuid())
            assign.add(node);
        // traverse the left child
        level += 1;
		preorderGet(assign, letter, node.getLeft(), level);
		// traverse the right child
		preorderGet(assign, letter, node.getRight(), level);
	}

    public Node<GameLetterBtn> getNodebyUuid(GameLetterBtn letter) {
        List<Node<GameLetterBtn>> assign = new ArrayList<Node<GameLetterBtn>>();
        preorderGet(assign, letter, context.getTree().getRoot(), 0);
        return assign.get(0);
    }

    public void add(GameLetterBtn letter) throws AlreadyExist {
        if (letters.size() >= 5) {
            JOptionPane.showMessageDialog(context, "Has llegado al limite del arbol.", "Alerta", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean isFromHand = context.getHand().isFromHand(letter.getUuid());
        if (validated.size() > 0) {
            if (letters.size() == 0) {
                if (isFromHand) {
                    JOptionPane.showMessageDialog(context, "La palabra debe comenzar desde el arbol.", "Alerta", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                lastNode = getNodebyUuid(letter);
                firstNode = lastNode;
            } else if (!isFromHand) {
                if (context.getHand().isFromHand(lastNode.getData().getUuid())) {
                    JOptionPane.showMessageDialog(context, "La ultima letra fue de la mano.", "Alerta", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!context.getHand().isFromHand(lastNode.getData().getUuid())) {
                    if (lastNode.getLeft().getData().getUuid() == letter.getUuid())
                        lastNode = lastNode.getLeft();
                    else if (lastNode.getRight().getData().getUuid() == letter.getUuid())
                        lastNode = lastNode.getRight();
                    else {
                        JOptionPane.showMessageDialog(context, "Esta letra no es hijo de la ultima letra.", "Alerta", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            } else {
                if (!lastNode.getLeft().getData().getText().equals("") && !lastNode.getRight().getData().getText().equals("")) {
                    JOptionPane.showMessageDialog(context, "La ultima letra en juego es del arbol y no tiene espacios.", "Alerta", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            if (letters.size() >= 5 - firstNode.getData().getLevel()) {
                JOptionPane.showMessageDialog(context, "Has llegado al limite del arbol.", "Alerta", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        for (GameLetterBtn inserted: letters) {
            if (letter.getUuid() == inserted.getUuid()) {
                throw new AlreadyExist(letter.getText());
            }
        }
        if (isFromHand)
            letter.setVisible(false);
        letters.add(letter);
        context.getLblStage().setText(toString());
    }

    public void undo() {
        if (toString().length() == 0) {
            JOptionPane.showMessageDialog(context, "No hay nada que deshacer.", "Alerta", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (GameLetterBtn inserted: letters) {
            inserted.setVisible(true);
            if (inserted.getIsSpecial()) {
                inserted.setText("*");
                inserted.setSpecial(false);
            }
        }
        letters.clear();
        lastNode = null;
        firstNode = null;
        context.getLblStage().setText(toString());
    }

    public static boolean spellcheck(String word) { try {
        URL url = new URL("https://api.paaksing.com/ucab/spellcheck/es");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "text/plain");
        con.setRequestProperty("Accept", "text/plain");
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = word.getBytes();
            os.write(input, 0, input.length);
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String response = "";
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response += inputLine;
        }
        in.close();
        if (response == "") return true;
        return false;
    } catch (Exception e) { e.printStackTrace(); } return false; }

    public void commit() {
        String raw = toRawString();
        GameLetterBtn first = null;
        if (!spellcheck(raw)) {
            JOptionPane.showMessageDialog(context, "Esta palabra no existe.", "Alerta", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (letters.size() <= 1) {
            JOptionPane.showMessageDialog(context, "Necesita al menos 2 letras para validar.", "Alerta", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (validated.contains(raw)) {
            JOptionPane.showMessageDialog(context, "Ya esta palabra esta jugada.", "Alerta", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Node<GameLetterBtn> root = context.getTree().getRoot();
        if (root.getData().getText().equals("")) {
            first = root.getData();
            root.getData().setText(letters.remove(0).getText());
            Node<GameLetterBtn> element = root;
            for (GameLetterBtn letter: letters) {
                element.getLeft().getData().setText(letter.getText());
                element = element.getLeft();
            }
        } else {
            Node<GameLetterBtn> element = getNodebyUuid(letters.get(0));
            first = element.getData();
            letters.remove(0);
            for (GameLetterBtn letter: letters) {
                if (!context.getHand().isFromHand(letter.getUuid())) {
                    if (element.getLeft().getData().getUuid() == letter.getUuid())
                        element = element.getLeft();
                    else if (element.getRight().getData().getUuid() == letter.getUuid())
                        element = element.getRight();
                    else {
                        JOptionPane.showMessageDialog(context, "Hubo un error.", "Alerta", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    if (element.getRight().getData().getText().equals("")) {
                        element = element.getRight();
                        element.getData().setText(letter.getText());
                    } else if (element.getLeft().getData().getText().equals("")) {
                        element = element.getLeft();
                        element.getData().setText(letter.getText());
                    } else {
                        JOptionPane.showMessageDialog(context, "Hubo un error.", "Alerta", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
        }
        validated.add(raw);
        context.getLblAsserts().setText("Palabras jugadas: " + Integer.toString(validated.size()));
        points += (int) (((((raw.length() - 1) / 2.0) * (first.getLevel() + raw.length()))) * 2) + 1;
        context.getLblNumeroPuntuacionActual().setText(Integer.toString(points));
        lastNode = null;
        firstNode = null;
        letters.clear();
        context.getLblStage().setText(toString());
        context.getHand().generateLetters(true);
    }

    public String toString() {
        String str = "";
        for (GameLetterBtn inserted: letters) {
            str += inserted.getText() + " ";
        }
        return str;
    }

    public String toRawString() {
        String str = "";
        for (GameLetterBtn inserted: letters) {
            str += inserted.getText();
        }
        return str;
    }

    public GameView getContext() {
        return context;
    }

    public List<String> getValidated() {
        return validated;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
        context.getLblNumeroPuntuacionActual().setText(Integer.toString(points));
    }

    public void setValidated(List<String> validated) {
        this.validated = validated;
        context.getLblAsserts().setText("Palabras jugadas: " + Integer.toString(validated.size()));
    }

}
