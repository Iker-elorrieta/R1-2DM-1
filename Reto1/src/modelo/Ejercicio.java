package modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;

public class Ejercicio {
	//Me falta nº repeticion , tiempo de descanso y nº de series
	// *** Atributos ***
	private String nombre;
	private String descripcion;
	private ArrayList<Serie> series; // ArrayList de series
	private String imagenURL;

	private static final String COLLECTION_NAME = "ejercicios";
	private static final String FIELD_NOMBRE = "nombre";
	private static final String FIELD_DESCRIPCION = "descripcion";
	private static final String FIELD_SERIES = "series"; // Este campo puede contener una lista de objetos
	private static final String FIELD_VIDEO_URL = "imagenURL";

	// *** Constructores ***
	public Ejercicio() {
		this.series = new ArrayList<>();
	}

	public Ejercicio(String nombre, String descripcion, String imagenURL) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.imagenURL = imagenURL;
	}

	// *** Métodos Getters y Setters ***
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public ArrayList<Serie> getSeries() {
		return series;
	}

	public void setSeries(ArrayList<Serie> series) {
		this.series = series;
	}

	public String getImagenURL() {
		return imagenURL;
	}

	public void setImagenURL(String imagenURL) {
		this.imagenURL = imagenURL;
	}

	// *** Métodos CRUD ***

	// Método para agregar un nuevo ejercicio
	public void mIngresarEjercicio(String coleccionRoot, String nombreWorkout) {
		Firestore co = null;
		try {
			co = Conexion.conectar();
			CollectionReference workoutsRoot = co.collection(coleccionRoot);
			DocumentReference workoutDoc = workoutsRoot.document(nombreWorkout);

			CollectionReference ejerciciosCollection = workoutDoc.collection(COLLECTION_NAME);
			if (!ejerciciosCollection.document(nombre).get().get().exists()) {
				Map<String, Object> nuevoEjercicio = new HashMap<>();
				nuevoEjercicio.put(FIELD_DESCRIPCION, this.descripcion);
				nuevoEjercicio.put(FIELD_VIDEO_URL, this.imagenURL);

				ejerciciosCollection.document(this.nombre).set(nuevoEjercicio);

				System.out.println("Ejercicio insertado con éxito");
			} else {
				System.out.println("Ya introducido");
			}

			co.close();
		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// Método para obtener todos los ejercicios
	public ArrayList<Ejercicio> mObtenerEjercicios(String coleccionRoot, String nombreWorkout) {
		Firestore co = null;
		ArrayList<Ejercicio> listaEjercicios = new ArrayList<>();
		try {
			co = Conexion.conectar();

	        ApiFuture<DocumentSnapshot> query = co.collection(coleccionRoot).document(nombreWorkout).get();
	        QuerySnapshot querySnapshot = query.get();
	        List<QueryDocumentSnapshot> ejercicios = querySnapshot.getDocuments();
			
			
			for (QueryDocumentSnapshot ejercicio : ejercicios) {
				Ejercicio e = new Ejercicio();
				e.setNombre(ejercicio.getId());
				e.setDescripcion(ejercicio.getString(FIELD_DESCRIPCION));
				e.setImagenURL(ejercicio.getString(FIELD_VIDEO_URL));
				// No recuperamos las series aquí; eso se haría al obtener un ejercicio específico
				listaEjercicios.add(e);
			}
			co.close();
		} catch (InterruptedException | ExecutionException | IOException e) {
			System.out.println("Error: Clase Ejercicio, método mObtenerEjercicios");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaEjercicios;
	}
}
