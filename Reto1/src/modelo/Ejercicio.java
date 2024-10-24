package modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
	// tiempo de descanso 
	// *** Atributos ***
	private String nombre;
	private String descripcion;
	private ArrayList<Serie> series; 
	private String imagenURL;
	private double tiempoDescanso;

	private static final String COLLECTION_NAME = "ejercicios";
	private static final String FIELD_DESCRIPCION = "descripcion";
	private static final String FIELD_SERIES = "series"; 
	private static final String FIELD_VIDEO_URL = "imagenURL";
	private static final String FIELD_TIEMPO_DESCANSO = "tiempoDescanso";

	// *** Constructores ***
	public Ejercicio() {
		this.series = new ArrayList<>();
	}

	public Ejercicio(String nombre, String descripcion, String imagenURL, double tiempoDescanso, ArrayList<Serie> series) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.imagenURL = imagenURL;
		this.tiempoDescanso = tiempoDescanso;
		this.series = series;
	}

	// *** Métodos Getters y Setters ***
	
	
	
	public String getNombre() {
		return nombre;
	}

	public double getTiempoDescanso() {
		return tiempoDescanso;
	}

	public void setTiempoDescanso(double tiempoDescanso) {
		this.tiempoDescanso = tiempoDescanso;
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
			DocumentReference workoutDoc = co.collection(coleccionRoot).document(nombreWorkout);
			CollectionReference ejerciciosCollection = workoutDoc.collection(COLLECTION_NAME);
			System.out.println(COLLECTION_NAME);
			
			if (!ejerciciosCollection.document(nombre).get().get().exists()) {
				Map<String, Object> nuevoEjercicio = new HashMap<>();
				nuevoEjercicio.put(FIELD_DESCRIPCION, this.descripcion);
				nuevoEjercicio.put(FIELD_VIDEO_URL, this.imagenURL);
				nuevoEjercicio.put(FIELD_TIEMPO_DESCANSO, this.tiempoDescanso);
			     for (Serie serie : series) { 
		            	serie.mIngresarSerie(coleccionRoot,COLLECTION_NAME, nombre,nombreWorkout);
		            }
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

			DocumentReference workOutDoc = co.collection(coleccionRoot).document(nombreWorkout);
			ApiFuture<QuerySnapshot> ejerciciosFuture = workOutDoc.collection(COLLECTION_NAME).get();
			QuerySnapshot ejerciciosSnapshot = ejerciciosFuture.get();
			List<QueryDocumentSnapshot> ejercicios = ejerciciosSnapshot.getDocuments();
			System.out.println(new Date());

			for (QueryDocumentSnapshot ejercicio : ejercicios) {

				Ejercicio e = new Ejercicio();
				e.setNombre(ejercicio.getId());
				e.setDescripcion(ejercicio.getString(FIELD_DESCRIPCION));
				e.setImagenURL(ejercicio.getString(FIELD_VIDEO_URL));
				e.setTiempoDescanso(ejercicio.getDouble(FIELD_TIEMPO_DESCANSO));
				e.setSeries(new Serie().mObtenerSeries(coleccionRoot, COLLECTION_NAME, e.getNombre(),nombreWorkout));

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
