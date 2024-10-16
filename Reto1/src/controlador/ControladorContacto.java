//Pte todo
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import modelo.Usuario;
import vista.PanelEliminar;
import vista.PanelIngresar;
import vista.PanelLogin;
import vista.Principal;
import vista.Principal.enumAcciones;


public class ControladorContacto implements ActionListener {

	private vista.Principal vistaPrincipal;

	/*
	 * *** CONSTRUCTORES ***
	 */

	/*
	 * Contructor del objeto controlador
	 * @param vistaPrincipal Objeto vista.
	 */
	public ControladorContacto(vista.Principal vistaPrincipal) {
		this.vistaPrincipal = vistaPrincipal;

		this.inicializarControlador();

	}




	private void inicializarControlador() {


		// Acciones del men� izquierdo
		/*	this.vistaPrincipal.getBtnConsultarContactos().addActionListener(this);
		this.vistaPrincipal.getBtnConsultarContactos()
		.setActionCommand(Principal.enumAcciones.CARGAR_PANEL_CONSULTA.toString());
		 */



		// Acciones del panel LOGIN

		this.vistaPrincipal.getPanelLogin().getBtnLogin().addActionListener(this);
		this.vistaPrincipal.getPanelLogin().getBtnLogin()
		.setActionCommand(Principal.enumAcciones.LOGIN.toString()); 









	}



	/*** Tratamiento de las acciones ***/

	@Override
	public void actionPerformed(ActionEvent e) {

		Principal.enumAcciones accion = Principal.enumAcciones.valueOf(e.getActionCommand());

		switch (accion) {
		case LOGIN:
			this.mConfirmarLogin(accion);
			break;
	

		default:
			break;


		}
	}




	private void mConfirmarLogin(enumAcciones accion) {
		PanelLogin panelLogin = this.vistaPrincipal.getPanelLogin();
		String usuarioIntroducido = panelLogin.getTextFieldUser().getText().trim();
		String passIntroducida =  panelLogin.getTextFieldPass().getText().trim();
		if(!usuarioIntroducido.isEmpty() && !passIntroducida.isEmpty()) {
			Usuario usuario1 = new Usuario();
			usuario1.mObtenerUsuario(usuarioIntroducido, passIntroducida);
			
			
		}else {
			JOptionPane.showMessageDialog(null, "Algun Campo esta vacio");
		}
		
		
	}





	/*** Llamados a m�todos CRUD ***/

	


	/*** Otros metodos ***/




	/*
	 * private void mCargarContactos(Principal.enumAcciones accion) {
	 * 
	 * Usuario contactos = new Usuario();
	 * 
	 * mLimpiarTabla(accion); ArrayList<Usuario> listaContactos =
	 * contactos.mObtenerContactos();
	 * 
	 * String matrizInfo[][] = new String[listaContactos.size()][4];
	 * 
	 * for (int i = 0; i < listaContactos.size(); i++) { matrizInfo[i][0] =
	 * listaContactos.get(i).getIdContacto(); matrizInfo[i][1] =
	 * listaContactos.get(i).getNombre(); matrizInfo[i][2] =
	 * String.valueOf(listaContactos.get(i).getTelefono()); matrizInfo[i][3] =
	 * listaContactos.get(i).getEmail();
	 * 
	 * switch (accion) { case CARGAR_PANEL_CONSULTA:
	 * this.vistaPrincipal.getPanelConsultar().getDefaultTableModel().addRow(
	 * matrizInfo[i]); break; case CARGAR_PANEL_INSERTAR:
	 * this.vistaPrincipal.getPanelIngrsar().getDefaultTableModel().addRow(
	 * matrizInfo[i]); break; case CARGAR_PANEL_ELIMINAR:
	 * this.vistaPrincipal.getPanelEliminar().getDefaultTableModel().addRow(
	 * matrizInfo[i]); break; case CARGAR_PANEL_MODIFICAR:
	 * this.vistaPrincipal.getPanelModificar().getDefaultTableModel().addRow(
	 * matrizInfo[i]); break;
	 * 
	 * default: break;
	 * 
	 * } }
	 * 
	 * }
	 */

	/*

	private void mInsertarContacto(Principal.enumAcciones accion) {
		PanelIngresar panelInsertar = this.vistaPrincipal.getPanelIngrsar();
		String nombre = panelInsertar.getTextFieldNombre().getText();
		String email =  panelInsertar.getTextFieldEmail().getText();
		String telString = panelInsertar.getTextFieldTel().getText();

		if(!nombre.isEmpty() && !email.isEmpty() && esDouble(telString)) {
			double tel =  Double.parseDouble(panelInsertar.getTextFieldTel().getText());

			Usuario contacto = new Usuario(nombre,tel,email);
			contacto.mIngresarContacto();
			mCargarContactos(Principal.enumAcciones.CARGAR_PANEL_INSERTAR);

			panelInsertar.getTextFieldNombre().setText("");
			panelInsertar.getTextFieldEmail().setText("");
			panelInsertar.getTextFieldTel().setText("");

		}else {
			JOptionPane.showMessageDialog(null, "Algun campo es invalido");
		}
	}




	private void mEliminarContacto(Principal.enumAcciones accion) {
		PanelEliminar panelEliminar = this.vistaPrincipal.getPanelEliminar();
		Usuario contacto = new  Usuario();

		JTable table = panelEliminar.getTablaContactos();
		int posicion = table.getSelectedRow();

		if (posicion != -1 ) {
			contacto.setIdContacto(table.getModel().getValueAt(posicion, 0).toString());
			contacto.mEliminar();

			mCargarContactos(Principal.enumAcciones.CARGAR_PANEL_ELIMINAR);
			//carga

		}else {
			JOptionPane.showMessageDialog(null, "No hay elemento seleccionado");

		}
	}


	private void mModificarContacto (Principal.enumAcciones accion) {
		PanelModificar panelModificar = this.vistaPrincipal.getPanelModificar();
		Usuario contacto = null;

		JTable table = panelModificar.getTablaContactos();

		if(table.getSelectedRow() !=-1) {

				String nombre = panelModificar.getTextFieldNombre().getText();
				String email =  panelModificar.getTextFieldEmail().getText();
				String telString = panelModificar.getTextFieldTel().getText();

				if(!nombre.isEmpty() && !email.isEmpty() && esDouble(telString)) {
					double tel =  Double.parseDouble(panelModificar.getTextFieldTel().getText());

					contacto = new Usuario(nombre,tel,email);
					contacto.setIdContacto(table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
					System.out.println("Clase controlador.contacto mModificar valor id "+ table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
					contacto.mModificar();

					panelModificar.getTextFieldNombre().setText("");
					panelModificar.getTextFieldEmail().setText("");
					panelModificar.getTextFieldTel().setText("");

					mCargarContactos(Principal.enumAcciones.CARGAR_PANEL_MODIFICAR);
				}else {
					JOptionPane.showMessageDialog(null, "Algun campo es invalido");
				}

			}else {
				JOptionPane.showMessageDialog(null, "No hay elemento seleccionado");

			}

	}



	public static boolean esDouble(String valor) {
		try {
			Double.parseDouble(valor);
			return true; //
		} catch (NumberFormatException e) {
			return false;
		}
	}
	 */




}








