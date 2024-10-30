package controlador;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Pruebas.Cronometro;
import Pruebas.CronometroRegresivo;
import Pruebas.GestionCronometros;
import modelo.GenerarBackups;
import modelo.Usuario;
import modelo.WorkOut;
import vista.PanelEjercicio;
import vista.PanelLogin;
import vista.PanelPerfil;
import vista.PanelRegistro;
import vista.PanelWorkout2;
import vista.Principal;
import vista.Principal.enumAcciones;

public class Controlador implements ActionListener, ListSelectionListener {

	private vista.Principal vistaPrincipal;
	private Usuario usuarioLogeado;
	private ArrayList<WorkOut> listaWorkouts;
	private WorkOut workoutSelect;
	private Cronometro cPrincipal;
	private CronometroRegresivo cDescanso;
	private Cronometro cEjercicio;
	private CronometroRegresivo cSerie;
	GestionCronometros gC;
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


		// VENTANA REGISTRO

		this.vistaPrincipal.getPanelRegistro().getBtnRegistrarse().addActionListener(this);
		this.vistaPrincipal.getPanelRegistro().getBtnRegistrarse()
		.setActionCommand(Principal.enumAcciones.REGISTRAR_USUARIO.toString());

		this.vistaPrincipal.getPanelRegistro().getBtnVolverLogin().addActionListener(this);
		this.vistaPrincipal.getPanelRegistro().getBtnVolverLogin()
		.setActionCommand(Principal.enumAcciones.CARGAR_PANEL_LOGIN.toString());

		// VENTANA LOGIN
		this.vistaPrincipal.getPanelLogin().getBtnLogin().addActionListener(this);
		this.vistaPrincipal.getPanelLogin().getBtnLogin().setActionCommand(Principal.enumAcciones.LOGIN.toString());

		this.vistaPrincipal.getPanelLogin().getBtnRegistrarse().addActionListener(this);
		this.vistaPrincipal.getPanelLogin().getBtnRegistrarse()
		.setActionCommand(Principal.enumAcciones.CARGAR_PANEL_REGISTRO.toString());

		// VENTANA PERFIL
		this.vistaPrincipal.getPanelPerfil().getBtnEditar().addActionListener(this);
		this.vistaPrincipal.getPanelPerfil().getBtnEditar()
		.setActionCommand(Principal.enumAcciones.EDITAR_PERFIL.toString());

		this.vistaPrincipal.getPanelPerfil().getBtnIconoVerContrasena().addActionListener(this);
		this.vistaPrincipal.getPanelPerfil().getBtnIconoVerContrasena()
		.setActionCommand(Principal.enumAcciones.VER_CONTRASENA.toString());

		this.vistaPrincipal.getPanelPerfil().getBtnAceptar().addActionListener(this);
		this.vistaPrincipal.getPanelPerfil().getBtnAceptar()
		.setActionCommand(Principal.enumAcciones.APLICAR_CAMBIOS_PERFIL.toString());

		this.vistaPrincipal.getPanelPerfil().getBtnVolver().addActionListener(this);
		this.vistaPrincipal.getPanelPerfil().getBtnVolver()
		.setActionCommand(Principal.enumAcciones.CARGAR_PANEL_WORKOUT.toString());

		// VENTANA WORKOUT
		this.vistaPrincipal.getPanelWorkout().getBtnIrAVideo().addActionListener(this);
		this.vistaPrincipal.getPanelWorkout().getBtnIrAVideo()
		.setActionCommand(Principal.enumAcciones.ABRIR_NAVEGADOR.toString());

		this.vistaPrincipal.getPanelWorkout().getBtnIniciar().addActionListener(this);
		this.vistaPrincipal.getPanelWorkout().getBtnIniciar()

		.setActionCommand(Principal.enumAcciones.CARGAR_PANEL_EJERCICIO.toString());

		this.vistaPrincipal.getPanelWorkout().getWorkoutsList().addListSelectionListener(this);
		this.vistaPrincipal.getPanelWorkout().getWorkoutsList().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		this.vistaPrincipal.getPanelWorkout().getBtnPerfil().addActionListener(this);
		this.vistaPrincipal.getPanelWorkout().getBtnPerfil()
		.setActionCommand(Principal.enumAcciones.CARGAR_PANEL_PERFIL.toString());

		// VENTANA PANEL EJERCICIO
		this.vistaPrincipal.getPanelEjercicio().getBtnIniciar().addActionListener(this);
		this.vistaPrincipal.getPanelEjercicio().getBtnIniciar()
		.setActionCommand(Principal.enumAcciones.PLAY_PAUSE.toString());

		this.vistaPrincipal.getPanelEjercicio().getBtnPausar().addActionListener(this);
		this.vistaPrincipal.getPanelEjercicio().getBtnPausar()
		.setActionCommand(Principal.enumAcciones.PAUSAR.toString());

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
			this.vistaPrincipal.getPanelRegistro().getTfNombre().setText("");
			this.vistaPrincipal.getPanelRegistro().getTfApellidos().setText("");
			this.vistaPrincipal.getPanelRegistro().getTfEmail().setText("");
			this.vistaPrincipal.getPanelRegistro().getPfContrasena().setText("");
			this.vistaPrincipal.getPanelRegistro().getFechaNacimientoCalendar().setDate(new Date());
			this.vistaPrincipal.mVisualizarPaneles(accion);
			break;
		case CARGAR_PANEL_REGISTRO:
			this.vistaPrincipal.getPanelLogin().getTextFieldUser().setText("");
			this.vistaPrincipal.getPanelLogin().getTextFieldPass().setText("");
			this.vistaPrincipal.mVisualizarPaneles(accion);
			break;
		case CARGAR_PANEL_PERFIL:
			this.vistaPrincipal.getPanelPerfil().setUsuarioLogeado(usuarioLogeado);
			this.vistaPrincipal.getPanelPerfil().getTfNombre().setText(usuarioLogeado.getNombre());
			this.vistaPrincipal.getPanelPerfil().getTfApellidos().setText(usuarioLogeado.getApellidos());
			this.vistaPrincipal.getPanelPerfil().getTfEmail().setText(usuarioLogeado.getEmail());
			this.vistaPrincipal.getPanelPerfil().getPfContrasena().setText(usuarioLogeado.getPass());
			this.vistaPrincipal.getPanelPerfil().getTfContrasenaVer().setText(usuarioLogeado.getPass());
			this.vistaPrincipal.getPanelPerfil().getFechaNacimientoCalendar()
			.setDate(usuarioLogeado.getFechaNacimiento());
			this.vistaPrincipal.mVisualizarPaneles(accion);
			break;
		case REGISTRAR_USUARIO:
			this.mRegistrarUsuario();
			break;
		case EDITAR_PERFIL:
			this.editarPerfil();
			break;
		case APLICAR_CAMBIOS_PERFIL:
			this.aplicarCambiosPerfil();
			break;
		case VER_CONTRASENA:
			this.verContrasena();
			break;
		case ABRIR_NAVEGADOR:
			this.abrirWebPagina();
			break;
		case CARGAR_PANEL_EJERCICIO:
			if (workoutSelect != null) {
				PanelEjercicio pEjercicio = this.vistaPrincipal.getPanelEjercicio();

				pEjercicio.setWorkouSelect(workoutSelect);
				pEjercicio.actualizarVentana();
				// Inicializamos los cronometros
				cPrincipal = new Cronometro(pEjercicio.getLblCWorkout());
				cEjercicio = new Cronometro(pEjercicio.getLblCTiempoE());
				cDescanso = new CronometroRegresivo(pEjercicio.getLblCDescanso(), workoutSelect.getEjercicios().get(0).getTiempoDescanso());
				cSerie = new CronometroRegresivo(
						this.vistaPrincipal.getPanelEjercicio().getConjuntoDeCronometros().get(0),
						this.vistaPrincipal.getPanelEjercicio().getSerieActual().getTiempoSerie());
				pEjercicio.getLblCDescanso().setText(String.format("%02d:%02d",
						((int) workoutSelect.getEjercicios().get(0).getTiempoDescanso() / 60), // min
						((int) workoutSelect.getEjercicios().get(0).getTiempoDescanso() % 60)));// seg

				
				 gC  = new GestionCronometros(pEjercicio, workoutSelect, cPrincipal, cDescanso, cEjercicio, cSerie);
				this.vistaPrincipal.mVisualizarPaneles(accion);

			} else {
				JOptionPane.showMessageDialog(null,"Selcciona una opcion");
			}
			break;
		case CARGAR_PANEL_WORKOUT:
			this.vistaPrincipal.mVisualizarPaneles(accion);
			break;
		case PLAY_PAUSE:
			gC.play();
			break;
		case PAUSAR:
			gC.pausar();

			break;

		default:
			break;

		}
	}

	

	private void verContrasena() {
		if (this.vistaPrincipal.getPanelPerfil().getPfContrasena().isVisible()) {
			this.vistaPrincipal.getPanelPerfil().getPfContrasena().setVisible(false);
			this.vistaPrincipal.getPanelPerfil().getTfContrasenaVer().setVisible(true);
		} else {
			this.vistaPrincipal.getPanelPerfil().getPfContrasena().setVisible(true);
			this.vistaPrincipal.getPanelPerfil().getTfContrasenaVer().setVisible(false);
		}
	}

	public void mCargarVentanas(Principal.enumAcciones accion) {

		switch (accion) {

		case CARGAR_PANEL_WORKOUT:

			listaWorkouts = new WorkOut().mObtenerWorkouts();
			this.vistaPrincipal.getPanelWorkout().setWorkouts(listaWorkouts);
			this.vistaPrincipal.getPanelWorkout().setUser(usuarioLogeado);
			this.vistaPrincipal.getPanelWorkout().getFiltroNivel()
			.setSelectedIndex((int) usuarioLogeado.getNivel() + 1);
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
			if (usuarioLogeado != null) {
				GenerarBackups.main(null);
				mCargarVentanas(Principal.enumAcciones.CARGAR_PANEL_WORKOUT);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Algún campo está vacío", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void mRegistrarUsuario() {
		PanelRegistro panelRegistro = this.vistaPrincipal.getPanelRegistro();

		String nombre = panelRegistro.getTfNombre().getText();
		String apellidos = panelRegistro.getTfNombre().getText();
		String email = panelRegistro.getTfEmail().getText();

		String contrasena = new String(panelRegistro.getPfContrasena().getPassword()).trim();

		Date fechaNacimiento = panelRegistro.getFechaNacimientoCalendar().getDate();

		if (!nombre.isEmpty() && !apellidos.isEmpty() && !email.isEmpty() && !contrasena.isEmpty()
				&& validarEmail(email)) {
			Usuario usuario = new Usuario(nombre, apellidos, email, contrasena, fechaNacimiento);
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

	private void editarPerfil() {
		PanelPerfil panelPerfil = this.vistaPrincipal.getPanelPerfil();
		panelPerfil.getTfNombre().setEditable(true);
		panelPerfil.getTfApellidos().setEditable(true);
		panelPerfil.getFechaNacimientoCalendar().setEnabled(true);
		panelPerfil.getBtnAceptar().setEnabled(true);
		panelPerfil.getBtnEditar().setEnabled(false);
		panelPerfil.getBtnIconoVerContrasena().setEnabled(true);
	}

	// Listeners para la selección en la lista

	public void valueChanged(ListSelectionEvent e) {

		PanelWorkout2 panelWorkout = vistaPrincipal.getPanelWorkout();
		JList<?> workoutsList = panelWorkout.getWorkoutsList();
		int selectedIndex = workoutsList.getSelectedIndex();
		if (selectedIndex != -1) {
			for (WorkOut workout : listaWorkouts) {
				if (workout.getNombre().equals(workoutsList.getSelectedValue().toString().split(": ")[1].trim())) {
					workoutSelect = workout;
					break;
				}
			}
			if (workoutSelect != null) {
				panelWorkout.getLblNEjer().setText("Nº Ejercicios: " + workoutSelect.getNumEjercicios());
				panelWorkout.getLblUrl().setText("Video: " + workoutSelect.getVideoURL());

				panelWorkout.getTextArea().setText(workoutSelect.getListaEjercicios());
			}

		}
	}

	private void aplicarCambiosPerfil() {
		PanelPerfil panelPerfil = this.vistaPrincipal.getPanelPerfil();

		Usuario usuario = new Usuario();
		usuario.setEmail(usuarioLogeado.getEmail());
		usuario.setNombre(panelPerfil.getTfNombre().getText());
		usuario.setApellidos(panelPerfil.getTfApellidos().getText());
		usuario.setPass(panelPerfil.getTfContrasenaVer().getText());
		usuario.setFechaNacimiento((panelPerfil.getFechaNacimientoCalendar().getDate()));

		boolean exito = usuario.mModificarUsuario();

		panelPerfil.getTfNombre().setEditable(false);
		panelPerfil.getTfApellidos().setEditable(false);
		panelPerfil.getPfContrasena().setVisible(true);
		panelPerfil.getTfContrasenaVer().setVisible(false);
		panelPerfil.getFechaNacimientoCalendar().setEnabled(false);
		panelPerfil.getBtnAceptar().setEnabled(false);
		panelPerfil.getBtnEditar().setEnabled(true);
		panelPerfil.getBtnIconoVerContrasena().setEnabled(false);

		if (exito) {
			usuarioLogeado = usuario;
			panelPerfil.getPfContrasena().setText(usuarioLogeado.getPass());
		}
	}

	private void abrirWebPagina() {
		PanelWorkout2 panelWk = this.vistaPrincipal.getPanelWorkout();

		if (panelWk.getFiltroNivel().getSelectedIndex() != -1 && panelWk.getWorkoutsList().getSelectedIndex() != -1) {

			try {
				URI uri = new URI(workoutSelect.getVideoURL());
				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					if (desktop.isSupported(Desktop.Action.BROWSE)) {
						desktop.browse(uri);
					}
				} else {
					System.out.println("Desktop no soportado");
				}
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}
}