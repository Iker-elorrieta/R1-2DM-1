package modelo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class GenerarBackups {

	public static void main(String[] args) {
		escribirUsuariosEnArchivo(new Usuario().mObtenerTodosLosUsuarios());
		escribirWorkoutsEnArchivo(new WorkOut().mObtenerWorkouts());
	}

	private static final String USUARIOSFILEROUTE = "backups/usuarios.dat";
	private static final String WORKOUTSFILEROUTE = "backups/workouts.dat";

	private static void escribirUsuariosEnArchivo(ArrayList<Usuario> usuarios) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USUARIOSFILEROUTE))) {
			for (Usuario usu : usuarios) {
				oos.writeObject(usu);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void escribirWorkoutsEnArchivo(ArrayList<WorkOut> workouts) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(WORKOUTSFILEROUTE))) {
			for (WorkOut wk : workouts) {
				oos.writeObject(wk);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	private static void leerWorkoutsDesdeArchivo() {
//		ArrayList<WorkOut> wkee = new ArrayList<>();
//		ArrayList<Ejercicio> ejerrs = new ArrayList<>();
//		ArrayList<Serie> series = new ArrayList<>();
//
//		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(WORKOUTSFILEROUTE))) {
//			wkee = (ArrayList<WorkOut>) ois.readObject();
//			for (WorkOut wk : wkee) {
//				System.out.println(wk.getNombre());
//				ejerrs = wk.getEjercicios();
//				for (Ejercicio ejer : ejerrs) {
//					System.out.println("	" + ejer.getNombre());
//					series = ejer.getSeries();
//					for (Serie serie : series) {
//						System.out.println("		-" + serie.getNombre());
//					}
//				}
//				System.out.println("\n");
//			}
//		} catch (FileNotFoundException e) {
//			System.out.println("Archivo no encontrado, se creará uno nuevo.");
//		} catch (IOException | ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
//
//	private static void leerUsuariosDesdeArchivo() {
//		ArrayList<Usuario> usuarios = new ArrayList<>();
//
//		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USUARIOSFILEROUTE))) {
//			usuarios = (ArrayList<Usuario>) ois.readObject();
//			for (Usuario nuevoUsuario : usuarios) {
//				System.out.println(nuevoUsuario.getEmail());
//			}
//		} catch (FileNotFoundException e) {
//			System.out.println("Archivo no encontrado, se creará uno nuevo.");
//		} catch (IOException | ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
}