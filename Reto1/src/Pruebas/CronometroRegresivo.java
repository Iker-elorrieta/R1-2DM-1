package Pruebas;

import javax.swing.JLabel;

public class CronometroRegresivo extends Thread {
	private int valorMinutos = 0;
	private int valorSegundos = 0;
	private int valorMilisegundos = 0;
	private boolean iniciado = false;
	private boolean enFuncionamiento = false;
	private JLabel lblVisualizarCronometro;
	private double tiempoEjercicio;
	private double tiempo;

	public CronometroRegresivo(JLabel lblVisualizarCronometro, double tiempoEjercicio) {
		this.lblVisualizarCronometro = lblVisualizarCronometro;
		this.tiempoEjercicio = tiempoEjercicio;
	}

	@Override
	public void run() {
		valorMinutos= ((int) tiempoEjercicio / 60);
		valorSegundos= ((int) tiempoEjercicio % 60);
		valorMilisegundos = (int) ((tiempoEjercicio - valorSegundos) * 1000);

		while (iniciado && tiempo<0) {
			if(enFuncionamiento) {
				valorMilisegundos++;

				if (valorMilisegundos == 100) {
					valorSegundos++;
					valorMilisegundos = 0;
				}
				if (valorSegundos == 60) {
					valorMinutos++;
					valorSegundos = 0;
				}
				if (valorMinutos == 60) {
					valorMinutos = 0;
				}

				lblVisualizarCronometro.setText(String.format("%02d:%02d:%02d", valorMinutos, valorSegundos, valorMilisegundos));
				System.out.println(String.format("%02d:%02d:%02d", valorMinutos, valorSegundos, valorMilisegundos));

			}	
			try {
				Thread.sleep(10); 
			} catch (InterruptedException e) {
				e.printStackTrace();

			}
		}
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
		System.out.println("Cambia");
	}


	public void resetear() {
		detener(); 
		valorMinutos = 0;
		valorSegundos = 0;
		valorMilisegundos = 0;
		iniciado = false; 
	}




	public boolean funcionando() {
		return enFuncionamiento;
	}

	public boolean iniciadoR() {
		return iniciado;
	}





}
