package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JButton;
import java.awt.Font;

import java.awt.Color;

public class Principal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Acciones
	public static enum enumAcciones {
		CARGAR_PANEL_INSERTAR, CARGAR_PANEL_LOGIN, INSERTAR_CONTACTO, LOGIN, CARGAR_PANEL_WORKOUT
	}

	private JPanel panelContenedor;
	private PanelRegistro panelRegistro;
	private PanelIngresar panelIngresar;
	private PanelEliminar panelEliminar;
	private PanelLogin panelLogin;
	private PanelWorkout2 panelWorkout;

	private JButton btnConsultarContactos;
	private JButton btnInsertarContacto;
	private JButton btnModificarContacto;
	private JButton btnEliminarContacto;

	public Principal() {

		// Panel que contiene todo el contenido de la ventana
		mCrearPanelContenedor();

		// Panel izquierdo, contiene el men� del programa.
		mCrearPanelMenu();

		// Panel que contiene el listado de contactos.
		mCrearPanelRegistro();

		mCrearPanelIngresar();

		mCrearPanelEliminar();

		mCrearPanelLogin();
		mCrearPanelWorkOut();

	}

	// *** Creaci�n de paneles ***

	private void mCrearPanelContenedor() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		panelContenedor = new JPanel();
		panelContenedor.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelContenedor);
		panelContenedor.setLayout(null);

	}

	private void mCrearPanelMenu() {

		JPanel panelMenu = new JPanel();
		panelMenu.setBackground(Color.YELLOW);
		panelMenu.setBounds(10, 11, 268, 541);
		panelContenedor.add(panelMenu);
		panelMenu.setLayout(null);

		btnConsultarContactos = new JButton("Consultar contactos");
		btnConsultarContactos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnConsultarContactos.setBounds(30, 75, 216, 35);
		panelMenu.add(btnConsultarContactos);

		btnInsertarContacto = new JButton("Añadir contacto");
		btnInsertarContacto.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnInsertarContacto.setBounds(30, 167, 216, 35);
		panelMenu.add(btnInsertarContacto);

		btnModificarContacto = new JButton("Modificar contacto");
		btnModificarContacto.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnModificarContacto.setBounds(28, 271, 216, 35);
		panelMenu.add(btnModificarContacto);

		btnEliminarContacto = new JButton("Eliminar contacto");
		btnEliminarContacto.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnEliminarContacto.setBounds(28, 363, 216, 35);
		panelMenu.add(btnEliminarContacto);

		panelMenu.setVisible(false);

	}

	private void mCrearPanelRegistro() {
		panelRegistro = new PanelRegistro();
		panelContenedor.add(panelRegistro);
		panelRegistro.setVisible(false);

	}

	private void mCrearPanelIngresar() {
		panelIngresar = new PanelIngresar();
		panelContenedor.add(panelIngresar);
		panelIngresar.setVisible(false);

	}

	private void mCrearPanelEliminar() {
		panelEliminar = new PanelEliminar();
		panelContenedor.add(panelEliminar);
		panelEliminar.setVisible(false);

	}

	private void mCrearPanelLogin() {
		panelLogin = new PanelLogin();
		panelContenedor.add(panelLogin);
		panelContenedor.setBounds(panelLogin.getBounds());
		panelLogin.setVisible(true);

	}
	
	private void mCrearPanelWorkOut() {
		panelWorkout = new PanelWorkout2();
		panelContenedor.add(panelWorkout);
		panelContenedor.setBounds(panelWorkout.getBounds());
		panelWorkout.setVisible(false);

	}
	

	// *** FIN creaci�n de paneles ***

	public void mVisualizarPaneles(enumAcciones panel) {

		panelRegistro.setVisible(false);
		panelIngresar.setVisible(false);
		panelLogin.setVisible(false);
		panelEliminar.setVisible(false);
		panelWorkout.setVisible(false);


		switch (panel) {
		case CARGAR_PANEL_LOGIN:
			panelLogin.setVisible(true);
			break;
		case CARGAR_PANEL_INSERTAR:
			panelRegistro.setVisible(true);
			break;
		case CARGAR_PANEL_WORKOUT:
			panelWorkout.setVisible(true);
			
		default:
			break;

		}
	}

	public JPanel getPanelContenedor() {
		return panelContenedor;
	}

	public void setPanelContenedor(JPanel panelContenedor) {
		this.panelContenedor = panelContenedor;
	}

	
	
	
	
	public PanelWorkout2 getPanelWorkout() {
		return panelWorkout;
	}

	public void setPanelWorkout(PanelWorkout2 panelWorkout) {
		this.panelWorkout = panelWorkout;
	}

	public PanelRegistro getPanelRegistro() {
		return panelRegistro;
	}

	public void setPanelRegistro(PanelRegistro panelConsultar) {
		this.panelRegistro = panelConsultar;
	}

	public PanelIngresar getPanelIngrsar() {
		return panelIngresar;
	}

	public void setPanelIngresar(PanelIngresar panelIngresar) {
		this.panelIngresar = panelIngresar;
	}

	public PanelLogin getPanelLogin() {
		return panelLogin;
	}

	public void setPanelLogin(PanelLogin panelLogin) {
		this.panelLogin = panelLogin;
	}

	public PanelEliminar getPanelEliminar() {
		return panelEliminar;
	}

	public void setPanelEliminar(PanelEliminar panelEliminar) {
		this.panelEliminar = panelEliminar;
	}

	public JButton getBtnConsultarContactos() {
		return btnConsultarContactos;
	}

	public void setBtnConsultarContactos(JButton btnConsultarContactos) {
		this.btnConsultarContactos = btnConsultarContactos;
	}

	public JButton getBtnInsertarContacto() {
		return btnInsertarContacto;
	}

	public void setBtnAnyadirContacto(JButton btnInsertarContacto) {
		this.btnInsertarContacto = btnInsertarContacto;
	}

	public JButton getBtnModificarContacto() {
		return btnModificarContacto;
	}

	public void setBtnModificarContacto(JButton btnModificarContacto) {
		this.btnModificarContacto = btnModificarContacto;
	}

	public JButton getBtnEliminarContacto() {
		return btnEliminarContacto;
	}

	public void setBtnEliminarContacto(JButton btnEliminarContacto) {
		this.btnEliminarContacto = btnEliminarContacto;
	}

	public void setBtnInsertarContacto(JButton btnInsertarContacto) {
		this.btnInsertarContacto = btnInsertarContacto;
	}

}
