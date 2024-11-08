package modelo;

import java.util.Date;

import javax.swing.JOptionPane;

import controlador.Controlador;
import vista.PanelEjercicio;

public class GestionCronometros extends Thread {
	private PanelEjercicio pEjercicio;
	private Workout workoutSelect;
	private Cronometro cPrincipal;
	private CronometroRegresivo cDescanso;
	private Cronometro cEjercicio;
	private CronometroRegresivo cSerie;
	private int contadorEjercicio = 0;
	private int contadorSerie = 0;
	boolean siguientePulsado = false;
	boolean finalizar = false;
	private Usuario usuarioLogeado;
	private Controlador controlador;

	public GestionCronometros(Controlador controlador, PanelEjercicio panelEjercicio, Usuario usarioLogeado,
			Workout workoutSelect, Cronometro cPrincipal, CronometroRegresivo cDescanso, Cronometro cEjercicio,
			CronometroRegresivo cSerie) {
		this.controlador = controlador;
		this.pEjercicio = panelEjercicio;
		this.usuarioLogeado = usarioLogeado;
		this.workoutSelect = workoutSelect;
		this.cPrincipal = cPrincipal;
		this.cDescanso = cDescanso;
		this.cEjercicio = cEjercicio;
		this.cSerie = cSerie;

	}

	@Override
	public void run() {
		Ejercicio ejercicioActual = workoutSelect.getEjercicios().get(contadorEjercicio);
		cPrincipal.iniciar();
		cEjercicio.iniciar();
		cSerie.iniciar();
		while (contadorEjercicio < workoutSelect.getEjercicios().size() && !finalizar) {
			ejercicioActual = workoutSelect.getEjercicios().get(contadorEjercicio);

			while (contadorSerie < ejercicioActual.getSeries().size() && !finalizar) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// Si la serie ha finalizado
				if (!cSerie.isAlive()) {
					if (!finalizar) {
						contadorSerie++;
						// Tras finalizar la ultima serie por algun motivo entra en el bucle para
						// controlar obligamos a salir
						if (contadorSerie >= ejercicioActual.getSeries().size() || finalizar) {
							break;
						}
						iniciarDescanso();

						// Los campos que varian seran el descanso y la serie
						cDescanso = new CronometroRegresivo(pEjercicio.getLblCDescanso(),
								workoutSelect.getEjercicios().get(contadorEjercicio).getTiempoDescanso());
						// Actulizamos el descanso en panel
						pEjercicio.getLblCDescanso().setText(String.format("%02d:%02d",
								((int) workoutSelect.getEjercicios().get(contadorEjercicio).getTiempoDescanso() / 60), // min
								((int) workoutSelect.getEjercicios().get(contadorEjercicio).getTiempoDescanso() % 60)));

						// Inicializamos la siguiente serie pero la detenomos hasta que lo active
						cSerie = new CronometroRegresivo(pEjercicio.getConjuntoDeCronometros().get(contadorSerie),
								ejercicioActual.getSeries().get(contadorSerie).getTiempoSerie());
						cSerie.iniciar();
						cSerie.detener();
						pEjercicio.getBtnIniciar().setVisible(true);
					}
				}
			}
			if (!finalizar) {
				// No se por que pero la ultima serie no se ejecuta el descanso como deberia
				iniciarDescanso();
				// Reiniciar variables para el siguiente ejercicio
				contadorSerie = -1; // por algun motivo al salir del bucle y vuelve a entrar automaticametnte accede
									// a la linea 54 y accede per
				contadorEjercicio++;
				pEjercicio.getBtnIniciar().setVisible(false);
				pEjercicio.getBtnPausar().setVisible(false);

				if (contadorEjercicio < workoutSelect.getEjercicios().size() && !finalizar) {
					pEjercicio.getBtnSiguiente().setVisible(true);

					while (!siguientePulsado) {
						// No hara nada hasta que pulse siguiente
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					Siguiente();
					// tras finalizar reseteamos para la ronda siguietne
					siguientePulsado = false;
				}
			}
		}
		if (!finalizar) {
			finalizarProceso();
			controlador.cambiarAVentanaWorkout();
		}
	}

	public void finalizarProceso() {
		finalizar = true;
		String nombreEjercicio = "";
		for (int i = 0; i < contadorEjercicio; i++) {
			nombreEjercicio += workoutSelect.getEjercicios().get(i).getNombre() + "\n";
		}
		String tiempoRealizacion = pEjercicio.getLblCWorkout().getText().toString();
		double porcentajeRealizacion = (100 * contadorEjercicio) / workoutSelect.getEjercicios().size();
		String texto = String.format("Tiempo total del ejercicio %s ejercicio realizado %s Porcentaje %s  bien hecho",
				tiempoRealizacion, nombreEjercicio, porcentajeRealizacion);

		Historial historial = new Historial(workoutSelect, new Date(), porcentajeRealizacion, tiempoRealizacion);
		historial.mIngresarHistorico(usuarioLogeado.getEmail(), workoutSelect);
		usuarioLogeado.insertarNuevoItemHistorial(historial);
		if (cPrincipal != null) {
			cPrincipal.TerminarProceso();
			cDescanso.TerminarProceso();
			cEjercicio.TerminarProceso();
			cSerie.TerminarProceso();
		}
		JOptionPane.showMessageDialog(null, texto);
		// El cambio de ventana

	}

	public void pausar() {
		cEjercicio.detener();
		cSerie.detener();
		cPrincipal.detener();
		pEjercicio.getBtnIniciar().setVisible(true);
		pEjercicio.getBtnPausar().setVisible(false);
	}

	public void play() {
		// La primera ejecucion seria desencadenar todo el proceso
		if (!cPrincipal.isAlive()) {
			this.start();
		}
		// Activar la serie
		if (cSerie != null) {
			cSerie.activar();
			cEjercicio.activar();
			cPrincipal.activar();

		}
		pEjercicio.getBtnIniciar().setVisible(false);
		pEjercicio.getBtnPausar().setVisible(true);
	}

	private void iniciarDescanso() {
		cDescanso.iniciar();
		pEjercicio.getBtnIniciar().setVisible(false);
		pEjercicio.getBtnPausar().setVisible(false);
		// Esperamos hasta que el proceso termine
		while (cDescanso.isAlive()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	// Al pulsar boton siguiente
	public void Siguiente() {
		siguientePulsado = true;
		cEjercicio.resetear();
		pEjercicio.actualizarVentana(workoutSelect.getEjercicios().get(contadorEjercicio));
		pEjercicio.getBtnIniciar().setVisible(true);
		pEjercicio.getBtnSiguiente().setVisible(false);

	}
}
