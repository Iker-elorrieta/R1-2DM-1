package Pruebas;

public class Cronometro extends Thread {
    private int valorMinutos = 0;
    private int valorSegundos = 0;
    private int valorMilisegundos = 0;
    private boolean iniciado = false;
    private boolean enFuncionamiento = false;

    @Override
    public void run() {
        while (enFuncionamiento) {
            try {
                Thread.sleep(10); 
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

    public String obtenerTiempo() {
        return String.format("%02d:%02d:%02d", valorMinutos, valorSegundos, valorMilisegundos);
    }

    public boolean isFuncionando() {
        return iniciado;
    }
}
