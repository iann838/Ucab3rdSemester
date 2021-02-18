package com.ucab.proyecto2.views;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucab.proyecto2.django.Validator.ValidationError;
import com.ucab.proyecto2.models.User;
import com.ucab.proyecto2.structures.Bag;
import com.ucab.proyecto2.structures.Hand;
import com.ucab.proyecto2.structures.Node;
import com.ucab.proyecto2.structures.Stage;
import com.ucab.proyecto2.structures.Tree;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Image;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

@SuppressWarnings("serial")
public class GameView extends JFrame {

	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private JPanel contentPane;
	private Bag<String> bag;
	private Tree<GameLetterBtn> tree;
	private Hand hand;
	private Stage stage;
	private JLabel lblStage;
	private JLabel lblCounter;
	private JLabel lblAsserts;
	private JLabel lblNumeroPuntuacionActual;
	private JLabel lblNumeroRecordPalabra;
	private JLabel lblNumeroRecord;
	private JButton btnResumir;
	private int currentPoints = 0;
	private User user;

	public class SavedGame {
		public int asserts;
		public int counter;
		public int points;
		public List<String> validated;
		public List<String> tree;
		public List<String> hand;
		public List<String> bag;
	}

	public static void main(String[] args) {
		User user = new User();
		user.alias = "123456789012345";
		user.assertRecord = 0;
		user.pointRecord = 0;
		user.savedGame = "";
		GameView frame = new GameView(user);
		frame.setVisible(true);
	}

	public GameView(User user) {
		this.user = user;
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		Image miIcono = mipantalla.getImage("icono.jpeg");
		setIconImage(miIcono);
		setTitle("Scrabble");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1050, 616);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		GameView this_ = this;
		JLabel lblUsuario = new JLabel(user.alias);
		lblUsuario.setForeground(new Color(247,249,250));
		lblUsuario.setFont(new Font("Kai", Font.BOLD, 16));
		lblUsuario.setBounds(50, 15, 150, 45);
		contentPane.add(lblUsuario);

		JLabel lblOnline = new JLabel("Logged In - Online");
		lblOnline.setForeground(new Color(32, 82, 26));
		lblOnline.setFont(new Font("Kai", Font.BOLD, 12));
		lblOnline.setBounds(52, 40, 150, 45);
		contentPane.add(lblOnline);

		JLabel lblPuntacionActual = new JLabel("<html><u>Puntaje Actual</u></html>");
		lblPuntacionActual.setForeground(new Color(247,249,250));
		lblPuntacionActual.setFont(new Font("Gill Sans", Font.PLAIN, 14));
		lblPuntacionActual.setBounds(250, 20, 150, 30);
		contentPane.add(lblPuntacionActual);
		
		lblNumeroPuntuacionActual = new JLabel(Integer.toString(currentPoints));
		lblNumeroPuntuacionActual.setForeground(new Color(247, 249, 250));
		lblNumeroPuntuacionActual.setFont(new Font("Gill Sans", Font.BOLD, 14));
		lblNumeroPuntuacionActual.setBackground(new Color(112, 128, 144));
		lblNumeroPuntuacionActual.setBounds(252, 47, 150, 30);
		contentPane.add(lblNumeroPuntuacionActual);
		
		JLabel lblRecordPalabra = new JLabel("<html><u>R\u00E9cord Palabras</u></html>");
		lblRecordPalabra.setForeground(new Color(247,249,250));
		lblRecordPalabra.setFont(new Font("Gill Sans", Font.PLAIN, 14));
		lblRecordPalabra.setBounds(450, 20, 150, 30);
		contentPane.add(lblRecordPalabra);
		
		lblNumeroRecordPalabra = new JLabel(Integer.toString(user.assertRecord));
		lblNumeroRecordPalabra.setForeground(new Color(247, 249, 250));
		lblNumeroRecordPalabra.setFont(new Font("Gill Sans", Font.BOLD, 14));
		lblNumeroRecordPalabra.setBackground(new Color(112, 128, 144));
		lblNumeroRecordPalabra.setBounds(452, 47, 150, 30);
		contentPane.add(lblNumeroRecordPalabra);

		JLabel lblRecord = new JLabel("<html><u>R\u00E9cord Puntaje</u></html>");
		lblRecord.setForeground(new Color(247,249,250));
		lblRecord.setFont(new Font("Gill Sans", Font.PLAIN, 14));
		lblRecord.setBounds(650, 20, 150, 30);
		contentPane.add(lblRecord);
		
		JButton btnPartida = new JButton("Partida Nueva");
		btnPartida.setBackground(new Color(7, 15, 15));
		btnPartida.setForeground(new Color(247, 249, 250));
		btnPartida.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnPartida.setBorder(BorderFactory.createLineBorder(new Color(110, 90, 0), 2));
		btnPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (user.hasUnfinished) {
					if (JOptionPane.showConfirmDialog(this_, "Esto abandonará la partida pausada, ¿ Desea seguir ?", "Alerta", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
						return;
				}
				if (bag == null && hand == null)
					newGame();
				else {
					setVisible(false);
					GameView newgame = new GameView(user);
					newgame.setVisible(true);
					newgame.setLocationRelativeTo(null);
					newgame.newGame();
					dispose();
				}
			}
		});
		
		lblNumeroRecord = new JLabel(Integer.toString(user.pointRecord));
		lblNumeroRecord.setForeground(new Color(247, 249, 250));
		lblNumeroRecord.setFont(new Font("Gill Sans", Font.BOLD, 14));
		lblNumeroRecord.setBackground(new Color(112, 128, 144));
		lblNumeroRecord.setBounds(652, 47, 150, 30);
		contentPane.add(lblNumeroRecord);
		btnPartida.setFont(new Font("Rockwell", Font.BOLD, 14));
		btnPartida.setBounds(832, 30, 150, 35);
		contentPane.add(btnPartida);

		JButton btnSalir = new JButton("Salir");
        btnSalir.setBackground(new Color(7, 15, 15));
		btnSalir.setForeground(new Color(247, 249, 250));
		btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSalir.setBorder(BorderFactory.createLineBorder(new Color(110, 90, 0), 2));
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bag != null && hand != null)
					saveGame();
				setVisible(false);
				LoginView login = new LoginView();
				login.setVisible(true);
				login.setLocationRelativeTo(null);
				dispose();
			}
		});
		btnSalir.setFont(new Font("Rockwell", Font.BOLD, 14));
		btnSalir.setBounds(830, 520, 150, 35);
		contentPane.add(btnSalir);

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (bag != null && hand != null)
					saveGame();
			}
		});

		btnResumir = new JButton("Resumir Partida");
        btnResumir.setBackground(new Color(7, 15, 15));
		btnResumir.setForeground(new Color(247, 249, 250));
		btnResumir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnResumir.setBorder(BorderFactory.createLineBorder(new Color(110, 90, 0), 2));
		btnResumir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resumeGame();
				btnResumir.setVisible(user.hasUnfinished);
			}
		});
		btnResumir.setVisible(user.hasUnfinished);
		btnResumir.setFont(new Font("Rockwell", Font.BOLD, 14));
		btnResumir.setBounds(450, 250, 150, 35);
		contentPane.add(btnResumir);
		
		JButton btnGenerar = new JButton("Cambiar Letras");
		btnGenerar.setBackground(new Color(7, 15, 15));
		btnGenerar.setForeground(new Color(247,249,250));
		btnGenerar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnGenerar.setBorder(BorderFactory.createLineBorder(new Color(110, 90, 0), 2));
		btnGenerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hand != null)
					hand.generateLetters(false);
				else
					JOptionPane.showMessageDialog(this_, "No hay partida en juego.", "Alerta", JOptionPane.ERROR_MESSAGE);
			}
		});
		btnGenerar.setFont(new Font("Rockwell", Font.BOLD, 14));
		btnGenerar.setBounds(40, 520, 180, 35);
		contentPane.add(btnGenerar);

		JButton btnDeshacer = new JButton("Deshacer");
		btnDeshacer.setBackground(new Color(7, 15, 15));
		btnDeshacer.setForeground(new Color(247,249,250));
		btnDeshacer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnDeshacer.setBorder(BorderFactory.createLineBorder(new Color(110, 90, 0), 2));
		btnDeshacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (stage != null)
					stage.undo();
				else
					JOptionPane.showMessageDialog(this_, "No hay partida en juego.", "Alerta", JOptionPane.ERROR_MESSAGE);
			}
		});
		btnDeshacer.setFont(new Font("Rockwell", Font.BOLD, 14));
		btnDeshacer.setBounds(230, 520, 150, 35);
		contentPane.add(btnDeshacer);

		paintBackground();
	}

	public void newGame() {
		bag = new Bag<String>();
		bag.put("A", 11);
		bag.put("E", 11);
		bag.put("O", 8);
		bag.put("S", 7);
		bag.put("I", 6);
		bag.put("U", 6);
		bag.put("N", 5);
		bag.put("L", 4);
		bag.put("R", 4);
		bag.put("T", 4);
		bag.put("C", 4);
		bag.put("D", 4);
		bag.put("G", 2);
		bag.put("M", 3);
		bag.put("B", 3);
		bag.put("P", 2);
		bag.put("F", 2);
		bag.put("H", 2);
		bag.put("V", 2);
		bag.put("Y", 1);
		bag.put("J", 2);
		bag.put("*", 2);
		bag.put("K");
		bag.put("LL");
		bag.put("Ñ");
		bag.put("Q");
		bag.put("RR");
		bag.put("W");
		bag.put("X");
		bag.put("Z");

		tree = new Tree<GameLetterBtn>();
		hand = new Hand(this);
		stage = new Stage(this);

		List<GameLine> lines = new ArrayList<GameLine>();

		lines.add(null);
		for (int i = 1; i < 5; i++) {
			for (int j = 0; j < Math.pow(2, i); j++) {
				GameLine line;
				if (j % 2 == 0) {
					line = new GameLine(i, j, true);
				} else {
					line = new GameLine(i, j, false);
				}
				line.setVisible(false);
				contentPane.add(line);
				contentPane.setComponentZOrder(line, 1);
				lines.add(line);
			}
		}

		GameLetterBtn letter0 = new GameLetterBtn("", this, lines.get(0));
		letter0.setBounds(495, 120, 30, 30);
		contentPane.add(letter0);
		contentPane.setComponentZOrder(letter0, 1);
		tree.setRoot(new Node<GameLetterBtn>(letter0));
		GameLetterBtn letter1 = new GameLetterBtn("", this, lines.get(1));
		letter1.setBounds(245, 160, 30, 30);
		contentPane.add(letter1);
		contentPane.setComponentZOrder(letter1, 1);
		tree.getRoot().setLeft(new Node<GameLetterBtn>(letter1));

		GameLetterBtn letter2 = new GameLetterBtn("", this, lines.get(2));
		letter2.setBounds(765, 160, 30, 30);
		contentPane.add(letter2);
		contentPane.setComponentZOrder(letter2, 1);
		tree.getRoot().setRight(new Node<GameLetterBtn>(letter2));

		GameLetterBtn letter3 = new GameLetterBtn("", this, lines.get(3));
		letter3.setBounds(115, 210, 30, 30);
		contentPane.add(letter3);
		contentPane.setComponentZOrder(letter3, 1);
		tree.getRoot().getLeft().setLeft(new Node<GameLetterBtn>(letter3));
		
		GameLetterBtn letter4 = new GameLetterBtn("", this, lines.get(4));
		letter4.setBounds(375, 210, 30, 30);
		contentPane.add(letter4);
		contentPane.setComponentZOrder(letter4, 1);
		tree.getRoot().getLeft().setRight(new Node<GameLetterBtn>(letter4));
		
		GameLetterBtn letter5 = new GameLetterBtn("", this, lines.get(5));
		letter5.setBounds(635, 210, 30, 30);
		contentPane.add(letter5);
		contentPane.setComponentZOrder(letter5, 1);
		tree.getRoot().getRight().setLeft(new Node<GameLetterBtn>(letter5));
		
		GameLetterBtn letter6 = new GameLetterBtn("", this, lines.get(6));
		letter6.setBounds(895, 210, 30, 30);
		contentPane.add(letter6);
		contentPane.setComponentZOrder(letter6, 1);
		tree.getRoot().getRight().setRight(new Node<GameLetterBtn>(letter6));
		
		GameLetterBtn letter7 = new GameLetterBtn("", this, lines.get(7));
		letter7.setBounds(50, 280, 30, 30);
		contentPane.add(letter7);
		contentPane.setComponentZOrder(letter7, 1);
		tree.getRoot().getLeft().getLeft().setLeft(new Node<GameLetterBtn>(letter7));
		
		GameLetterBtn letter8 = new GameLetterBtn("", this, lines.get(8));
		letter8.setBounds(180, 280, 30, 30);
		contentPane.add(letter8);
		contentPane.setComponentZOrder(letter8, 1);
		tree.getRoot().getLeft().getLeft().setRight(new Node<GameLetterBtn>(letter8));
		
		GameLetterBtn letter9 = new GameLetterBtn("", this, lines.get(9));
		letter9.setBounds(310, 280, 30, 30);
		contentPane.add(letter9);
		contentPane.setComponentZOrder(letter9, 1);
		tree.getRoot().getLeft().getRight().setLeft(new Node<GameLetterBtn>(letter9));
		
		GameLetterBtn letter10 = new GameLetterBtn("", this, lines.get(10));
		letter10.setBounds(440, 280, 30, 30);
		contentPane.add(letter10);
		contentPane.setComponentZOrder(letter10, 1);
		tree.getRoot().getLeft().getRight().setRight(new Node<GameLetterBtn>(letter10));
		
		GameLetterBtn letter11 = new GameLetterBtn("", this, lines.get(11));
		letter11.setBounds(570, 280, 30, 30);
		contentPane.add(letter11);
		contentPane.setComponentZOrder(letter11, 1);
		tree.getRoot().getRight().getLeft().setLeft(new Node<GameLetterBtn>(letter11));
		
		GameLetterBtn letter12 = new GameLetterBtn("", this, lines.get(12));
		letter12.setBounds(700, 280, 30, 30);
		contentPane.add(letter12);
		contentPane.setComponentZOrder(letter12, 1);
		tree.getRoot().getRight().getLeft().setRight(new Node<GameLetterBtn>(letter12));
		
		GameLetterBtn letter13 = new GameLetterBtn("", this, lines.get(13));
		letter13.setBounds(830, 280, 30, 30);
		contentPane.add(letter13);
		contentPane.setComponentZOrder(letter13, 1);
		tree.getRoot().getRight().getRight().setLeft(new Node<GameLetterBtn>(letter13));
		
		GameLetterBtn letter14 = new GameLetterBtn("", this, lines.get(14));
		letter14.setBounds(960, 280, 30, 30);
		contentPane.add(letter14);
		contentPane.setComponentZOrder(letter14, 1);
		tree.getRoot().getRight().getRight().setRight(new Node<GameLetterBtn>(letter14));
		
		GameLetterBtn letter15 = new GameLetterBtn("", this, lines.get(15));
		letter15.setBounds(18, 360, 30, 30);
		contentPane.add(letter15);
		contentPane.setComponentZOrder(letter15, 1);
		tree.getRoot().getLeft().getLeft().getLeft().setLeft(new Node<GameLetterBtn>(letter15));
		
		GameLetterBtn letter16 = new GameLetterBtn("", this, lines.get(16));
		letter16.setBounds(83, 360, 30, 30);
		contentPane.add(letter16);
		contentPane.setComponentZOrder(letter16, 1);
		tree.getRoot().getLeft().getLeft().getLeft().setRight(new Node<GameLetterBtn>(letter16));
		
		GameLetterBtn letter17 = new GameLetterBtn("", this, lines.get(17));
		letter17.setBounds(148, 360, 30, 30);
		contentPane.add(letter17);
		contentPane.setComponentZOrder(letter17, 1);
		tree.getRoot().getLeft().getLeft().getRight().setLeft(new Node<GameLetterBtn>(letter17));
		
		GameLetterBtn letter18 = new GameLetterBtn("", this, lines.get(18));
		letter18.setBounds(213, 360, 30, 30);
		contentPane.add(letter18);
		contentPane.setComponentZOrder(letter18, 1);
		tree.getRoot().getLeft().getLeft().getRight().setRight(new Node<GameLetterBtn>(letter18));
		
		GameLetterBtn letter19 = new GameLetterBtn("", this, lines.get(19));
		letter19.setBounds(278, 360, 30, 30);
		contentPane.add(letter19);
		contentPane.setComponentZOrder(letter19, 1);
		tree.getRoot().getLeft().getRight().getLeft().setLeft(new Node<GameLetterBtn>(letter19));
		
		GameLetterBtn letter20 = new GameLetterBtn("", this, lines.get(20));
		letter20.setBounds(343, 360, 30, 30);
		contentPane.add(letter20);
		contentPane.setComponentZOrder(letter20, 1);
		tree.getRoot().getLeft().getRight().getLeft().setRight(new Node<GameLetterBtn>(letter20));

		GameLetterBtn letter21 = new GameLetterBtn("", this, lines.get(21));
		letter21.setBounds(408, 360, 30, 30);
		contentPane.add(letter21);
		contentPane.setComponentZOrder(letter21, 1);
		tree.getRoot().getLeft().getRight().getRight().setLeft(new Node<GameLetterBtn>(letter21));
		
		GameLetterBtn letter22 = new GameLetterBtn("", this, lines.get(22));
		letter22.setBounds(473, 360, 30, 30);
		contentPane.add(letter22);
		contentPane.setComponentZOrder(letter22, 1);
		tree.getRoot().getLeft().getRight().getRight().setRight(new Node<GameLetterBtn>(letter22));
		
		GameLetterBtn letter23 = new GameLetterBtn("", this, lines.get(23));
		letter23.setBounds(538, 360, 30, 30);
		contentPane.add(letter23);
		contentPane.setComponentZOrder(letter23, 1);
		tree.getRoot().getRight().getLeft().getLeft().setLeft(new Node<GameLetterBtn>(letter23));
		
		GameLetterBtn letter24 = new GameLetterBtn("", this, lines.get(24));
		letter24.setBounds(603, 360, 30, 30);
		contentPane.add(letter24);
		contentPane.setComponentZOrder(letter24, 1);
		tree.getRoot().getRight().getLeft().getLeft().setRight(new Node<GameLetterBtn>(letter24));
		
		GameLetterBtn letter25 = new GameLetterBtn("", this, lines.get(25));
		letter25.setBounds(668, 360, 30, 30);
		contentPane.add(letter25);
		contentPane.setComponentZOrder(letter25, 1);
		tree.getRoot().getRight().getLeft().getRight().setLeft(new Node<GameLetterBtn>(letter25));
		
		GameLetterBtn letter26 = new GameLetterBtn("", this, lines.get(26));
		letter26.setBounds(733, 360, 30, 30);
		contentPane.add(letter26);
		contentPane.setComponentZOrder(letter26, 1);
		tree.getRoot().getRight().getLeft().getRight().setRight(new Node<GameLetterBtn>(letter26));

		GameLetterBtn letter27 = new GameLetterBtn("", this, lines.get(27));
		letter27.setBounds(798, 360, 30, 30);
		contentPane.add(letter27);
		contentPane.setComponentZOrder(letter27, 1);
		tree.getRoot().getRight().getRight().getLeft().setLeft(new Node<GameLetterBtn>(letter27));

		GameLetterBtn letter28 = new GameLetterBtn("", this, lines.get(28));
		letter28.setBounds(863, 360, 30, 30);
		contentPane.add(letter28);
		contentPane.setComponentZOrder(letter28, 1);
		tree.getRoot().getRight().getRight().getLeft().setRight(new Node<GameLetterBtn>(letter28));
		
		GameLetterBtn letter29 = new GameLetterBtn("", this, lines.get(29));
		letter29.setBounds(928, 360, 30, 30);
		contentPane.add(letter29);
		contentPane.setComponentZOrder(letter29, 1);
		tree.getRoot().getRight().getRight().getRight().setLeft(new Node<GameLetterBtn>(letter29));
		
		GameLetterBtn letter30 = new GameLetterBtn("", this, lines.get(30));
		letter30.setBounds(993, 360, 30, 30);
		contentPane.add(letter30);
		contentPane.setComponentZOrder(letter30, 1);
		tree.getRoot().getRight().getRight().getRight().setRight(new Node<GameLetterBtn>(letter30));

		lblStage = new JLabel(stage.toString());
		lblStage.setBounds(520, 465, 300, 30);
		lblStage.setFont(new Font("Gill Sans", Font.BOLD, 16));
		lblStage.setForeground(Color.WHITE);
		contentPane.add(lblStage);
		contentPane.setComponentZOrder(lblStage, 1);

		lblCounter = new JLabel("Cambios: " + Integer.toString(hand.getCounter()));
		lblCounter.setBounds(60, 465, 300, 30);
		lblCounter.setFont(new Font("Gill Sans", Font.BOLD, 14));
		lblCounter.setForeground(Color.WHITE);
		contentPane.add(lblCounter);
		contentPane.setComponentZOrder(lblCounter, 1);

		lblAsserts = new JLabel("Palabras jugadas: " + Integer.toString(stage.getValidated().size()));
		lblAsserts.setBounds(200, 465, 300, 30);
		lblAsserts.setFont(new Font("Gill Sans", Font.BOLD, 14));
		lblAsserts.setForeground(Color.WHITE);
		contentPane.add(lblAsserts);
		contentPane.setComponentZOrder(lblAsserts, 1);

		JLabel lblValidateBtn = new JLabel(new ImageIcon(GameLetterBtn.getIcon("button-add.png", 35, 35)));
		lblValidateBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblValidateBtn.setBounds(680, 462, 35, 35);
		lblValidateBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				stage.commit();
				if (stage.getValidated().size() > user.assertRecord) {
					user.assertRecord = stage.getValidated().size();
					lblNumeroRecordPalabra.setText(Integer.toString(user.assertRecord));
				}
				if (stage.getPoints() > user.pointRecord) {
					user.pointRecord = stage.getPoints();
					lblNumeroRecord.setText(Integer.toString(stage.getPoints()));
				}
				try {
					user.save();
				} catch (ValidationError er) { er.printStackTrace(); }
			}
		});
		
		contentPane.add(lblValidateBtn);
		contentPane.setComponentZOrder(lblValidateBtn, 1);
		
		hand.generateLetters(true);
		
		try {
			user.hasUnfinished = false;
			btnResumir.setVisible(user.hasUnfinished);
			user.save();
		} catch (ValidationError er) { er.printStackTrace(); }

		contentPane.repaint();
	}

	private void paintBackground() {
		JLabel bgTop = new JLabel("");
        bgTop.setBackground(new Color(0, 8, 18));
        bgTop.setOpaque(true);
        bgTop.setBounds(0, 0, 1200, 90);
		bgTop.setBorder(new MatteBorder(0, 0, 1, 0, new Color(110, 90, 0)));
		contentPane.add(bgTop);

		JLabel lblTable = new JLabel();
		lblTable.setIcon(new ImageIcon("nexusblitzbackground.png"));
		lblTable.setBounds(0, 90, 1366, 350);
		contentPane.add(lblTable);

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("gamebg.png"));
		lblFondo.setBounds(0, 0, 1366, 600);
		contentPane.add(lblFondo);
	}

	public void saveGame() {
		SavedGame game = new SavedGame();
		game.asserts = stage.getValidated().size();
		game.bag = bag.getThings();
		game.counter = hand.getCounter();
		game.hand = new ArrayList<String>();
		for (GameLetterBtn letter: hand.getLetters()) {
			game.hand.add(letter.getText());
		}
		game.validated = stage.getValidated();
		game.points = stage.getPoints();
		game.tree = new ArrayList<String>();
		preorderToList(game.tree, tree.getRoot());
		user.savedGame = gson.toJson(game);
		try {
			user.hasUnfinished = true;
			user.save();
		} catch (ValidationError er) { er.printStackTrace(); }
	}

	public void resumeGame() {
		newGame();
		SavedGame game = gson.fromJson(user.savedGame, SavedGame.class);
		try {
			user.hasUnfinished = false;
			user.save();
		} catch (ValidationError er) { er.printStackTrace(); }
		stage.setValidated(game.validated);
		stage.setPoints(game.points);
		hand.setCounter(game.counter);
		for (GameLetterBtn letter: hand.getLetters()) {
			letter.setText(game.hand.remove(0));
		}
		bag.setThings(game.bag);
		listToPreorder(game.tree, tree.getRoot());
	}

	public static void preorderToList(List<String> list, Node<GameLetterBtn> node) {
		if (node == null)
			return;
		list.add(node.getData().getText());
		// traverse the left child
		preorderToList(list, node.getLeft());
		// traverse the right child
		preorderToList(list, node.getRight());
	}

	public static void listToPreorder(List<String> list, Node<GameLetterBtn> node) {
		if (node == null)
			return;
		node.getData().setText(list.remove(0));
		// traverse the left child
		listToPreorder(list, node.getLeft());
		// traverse the right child
		listToPreorder(list, node.getRight());
	}

	public Stage getStage() {
		return stage;
	}

	public Hand getHand() {
		return hand;
	}

	public int getCurrentPoints() {
		return currentPoints;
	}

	public JLabel getLblStage() {
		return lblStage;
	}

	public Bag<String> getBag() {
		return bag;
	}

	public JLabel getLblCounter() {
		return lblCounter;
	}

	public Tree<GameLetterBtn> getTree() {
		return tree;
	}

	public JLabel getLblAsserts() {
		return lblAsserts;
	}

	public User getUser() {
		return user;
	}

	public JLabel getLblNumeroPuntuacionActual() {
		return lblNumeroPuntuacionActual;
	}

}
