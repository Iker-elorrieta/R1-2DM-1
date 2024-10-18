package modelo;

import java.util.ArrayList;

public class GenerarWorkoutFireBase {
	public static void main(String[] args) {
		ArrayList<WorkOut> workouts = crearWorkouts();
		for (WorkOut workout: workouts) {
			workout.mIngresarWorkout();

		}
	}

	private static ArrayList<WorkOut> crearWorkouts() {
		ArrayList<WorkOut> workouts = new ArrayList<>();

		// Crear 3 niveles, con 3 workouts por nivel
		for (int nivel = 0; nivel <= 2; nivel++) {
			for (int i = 1; i <= 3; i++) {
				String nombreWorkout = generarNombreWorkout(nivel, i);
				String videoURL = generarVideoURL(nivel, i);

				WorkOut workout = new WorkOut(nombreWorkout, nivel, videoURL);
				workouts.add(workout);
			}
		}

		return workouts;
	}

	private static String generarVideoURL(int nivel, int numero) {
		if (nivel == 0) {
			switch (numero) {
			case 1: return "https://www.youtube.com/watch?v=39Sn10y7HMM"; // Cardio Principiante
			case 2: return "https://www.youtube.com/watch?v=HwSnZAVvAp4"; // Fuerza Principiante
			case 3: return "https://www.youtube.com/watch?v=msTPZniCgR8"; // Flexibilidad Principiante
			}
		} else if (nivel == 1) {
			switch (numero) {
			case 1: return "https://www.youtube.com/watch?v=yOkFhJBEKwo"; // Cardio Intermedio
			case 2: return "https://www.youtube.com/watch?v=Y-S129ji8N0"; // Fuerza Intermedio
			case 3: return "https://www.youtube.com/watch?v=j397EgMX8dA"; // Flexibilidad Intermedia
			}
		} else if (nivel == 2) {
			switch (numero) {
			case 1: return "https://www.youtube.com/watch?v=YgxT9sZw4ek"; // Cardio Avanzado
			case 2: return "https://www.youtube.com/watch?v=b6CUJlpmxOE"; // Fuerza Avanzada
			case 3: return "https://www.youtube.com/watch?v=NBpsqBQceSU"; // Flexibilidad Avanzada
			}
		}
		return "https://www.youtube.com/watch?v=SgyUoY0IZ7A"; // URL por defecto si no se encuentra el nivel o número
	}

	private static String generarNombreWorkout(int nivel, int numero) {
		String[] nombresNivel0 = {"Cardio Principiante", "Fuerza Principiante", "Flexibilidad Principiante"};
		String[] nombresNivel1 = {"Cardio Intermedio", "Fuerza Intermedio", "Flexibilidad Intermedia"};
		String[] nombresNivel2 = {"Cardio Avanzado", "Fuerza Avanzada", "Flexibilidad Avanzada"};

		switch (nivel) {
		case 0: return nombresNivel0[numero - 1];
		case 1: return nombresNivel1[numero - 1];
		case 2: return nombresNivel2[numero - 1];
		default: return "Workout Desconocido";
		}
	}


	
}
