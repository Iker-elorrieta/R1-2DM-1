package Pruebas;
//Preguntar a iker

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

		cPrincipal = new Cronometro(pEjercicio.getLblCWorkout());
		cPrincipal.iniciar();

		cEjercicio = new Cronometro(pEjercicio.getLblCTiempoE());
		cEjercicio.iniciar();

		cSerie = new CronometroRegresivo(pEjercicio.getConjuntoDeCronometros().get(contadorSerie),  ejercicioActual.getSeries().get(contadorSerie).getTiempoSerie());
		cSerie.iniciar();
		cDescanso = new CronometroRegresivo(pEjercicio.getLblCDescanso(), workoutSelect.getEjercicios().get(contadorEjercicio).getTiempoDescanso());


		while (contadorEjercicio < workoutSelect.getEjercicios().size()) {
			ejercicioActual = workoutSelect.getEjercicios().get(contadorEjercicio);

			while (contadorSerie < ejercicioActual.getSeries().size()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// Si la serie ha finalizado
				if (!cSerie.isAlive()) {
					System.out.println("Contador serie" + contadorSerie);	
					contadorSerie++;
					//Tras finalizar la ultima serie por algun motivo entra en el bucle para controlar obligamos a salir 
					if (contadorSerie >= ejercicioActual.getSeries().size()) {
		                break;
		            }		
					iniciarDescanso();

					// Esperar hasta que el descanso termine
					while (cDescanso.isAlive() ) {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
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
			// Reiniciar variables para el siguiente ejercicio
			contadorSerie = -1; // por algun motivo al salir del bucle y volver a entrar automaticametnte accede a la linea 54 y accede per
			contadorEjercicio++;
			// Actualizar la ventana con el nuevo ejercicio
			//En el caso de que sea la ultima ejecucion no se actualiza la ventana si no que se muestra el reloj de descanso por ultima vez
			if (contadorEjercicio < workoutSelect.getEjercicios().size()) {
			    pEjercicio.actualizarVentana(workoutSelect.getEjercicios().get(contadorEjercicio));
			}else {
				cDescanso = new CronometroRegresivo(pEjercicio.getLblCDescanso(), ejercicioActual.getTiempoDescanso());
				iniciarDescanso(); 
			}
		}
	}

	public void pausar() {
		cEjercicio.detener();
		cSerie.detener();

		pEjercicio.getBtnIniciar().setVisible(true);
		pEjercicio.getBtnPausar().setVisible(false);
	}

	public void play() {
		//La primera ejecucion seria desencadenar todo el proceso
		if(cPrincipal== null) {
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
	}
}
