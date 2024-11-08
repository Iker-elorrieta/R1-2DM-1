package modelo;

import java.util.ArrayList;

public class GenerarWorkoutFireBase {
	public static void main(String[] args) {
		ArrayList<Workout> workouts = crearWorkouts();
		for (Workout workout : workouts) {
			workout.mIngresarWorkout();
		}
	}

	private static ArrayList<Workout> crearWorkouts() {
		ArrayList<Workout> workouts = new ArrayList<>();

		// Crear 3 niveles, con 3 workouts por nivel
		for (int nivel = 0; nivel <= 2; nivel++) {
			for (int i = 0; i < 2; i++) {
				String nombreWorkout = generarNombreWorkout(nivel, i);
				String videoURL = generarVideoURL(nivel, i);
				ArrayList<Ejercicio> ejercicios = crearEjercicios(nombreWorkout);
				Workout workout = new Workout(nombreWorkout, nivel, videoURL, ejercicios);
				workouts.add(workout);
			}
		}
		return workouts;
	}

	private static String generarVideoURL(int nivel, int numero) {
		// URLs por nivel
		String[][] videoURLs = { { "https://www.youtube.com/watch?v=39Sn10y7HMM", // Cardio Principiante
				"https://www.youtube.com/watch?v=HwSnZAVvAp4", // Fuerza Principiante
				// "https://www.youtube.com/watch?v=msTPZniCgR8" // Flexibilidad Principiante
				}, { "https://www.youtube.com/watch?v=yOkFhJBEKwo", // Cardio Intermedio
						"https://www.youtube.com/watch?v=Y-S129ji8N0", // Fuerza Intermedio
				// "https://www.youtube.com/watch?v=j397EgMX8dA" // Flexibilidad Intermedia
				}, { "https://www.youtube.com/watch?v=YgxT9sZw4ek", // Cardio Avanzado
						"https://www.youtube.com/watch?v=b6CUJlpmxOE", // Fuerza Avanzada
				// "https://www.youtube.com/watch?v=NBpsqBQceSU" // Flexibilidad Avanzada
				} };
		return videoURLs[nivel][numero];
	}

	private static String generarNombreWorkout(int nivel, int numero) {
		String[][] nombresWorkouts = {
				// nivel 0
				{ "Cardio Principiante", "Fuerza Principiante" },
				// nivel 1
				{ "Cardio Intermedio", "Fuerza Intermedio" },
				// nivel 2
				{ "Cardio Avanzado", "Fuerza Avanzada" } };

		return nombresWorkouts[nivel][numero];
	}

	private static int asignarTiempoDescanso(String nombreWorkout) {
		if (nombreWorkout.contains("Cardio")) {
			return 40; // 30 segundos para cardio
		} else {
			return 30; // 60 segundos para fuerza
		}
	}

	private static ArrayList<Ejercicio> crearEjercicios(String nombreWorkout) {
		ArrayList<Ejercicio> ejercicios = new ArrayList<>();
		int tiempoDescanso = asignarTiempoDescanso(nombreWorkout);
		for (int i = 0; i <= 2; i++) {
			String nombreEjercicio = generarNombreEjercicio(i, nombreWorkout);
			String descripcion = "Descripción del ejercicio " + nombreEjercicio;
			String imagenURL = "Multimedia/defecto.png" + i; // Ruta por defecto
			ArrayList<Serie> series = generarSeries(nombreEjercicio, nombreWorkout);

			Ejercicio ejercicio = new Ejercicio(nombreEjercicio, descripcion, imagenURL, tiempoDescanso, series);
			ejercicios.add(ejercicio);
		}
		return ejercicios;
	}

	private static String generarNombreEjercicio(int numEjercicio, String nombreWorkout) {
		String[] ejerciciosCardio = { "Saltos", "Flexiones", "Estiramiento" };
		String[] ejerciciosFuerza = { "Press Banca", "Pull over", "Remo" };
		String nombre = "";
		if (nombreWorkout.contains("Cardio")) {
			nombre = ejerciciosCardio[numEjercicio];
		} else {
			nombre = ejerciciosFuerza[numEjercicio];
		}
		return nombre + " " + nombreWorkout.split(" ")[1];
	}

	private static ArrayList<Serie> generarSeries(String nombreEjercicio, String nombreWorkout) {
		ArrayList<Serie> series = new ArrayList<>();
		int numberOfSeries = nombreWorkout.contains("Avanzado") ? 3 : 2; // El avanzado tiene mas series
		double repeticionesBase = nombreWorkout.contains("Cardio") ? 15 : 10; //
		double tiempoSerie = nombreWorkout.contains("Cardio") ? 20 : 30; //

		for (int i = 1; i <= numberOfSeries; i++) {
			String serieNombre = nombreEjercicio + " - Serie " + i;
			double repeticiones = repeticionesBase + (i * 2);
			String imagenURL = "Multimedia/defecto.png";
			Serie serie = new Serie(serieNombre, repeticiones, imagenURL, tiempoSerie);
			series.add(serie);
		}
		return series;
	}
}