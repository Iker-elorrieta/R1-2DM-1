package Pruebas;

import javax.swing.JLabel;

public class CronometroRegresivo extends Thread {
	private int valorMinutos = 0;
	private int valorSegundos = 0;
	private int valorMilisegundos = 0;
	private boolean iniciado = false;
	private boolean enFuncionamiento = false;
	private JLabel lblVisualizarCronometro;
	private double tiempoEjercicioMiliSegundos;

	public CronometroRegresivo(JLabel lblVisualizarCronometro, double tiempoEjercicio) {
		this.lblVisualizarCronometro = lblVisualizarCronometro;
		this.tiempoEjercicioMiliSegundos = tiempoEjercicio * 100;
		this.valorMinutos = ((int) tiempoEjercicio / 60);
		this.valorSegundos = ((int) tiempoEjercicio % 60);
		this.valorMilisegundos = (int) ((tiempoEjercicio - valorSegundos) * 1000);
	}

	@Override
	public void run() {

		System.out.println("Entra en el run del cronometro");
		System.out.println(iniciado);
		System.out.println(valorMilisegundos);
		;
		while (iniciado && tiempoEjercicioMiliSegundos > 0) {
			System.out.println("Entra en el bucle");
			if (enFuncionamiento) {
				System.out.println("Entra en el funcionamiento");
				tiempoEjercicioMiliSegundos--;
				System.out.println(tiempoEjercicioMiliSegundos);
				if (valorMilisegundos != 0) {
					valorMilisegundos--;
				}

				if (valorMilisegundos == 0 && valorSegundos != 0) {
					valorMilisegundos = 100;
					valorSegundos--;
				}
				if (valorSegundos == 0 && valorMinutos != 0) {
					valorMinutos--;
				}

				lblVisualizarCronometro
						.setText(String.format("%02d:%02d:%02d", valorMinutos, valorSegundos, valorMilisegundos));
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
			System.out.println("Entra en el metodo");
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
