package com.ucab.Taller8;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ApartadoGrafico extends JFrame {

	private JPanel contentPane;

	public ApartadoGrafico() {
		Arbol arbol = new Arbol();
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 483, 327);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnIngresarNumero = new JButton("Ingresar numero a buscar");
		btnIngresarNumero.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				if (!arbol.esVacia()) {
					String dato;
					dato = JOptionPane.showInputDialog("Dame el valor del nodo al cual buscar la altura");
					int altura;		
					Nodo nodo = arbol.NodoAbuscar(Integer.parseInt(dato));
					if (nodo != null) {
						altura = arbol.Altura(nodo);
						JOptionPane.showMessageDialog(null,"La altura del nodo es: " + altura,"Altura del nodo",JOptionPane.DEFAULT_OPTION);
					}
					else {
						getToolkit().beep();
						JOptionPane.showMessageDialog(null,"El dato ingresado no se encuentra en el arbol","Error",JOptionPane.WARNING_MESSAGE);
					}
				}
				else{
					getToolkit().beep();
					JOptionPane.showMessageDialog(null,"El arbol se encuentra vacio","Error",JOptionPane.WARNING_MESSAGE);				
				}
			}
		});
		btnIngresarNumero.setBounds(31, 45, 173, 23);
		contentPane.add(btnIngresarNumero);

		JLabel txtArbol = new JLabel();
		// txtArbol.addKeyListener(new java.awt.event.KeyAdapter() {
		// 	@Override
        //     public void keyTyped(java.awt.event.KeyEvent evt) {
		// 		evt.consume();
        //     }
		// });
		JScrollPane scroller = new JScrollPane(txtArbol, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroller.setBounds(230, 11, 210, 250);
		contentPane.add(scroller);

		JButton btnIngresarNodo = new JButton("Ingresar nodo al arbol");
		btnIngresarNodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dato;
				dato = JOptionPane.showInputDialog("Dame el valor del nodo para colocar en el Arbol");
				if (dato.length() == 0) return;
				arbol.insertar(Integer.parseInt(dato));
				txtArbol.setText("<html><pre>" + ImpresoraArbol.ImprimirNodo(arbol.getRaiz()) + "</pre></html>");
			}
		});
		btnIngresarNodo.setBounds(31, 11, 173, 23);
		contentPane.add(btnIngresarNodo);
		
		// JButton btnNewButton = new JButton("Imprimir arbol");
		// btnNewButton.addActionListener(new ActionListener() {
		// 	public void actionPerformed(ActionEvent e) {
		// 		System.out.println("\n\nRecorrido Preorden");
		//          arbol.recorridoPreorden(); 

		//          System.out.println("\n\nRecorrido Inorden");
		//          arbol.recorridoInorden();
		// 	}
		// });
		// btnNewButton.setBounds(31, 79, 173, 23);
		// contentPane.add(btnNewButton);

	}
}
