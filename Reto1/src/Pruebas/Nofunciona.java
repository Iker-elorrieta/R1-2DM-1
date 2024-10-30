package Pruebas;

import javax.swing.JLabel;

import modelo.WorkOut;
import vista.PanelEjercicio;

public class Nofunciona extends Thread {
	private PanelEjercicio pEjercicio;
	private WorkOut workoutSelect;
	private Cronometro cPrincipal;
	private CronometroRegresivo cDescanso;
	private Cronometro cEjercicio;
	private CronometroRegresivo cSerie;
	private int  posicionEjercicio =0;
	private int  posicoinSerie =0;
	


	public Nofunciona (PanelEjercicio panelEjercicio ,WorkOut workoutSelect ,	 
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
		while(posicionEjercicio<workoutSelect.getEjercicios().size()) {
			
			while(cSerie.finalizado==false) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			activarDescanso();

		}
		
	}


	private void pausar() {
		cEjercicio.detener();
		cSerie.detener();
		pEjercicio.getBtnIniciar().setVisible(true);
		pEjercicio.getBtnPausar().setVisible(false);
	}

	private void play() {
		if (!cPrincipal.iniciadoR()) {
			cPrincipal.iniciar();
			cEjercicio.iniciar();
			cSerie.iniciar();

			pEjercicio.getBtnIniciar().setVisible(false);
			pEjercicio.getBtnPausar().setVisible(true);
		} else {
			cEjercicio.activar();

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
