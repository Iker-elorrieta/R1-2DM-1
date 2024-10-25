package vista;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
	private JButton btnVolverLogin;

	/**
	 * Create the panel.
	 */
	public PanelRegistro() {
		Calendar ahoraMismo = Calendar.getInstance();
		int ano = ahoraMismo.get(Calendar.YEAR);
		int mes = ahoraMismo.get(Calendar.MONTH) + 1;
		int dia = ahoraMismo.get(Calendar.DATE);
		String maxString = ano + "-" + mes + "-" + dia;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		setBounds(288, 11, 784, 541);
		setLayout(null);

		btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.setBounds(294, 354, 89, 23);
		add(btnRegistrarse);

		btnVolverLogin = new JButton("Volver");
		btnVolverLogin.setBounds(175, 354, 89, 23);
		add(btnVolverLogin);

		tfNombre = new JTextField();
		tfNombre.setBounds(294, 182, 200, 20);
		add(tfNombre);
		tfNombre.setColumns(10);

		tfApellidos = new JTextField();
		tfApellidos.setBounds(294, 213, 200, 20);
		add(tfApellidos);
		tfApellidos.setColumns(10);

		tfEmail = new JTextField();
		tfEmail.setBounds(294, 244, 200, 20);
		add(tfEmail);
		tfEmail.setColumns(10);

		fechaNacimientoCalendar = new JDateChooser();
		fechaNacimientoCalendar.setBounds(294, 306, 200, 20);
		add(fechaNacimientoCalendar);

		JTextFieldDateEditor editor = (JTextFieldDateEditor) fechaNacimientoCalendar.getDateEditor();
		editor.setEditable(false);

		try {
			fechaNacimientoCalendar.setMaxSelectableDate(dateFormat.parse(maxString));
			fechaNacimientoCalendar.setDate(dateFormat.parse(maxString));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		pfContrasena = new JPasswordField();
		pfContrasena.setBounds(294, 275, 200, 20);
		add(pfContrasena);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(129, 188, 155, 14);
		add(lblNombre);

		JLabel lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setBounds(129, 219, 155, 14);
		add(lblApellidos);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(129, 250, 155, 14);
		add(lblEmail);

		JLabel lblContrasena = new JLabel("Contraseña:");
		lblContrasena.setBounds(129, 281, 155, 14);
		add(lblContrasena);

		JLabel lblFechaNacimiento = new JLabel("Fecha de nacimiento:");
		lblFechaNacimiento.setBounds(129, 312, 155, 14);
		add(lblFechaNacimiento);

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