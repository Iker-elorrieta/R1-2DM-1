package principal;

import controlador.Controlador;

public class Principal {

	private static boolean internet;

	public static void main(String[] args) {
		try {
			// Creamos el objeto vista.
			vista.Principal ventanaPrincipal = new vista.Principal();
			ventanaPrincipal.setVisible(true);

			// Creamos en controlador con acceso al modelo y la vista
			Controlador controlador = new Controlador(ventanaPrincipal);

			// Comprobamos si hay internet o no
			setInternet(controlador.hayInternet());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean getInternet() {
		return internet;
	}

	public static void setInternet(boolean internet) {
		Principal.internet = internet;
	}
}