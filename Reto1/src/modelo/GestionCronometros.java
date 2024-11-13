package modelo;

import java.util.Date;

import javax.swing.JOptionPane;

import controlador.Controlador;
import principal.Principal;
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
	private boolean botonIniciarSeriePulsado = true;
	private boolean serieFinalizada = true;
	private final double segundosCuentaRegresiva =5;

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
cSerie.detener();
		while (contadorEjercicio < workoutSelect.getEjercicios().size() && !finalizar) {
			ejercicioActual = workoutSelect.getEjercicios().get(contadorEjercicio);

			while (contadorSerie < ejercicioActual.getSeries().size() && !finalizar) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				  if (botonIniciarSeriePulsado && contadorEjercicio!=-1) {
					  cSerie.detener();
		                iniciarCuentaRegresiva();
		       
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
							serieFinalizada = true;
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
		botonIniciarSeriePulsado=false; // por si acaso
		serieFinalizada = false;
		Principal principal = new Principal();

		finalizar = true;
		String nombreEjercicio = "";
		for (int i = 0; i < contadorEjercicio; i++) {
			nombreEjercicio += "\n - " + workoutSelect.getEjercicios().get(i).getNombre();
		}
		String tiempoRealizacion = pEjercicio.getLblCWorkout().getText().toString();
		double porcentajeRealizacion = (100 * contadorEjercicio) / workoutSelect.getEjercicios().size();
		String texto = String.format(
				"Tiempo total del ejercicio: %s \r\nPorcentaje de ejercicios realizados %s \r\nEjercicios: %s\r\n¡Bien hecho!",
				tiempoRealizacion, porcentajeRealizacion, nombreEjercicio);

		if (principal.getInternet()) {
			Historial historial = new Historial(workoutSelect, new Date(), porcentajeRealizacion, tiempoRealizacion);
			historial.mIngresarHistorico(usuarioLogeado.getEmail(), workoutSelect);
			usuarioLogeado.insertarNuevoItemHistorial(historial);
			if (cPrincipal != null) {
				cPrincipal.TerminarProceso();
				cDescanso.TerminarProceso();
				cEjercicio.TerminarProceso();
				cSerie.TerminarProceso();
			}
		}
		JOptionPane.showMessageDialog(null, texto);
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
			//SIempre que inicamos la siguiente serie hacemos reseteamos la propiedad de la cuenta atras 5 seg
			if(serieFinalizada) {
			botonIniciarSeriePulsado= true;
			serieFinalizada = false;
			}else {
				
			}
		}
		pEjercicio.getBtnIniciar().setVisible(false);
		pEjercicio.getBtnPausar().setVisible(true);
	}
	
	private void iniciarCuentaRegresiva() {
	    pEjercicio.getLblCProximaSerie().setText("00:05");
	    pEjercicio.getLblCProximaSerie().setVisible(true);
	    pEjercicio.getLblLaProximaSerie().setVisible(true);
	    pEjercicio.getBtnPausar().setVisible(false);

	    // Inicia el cronómetro de cuenta regresiva de 5 segundos
	    CronometroRegresivo cuentaAtras = new CronometroRegresivo(pEjercicio.getLblCProximaSerie(), segundosCuentaRegresiva);
	    cuentaAtras.iniciar();

	    while (cuentaAtras.isAlive()) {
	        try {
	            Thread.sleep(100);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

	    pEjercicio.getBtnPausar().setVisible(true);
	    pEjercicio.getLblCProximaSerie().setVisible(false);
	    pEjercicio.getLblLaProximaSerie().setVisible(false);
        cSerie.activar();
        botonIniciarSeriePulsado = false; // Reseteamos para evitar que se active 
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
		cEjercicio.detener();
		pEjercicio.actualizarVentana(workoutSelect.getEjercicios().get(contadorEjercicio));
		pEjercicio.getBtnIniciar().setVisible(true);
		pEjercicio.getBtnSiguiente().setVisible(false);

	}
}
