package Pruebas;

import javax.swing.JLabel;

public class CronometroRegresivo extends Thread {
	private int valorMinutos = 0;
	private int valorSegundos = 0;
	private boolean iniciado = false;
	private boolean enFuncionamiento = false;
	private JLabel lblVisualizarCronometro;
	private double tiempoEjercicioSegundos;
	boolean finalizado = false;


	public CronometroRegresivo(JLabel lblVisualizarCronometro, double tiempoEjercicio) {
		this.lblVisualizarCronometro = lblVisualizarCronometro;
		this.tiempoEjercicioSegundos = tiempoEjercicio ;
		this.valorMinutos = ((int) tiempoEjercicio / 60);
		this.valorSegundos = ((int) tiempoEjercicio % 60);
	}

	@Override
	public void run() {
		while (iniciado && tiempoEjercicioSegundos > 0) {
			if (enFuncionamiento) {
				tiempoEjercicioSegundos--;
		
				if (valorSegundos != 0) {
					valorSegundos--;
				}
				if (valorSegundos == 0 && valorMinutos != 0) {
					valorMinutos--;
					valorSegundos = 60;
				}
				lblVisualizarCronometro
						.setText(String.format("%02d:%02d", valorMinutos, valorSegundos));
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();

			}
		}
		finalizado = true;
	}

	public void iniciar() {
		if (!iniciado) {
			iniciado = true;
			enFuncionamiento = true;
			start();
		}
	}

	public void detener() {
		enFuncionamiento = false;
	}

	public void activar() {
		enFuncionamiento = true;
	}

	public boolean finalizado() {
		return finalizado;
	}


	public boolean funcionando() {
		return enFuncionamiento;
	}

	public boolean iniciadoR() {
		return iniciado;
	}

}
