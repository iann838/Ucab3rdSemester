package com.ucab.taller6;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Panel {

    public static void loop() {
        Main main = new Main();
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel statement = new JLabel(
            "<html>" +
            "5. Dada una lista A (desordenada y con elementos repetidos) " +
            "se quiere que usted resuelva de manera eficiente el siguiente requerimiento: " +
            "Obtener una lista B que contenga los elementos que están en A sin repeticiones, " +
            "seguidos por el número de ocurrencias. Resuelva el problema utilizando manejo de apuntadores. " +
            "La lista A puede ser modificada. " +
            "</html>"
        );
        statement.setBounds(35, 20, 500, 100);
        mainFrame.add(statement);

        JLabel inputLabel = new JLabel("Lista A: ");
        inputLabel.setBounds(35, 123, 60, 30);
        mainFrame.add(inputLabel);
        
        JTextField input = new JTextField();
        input.setBounds(100, 130, 430, 20);
        mainFrame.add(input);

        JLabel outputLabel = new JLabel("Lista B: ");
        outputLabel.setBounds(35, 153, 60, 30);
        mainFrame.add(outputLabel);
        
        JTextField output = new JTextField();
        output.setBounds(100, 160, 430, 20);
        mainFrame.add(output);

        JButton button = new JButton("Ejecutar");
        button.setBounds(35, 200, 105, 20);
        mainFrame.add(button);
        
        JButton buttonRandom = new JButton("Generar aliatoriamente Lista A");
        buttonRandom.setBounds(160, 200, 250, 20);
        mainFrame.add(buttonRandom);

        JButton buttonClear = new JButton("Borrar");
        buttonClear.setBounds(425, 200, 105, 20);
        mainFrame.add(buttonClear);

        input.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char key = evt.getKeyChar();
                if (
                    !(
                        Character.isDigit(key)) && key != ',' ||
                        key == ',' && (input.getText().endsWith(",") ||
                        input.getText().equals("")
                    )
                ) {
                    evt.consume();
                }
                if (Character.isDigit(key))
                    main.parseListA(input.getText() + key);
                else
                    main.parseListA(input.getText());
                main.generateB();
                output.setText(main.listB.toString());
            }
        });

        output.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                evt.consume();
            }
        });

        button.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e){
                main.parseListA(input.getText());
                main.generateB();
                output.setText(main.listB.toString());
            }
        });

        buttonRandom.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e){
                main.randomListA();
                input.setText(main.listA.toString());
                main.generateB();
                output.setText(main.listB.toString());
            }
        });

        buttonClear.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e){
                input.setText("");
                output.setText("");
            }
        });

        mainFrame.setSize(590, 300);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);
    }

}
