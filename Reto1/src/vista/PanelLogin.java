package vista;

import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;

public class PanelLogin extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldUser;
	private JTextField textFieldPass;
	private JButton btnRegistrarse;
	private JButton btnLogin;

	/**
	 * Create the panel.
	 */
	public PanelLogin() {
		setBackground(new Color(204, 193, 221));
		setBounds(288, 11, 688, 541);
		setLayout(null);

		JLabel lblUser = new JLabel("Usuario");
		lblUser.setBounds(188, 194, 46, 14);
		add(lblUser);

		JLabel lblPass = new JLabel("Contraseña");
		lblPass.setBounds(188, 244, 76, 14);
		add(lblPass);

		textFieldUser = new JTextField();
		textFieldUser.setBounds(280, 191, 86, 20);
		add(textFieldUser);
		textFieldUser.setColumns(10);

		textFieldPass = new JTextField();
		textFieldPass.setColumns(10);
		textFieldPass.setBounds(280, 241, 86, 20);
		add(textFieldPass);

		btnLogin = new JButton("Aceptar");
		btnLogin.setBounds(239, 312, 107, 23);
		add(btnLogin);

		JLabel lblNewLabel = new JLabel("LOGO");
		lblNewLabel.setBounds(263, 11, 92, 101);
		add(lblNewLabel);

		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblLogin.setBounds(75, 93, 92, 101);
		add(lblLogin);

		btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.setBounds(367, 312, 89, 23);
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