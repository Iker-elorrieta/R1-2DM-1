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
	
	
	 private static int asignarTiempoDescanso(String nombreWorkout) {
	        if (nombreWorkout.contains("Cardio")) {
	            return 30; // 30 segundos para cardio
	        } else if (nombreWorkout.contains("Fuerza")) {
	            return 60; // 60 segundos para fuerza
	        } else {
	            return 45; // 45 segundos para flexibilidad
	        }
	    }
	 
	private static ArrayList<Ejercicio> crearEjercicios(String nombreWorkout) {
		ArrayList<Ejercicio> ejercicios = new ArrayList<>();
		int tiempoDescanso = asignarTiempoDescanso(nombreWorkout);
		for (int i = 0; i <= 2; i++) {
			String nombreEjercicio = generarNombreEjercicio(i, nombreWorkout);
			String descripcion = "Descripción del ejercicio " + nombreEjercicio;
			String imagenURL = "img/defecto.png" + i; //Ruta por defecto
            ArrayList<Serie> series = generarSeries(nombreEjercicio, nombreWorkout);

			Ejercicio ejercicio = new Ejercicio(nombreEjercicio, descripcion, imagenURL, tiempoDescanso, series);
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
	
	 private static ArrayList<Serie> generarSeries(String nombreEjercicio, String nombreWorkout) {
	        ArrayList<Serie> series = new ArrayList<>();
	        int numberOfSeries = nombreWorkout.contains("Avanzado") ? 3 : 2; // El avanzado tiene mas series
	        double repeticionesBase = nombreWorkout.contains("Cardio") ? 15 : 10; // 

	        for (int i = 1; i <= numberOfSeries; i++) {
	            String serieNombre = nombreEjercicio + " - Serie " + i;
	            double repeticiones = repeticionesBase + (i * 2); 
	            String imagenURL = "img/serie_default.png";
	            Serie serie = new Serie(serieNombre, repeticiones, imagenURL);
	            series.add(serie);
	        }

	        return series;
	    }
	
}