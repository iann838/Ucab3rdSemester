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
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;

import com.ucab.proyecto2.django.Validator.RegexMissmatch;
import com.ucab.proyecto2.django.Validator.UniqueConstraintFailed;
import com.ucab.proyecto2.django.Validator.ValidationError;
import com.ucab.proyecto2.models.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class RegisterView extends JFrame {

	private JTextField textFieldEmail;
	private JTextField textFieldAlias;

	public static void main(String[] args) {
		
		RegisterView frame = new RegisterView();
		frame.setVisible(true);
	}

	public RegisterView() {
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
		add(new JImgBgPanel("kingporobackground.png"), BorderLayout.CENTER);
		
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
		
		JLabel lblTituloNombre = new JLabel("Alias");
		lblTituloNombre.setForeground(Color.BLACK);
		lblTituloNombre.setFont(new Font("Gill Sans", Font.BOLD, 12));
		lblTituloNombre.setBounds(70, 192, 236, 28);
		mainPane.add(lblTituloNombre);

		JButton btnBotonRegistro = new JButton("Registrar");
		btnBotonRegistro.setForeground(new Color(247, 249, 250));
		btnBotonRegistro.setFont(new Font("Rockwell", Font.BOLD, 20));
		btnBotonRegistro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBotonRegistro.setBackground(new Color(0, 155, 219));
		btnBotonRegistro.setBounds(60, 270, 260, 40);
		mainPane.add(btnBotonRegistro);
		
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
		btnBotonSalir.setBounds(60, 330, 260, 40);
        mainPane.add(btnBotonSalir);
        
        
		JLabel lblLogin = new JLabel("¿ Ya estás registrado ?");
		lblLogin.setForeground(Color.BLACK);
		lblLogin.setFont(new Font("Gill Sans", Font.PLAIN, 12));
		lblLogin.setBounds(70, 380, 130, 28);
		mainPane.add(lblLogin);

		JLabel lblLoginBtn = new JLabel("Login");
		lblLoginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblLoginBtn.setForeground(Color.BLUE);
		lblLoginBtn.setFont(new Font("Gill Sans", Font.PLAIN, 12));
		lblLoginBtn.setBounds(225, 380, 35, 28);
		lblLoginBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				LoginView login = new LoginView();
				login.setVisible(true);
				login.setLocationRelativeTo(null);
				dispose();
			}
		});
		mainPane.add(lblLoginBtn);
		
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

		textFieldAlias = new JTextField();
		textFieldAlias.setForeground(Color.BLACK);
		textFieldAlias.setColumns(10);
		textFieldAlias.setBackground(Color.WHITE);
        textFieldAlias.setBounds(70, 220, 240, 30);
        textFieldAlias.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if (textFieldAlias.getText().length() >= 12) 
                    evt.consume();
            }
        });
		mainPane.add(textFieldAlias);

        RegisterView this_ = this;        
		btnBotonRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                String alias = textFieldAlias.getText();
                String email = textFieldEmail.getText();
                if (alias.length() > 0 && email.length() > 0) {
                    User user = new User();
                    user.alias = alias;
                    user.email = email;
                    user.assertRecord = 0;
                    user.pointRecord = 0;
                    user.savedGame = "";
                    user.hasUnfinished = false;
                    try {
                        user.save();
                        setVisible(false);
                        GameView game = new GameView(user);
                        game.setVisible(true);
                        game.setLocationRelativeTo(null);
                        dispose();
                    } catch (UniqueConstraintFailed er) {
                        JOptionPane.showMessageDialog(this_, "Ya existe un registro con este email.", "Alerta", JOptionPane.ERROR_MESSAGE);
                    } catch (RegexMissmatch er) {
                        JOptionPane.showMessageDialog(this_, "El email introducido no es válido.", "Alerta", JOptionPane.ERROR_MESSAGE);
                    } catch (ValidationError er) {
                        JOptionPane.showMessageDialog(this_, er.getMessage(), "Alerta", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this_, "Por favor complete los campos.", "Alerta", JOptionPane.ERROR_MESSAGE);
                }
			}
		});
	}

}
