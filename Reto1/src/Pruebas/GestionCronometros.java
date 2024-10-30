package Pruebas;


import modelo.WorkOut;
import vista.PanelEjercicio;

public class GestionCronometros extends Thread {
	private PanelEjercicio pEjercicio;
	private WorkOut workoutSelect;
	private Cronometro cPrincipal;
	private CronometroRegresivo cDescanso;
	private Cronometro cEjercicio;
	private CronometroRegresivo cSerie;



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
		while(cEjercicio.finalizado()==false) {
			
			while(cSerie.finalizado==false) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			activarDescanso();
			cSerie = new CronometroRegresivo ( pEjercicio.getConjuntoDeCronometros().get(1),
					workoutSelect.getEjercicios().get(0).getSeries().get(1).getTiempoSerie());
			pEjercicio.getBtnIniciar().setVisible(true);

		}
	}


	public void pausar() {
		cEjercicio.detener();
		cSerie.detener();
		pEjercicio.getBtnIniciar().setVisible(true);
		pEjercicio.getBtnPausar().setVisible(false);
	}

	public void play() {
		if (!cPrincipal.iniciadoR()) {
			cPrincipal.iniciar();
			cEjercicio.iniciar();
			cSerie.iniciar();
			this.start();

			pEjercicio.getBtnIniciar().setVisible(false);
			pEjercicio.getBtnPausar().setVisible(true);
		} else {
			cEjercicio.activar();
			cSerie.activar();
			pEjercicio.getBtnIniciar().setVisible(false);
			pEjercicio.getBtnPausar().setVisible(true);
		}

		if(cEjercicio.finalizado()) {}
	}

	private void activarDescanso() {
		pEjercicio.getBtnIniciar().setVisible(false);
		pEjercicio.getBtnPausar().setVisible(false);
		cDescanso.iniciar();
	}
}
