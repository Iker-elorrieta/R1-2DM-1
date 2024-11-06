package Pruebas;
import javax.swing.JOptionPane;

//Preguntar a iker
//Crear una ventana intermedia para el proceso de de descanso tarda mas no se por que 
import modelo.Ejercicio;
import modelo.WorkOut;
import vista.PanelEjercicio;

public class GestionCronometros extends Thread {
	private PanelEjercicio pEjercicio;
	private WorkOut workoutSelect;
	private Cronometro cPrincipal;
	private CronometroRegresivo cDescanso;
	private Cronometro cEjercicio;
	private CronometroRegresivo cSerie;
	private int contadorEjercicio = 0;
	private int contadorSerie = 0;
	boolean siguientePulsado = false;
	boolean finalizar = false;

	public GestionCronometros(PanelEjercicio panelEjercicio, WorkOut workoutSelect,
			Cronometro cPrincipal, CronometroRegresivo cDescanso,
			Cronometro cEjercicio, CronometroRegresivo cSerie) {
		this.pEjercicio = panelEjercicio;
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
		while (contadorEjercicio < workoutSelect.getEjercicios().size() && !finalizar ) {
			ejercicioActual = workoutSelect.getEjercicios().get(contadorEjercicio);

			while (contadorSerie < ejercicioActual.getSeries().size() && !finalizar) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// Si la serie ha finalizado
				if (!cSerie.isAlive()) {
					if(!finalizar) {
						contadorSerie++;
						//Tras finalizar la ultima serie por algun motivo entra en el bucle para controlar obligamos a salir 
						if (contadorSerie >= ejercicioActual.getSeries().size() || finalizar) {
							break;
						}		
						iniciarDescanso();

						//Los campos que varian seran el descanso y la serie
						cDescanso = new CronometroRegresivo(pEjercicio.getLblCDescanso(), workoutSelect.getEjercicios().get(contadorEjercicio).getTiempoDescanso());
						//Actulizamos el descanso en panel
						pEjercicio.getLblCDescanso().setText(String.format("%02d:%02d",
								((int) workoutSelect.getEjercicios().get(contadorEjercicio).getTiempoDescanso() / 60), // min
								((int) workoutSelect.getEjercicios().get(contadorEjercicio).getTiempoDescanso() % 60)));

						// Inicializamos la siguiente serie pero la detenomos hasta que lo active
						cSerie = new CronometroRegresivo(pEjercicio.getConjuntoDeCronometros().get(contadorSerie),  ejercicioActual.getSeries().get(contadorSerie).getTiempoSerie());
						cSerie.iniciar();
						cSerie.detener();	
						pEjercicio.getBtnIniciar().setVisible(true);
					}
				}
			}
			//No se por que pero la ultima serie no se ejecuta el descanso como deberia 
			iniciarDescanso(); 
			// Reiniciar variables para el siguiente ejercicio
			contadorSerie = -1; // por algun motivo al salir del bucle y vuelve a entrar automaticametnte accede a la linea 54 y accede per
			contadorEjercicio++;
			pEjercicio.getBtnIniciar().setVisible(false);
			pEjercicio.getBtnPausar().setVisible(false);


			if (contadorEjercicio < workoutSelect.getEjercicios().size() && !finalizar) {
				pEjercicio.getBtnSiguiente().setVisible(true);

				while(!siguientePulsado) {
					// No hara nada hasta que pulse siguiente
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				Siguiente();
				//tras finalizar reseteamos para la ronda siguietne
				siguientePulsado=false;
			}
		}
	}


	public void finalizarProceso() {
		finalizar = true;
		String nombreEjercicio ="";
		for(int i =0; i<contadorEjercicio;i++ ) {
			nombreEjercicio = workoutSelect.getEjercicios().get(i).getNombre() + "\n";
		}
		String texto = String.format("Tiempo total del ejercicio %s ejercicio realizado %s Porcentaje %d bien hecho", pEjercicio.getLblCWorkout().getText().toString(), 
				nombreEjercicio,(100 * contadorEjercicio) / workoutSelect.getEjercicios().size());
		if(cPrincipal!= null) {
			cPrincipal.TerminarProceso();
			cDescanso.TerminarProceso();
			cEjercicio.TerminarProceso();
			cSerie.TerminarProceso();
		}
		JOptionPane.showMessageDialog(null,texto );


	}

	public void pausar() {
		cEjercicio.detener();
		cSerie.detener();

		pEjercicio.getBtnIniciar().setVisible(true);
		pEjercicio.getBtnPausar().setVisible(false);
	}

	public void play() {
		//La primera ejecucion seria desencadenar todo el proceso
		if(!cPrincipal.isAlive()  ) {
			this.start(); 
		}
		// Activar la serie 
		if(cSerie != null) {
			cSerie.activar();
			cEjercicio.activar();
		}
		pEjercicio.getBtnIniciar().setVisible(false);
		pEjercicio.getBtnPausar().setVisible(true);
	}

	private void iniciarDescanso() {
		cDescanso.iniciar();
		pEjercicio.getBtnIniciar().setVisible(false);
		pEjercicio.getBtnPausar().setVisible(false);
		//Esperamos hasta que el proceso termine
		while (cDescanso.isAlive() ) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	//Al pulsar boton siguiente
	public void Siguiente() {
		siguientePulsado = true;
		cEjercicio.resetear();
		pEjercicio.actualizarVentana(workoutSelect.getEjercicios().get(contadorEjercicio));
		pEjercicio.getBtnIniciar().setVisible(true);
		pEjercicio.getBtnSiguiente().setVisible(false);

	}
}
