package modelo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
import principal.Principal;

public class Workout implements Serializable {

	// *** Atributos ***

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private String nombre;
	private double nivel;
	private String videoURL;
	private String descripcion;
	private ArrayList<Ejercicio> ejercicios = new ArrayList<Ejercicio>();
	private double tiempoEstimado;

	private static final String WORKOUTSFILEROUTE = "backups/workouts.dat";

	private static final String COLLECTION_NAME = "workouts";
	private static final String FIELD_NIVEL = "nivel";
	private static final String FIELD_VIDEO_URL = "videoURL";
	private static final String FIELD_DESCRIPCION = "descripcion";
	private static final String FIELD_TIEMPO_ESTIMADO = "tiempoEstimado";

	// *** Constructores ***

	public Workout() {
		this.ejercicios = new ArrayList<>();
	}

	public Workout(String nombre, double nivel, String videoURL, ArrayList<Ejercicio> ejercicios) {
		this.nombre = nombre;
		this.nivel = nivel;
		this.videoURL = videoURL;
		this.ejercicios = ejercicios;
		this.tiempoEstimado = calcularTiempoEstimado();
	}

	// *** Métodos Getters y Setters ***

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getNivel() {
		return nivel;
	}

	public void setNivel(double nivel) {
		this.nivel = nivel;
	}

	public String getVideoURL() {
		return videoURL;
	}

	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}

	public ArrayList<Ejercicio> getEjercicios() {
		return ejercicios;
	}

	public void setEjercicios(ArrayList<Ejercicio> ejercicios) {
		this.ejercicios = ejercicios;
	}

	public double getTiempoEstimado() {
		return tiempoEstimado;
	}

	public int getNumEjercicios() {
		return ejercicios.size();
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	// Calcular el tiempo total estimado sumando la duración de cada ejercicio
	public void setTiempoEstimado(double tiempoEstimado) {
		this.tiempoEstimado = tiempoEstimado;
	}

	private double calcularTiempoEstimado() {
		double totalTiempo = 0;
		for (Ejercicio ejercicio : ejercicios) {
			for (Serie serie : ejercicio.getSeries()) {
				totalTiempo += ejercicio.getTiempoDescanso() + serie.getTiempoSerie();
			}
		}
		return totalTiempo;
	}

	public String getListaEjercicios() {
		String texto = "";
		for (Ejercicio ejercicio : ejercicios) {
			texto += ejercicio.getNombre() + "\n";
		}
		return texto;
	}

	public DocumentReference mObtenerWorkoutByID(String id) {
		Firestore co = null;
		DocumentReference ruta = null;
		try {
			co = Conexion.conectar();
			// Obtener el documento con el ID especificado
			ruta = co.collection(COLLECTION_NAME).document(id);

			co.close();

		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ruta;
	}

	public ArrayList<Workout> mObtenerWorkouts() {
		Principal principal = new Principal();
		ArrayList<Workout> listaWorkouts = new ArrayList<Workout>();

		if (principal.getInternet()) {
			Firestore co = null;
			try {
				co = Conexion.conectar();

				ApiFuture<QuerySnapshot> query = co.collection(COLLECTION_NAME).get();
				QuerySnapshot querySnapshot = query.get();
				List<QueryDocumentSnapshot> workouts = querySnapshot.getDocuments();

				for (QueryDocumentSnapshot workout : workouts) {

					Workout w = new Workout();

					w.setNombre(workout.getId());
					w.setNivel(workout.getDouble(FIELD_NIVEL));
					w.setVideoURL(workout.getString(FIELD_VIDEO_URL));
					w.setDescripcion(workout.getString(FIELD_DESCRIPCION));
					w.setTiempoEstimado(workout.getDouble(FIELD_TIEMPO_ESTIMADO));
					w.setEjercicios(new Ejercicio().mObtenerEjercicios(COLLECTION_NAME, w.getNombre()));

					listaWorkouts.add(w);
				}
				co.close();

			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return listaWorkouts;

		} else {
			try {
				FileInputStream fic = new FileInputStream(WORKOUTSFILEROUTE);
				ObjectInputStream ois = new ObjectInputStream(fic);
				while (fic.getChannel().position() < fic.getChannel().size()) {
					Workout workout = (Workout) ois.readObject();
					listaWorkouts.add(workout);
				}
				ois.close();
				return listaWorkouts;
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
		return listaWorkouts;

	}

	@Override
	public String toString() {
		return "Workout [nombre=" + nombre + ", nivel=" + nivel + ", videoURL=" + videoURL + ", ejercicios="
				+ ejercicios + ", tiempoEstimado=" + tiempoEstimado + "]";
	}

}
