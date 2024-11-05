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
	private int  contadorEjercicio =0;
	private int  contadorSerie =0;
	boolean enPausa = false;
	boolean serieCambiada = false;
	boolean ejercioCambiada = false;

	public GestionCronometros (PanelEjercicio panelEjercicio ,WorkOut workoutSelect ,	 
			Cronometro cPrincipal, CronometroRegresivo cDescanso,  Cronometro cEjercicio,  CronometroRegresivo cSerie) {
		this.pEjercicio = panelEjercicio;
		this.workoutSelect = workoutSelect;
		this.cPrincipal = cPrincipal;
		this.cDescanso = cDescanso;
		this.cEjercicio = cEjercicio;
		this.cSerie = cSerie;


	}
	@Override
	public void run() {
		Ejercicio ejercicioActual =workoutSelect.getEjercicios().get(contadorEjercicio);

		cPrincipal = new Cronometro(pEjercicio.getLblCWorkout());
		cPrincipal.iniciar();

		cEjercicio = new Cronometro(pEjercicio.getLblCTiempoE());
		cEjercicio.iniciar();



		while (contadorEjercicio < workoutSelect.getEjercicios().size()) {


			while (contadorSerie < ejercicioActual.getSeries().size()) {
				Serie serieActual = ejercicioActual.getSeries().get(contadorSerie);
				cSerie = new CronometroRegresivo(pEjercicio.getConjuntoDeCronometros().get(contadorSerie),
						serieActual.getTiempoSerie());
				cSerie.iniciar();

				// Espera hasta que la serie se haya completado 
				while (!cSerie.finalizado() && !enPausa) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				// Si está en pausa, no aumentamos el contador de series
				if (!enPausa) {
					contadorSerie++;
					activarDescanso();
					
					
					if(ejercicioActual.getSeries().size() == contadorSerie && contadorEjercicio < workoutSelect.getEjercicios().size()) {
						//cambiamos de jercicio
						contadorEjercicio++;
						contadorSerie = 0;
						ejercicioActual = workoutSelect.getEjercicios().get(contadorEjercicio);
						pEjercicio.actualizarVentana(ejercicioActual);
					}


					while (!cDescanso.finalizado() && !enPausa) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						if (cDescanso.finalizado()) {
						    pEjercicio.getBtnIniciar().setVisible(true);
						    pEjercicio.getBtnPausar().setVisible(false);
						}
					}
				}
			}
			contadorSerie = 0;
			contadorEjercicio++;
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
		if(!this.isAlive()) {
			this.start(); 
		}else {
			enPausa = false; 
			pEjercicio.getBtnIniciar().setVisible(false);
			pEjercicio.getBtnPausar().setVisible(true); 
		}
	}

	private void activarDescanso() {
		cDescanso = new CronometroRegresivo(pEjercicio.getLblCDescanso(),
				workoutSelect.getEjercicios().get(contadorEjercicio).getTiempoDescanso());
		cDescanso.iniciar();
		pEjercicio.getBtnIniciar().setVisible(false);
		pEjercicio.getBtnPausar().setVisible(false);
		
		
		
		
		
	}
}
