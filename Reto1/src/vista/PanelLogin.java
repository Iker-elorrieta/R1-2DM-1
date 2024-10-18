package vista;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.border.EmptyBorder;

public class PanelLogin extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField textFieldUser;
    private JTextField textFieldPass;
    private JButton btnRegistrarse;
    private JButton btnLogin;

    /**
     * Create the panel.
     */
    public PanelLogin() {
        // Set background color and layout
		setBackground(new Color(230, 230, 250)); // Un color más claro
        setBounds(288, 11, 688, 541);
        setLayout(null);
        
        // Add title label
        JLabel lblLogin = new JLabel("LOGIN");
        lblLogin.setFont(new Font("Arial", Font.BOLD, 28));
        lblLogin.setBounds(263, 50, 200, 50); // Centered title
        add(lblLogin);

        // Add logo placeholder
        JLabel lblNewLabel = new JLabel("LOGO");
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        lblNewLabel.setBounds(300, 100, 100, 30); // Adjust logo position
        add(lblNewLabel);

        // User label and text field
        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setFont(new Font("Arial", Font.PLAIN, 16));
        lblUser.setBounds(176, 203, 80, 20);
        add(lblUser);
        
        textFieldUser = new JTextField();
        textFieldUser.setBounds(280, 200, 200, 30);
        textFieldUser.setBorder(new EmptyBorder(5, 5, 5, 5)); // Add padding to text field
        add(textFieldUser);
        
        // Password label and text field
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(new Font("Arial", Font.PLAIN, 16));
        lblPass.setBounds(176, 253, 100, 20);
        add(lblPass);

        textFieldPass = new JTextField();
        textFieldPass.setBounds(280, 250, 200, 30);
        textFieldPass.setBorder(new EmptyBorder(5, 5, 5, 5)); // Add padding to text field
        add(textFieldPass);

        // Login button
        btnLogin = new JButton("Aceptar");
        btnLogin.setBounds(220, 310, 100, 40);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBackground(new Color(100, 149, 237)); // Cornflower blue
        btnLogin.setForeground(Color.WHITE); // White text
        btnLogin.setBorderPainted(false); // No border
        btnLogin.setFocusPainted(false); // No focus border
        add(btnLogin);

        // Register button
        btnRegistrarse = new JButton("Registrarse");
        btnRegistrarse.setBounds(350, 310, 120, 40);
        btnRegistrarse.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrarse.setBackground(new Color(255, 105, 180)); // Hot pink
        btnRegistrarse.setForeground(Color.WHITE); // White text
        btnRegistrarse.setBorderPainted(false); // No border
        btnRegistrarse.setFocusPainted(false); // No focus border
        add(btnRegistrarse);
    }

    public JButton getBtnRegistrarse() {
        return btnRegistrarse;
    }

    public void setBtnRegistrarse(JButton btnRegistrarse) {
        this.btnRegistrarse = btnRegistrarse;
    }

    public JTextField getTextFieldUser() {
        return textFieldUser;
    }

    public void setTextFieldUser(JTextField textFieldUser) {
        this.textFieldUser = textFieldUser;
    }

    public JTextField getTextFieldPass() {
        return textFieldPass;
    }

    public void setTextFieldPass(JTextField textFieldPass) {
        this.textFieldPass = textFieldPass;
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public void setBtnLogin(JButton btnLogin) {
        this.btnLogin = btnLogin;
    }
}
