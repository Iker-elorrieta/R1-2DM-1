package modelo;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;

public class Serie implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// *** Atributos ***
	private String nombre;
	private double repeticiones;
	private String imagenURL;

	private static final String COLLECTION_NAME = "series";
	private static final String FIELD_IMG_URL = "imagenURL";
	private static final String FIELD_REPETICIONES = "repeticiones";

	// *** Constructores ***
	public Serie() {
	}

	public Serie(String nombre, double repeticiones, String imagenURL) {
		this.nombre = nombre;
		this.repeticiones = repeticiones;
		this.imagenURL = imagenURL;

	}

	// *** Métodos Getters y Setters ***
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getRepeticiones() {
		return repeticiones;
	}

	public void setRepeticiones(double repeticiones) {
		this.repeticiones = repeticiones;
	}

	// *** Métodos CRUD ***

	public String getImagenURL() {
		return imagenURL;
	}

	public void setImagenURL(String imagenURL) {
		this.imagenURL = imagenURL;
	}

	// Método para agregar una nueva serie
	public void mIngresarSerie(String coleccionWorkout, String colectionEjercicio, String nombreEjercicio,
			String nombreWorkout) {
		Firestore co = null;
		try {
			co = Conexion.conectar();
			// Como hay un nivel anterior
			DocumentReference workoutDoc = co.collection(coleccionWorkout).document(nombreWorkout);

			DocumentReference ejercicioDoc = workoutDoc.collection(colectionEjercicio).document(nombreEjercicio);
			CollectionReference seriesCollection = ejercicioDoc.collection(COLLECTION_NAME);

			if (!seriesCollection.document(nombre).get().get().exists()) {
				Map<String, Object> nuevaSerie = new HashMap<>();
				nuevaSerie.put(FIELD_IMG_URL, this.imagenURL);
				nuevaSerie.put(FIELD_REPETICIONES, this.repeticiones);

				seriesCollection.document(this.nombre).set(nuevaSerie);

				System.out.println("Serie insertada con éxito");
			} else {
				System.out.println("La serie ya está introducida");
			}

			co.close();
		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Método para obtener todas las series de un ejercicio
	public ArrayList<Serie> mObtenerSeries(String coleccionWorkout, String colectionEjercicio, String nombreEjercicio,
			String nombreWorkout) {
		Firestore co = null;
		ArrayList<Serie> listaSeries = new ArrayList<>();
		try {
			co = Conexion.conectar();
			// Como hay un nivel anterior
			DocumentReference workoutDoc = co.collection(coleccionWorkout).document(nombreWorkout);
			DocumentReference ejercicioDoc = workoutDoc.collection(colectionEjercicio).document(nombreEjercicio);
			ApiFuture<QuerySnapshot> seriesFuture = ejercicioDoc.collection(COLLECTION_NAME).get();
			QuerySnapshot seriesSnapshot = seriesFuture.get();
			List<QueryDocumentSnapshot> ejercicios = seriesSnapshot.getDocuments();

			for (QueryDocumentSnapshot serieDoc : ejercicios) {
				Serie serie = new Serie();
				serie.setNombre(serieDoc.getId());
				serie.setRepeticiones(serieDoc.getDouble(FIELD_REPETICIONES));
				serie.setImagenURL(serieDoc.getString(FIELD_IMG_URL));
				listaSeries.add(serie);
			}
			co.close();
		} catch (InterruptedException | ExecutionException | IOException e) {
			System.out.println("Error: Clase Serie, método mObtenerSeries");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaSeries;
	}

	// Método para actualizar una serie
	public void mActualizarSerie(String coleccionRoot, String nombreWorkout, String nombreEjercicio) {
		Firestore co = null;
		try {
			co = Conexion.conectar();
			DocumentReference ejercicioDoc = co.collection(coleccionRoot).document(nombreEjercicio);
			CollectionReference seriesCollection = ejercicioDoc.collection(COLLECTION_NAME);

			DocumentReference serieDoc = seriesCollection.document(nombre);
			Map<String, Object> updatedSerie = new HashMap<>();
			updatedSerie.put(FIELD_IMG_URL, this.imagenURL);
			updatedSerie.put(FIELD_REPETICIONES, this.repeticiones);

			serieDoc.update(updatedSerie);

			System.out.println("Serie actualizada con éxito");
			co.close();
		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Método para eliminar una serie
	public void mEliminarSerie(String coleccionRoot, String nombreWorkout, String nombreEjercicio) {
		Firestore co = null;
		try {
			co = Conexion.conectar();
			CollectionReference workoutsRoot = co.collection(coleccionRoot);
			DocumentReference workoutDoc = workoutsRoot.document(nombreWorkout);
			DocumentReference ejercicioDoc = workoutDoc.collection("ejercicios").document(nombreEjercicio);
			CollectionReference seriesCollection = ejercicioDoc.collection(COLLECTION_NAME);

			seriesCollection.document(nombre).delete();

			System.out.println("Serie eliminada con éxito");
			co.close();
		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
