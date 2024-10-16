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


import modelo.Contacto;
import vista.PanelEliminar;
import vista.PanelIngresar;
import vista.PanelModificar;
import vista.Principal;


public class ControladorContacto implements ActionListener, ListSelectionListener {

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
		this.vistaPrincipal.getBtnConsultarContactos().addActionListener(this);
		this.vistaPrincipal.getBtnConsultarContactos()
		.setActionCommand(Principal.enumAcciones.CARGAR_PANEL_CONSULTA.toString());

		this.vistaPrincipal.getBtnInsertarContacto().addActionListener(this);
		this.vistaPrincipal.getBtnInsertarContacto()
		.setActionCommand(Principal.enumAcciones.CARGAR_PANEL_INSERTAR.toString());

		this.vistaPrincipal.getBtnModificarContacto().addActionListener(this);
		this.vistaPrincipal.getBtnModificarContacto()
		.setActionCommand(Principal.enumAcciones.CARGAR_PANEL_MODIFICAR.toString());

		this.vistaPrincipal.getBtnEliminarContacto().addActionListener(this);
		this.vistaPrincipal.getBtnEliminarContacto()
		.setActionCommand(Principal.enumAcciones.CARGAR_PANEL_ELIMINAR.toString());


		// Acciones del panel Insertar

		this.vistaPrincipal.getPanelIngrsar().getBtnInsertarContarctos().addActionListener(this);
		this.vistaPrincipal.getPanelIngrsar().getBtnInsertarContarctos()
		.setActionCommand(Principal.enumAcciones.INSERTAR_CONTACTO.toString()); 

		// Acciones del panel Modificar

		this.vistaPrincipal.getPanelModificar().getBtnModificar().addActionListener(this);
		this.vistaPrincipal.getPanelModificar().getBtnModificar()
		.setActionCommand(Principal.enumAcciones.MODIFICAR_CONTACTO.toString()); 

		this.vistaPrincipal.getPanelModificar().getTablaContactos().getSelectionModel().addListSelectionListener(this);
		this.vistaPrincipal.getPanelModificar().getTablaContactos().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//Acciones del panel Eliminar

		this.vistaPrincipal.getPanelEliminar().getBtnEliminar().addActionListener(this);
		this.vistaPrincipal.getPanelEliminar().getBtnEliminar()
		.setActionCommand(Principal.enumAcciones.ELIMINAR_CONTACTO.toString()); 








	}



	/*** Tratamiento de las acciones ***/

	@Override
	public void actionPerformed(ActionEvent e) {

		Principal.enumAcciones accion = Principal.enumAcciones.valueOf(e.getActionCommand());

		switch (accion) {
		case CARGAR_PANEL_CONSULTA:
			this.vistaPrincipal.mVisualizarPaneles(Principal.enumAcciones.CARGAR_PANEL_CONSULTA);
			this.mCargarContactos(accion);
			break;
		case CARGAR_PANEL_INSERTAR:
			this.vistaPrincipal.mVisualizarPaneles(Principal.enumAcciones.CARGAR_PANEL_INSERTAR);
			this.mCargarContactos(accion);
			break;
		case CARGAR_PANEL_MODIFICAR:
			this.vistaPrincipal.mVisualizarPaneles(Principal.enumAcciones.CARGAR_PANEL_MODIFICAR);
			this.mCargarContactos(accion);
			break;
		case CARGAR_PANEL_ELIMINAR:
			this.vistaPrincipal.mVisualizarPaneles(Principal.enumAcciones.CARGAR_PANEL_ELIMINAR);
			this.mCargarContactos(accion);
			break;
		case INSERTAR_CONTACTO:
			this.mInsertarContacto(accion);
			break;
		case ELIMINAR_CONTACTO:
			this.mEliminarContacto(accion);
			break;

		case MODIFICAR_CONTACTO:
			this.mModificarContacto(accion);
			break;


		default:
			break;


		}
	}





	/*** Llamados a m�todos CRUD ***/




	/*** Otros metodos ***/





	private void mCargarContactos(Principal.enumAcciones accion) {

		Contacto contactos = new Contacto();

		mLimpiarTabla(accion);
		ArrayList<Contacto> listaContactos = contactos.mObtenerContactos();

		String matrizInfo[][] = new String[listaContactos.size()][4];

		for (int i = 0; i < listaContactos.size(); i++) {
			matrizInfo[i][0] = listaContactos.get(i).getIdContacto();
			matrizInfo[i][1] = listaContactos.get(i).getNombre();
			matrizInfo[i][2] = String.valueOf(listaContactos.get(i).getTelefono());
			matrizInfo[i][3] = listaContactos.get(i).getEmail();

			switch (accion) {
			case CARGAR_PANEL_CONSULTA:
				this.vistaPrincipal.getPanelConsultar().getDefaultTableModel().addRow(matrizInfo[i]);
				break;
			case CARGAR_PANEL_INSERTAR:
				this.vistaPrincipal.getPanelIngrsar().getDefaultTableModel().addRow(matrizInfo[i]);
				break;
			case CARGAR_PANEL_ELIMINAR:
				this.vistaPrincipal.getPanelEliminar().getDefaultTableModel().addRow(matrizInfo[i]);
				break;
			case CARGAR_PANEL_MODIFICAR:
				this.vistaPrincipal.getPanelModificar().getDefaultTableModel().addRow(matrizInfo[i]);
				break;

			default:
				break;

			}
		}

	}



	private void mInsertarContacto(Principal.enumAcciones accion) {
		PanelIngresar panelInsertar = this.vistaPrincipal.getPanelIngrsar();
		String nombre = panelInsertar.getTextFieldNombre().getText();
		String email =  panelInsertar.getTextFieldEmail().getText();
		String telString = panelInsertar.getTextFieldTel().getText();

		if(!nombre.isEmpty() && !email.isEmpty() && esDouble(telString)) {
			double tel =  Double.parseDouble(panelInsertar.getTextFieldTel().getText());

			Contacto contacto = new Contacto(nombre,tel,email);
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
		Contacto contacto = new  Contacto();

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
		Contacto contacto = null;

		JTable table = panelModificar.getTablaContactos();

		if(table.getSelectedRow() !=-1) {

				String nombre = panelModificar.getTextFieldNombre().getText();
				String email =  panelModificar.getTextFieldEmail().getText();
				String telString = panelModificar.getTextFieldTel().getText();

				if(!nombre.isEmpty() && !email.isEmpty() && esDouble(telString)) {
					double tel =  Double.parseDouble(panelModificar.getTextFieldTel().getText());

					contacto = new Contacto(nombre,tel,email);
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





	private void mLimpiarTabla(Principal.enumAcciones accion) {

		switch (accion) {
		case CARGAR_PANEL_CONSULTA:
			if (this.vistaPrincipal.getPanelConsultar().getDefaultTableModel().getRowCount() > 0) {
				this.vistaPrincipal.getPanelConsultar().getDefaultTableModel().setRowCount(0);
			}
			break;

		case CARGAR_PANEL_INSERTAR:
			if (this.vistaPrincipal.getPanelIngrsar().getDefaultTableModel().getRowCount() > 0) {
				this.vistaPrincipal.getPanelIngrsar().getDefaultTableModel().setRowCount(0);
			}
			break;
		case CARGAR_PANEL_ELIMINAR:
			if (this.vistaPrincipal.getPanelEliminar().getDefaultTableModel().getRowCount() > 0) {
				this.vistaPrincipal.getPanelEliminar().getDefaultTableModel().setRowCount(0);
			}
			break;

		case CARGAR_PANEL_MODIFICAR:
			if (this.vistaPrincipal.getPanelModificar().getDefaultTableModel().getRowCount() > 0) {
				this.vistaPrincipal.getPanelModificar().getDefaultTableModel().setRowCount(0);
			}
			break;


		default:
			break;
		}

	}


	@Override
	public void valueChanged(ListSelectionEvent e ) {
		PanelModificar panelModificar = vistaPrincipal.getPanelModificar();

		JTable table = panelModificar.getTablaContactos();

		int posicion = table.getSelectedRow();
		System.out.println(posicion);
		if (posicion != -1 ) {
			panelModificar.getTextFieldNombre().setText(table.getModel().getValueAt(posicion, 1).toString());
			panelModificar.getTextFieldEmail().setText(table.getModel().getValueAt(posicion, 3).toString());
			panelModificar.getTextFieldTel().setText(table.getModel().getValueAt(posicion, 2).toString());

		}
	}
}







