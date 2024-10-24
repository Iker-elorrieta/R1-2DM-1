package Pruebas;

import javax.swing.JLabel;

public class Cronometro extends Thread {
    private int valorMinutos = 0;
    private int valorSegundos = 0;
    private int valorMilisegundos = 0;
    private boolean iniciado = false;
    private boolean enFuncionamiento = false;
    private JLabel lblVisualizarCronometro;

    public Cronometro(JLabel lblVisualizarCronometro) {
    	this.lblVisualizarCronometro = lblVisualizarCronometro;
    }
    @Override
    public void run() {
        while (enFuncionamiento) {
            try {
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
                Thread.sleep(10); 

            } catch (InterruptedException e) {
                enFuncionamiento = false;
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

    public void resetear() {
        detener(); 
        valorMinutos = 0;
        valorSegundos = 0;
        valorMilisegundos = 0;
        iniciado = false; 
    }

    public boolean isFuncionando() {
        return iniciado;
    }
}
