package modelo;

import java.util.ArrayList;

public class GenerarWorkoutFireBase {
	public static void main(String[] args) {
		ArrayList<WorkOut> workouts = crearWorkouts();
		for (WorkOut workout : workouts) {
			workout.mIngresarWorkout();
		}
	}

	private static ArrayList<WorkOut> crearWorkouts() {
		ArrayList<WorkOut> workouts = new ArrayList<>();

		// Crear 3 niveles, con 3 workouts por nivel
		for (int nivel = 0; nivel <= 2; nivel++) {
			for (int i = 0; i <= 2; i++) {
				String nombreWorkout = generarNombreWorkout(nivel, i);
				String videoURL = generarVideoURL(nivel, i);
				ArrayList<Ejercicio> ejercicios = crearEjercicios(nombreWorkout);
				WorkOut workout = new WorkOut(nombreWorkout, nivel, videoURL,ejercicios);
				workouts.add(workout);
			}
		}

		return workouts;
	}




	private static String generarVideoURL(int nivel, int numero) {
		// URLs por nivel
		String[][] videoURLs = {
				{
					"https://www.youtube.com/watch?v=39Sn10y7HMM", // Cardio Principiante
					"https://www.youtube.com/watch?v=HwSnZAVvAp4", // Fuerza Principiante
					"https://www.youtube.com/watch?v=msTPZniCgR8"  // Flexibilidad Principiante
				},
				{
					"https://www.youtube.com/watch?v=yOkFhJBEKwo", // Cardio Intermedio
					"https://www.youtube.com/watch?v=Y-S129ji8N0", // Fuerza Intermedio
					"https://www.youtube.com/watch?v=j397EgMX8dA"  // Flexibilidad Intermedia
				},
				{
					"https://www.youtube.com/watch?v=YgxT9sZw4ek", // Cardio Avanzado
					"https://www.youtube.com/watch?v=b6CUJlpmxOE", // Fuerza Avanzada
					"https://www.youtube.com/watch?v=NBpsqBQceSU"  // Flexibilidad Avanzada
				}
		};
		return videoURLs[nivel][numero ];
	}

	private static String generarNombreWorkout(int nivel, int numero) {
		String[][] nombresWorkouts = {
				//nivel 0
				{"Cardio Principiante", "Fuerza Principiante", "Flexibilidad Principiante"},
				//nivel 1
				{"Cardio Intermedio", "Fuerza Intermedio", "Flexibilidad Intermedia"},
				//nivel 2
				{"Cardio Avanzado", "Fuerza Avanzada", "Flexibilidad Avanzada"}
		};

		return nombresWorkouts[nivel][numero];
	}
	private static ArrayList<Ejercicio> crearEjercicios(String nombreWorkout) {
		ArrayList<Ejercicio> ejercicios = new ArrayList<>();
		for (int i = 0; i <= 2; i++) {
			String nombreEjercicio = generarNombreEjercicio(i, nombreWorkout);
			String descripcion = "Descripción del ejercicio " + nombreEjercicio;
			String imagenURL = "img/defecto.png" + i; //Ruta por defecto

			Ejercicio ejercicio = new Ejercicio(nombreEjercicio, descripcion, imagenURL);
			ejercicios.add(ejercicio);
		}
		return ejercicios;
	}

	private static String generarNombreEjercicio(int numEjercicio, String nombreWorkout) {
		String[] ejerciciosCardio = {"Saltos", "Flexiones", "Estiramiento"};
		String[] ejerciciosFuerza = {"Correr", "Levantamiento de pesas", "Yoga"};
		String[] ejerciciosFlexibilidad = {"Sprints", "Sentadillas", "Pilates"};
		String nombre ="";
		if(nombreWorkout.contains("Cardio")) {
			nombre=  ejerciciosCardio[numEjercicio];
		}else if(nombreWorkout.contains("Fuerza")) {
			nombre =  ejerciciosFuerza[numEjercicio];
		}else {
			nombre = ejerciciosFlexibilidad[numEjercicio];
		}
		return nombre + " " + nombreWorkout.split(" ")[1];
	}
}