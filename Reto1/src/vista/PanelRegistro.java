package vista;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
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

	/**
	 * Create the panel.
	 */
	public PanelRegistro() {
		setBounds(288, 11, 688, 541);
		setLayout(null);

		btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.setBounds(342, 339, 89, 23);
		add(btnRegistrarse);

		JButton btnVolverLogin = new JButton("Volver");
		btnVolverLogin.setBounds(192, 339, 89, 23);
		add(btnVolverLogin);

		tfNombre = new JTextField();
		tfNombre.setBounds(281, 153, 131, 20);
		add(tfNombre);
		tfNombre.setColumns(10);

		tfApellidos = new JTextField();
		tfApellidos.setBounds(281, 184, 131, 20);
		add(tfApellidos);
		tfApellidos.setColumns(10);

		tfEmail = new JTextField();
		tfEmail.setBounds(281, 215, 131, 20);
		add(tfEmail);
		tfEmail.setColumns(10);

		fechaNacimientoCalendar = new JDateChooser();
		fechaNacimientoCalendar.setBounds(281, 277, 131, 20);
		add(fechaNacimientoCalendar);

		JTextFieldDateEditor editor = (JTextFieldDateEditor) fechaNacimientoCalendar.getDateEditor();
		editor.setEditable(false);

		/*
		 * try {
		 * fechaNacimientoCalendar.setMaxSelectableDate(dateFormat.parse(maxString));
		 * fechaNacimientoCalendar.setMinSelectableDate(dateFormat.parse(minString));
		 * fechaNacimientoCalendar.setDate(dateFormat.parse(maxString)); } catch
		 * (ParseException e1) { e1.printStackTrace(); }
		 */

		pfContrasena = new JPasswordField();
		pfContrasena.setBounds(281, 246, 131, 20);
		add(pfContrasena);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(141, 156, 46, 14);
		add(lblNombre);

		JLabel lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setBounds(141, 187, 46, 14);
		add(lblApellidos);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(141, 218, 46, 14);
		add(lblEmail);

		JLabel lblContrasena = new JLabel("Contraseña:");
		lblContrasena.setBounds(141, 249, 93, 14);
		add(lblContrasena);

		JLabel lblFechaNacimiento = new JLabel("Fecha de nacimiento:");
		lblFechaNacimiento.setBounds(141, 280, 117, 14);
		add(lblFechaNacimiento);

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
