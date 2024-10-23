package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;

public class Principal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Acciones
	public static enum enumAcciones {
		CARGAR_PANEL_LOGIN, CARGAR_PANEL_REGISTRO, CARGAR_PANEL_PERFIL, CARGAR_PANEL_WORKOUT, LOGIN, REGISTRAR_USUARIO,
		EDITAR_PERFIL, VER_CONTRASENA, APLICAR_CAMBIOS_PERFIL, ABRIR_NAVEGADOR, CARGAR_PANEL_EJERCICIO
	}

	private JPanel panelContenedor;
	private PanelRegistro panelRegistro;
	private PanelLogin panelLogin;
	private PanelWorkout2 panelWorkout;
	private PanelPerfil panelPerfil;
	private PanelEjercicio panelEjercicio;
	


	public Principal() {

		// Panel que contiene todo el contenido de la ventana
		mCrearPanelContenedor();

	
		// Panel que contiene el listado de contactos.
		mCrearPanelRegistro();

		mCrearPanelLogin();
		mCrearPanelWorkOut();
		mCrearPanelEjercicio();

		mCrearPanelPerfil();
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

	private void mCrearPanelRegistro() {
		panelRegistro = new PanelRegistro();
		panelContenedor.add(panelRegistro);
		panelContenedor.setBounds(panelRegistro.getBounds());
		panelRegistro.setVisible(false);
	}
	
	private void mCrearPanelEjercicio() {
		panelEjercicio = new PanelEjercicio();
		panelContenedor.add(panelEjercicio);
		panelContenedor.setBounds(panelEjercicio.getBounds());
		panelEjercicio.setVisible(false);
	}

	private void mCrearPanelPerfil() {
		panelPerfil = new PanelPerfil();
		panelContenedor.add(panelPerfil);
		panelContenedor.setBounds(panelPerfil.getBounds());
		panelPerfil.setVisible(false);

	}

	// *** FIN creaci�n de paneles ***

	public void mVisualizarPaneles(enumAcciones panel) {

		panelLogin.setVisible(false);
		panelWorkout.setVisible(false);
		panelRegistro.setVisible(false);
		panelPerfil.setVisible(false);
		panelEjercicio.setVisible(false);
		switch (panel) {
		case CARGAR_PANEL_LOGIN:
			panelLogin.setVisible(true);
			break;
		case CARGAR_PANEL_REGISTRO:
			panelRegistro.setVisible(true);
			break;
		case CARGAR_PANEL_PERFIL:
			panelPerfil.setVisible(true);
			break;
		case CARGAR_PANEL_WORKOUT:
			panelWorkout.setVisible(true);
			break;
		case CARGAR_PANEL_EJERCICIO:
			panelEjercicio.setVisible(true);
			break;
		default:
			break;

		}
	}


	public JPanel getPanelContenedor() {
		return panelContenedor;
	}
	
	

	public PanelEjercicio getPanelEjercicio() {
		return panelEjercicio;
	}

	public void setPanelEjercicio(PanelEjercicio panelEjercicio) {
		this.panelEjercicio = panelEjercicio;
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

	public PanelLogin getPanelLogin() {
		return panelLogin;
	}

	public void setPanelLogin(PanelLogin panelLogin) {
		this.panelLogin = panelLogin;
	}

	public PanelPerfil getPanelPerfil() {
		return panelPerfil;
	}

	public void setPanelPerfil(PanelPerfil panelPerfil) {
		this.panelPerfil = panelPerfil;
	}
}