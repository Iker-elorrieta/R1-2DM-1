package Pruebas;

import modelo.Ejercicio;
import modelo.Serie;
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
	private boolean enPausa = true; // Inicialmente en pausa para que el usuario comience manualmente
	private boolean enDescanso = false;

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
		

		while (contadorEjercicio < workoutSelect.getEjercicios().size()) {
			ejercicioActual = workoutSelect.getEjercicios().get(contadorEjercicio);
			cDescanso = new CronometroRegresivo(pEjercicio.getLblCDescanso(), workoutSelect.getEjercicios().get(contadorEjercicio).getTiempoDescanso());

			while (contadorSerie < ejercicioActual.getSeries().size()) {

				// Iniciar la serie tanto cunado termina como cuando no existe y es la primera ejecucion

				// Espera hasta que la serie termine o esté en pausa
				while (!cSerie.finalizado()) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				// Si la serie ha finalizado, iniciar descanso automáticamente
				if (cSerie.finalizado() && !enPausa) {
					contadorSerie++;
					iniciarDescanso();
					System.out.println("entra");
					// Esperar hasta que el descanso termine o esté en pausa
					while (cDescanso.isAlive() ) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					if(contadorSerie < ejercicioActual.getSeries().size()) {
						cSerie = new CronometroRegresivo(pEjercicio.getConjuntoDeCronometros().get(contadorSerie),  ejercicioActual.getSeries().get(contadorSerie).getTiempoSerie());
						cDescanso = new CronometroRegresivo(pEjercicio.getLblCDescanso(), workoutSelect.getEjercicios().get(contadorEjercicio).getTiempoDescanso());

						cSerie.iniciar();
						// Pausar después del descanso para esperar el botón "Play" para la siguiente serie
						cSerie.detener();
						pEjercicio.getBtnIniciar().setVisible(true);
		                pEjercicio.actualizarVentana(workoutSelect.getEjercicios().get(contadorEjercicio));


					}


				}
			}
			// Reiniciar variables para el siguiente ejercicio
			contadorSerie = 0;
			contadorEjercicio++;
			if (contadorEjercicio < workoutSelect.getEjercicios().size()) {
				pEjercicio.actualizarVentana(workoutSelect.getEjercicios().get(contadorEjercicio));
			}
		}
	}

	public void pausar() {
		enPausa = true;
		cEjercicio.detener();
		cSerie.detener();

		pEjercicio.getBtnIniciar().setVisible(true);
		pEjercicio.getBtnPausar().setVisible(false);
	}

	public void play() {
		if(cPrincipal== null) {
			this.start(); 
			pEjercicio.getBtnIniciar().setVisible(false);
			pEjercicio.getBtnPausar().setVisible(true); 
		}

		enPausa = false;
		pEjercicio.getBtnIniciar().setVisible(false);
		pEjercicio.getBtnPausar().setVisible(true);

		// Activar la serie 
		if(cSerie != null) {
			cSerie.activar();
			cEjercicio.activar();
		}
	}

	private void iniciarDescanso() {
		enDescanso = true;
		cDescanso.iniciar();

		pEjercicio.getBtnIniciar().setVisible(false);
		pEjercicio.getBtnPausar().setVisible(false);
	}
}
