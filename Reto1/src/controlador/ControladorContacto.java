//Pte todo
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.Date;
import modelo.Usuario;
import modelo.Usuario.enumTipoUsuario;
import vista.PanelEliminar;
import vista.PanelIngresar;
import vista.PanelLogin;
import vista.PanelRegistro;
import vista.Principal;
import vista.Principal.enumAcciones;

public class ControladorContacto implements ActionListener {

	private vista.Principal vistaPrincipal;

	/*
	 * *** CONSTRUCTORES ***
	 */

	/*
	 * Contructor del objeto controlador
	 * 
	 * @param vistaPrincipal Objeto vista.
	 */
	public ControladorContacto(vista.Principal vistaPrincipal) {
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
				.setActionCommand(Principal.enumAcciones.INSERTAR_CONTACTO.toString());

		this.vistaPrincipal.getPanelLogin().getBtnLogin().addActionListener(this);
		this.vistaPrincipal.getPanelLogin().getBtnLogin().setActionCommand(Principal.enumAcciones.LOGIN.toString());

	}

	/*** Tratamiento de las acciones ***/

	@Override
	public void actionPerformed(ActionEvent e) {

		Principal.enumAcciones accion = Principal.enumAcciones.valueOf(e.getActionCommand());

		switch (accion) {
		case LOGIN:
			this.mConfirmarLogin(accion);
			break;
		case INSERTAR_CONTACTO:
			this.mInsertarContacto(accion);
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
			Usuario usuario1 = new Usuario();
			usuario1.mObtenerUsuario(usuarioIntroducido, passIntroducida);

		} else {
			JOptionPane.showMessageDialog(null, "Algun Campo esta vacio");
		}

	}

	private void mInsertarContacto(Principal.enumAcciones accion) {
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

		Usuario usuario = new Usuario(nombre, apellidos, email, contrasena, fechaNacimiento, fechaRegistro);
		usuario.mIngresarContacto();

		panelRegistro.getTfNombre().setText("");
		panelRegistro.getTfEmail().setText("");
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