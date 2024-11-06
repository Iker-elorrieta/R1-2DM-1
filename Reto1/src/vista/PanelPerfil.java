package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

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
	private JButton btnIconoVerContrasena;
	private Usuario usuarioLogeado;
	private JTextField tfContrasenaVer;
	private JButton btnVolver;
	private JButton btnVerHistorico;

	/**
	 * Create the panel.
	 */
	public PanelPerfil() {setBackground(new Color(230, 230, 250));
	setBounds(288, 11, 784, 541);
	setLayout(null);

	JLabel lblTitulo = new JLabel("PERFIL");
	lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
	lblTitulo.setBounds(290, 30, 200, 50);
	add(lblTitulo);

	JLabel lblNombre = new JLabel("Nombre:");
	lblNombre.setFont(new Font("Arial", Font.PLAIN, 16));
	lblNombre.setBounds(150, 200, 130, 20);
	add(lblNombre);

	tfNombre = new JTextField();
	tfNombre.setBounds(300, 200, 220, 30);
	tfNombre.setBorder(new EmptyBorder(5, 5, 5, 5));
	tfNombre.setEditable(false);
	add(tfNombre);

	JLabel lblApellidos = new JLabel("Apellidos:");
	lblApellidos.setFont(new Font("Arial", Font.PLAIN, 16));
	lblApellidos.setBounds(150, 240, 130, 20);
	add(lblApellidos);

	tfApellidos = new JTextField();
	tfApellidos.setBounds(300, 240, 220, 30);
	tfApellidos.setBorder(new EmptyBorder(5, 5, 5, 5));
	tfApellidos.setEditable(false);
	add(tfApellidos);

	JLabel lblEmail = new JLabel("Email:");
	lblEmail.setFont(new Font("Arial", Font.PLAIN, 16));
	lblEmail.setBounds(150, 280, 130, 20);
	add(lblEmail);

	tfEmail = new JTextField();
	tfEmail.setBounds(300, 280, 220, 30);
	tfEmail.setBorder(new EmptyBorder(5, 5, 5, 5));
	tfEmail.setEditable(false);
	add(tfEmail);

	JLabel lblContrasena = new JLabel("Contraseña:");
	lblContrasena.setFont(new Font("Arial", Font.PLAIN, 16));
	lblContrasena.setBounds(150, 320, 130, 20);
	add(lblContrasena);

	pfContrasena = new JPasswordField();
	pfContrasena.setBounds(300, 320, 220, 30);
	pfContrasena.setBorder(new EmptyBorder(5, 5, 5, 5));
	pfContrasena.setEditable(false);
	add(pfContrasena);

	JLabel lblFechaNacimiento = new JLabel("Fecha de nacimiento:");
	lblFechaNacimiento.setFont(new Font("Arial", Font.PLAIN, 16));
	lblFechaNacimiento.setBounds(150, 360, 180, 20);
	add(lblFechaNacimiento);

	fechaNacimientoCalendar = new JDateChooser();
	fechaNacimientoCalendar.setBounds(300, 360, 220, 30);
	JTextFieldDateEditor editor = (JTextFieldDateEditor) fechaNacimientoCalendar.getDateEditor();
	editor.setEditable(false);
	fechaNacimientoCalendar.setEnabled(false);
	add(fechaNacimientoCalendar);

	Calendar ahoraMismo = Calendar.getInstance();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	String maxString = ahoraMismo.get(Calendar.YEAR) + "-" + (ahoraMismo.get(Calendar.MONTH) + 1) + "-" + ahoraMismo.get(Calendar.DATE);
	try {
		fechaNacimientoCalendar.setMaxSelectableDate(dateFormat.parse(maxString));
		fechaNacimientoCalendar.setDate(dateFormat.parse(maxString));
	} catch (ParseException e) {
		e.printStackTrace();
	}

	btnAceptar = new JButton("Aceptar");
	btnAceptar.setBounds(320, 420, 140, 40);
	btnAceptar.setFont(new Font("Arial", Font.BOLD, 14));
	btnAceptar.setBackground(new Color(255, 105, 180));
	btnAceptar.setForeground(Color.WHITE);
	btnAceptar.setBorderPainted(false);
	btnAceptar.setFocusPainted(false);
	btnAceptar.setEnabled(false);
	add(btnAceptar);

	btnEditar = new JButton("Editar");
	btnEditar.setBounds(180, 420, 100, 40);
	btnEditar.setFont(new Font("Arial", Font.BOLD, 14));
	btnEditar.setBackground(new Color(100, 149, 237));
	btnEditar.setForeground(Color.WHITE);
	btnEditar.setBorderPainted(false);
	btnEditar.setFocusPainted(false);
	add(btnEditar);

	ImageIcon originalIcon = new ImageIcon("multimedia/Ojo.png");
	Image scaledImage = originalIcon.getImage().getScaledInstance(26, 21, Image.SCALE_SMOOTH);
	btnIconoVerContrasena = new JButton(new ImageIcon(scaledImage));
	btnIconoVerContrasena.setBounds(540, 320, 26, 21);
	btnIconoVerContrasena.setEnabled(false);
	add(btnIconoVerContrasena);

	btnVolver = new JButton("Volver");
	btnVolver.setBounds(59, 82, 100, 40);
	btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
	btnVolver.setBackground(new Color(100, 149, 237));
	btnVolver.setForeground(Color.WHITE);
	btnVolver.setBorderPainted(false);
	btnVolver.setFocusPainted(false);
	add(btnVolver);

	}

	public void setUsuarioLogeado(Usuario usuarioLogeado) {
		this.usuarioLogeado = usuarioLogeado;
	}

	public Usuario getUsuarioLogeado() {
		return usuarioLogeado;
	}

	public JButton getBtnVerHistorico() {
		return btnVerHistorico;
	}

	public void setBtnVerHistorico(JButton btnVerHistorico) {
		this.btnVerHistorico = btnVerHistorico;
	}

	public JButton getBtnIconoVerContrasena() {
		return btnIconoVerContrasena;
	}

	public void setBtnIconoVerContrasena(JButton btnIconoVerContrasena) {
		this.btnIconoVerContrasena = btnIconoVerContrasena;
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

	public JTextField getTfContrasenaVer() {
		return tfContrasenaVer;
	}

	public void setTfContrasenaVer(JTextField tfContrasenaVer) {
		this.tfContrasenaVer = tfContrasenaVer;
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

	public JButton getBtnVolver() {
		return btnVolver;
	}

	public void setBtnVolver(JButton btnVolver) {
		this.btnVolver = btnVolver;
	}
}