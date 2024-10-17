package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import modelo.Usuario;
import modelo.WorkOut;
import vista.PanelLogin;
import vista.PanelPerfil;
import vista.PanelRegistro;
import vista.Principal;
import vista.Principal.enumAcciones;

public class Controlador implements ActionListener {

	private vista.Principal vistaPrincipal;
	private Usuario usuarioLogeado;

	/*
	 * *** CONSTRUCTORES ***
	 */

	/*
	 * Contructor del objeto controlador
	 * 
	 * @param vistaPrincipal Objeto vista.
	 */
	public Controlador(vista.Principal vistaPrincipal) {
		this.vistaPrincipal = vistaPrincipal;

		this.inicializarControlador();

	}

	private void inicializarControlador() {

		// Acciones del men� izquierdo
		/*
		 * this.vistaPrincipal.getBtnConsultarContactos().addActionListener(this);
		 * this.vistaPrincipal.getBtnConsultarContactos()
		 * 
		 * .setActionCommand(Principal.enumAcciones.CARGAR_PANEL_CONSULTA.toString());
		 */

		this.vistaPrincipal.getPanelRegistro().getBtnRegistrarse().addActionListener(this);
		this.vistaPrincipal.getPanelRegistro().getBtnRegistrarse()
				.setActionCommand(Principal.enumAcciones.REGISTRAR_USUARIO.toString());

		this.vistaPrincipal.getPanelRegistro().getBtnVolverLogin().addActionListener(this);
		this.vistaPrincipal.getPanelRegistro().getBtnVolverLogin()
				.setActionCommand(Principal.enumAcciones.CARGAR_PANEL_LOGIN.toString());

		this.vistaPrincipal.getPanelLogin().getBtnLogin().addActionListener(this);
		this.vistaPrincipal.getPanelLogin().getBtnLogin().setActionCommand(Principal.enumAcciones.LOGIN.toString());

		this.vistaPrincipal.getPanelLogin().getBtnRegistrarse().addActionListener(this);
		this.vistaPrincipal.getPanelLogin().getBtnRegistrarse()
				.setActionCommand(Principal.enumAcciones.CARGAR_PANEL_REGISTRO.toString());

		this.vistaPrincipal.getPanelPerfil().getBtnEditar().addActionListener(this);
		this.vistaPrincipal.getPanelPerfil().getBtnEditar()
				.setActionCommand(Principal.enumAcciones.EDITAR_PERFIL.toString());

		this.vistaPrincipal.getBtnPerfil().addActionListener(this);
		this.vistaPrincipal.getBtnPerfil().setActionCommand(Principal.enumAcciones.CARGAR_PANEL_PERFIL.toString());
	}

	/*** Tratamiento de las acciones ***/

	@Override
	public void actionPerformed(ActionEvent e) {

		Principal.enumAcciones accion = Principal.enumAcciones.valueOf(e.getActionCommand());

		switch (accion) {
		case LOGIN:
			this.mConfirmarLogin(accion);
			break;
		case CARGAR_PANEL_LOGIN:
			this.vistaPrincipal.mVisualizarPaneles(accion);
			break;
		case CARGAR_PANEL_REGISTRO:
			this.vistaPrincipal.mVisualizarPaneles(accion);
			break;
		case CARGAR_PANEL_PERFIL:
			this.vistaPrincipal.getPanelPerfil().setUsuarioLogeado(usuarioLogeado);
			this.vistaPrincipal.mVisualizarPaneles(accion);
			System.out.println("hola");
			break;
		case REGISTRAR_USUARIO:
			this.mRegistrarUsuario(accion);
			break;
		case EDITAR_PERFIL:
			this.editarPerfil(accion);
			break;
		default:
			break;

		}
	}

	public void mCargarVentanas(Principal.enumAcciones accion) {

		switch (accion) {
		case CARGAR_PANEL_WORKOUT:
			ArrayList<WorkOut> listaWorkouts = new WorkOut().mObtenerWorkouts();
			this.vistaPrincipal.getPanelWorkout().setWorkouts(listaWorkouts);
			this.vistaPrincipal.getPanelWorkout().setUser(usuarioLogeado);
			this.vistaPrincipal.mVisualizarPaneles(Principal.enumAcciones.CARGAR_PANEL_WORKOUT);

			break;

		default:
			break;

		}
	}

	private void mConfirmarLogin(enumAcciones accion) {
		PanelLogin panelLogin = this.vistaPrincipal.getPanelLogin();
		String usuarioIntroducido = panelLogin.getTextFieldUser().getText().trim();
		String passIntroducida = panelLogin.getTextFieldPass().getText().trim();
		if (!usuarioIntroducido.isEmpty() && !passIntroducida.isEmpty()) {
			Usuario usuario = new Usuario();
			usuarioLogeado = usuario.mObtenerUsuario(usuarioIntroducido, passIntroducida);
			mCargarVentanas(Principal.enumAcciones.CARGAR_PANEL_WORKOUT);
		} else {
			JOptionPane.showMessageDialog(null, "Algún campo está vacío", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void mRegistrarUsuario(Principal.enumAcciones accion) {
		PanelRegistro panelRegistro = this.vistaPrincipal.getPanelRegistro();

		String nombre = panelRegistro.getTfNombre().getText();
		String apellidos = panelRegistro.getTfNombre().getText();
		String email = panelRegistro.getTfEmail().getText();

		String contrasena = new String(panelRegistro.getPfContrasena().getPassword()).trim();

		Date fechaNacimiento = panelRegistro.getFechaNacimientoCalendar().getDate();

		String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		Date fechaRegistro = null;
		try {
			fechaRegistro = new SimpleDateFormat("yyyy-MM-dd").parse(timeStamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (!nombre.isEmpty() && !apellidos.isEmpty() && !email.isEmpty() && !contrasena.isEmpty()
				&& validarEmail(email)) {
			Usuario usuario = new Usuario(nombre, apellidos, email, contrasena, fechaNacimiento, fechaRegistro);
			usuario.mRegistrarUsuario();

			panelRegistro.getTfNombre().setText("");
			panelRegistro.getTfApellidos().setText("");
			panelRegistro.getTfEmail().setText("");
			panelRegistro.getPfContrasena().setText("");
			panelRegistro.getFechaNacimientoCalendar().setDate(new Date());
		} else if (!validarEmail(email)) {
			JOptionPane.showMessageDialog(null, "El email no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Algún campo está vacío", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean validarEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	private void editarPerfil(enumAcciones accion) {
		PanelPerfil panelPerfil = this.vistaPrincipal.getPanelPerfil();
		panelPerfil.getTfNombre().setEditable(true);
		panelPerfil.getTfApellidos().setEditable(true);
		panelPerfil.getPfContrasena().setEditable(true);
		panelPerfil.getFechaNacimientoCalendar().setEnabled(true);
		panelPerfil.getBtnAceptar().setEnabled(true);
		panelPerfil.getBtnEditar().setEnabled(false);
	}

	private void aplicarCambiosPerfil(enumAcciones accion) {
		PanelPerfil panelPerfil = this.vistaPrincipal.getPanelPerfil();
	}

//	private void mModificarContacto(Principal.enumAcciones accion) {
//		PanelModificar panelModificar = this.vistaPrincipal.getPanelModificar();
//		Usuario contacto = null;
//
//		JTable table = panelModificar.getTablaContactos();
//
//		if (table.getSelectedRow() != -1) {
//
//			String nombre = panelModificar.getTextFieldNombre().getText();
//			String email = panelModificar.getTextFieldEmail().getText();
//			String telString = panelModificar.getTextFieldTel().getText();
//
//			if (!nombre.isEmpty() && !email.isEmpty() && esDouble(telString)) {
//				double tel = Double.parseDouble(panelModificar.getTextFieldTel().getText());
//
//				contacto = new Usuario(nombre, tel, email);
//				contacto.setIdContacto(table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
//				System.out.println("Clase controlador.contacto mModificar valor id "
//						+ table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
//				contacto.mModificar();
//
//				panelModificar.getTextFieldNombre().setText("");
//				panelModificar.getTextFieldEmail().setText("");
//				panelModificar.getTextFieldTel().setText("");
//
//				mCargarContactos(Principal.enumAcciones.CARGAR_PANEL_MODIFICAR);
//			} else {
//				JOptionPane.showMessageDialog(null, "Algun campo es invalido");
//			}
//
//		} else {
//			JOptionPane.showMessageDialog(null, "No hay elemento seleccionado");
//
//		}
//
//	}
}