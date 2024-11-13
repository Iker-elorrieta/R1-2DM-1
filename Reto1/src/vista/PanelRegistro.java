package vista;

import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

public class PanelRegistro extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField tfNombre;
	private JTextField tfApellidos;
	private JTextField tfEmail;
	private JPasswordField pfContrasena;
	private JDateChooser fechaNacimientoCalendar;
	private JButton btnRegistrarse;
	private JButton btnVolverLogin;

	/**
	 * Create the panel.
	 */
	public PanelRegistro() {
		setBackground(new Color(230, 230, 250));
		setBounds(288, 11, 784, 541);
		setLayout(null);

		JLabel lblTitulo = new JLabel("REGISTRO");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
		lblTitulo.setBounds(290, 30, 200, 50);
		add(lblTitulo);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNombre.setBounds(150, 200, 130, 20);
		add(lblNombre);

		tfNombre = new JTextField();
		tfNombre.setBounds(315, 190, 220, 30);
		tfNombre.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(tfNombre);

		JLabel lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setFont(new Font("Arial", Font.PLAIN, 16));
		lblApellidos.setBounds(150, 240, 130, 20);
		add(lblApellidos);

		tfApellidos = new JTextField();
		tfApellidos.setBounds(315, 230, 220, 30);
		tfApellidos.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(tfApellidos);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEmail.setBounds(150, 280, 130, 20);
		add(lblEmail);

		tfEmail = new JTextField();
		tfEmail.setBounds(315, 270, 220, 30);
		tfEmail.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(tfEmail);

		JLabel lblContrasena = new JLabel("Contraseña:");
		lblContrasena.setFont(new Font("Arial", Font.PLAIN, 16));
		lblContrasena.setBounds(150, 320, 130, 20);
		add(lblContrasena);

		pfContrasena = new JPasswordField();
		pfContrasena.setBounds(315, 310, 220, 30);
		pfContrasena.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(pfContrasena);

		JLabel lblFechaNacimiento = new JLabel("Fecha de nacimiento:");
		lblFechaNacimiento.setFont(new Font("Arial", Font.PLAIN, 16));
		lblFechaNacimiento.setBounds(150, 360, 180, 20);
		add(lblFechaNacimiento);

		fechaNacimientoCalendar = new JDateChooser();
		fechaNacimientoCalendar.setBounds(315, 350, 220, 30);
		JTextFieldDateEditor editor = (JTextFieldDateEditor) fechaNacimientoCalendar.getDateEditor();
		editor.setEditable(false);
		add(fechaNacimientoCalendar);

		Calendar ahoraMismo = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String maxString = ahoraMismo.get(Calendar.YEAR) + "-" + (ahoraMismo.get(Calendar.MONTH) + 1) + "-"
				+ ahoraMismo.get(Calendar.DATE);
		try {
			fechaNacimientoCalendar.setMaxSelectableDate(dateFormat.parse(maxString));
			fechaNacimientoCalendar.setDate(dateFormat.parse(maxString));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.setBounds(320, 420, 140, 40);
		btnRegistrarse.setFont(new Font("Arial", Font.BOLD, 14));
		btnRegistrarse.setBackground(new Color(255, 105, 180));
		btnRegistrarse.setForeground(Color.WHITE);
		btnRegistrarse.setBorderPainted(false);
		btnRegistrarse.setFocusPainted(false);
		add(btnRegistrarse);

		btnVolverLogin = new JButton("Volver");
		btnVolverLogin.setBounds(180, 420, 100, 40);
		btnVolverLogin.setFont(new Font("Arial", Font.BOLD, 14));
		btnVolverLogin.setBackground(new Color(100, 149, 237));
		btnVolverLogin.setForeground(Color.WHITE);
		btnVolverLogin.setBorderPainted(false);
		btnVolverLogin.setFocusPainted(false);
		add(btnVolverLogin);

	}

	public JButton getBtnVolverLogin() {
		return btnVolverLogin;
	}

	public void setBtnVolverLogin(JButton btnVolverLogin) {
		this.btnVolverLogin = btnVolverLogin;
	}

	public JButton getBtnRegistrarse() {
		return btnRegistrarse;
	}

	public void setBtnRegistrarse(JButton btnRegistrarse) {
		this.btnRegistrarse = btnRegistrarse;
	}

	public JTextField getTfNombre() {
		return tfNombre;
	}

	public void setTfNombre(JTextField tfNombre) {
		this.tfNombre = tfNombre;
	}

	public JTextField getTfApellidos() {
		return tfApellidos;
	}

	public void setTfApellidos(JTextField tfApellidos) {
		this.tfApellidos = tfApellidos;
	}

	public JTextField getTfEmail() {
		return tfEmail;
	}

	public void setTfEmail(JTextField tfEmail) {
		this.tfEmail = tfEmail;
	}

	public JPasswordField getPfContrasena() {
		return pfContrasena;
	}

	public void setPfContrasena(JPasswordField pfContrasena) {
		this.pfContrasena = pfContrasena;
	}

	public JDateChooser getFechaNacimientoCalendar() {
		return fechaNacimientoCalendar;
	}

	public void setFechaNacimientoCalendar(JDateChooser fechaNacimientoCalendar) {
		this.fechaNacimientoCalendar = fechaNacimientoCalendar;
	}
}