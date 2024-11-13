package controlador;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
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

import modelo.Cronometro;
import modelo.CronometroRegresivo;
import modelo.GestionCronometros;
import modelo.Usuario;
import modelo.Workout;
import vista.PanelEjercicio;
import vista.PanelLogin;
import vista.PanelPerfil;
import vista.PanelRegistro;
import vista.PanelWorkout;
import vista.PanelPrincipal;
import vista.PanelPrincipal.enumAcciones;

public class Controlador implements ActionListener, ListSelectionListener {

	private vista.PanelPrincipal vistaPrincipal;
	private Usuario usuarioLogeado;
	private ArrayList<Workout> listaWorkouts;
	private Workout workoutSelect;
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
	public Controlador(vista.PanelPrincipal vistaPrincipal) {
		this.vistaPrincipal = vistaPrincipal;
		this.inicializarControlador();
	}

	private void inicializarControlador() {

		// VENTANA REGISTRO

		this.vistaPrincipal.getPanelRegistro().getBtnRegistrarse().addActionListener(this);
		this.vistaPrincipal.getPanelRegistro().getBtnRegistrarse()
				.setActionCommand(PanelPrincipal.enumAcciones.REGISTRAR_USUARIO.toString());

		this.vistaPrincipal.getPanelRegistro().getBtnVolverLogin().addActionListener(this);
		this.vistaPrincipal.getPanelRegistro().getBtnVolverLogin()
				.setActionCommand(PanelPrincipal.enumAcciones.CARGAR_PANEL_LOGIN.toString());

		// VENTANA LOGIN
		this.vistaPrincipal.getPanelLogin().getBtnLogin().addActionListener(this);
		this.vistaPrincipal.getPanelLogin().getBtnLogin()
				.setActionCommand(PanelPrincipal.enumAcciones.LOGIN.toString());

		this.vistaPrincipal.getPanelLogin().getBtnRegistrarse().addActionListener(this);
		this.vistaPrincipal.getPanelLogin().getBtnRegistrarse()
				.setActionCommand(PanelPrincipal.enumAcciones.CARGAR_PANEL_REGISTRO.toString());

		// VENTANA PERFIL
		this.vistaPrincipal.getPanelPerfil().getBtnEditar().addActionListener(this);
		this.vistaPrincipal.getPanelPerfil().getBtnEditar()
				.setActionCommand(PanelPrincipal.enumAcciones.EDITAR_PERFIL.toString());

		this.vistaPrincipal.getPanelPerfil().getBtnIconoVerContrasena().addActionListener(this);
		this.vistaPrincipal.getPanelPerfil().getBtnIconoVerContrasena()
				.setActionCommand(PanelPrincipal.enumAcciones.VER_CONTRASENA.toString());

		this.vistaPrincipal.getPanelPerfil().getBtnAceptar().addActionListener(this);
		this.vistaPrincipal.getPanelPerfil().getBtnAceptar()
				.setActionCommand(PanelPrincipal.enumAcciones.APLICAR_CAMBIOS_PERFIL.toString());

		this.vistaPrincipal.getPanelPerfil().getBtnVolver().addActionListener(this);
		this.vistaPrincipal.getPanelPerfil().getBtnVolver()
				.setActionCommand(PanelPrincipal.enumAcciones.CARGAR_PANEL_WORKOUT.toString());

		this.vistaPrincipal.getPanelPerfil().getBtnVerHistorico().addActionListener(this);
		this.vistaPrincipal.getPanelPerfil().getBtnVerHistorico()
				.setActionCommand(PanelPrincipal.enumAcciones.CARGAR_PANEL_HISTORICO.toString());

		// VENTANA WORKOUT
		this.vistaPrincipal.getPanelWorkout().getBtnIrAVideo().addActionListener(this);
		this.vistaPrincipal.getPanelWorkout().getBtnIrAVideo()
				.setActionCommand(PanelPrincipal.enumAcciones.ABRIR_NAVEGADOR.toString());

		this.vistaPrincipal.getPanelWorkout().getBtnIniciar().addActionListener(this);
		this.vistaPrincipal.getPanelWorkout().getBtnIniciar()
				.setActionCommand(PanelPrincipal.enumAcciones.CARGAR_PANEL_EJERCICIO.toString());

		this.vistaPrincipal.getPanelWorkout().getWorkoutsList().addListSelectionListener(this);
		this.vistaPrincipal.getPanelWorkout().getWorkoutsList().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		this.vistaPrincipal.getPanelWorkout().getBtnPerfil().addActionListener(this);
		this.vistaPrincipal.getPanelWorkout().getBtnPerfil()
				.setActionCommand(PanelPrincipal.enumAcciones.CARGAR_PANEL_PERFIL.toString());

		// VENTANA PANEL EJERCICIO
		this.vistaPrincipal.getPanelEjercicio().getBtnIniciar().addActionListener(this);
		this.vistaPrincipal.getPanelEjercicio().getBtnIniciar()
				.setActionCommand(PanelPrincipal.enumAcciones.PLAY_PAUSE.toString());

		this.vistaPrincipal.getPanelEjercicio().getBtnPausar().addActionListener(this);
		this.vistaPrincipal.getPanelEjercicio().getBtnPausar()
				.setActionCommand(PanelPrincipal.enumAcciones.PAUSAR.toString());

		this.vistaPrincipal.getPanelEjercicio().getBtnSiguiente().addActionListener(this);
		this.vistaPrincipal.getPanelEjercicio().getBtnSiguiente()
				.setActionCommand(PanelPrincipal.enumAcciones.SIGUIENTE_EJERCICIO.toString());

		this.vistaPrincipal.getPanelEjercicio().getBtnSalir().addActionListener(this);
		this.vistaPrincipal.getPanelEjercicio().getBtnSalir()
				.setActionCommand(PanelPrincipal.enumAcciones.SALIR.toString());

		// VENTANA HISTORICO
		this.vistaPrincipal.getPanelHistorico().getBtnAtras().addActionListener(this);
		this.vistaPrincipal.getPanelHistorico().getBtnAtras()
				.setActionCommand(PanelPrincipal.enumAcciones.CARGAR_PANEL_PERFIL.toString());

	}

	/*** Tratamiento de las acciones ***/

	@Override
	public void actionPerformed(ActionEvent e) {

		PanelPrincipal.enumAcciones accion = PanelPrincipal.enumAcciones.valueOf(e.getActionCommand());

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
			this.vistaPrincipal.getPanelPerfil().actualizarPanelPerfil();
			this.vistaPrincipal.mVisualizarPaneles(accion);
			break;
		case REGISTRAR_USUARIO:
			this.mRegistrarUsuario();
			break;
		case EDITAR_PERFIL:
			this.editarPerfil();
			break;
		case CARGAR_PANEL_HISTORICO:
			if (usuarioLogeado.getHistoricoUsuario().size() > 0) {
				this.vistaPrincipal.getPanelHistorico().setHistoricosDelUsurio(usuarioLogeado.getHistoricoUsuario());
				this.vistaPrincipal.getPanelHistorico().actualizarVentana();
				this.vistaPrincipal.mVisualizarPaneles(accion);

			} else {
				JOptionPane.showMessageDialog(null, "No hay historico");
			}
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

				// Actualizamos la ventana

				pEjercicio.setWorkouSelect(workoutSelect);
				pEjercicio.actualizarVentana(workoutSelect.getEjercicios().get(0));

				cPrincipal = new Cronometro(pEjercicio.getLblCWorkout());
				cEjercicio = new Cronometro(pEjercicio.getLblCTiempoE());
				cSerie = new CronometroRegresivo(pEjercicio.getConjuntoDeCronometros().get(0),
						workoutSelect.getEjercicios().get(0).getSeries().get(0).getTiempoSerie());
				cDescanso = new CronometroRegresivo(pEjercicio.getLblCDescanso(),
						workoutSelect.getEjercicios().get(0).getTiempoDescanso());

				gC = new GestionCronometros(this, pEjercicio, usuarioLogeado, workoutSelect, cPrincipal, cDescanso,
						cEjercicio, cSerie);

				this.vistaPrincipal.mVisualizarPaneles(accion);

			} else {
				JOptionPane.showMessageDialog(null, "Selcciona una opcion");
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
		case SIGUIENTE_EJERCICIO:
			gC.Siguiente();
			break;

		case SALIR:
			gC.finalizarProceso();
			mCargarVentanas(enumAcciones.CARGAR_PANEL_WORKOUT);

			break;

		default:
			break;

		}
	}

	// para poder acceder al metodo desde la propiedad GestionCronometro
	public void cambiarAVentanaWorkout() {
		mCargarVentanas(enumAcciones.CARGAR_PANEL_WORKOUT);

	}

	private void verContrasena() {
		if (this.vistaPrincipal.getPanelPerfil().getPfContrasena().isVisible()) {
			this.vistaPrincipal.getPanelPerfil().getPfContrasena().setVisible(false);
			this.vistaPrincipal.getPanelPerfil().getTfContrasenaVer().setVisible(true);
			this.vistaPrincipal.getPanelPerfil().getTfContrasenaVer().setEditable(true);

		} else {
			this.vistaPrincipal.getPanelPerfil().getPfContrasena().setVisible(true);
			this.vistaPrincipal.getPanelPerfil().getTfContrasenaVer().setVisible(false);
		}
	}

	public void mCargarVentanas(PanelPrincipal.enumAcciones accion) {

		switch (accion) {

		case CARGAR_PANEL_WORKOUT:
			if (listaWorkouts == null) {
				listaWorkouts = new Workout().mObtenerWorkouts();
			}
			this.vistaPrincipal.getPanelWorkout().setWorkouts(listaWorkouts);
			this.vistaPrincipal.getPanelWorkout().setUser(usuarioLogeado);
			this.vistaPrincipal.getPanelWorkout().getFiltroNivel()
					.setSelectedIndex((int) usuarioLogeado.getNivel() + 1);
			this.vistaPrincipal.mVisualizarPaneles(PanelPrincipal.enumAcciones.CARGAR_PANEL_WORKOUT);

			break;

		default:
			break;

		}
	}

	private void mConfirmarLogin(enumAcciones accion) {
		PanelLogin panelLogin = this.vistaPrincipal.getPanelLogin();
		String usuarioIntroducido = panelLogin.getTextFieldUser().getText().trim();
		String passIntroducida = new String(panelLogin.getTextFieldPass().getPassword()).trim();
		if (!usuarioIntroducido.isEmpty() && !passIntroducida.isEmpty()) {
			Usuario usuario = new Usuario();
			usuarioLogeado = usuario.mObtenerUsuario(usuarioIntroducido, passIntroducida);
			if (usuarioLogeado != null) {
				if (hayInternet()) {
					try {
						ProcessBuilder builder = new ProcessBuilder("java", "-jar", "GenerarBackups.jar");
						builder.start();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				mCargarVentanas(PanelPrincipal.enumAcciones.CARGAR_PANEL_WORKOUT);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Algún campo está vacío", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void mRegistrarUsuario() {
		if (hayInternet()) {
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
		} else
			JOptionPane.showMessageDialog(null, "Registro no disponible sin conexión a internet", "Error",
					JOptionPane.ERROR_MESSAGE);
	}

	private boolean validarEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	private void editarPerfil() {
		if (hayInternet()) {
			PanelPerfil panelPerfil = this.vistaPrincipal.getPanelPerfil();
			panelPerfil.getTfNombre().setEditable(true);
			panelPerfil.getTfApellidos().setEditable(true);

			panelPerfil.getFechaNacimientoCalendar().setEnabled(true);
			panelPerfil.getBtnAceptar().setEnabled(true);
			panelPerfil.getBtnEditar().setEnabled(false);
			panelPerfil.getBtnIconoVerContrasena().setEnabled(true);
		} else
			JOptionPane.showMessageDialog(null, "Edición de perfil no disponible sin conexión", "Error",
					JOptionPane.ERROR_MESSAGE);
	}

	// Listeners para la selección en la lista

	public void valueChanged(ListSelectionEvent e) {

		PanelWorkout panelWorkout = vistaPrincipal.getPanelWorkout();
		JList<?> workoutsList = panelWorkout.getWorkoutsList();
		int selectedIndex = workoutsList.getSelectedIndex();
		if (selectedIndex != -1) {
			for (Workout workout : listaWorkouts) {
				if (workout.getNombre().equals(workoutsList.getSelectedValue().toString().split(": ")[1].trim())) {
					workoutSelect = workout;
					break;
				}
			}
			if (workoutSelect != null) {
				panelWorkout.getLblNEjer().setText("Nº Ejercicios: " + workoutSelect.getNumEjercicios());
				panelWorkout.getLblUrl().setText("Video: " + workoutSelect.getVideoURL());
				panelWorkout.getTextArea().setText(workoutSelect.getListaEjercicios());
				panelWorkout.getTextAreaDescripcion().setText(workoutSelect.getDescripcion());
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

	public boolean hayInternet() {
		try {
			InetAddress address = InetAddress.getByName("8.8.8.8");
			return address.isReachable(3000);
		} catch (IOException e) {
			return false;
		}
	}

	private void abrirWebPagina() {
		PanelWorkout panelWk = this.vistaPrincipal.getPanelWorkout();

		if (panelWk.getFiltroNivel().getSelectedIndex() != -1 && panelWk.getWorkoutsList().getSelectedIndex() != -1) {

			try {
				URI uri = new URI(workoutSelect.getVideoURL());
				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					if (desktop.isSupported(Desktop.Action.BROWSE)) {
						desktop.browse(uri);
					}
				}
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}
}
