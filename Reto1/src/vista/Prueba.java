package vista;

import javax.swing.*;

import java.awt.*;

public class Prueba extends JFrame {



    public Prueba() {

        setTitle("Workout Application");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(600, 400);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());



        // Top panel for timers and descriptions

        JPanel topPanel = new JPanel(new GridLayout(1, 3, 5, 5));

        topPanel.setBackground(Color.DARK_GRAY);



        // Create and add components to the top panel

        topPanel.add(createLabelPanel1("Cronómetro Workout  00:00"));

        topPanel.add(createLabelPanel1("Ejercicio X - Descripción"));

        topPanel.add(createLabelPanel1("Workout"));



        add(topPanel, BorderLayout.NORTH);



        // Left panel for exercise time and rest

        JPanel leftPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        leftPanel.setBackground(Color.DARK_GRAY);



        // Create and add components to the left panel

        leftPanel.add(createLabelPanel("Tiempo Ejercicio 00:00"));

        leftPanel.add(createLabelPanel("Descanso 1 minuto 00:45"));



        add(leftPanel, BorderLayout.WEST);



        // Center panel for the buttons (FOTO buttons)

        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 5, 5));

        centerPanel.setBackground(Color.DARK_GRAY);



        JButton fotoButton1 = new JButton("*FOTO* Número Serie 12:37");

        JButton fotoButton2 = new JButton("*FOTO* Número Serie 30:00");

        JButton fotoButton3 = new JButton("*FOTO* Número Serie 30:00");



        centerPanel.add(fotoButton1);

        centerPanel.add(fotoButton2);

        centerPanel.add(fotoButton3);



        add(centerPanel, BorderLayout.CENTER);



        // Bottom panel for exit button

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        bottomPanel.setBackground(Color.DARK_GRAY);



        JButton exitButton = new JButton("Salir");

        exitButton.setBackground(Color.RED);

        exitButton.setForeground(Color.WHITE);

        exitButton.addActionListener(e -> System.exit(0));



        bottomPanel.add(exitButton);

        add(bottomPanel, BorderLayout.SOUTH);



        // Set visible

        setVisible(true);

    }



    private JPanel createLabelPanel(String labelText) {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(Color.DARK_GRAY);

        JLabel label = new JLabel(labelText, SwingConstants.CENTER);

        label.setForeground(Color.WHITE);

        panel.add(label, BorderLayout.CENTER);

        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        return panel; // Ensure a new instance is returned each time

    }



    private JPanel createLabelPanel1(String labelText) {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(Color.DARK_GRAY);

        JLabel label = new JLabel(labelText, SwingConstants.CENTER);

        label.setForeground(Color.WHITE);

        panel.add(label, BorderLayout.CENTER);

        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        return panel; // Ensure a new instance is returned each time

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new Prueba());

    }

}

