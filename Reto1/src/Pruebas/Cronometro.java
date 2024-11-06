package Pruebas;

import javax.swing.JLabel;

public class Cronometro extends Thread {
	private int valorMinutos = 0;
	private int valorSegundos = 0;
	private boolean iniciado = false;
	private boolean finalizado = false;

	private boolean enFuncionamiento = false;
	private JLabel lblVisualizarCronometro;

	public Cronometro(JLabel lblVisualizarCronometro) {
		this.lblVisualizarCronometro = lblVisualizarCronometro;
	}

	@Override
	public void run() {
		while (iniciado) {
			if(enFuncionamiento) {
					valorSegundos++;
					
				if (valorSegundos == 60) {
					valorMinutos++;
					valorSegundos = 0;
				}
				if (valorMinutos == 60) {
					valorMinutos = 0;
				}

				lblVisualizarCronometro.setText(String.format("%02d:%02d", valorMinutos, valorSegundos));

			}	
			try {
				Thread.sleep(1000); 
			} catch (InterruptedException e) {
				e.printStackTrace();

			}
		}
		System.out.println("finaliza");
		finalizado = true;
	}

	public void iniciar() {
		if (!iniciado) {
			iniciado = true;
			enFuncionamiento = true;
			start(); 
		}
	}

	public void resetear () {
		valorMinutos=0;
		valorSegundos=0;
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

	public void TerminarProceso() {
		 iniciado =false;
	}
}
