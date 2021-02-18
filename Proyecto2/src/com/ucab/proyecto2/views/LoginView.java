package com.ucab.proyecto2.views;
// import java.awt.BorderLayout;
// import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;

import com.ucab.proyecto2.django.Database.ObjectDoesNotExist;
import com.ucab.proyecto2.models.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class LoginView extends JFrame {

	private JTextField textFieldEmail;

	public static void main(String[] args) {
		
		LoginView frame = new LoginView();
		frame.setVisible(true);
	}

	public LoginView() {
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		Image miIcono = mipantalla.getImage("icono.jpeg");
		setIconImage(miIcono);
		setTitle("Scrabble");
		setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1050, 620);
		setLocationRelativeTo(null);
		JPanel mainPane = new JPanel(null);
		mainPane.setPreferredSize(new Dimension(380, 500));

		add(mainPane, BorderLayout.WEST);
		add(new JImgBgPanel("nexusblitzbackground.png"), BorderLayout.CENTER);
		
		JLabel lblTituloPrincipal = new JLabel("SCRABBLE");
		lblTituloPrincipal.setForeground(new Color(0, 0, 0));
		lblTituloPrincipal.setFont(new Font("Rockwell", Font.PLAIN, 30));
		lblTituloPrincipal.setBounds(120, 80, 153, 55);
		mainPane.add(lblTituloPrincipal);
		
		JLabel lblTituloCorreo = new JLabel("Email");
		lblTituloCorreo.setForeground(Color.BLACK);
		lblTituloCorreo.setFont(new Font("Gill Sans", Font.BOLD, 12));
		lblTituloCorreo.setBounds(70, 132, 236, 28);
		mainPane.add(lblTituloCorreo);

		JButton btnBotonLogin = new JButton("Login");
		// btnBotonLogin.addActionListener(new ActionListener() {
		// 	public void actionPerformed(ActionEvent e) {
		// 		setVisible(false);
		// 		Ventana_de_menu a = new Ventana_de_menu();
		// 		a.setVisible(true);
		// 		a.setLocationRelativeTo(null);
		// 		dispose();
		// 	}

		// });
		btnBotonLogin.setForeground(new Color(247, 249, 250));
		btnBotonLogin.setFont(new Font("Rockwell", Font.BOLD, 20));
		btnBotonLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBotonLogin.setBackground(new Color(0, 155, 219));
		btnBotonLogin.setBounds(60, 220, 260, 40);
		mainPane.add(btnBotonLogin);
		
		JButton btnBotonSalir = new JButton("Salir");
		btnBotonSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnBotonSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBotonSalir.setForeground(new Color(247, 249, 250));
		btnBotonSalir.setFont(new Font("Rockwell", Font.BOLD, 20));
		btnBotonSalir.setBackground(new Color(221, 75, 57));
		btnBotonSalir.setBounds(60, 280, 260, 40);
		mainPane.add(btnBotonSalir);

		JLabel lblRegistrar = new JLabel("¿ No estás registrado ?");
		lblRegistrar.setForeground(Color.BLACK);
		lblRegistrar.setFont(new Font("Gill Sans", Font.PLAIN, 12));
		lblRegistrar.setBounds(70, 340, 130, 28);
		mainPane.add(lblRegistrar);

		JLabel lblRegistrarBtn = new JLabel("Registrar");
		lblRegistrarBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblRegistrarBtn.setForeground(Color.BLUE);
		lblRegistrarBtn.setFont(new Font("Gill Sans", Font.PLAIN, 12));
		lblRegistrarBtn.setBounds(225, 340, 60, 28);
		lblRegistrarBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				RegisterView register = new RegisterView();
				register.setVisible(true);
				register.setLocationRelativeTo(null);
				dispose();
			}
		});
		mainPane.add(lblRegistrarBtn);

		textFieldEmail = new JTextField();
		textFieldEmail.setForeground(new Color(0, 0, 0));
		textFieldEmail.setColumns(10);
		textFieldEmail.setBackground(new Color(255, 255, 255));
		textFieldEmail.setBounds(70, 160, 240, 30);
		textFieldEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if (textFieldEmail.getText().length() >= 30) 
                    evt.consume();
            }
        });
		mainPane.add(textFieldEmail);

		LoginView this_ = this;        
		btnBotonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = textFieldEmail.getText();
                if (email.length() > 0) {
					try {
						User user = User.objects.first("email", email);
						setVisible(false);
                        GameView game = new GameView(user);
                        game.setVisible(true);
                        game.setLocationRelativeTo(null);
                        dispose();
					} catch (ObjectDoesNotExist er) {
						JOptionPane.showMessageDialog(this_, "Este email no está registrado.", "Alerta", JOptionPane.ERROR_MESSAGE);
					} catch (IOException er) {
						JOptionPane.showMessageDialog(this_, er.getMessage(), "Error Interno", JOptionPane.ERROR_MESSAGE);
					}
                } else {
                    JOptionPane.showMessageDialog(this_, "Por favor complete los campos.", "Alerta", JOptionPane.ERROR_MESSAGE);
                }
			}
		});
	}

}
