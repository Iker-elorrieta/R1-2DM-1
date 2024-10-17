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

import modelo.Usuario;

public class PanelPerfil extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField tfNombre;
	private JTextField tfApellidos;
	private JTextField tfEmail;
	private JPasswordField pfContrasena;
	private JDateChooser fechaNacimientoCalendar;
	private JButton btnEditar;
	private JButton btnAceptar;
	private Usuario usuarioLogeado;


	/**
	 * Create the panel.
	 */
	public PanelPerfil() {
		Calendar ahoraMismo = Calendar.getInstance();
		int ano = ahoraMismo.get(Calendar.YEAR);
		int mes = ahoraMismo.get(Calendar.MONTH) + 1;
		int dia = ahoraMismo.get(Calendar.DATE);
		String maxString = ano + "-" + mes + "-" + dia;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		setBounds(288, 11, 784, 541);
		setLayout(null);

		btnEditar = new JButton("Editar");
		btnEditar.setBounds(216, 354, 89, 23);
		add(btnEditar);

		tfNombre = new JTextField();
		tfNombre.setEditable(false);
		tfNombre.setBounds(294, 182, 200, 20);
		add(tfNombre);
		tfNombre.setColumns(10);

		tfApellidos = new JTextField();
		tfApellidos.setEditable(false);
		tfApellidos.setBounds(294, 213, 200, 20);
		add(tfApellidos);
		tfApellidos.setColumns(10);

		tfEmail = new JTextField();
		tfEmail.setEditable(false);
		tfEmail.setBounds(294, 244, 200, 20);
		add(tfEmail);
		tfEmail.setColumns(10);

		fechaNacimientoCalendar = new JDateChooser();
		fechaNacimientoCalendar.setBounds(294, 306, 200, 20);
		fechaNacimientoCalendar.setEnabled(false);
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
		pfContrasena.setEditable(false);
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

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setEnabled(false);
		btnAceptar.setBounds(315, 354, 89, 23);
		add(btnAceptar);
	

	}

	public void setUsuarioLogeado(Usuario usuarioLogeado) {
		this.usuarioLogeado = usuarioLogeado;
	}
	
	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}

	public JButton getBtnEditar() {
		return btnEditar;
	}

	public void setBtnEditar(JButton btnEditar) {
		this.btnEditar = btnEditar;
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