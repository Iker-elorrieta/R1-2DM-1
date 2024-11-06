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
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;
import principal.Principal;

public class WorkOut implements Serializable {

	// *** Atributos ***

	/**
	 * 
	 */

	private principal.Principal principal = new Principal();

	private static final long serialVersionUID = 1L;
	private String nombre;
	private double nivel;
	private String videoURL;
	private ArrayList<Ejercicio> ejercicios = new ArrayList<Ejercicio>();
	private double tiempoEstimado;

	private static final String WORKOUTSFILEROUTE = "backups/workouts.dat";

	private static final String COLLECTION_NAME = "workouts";
	private static final String FIELD_NIVEL = "nivel";
	private static final String FIELD_VIDEO_URL = "videoURL";
	private static final String FIELD_TIEMPO_ESTIMADO = "tiempoEstimado";

	// *** Constructores ***

	public WorkOut() {
		this.ejercicios = new ArrayList<>();
	}

	public WorkOut(String nombre, double nivel, String videoURL, ArrayList<Ejercicio> ejercicios) {
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

	// *** Métodos CRUD ***

	// Método para agregar un nuevo workout
	public void mIngresarWorkout() {
		Firestore co = null;
		try {
			co = Conexion.conectar();
			CollectionReference root = co.collection(COLLECTION_NAME);

			if (!root.document(nombre).get().get().exists()) {
				Map<String, Object> nuevoWorkout = new HashMap<>();
				nuevoWorkout.put(FIELD_NIVEL, this.nivel);
				nuevoWorkout.put(FIELD_VIDEO_URL, this.videoURL);
				nuevoWorkout.put(FIELD_TIEMPO_ESTIMADO, this.tiempoEstimado);

				for (Ejercicio ejercicio : ejercicios) {
					ejercicio.mIngresarEjercicio(COLLECTION_NAME, nombre);

				}

				root.document(this.nombre).set(nuevoWorkout);

			}
			co.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public WorkOut mObtenerWorkoutByID(String id) {
	    Firestore co = null;
	    WorkOut w = null;
	    try {
	        co = Conexion.conectar();

	        // Obtener el documento con el ID especificado
	        DocumentReference workoutDocRef = co.collection(COLLECTION_NAME).document(id);
	        ApiFuture<DocumentSnapshot> future = workoutDocRef.get();
	        DocumentSnapshot workout = future.get();

	        // Verificar si el documento existe
	        if (workout.exists()) {
	            w = new WorkOut();

	            // Asignar valores del documento al objeto WorkOut
	            w.setNombre(workout.getId());
	            w.setNivel(workout.getDouble(FIELD_NIVEL));
	            w.setVideoURL(workout.getString(FIELD_VIDEO_URL));
	            w.setTiempoEstimado(workout.getDouble(FIELD_TIEMPO_ESTIMADO));

	            // Obtener los ejercicios relacionados con el WorkOut
	            w.setEjercicios(new Ejercicio().mObtenerEjercicios(COLLECTION_NAME, w.getNombre()));
	        } else {
	            System.out.println("No existe un WorkOut con el ID proporcionado.");
	        }

	        co.close();

	    } catch (InterruptedException | ExecutionException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return w;
	}

	
	public ArrayList<WorkOut> mObtenerWorkouts() {
		ArrayList<WorkOut> listaWorkOuts = new ArrayList<WorkOut>();
		if (principal.getInternet()) {
			Firestore co = null;
			try {
				co = Conexion.conectar();

				ApiFuture<QuerySnapshot> query = co.collection(COLLECTION_NAME).get();
				QuerySnapshot querySnapshot = query.get();
				List<QueryDocumentSnapshot> workouts = querySnapshot.getDocuments();

				for (QueryDocumentSnapshot workout : workouts) {

					WorkOut w = new WorkOut();

					w.setNombre(workout.getId());
					w.setNivel(workout.getDouble(FIELD_NIVEL));
					w.setVideoURL(workout.getString(FIELD_VIDEO_URL));
					w.setTiempoEstimado(workout.getDouble(FIELD_TIEMPO_ESTIMADO));
					w.setEjercicios(new Ejercicio().mObtenerEjercicios(COLLECTION_NAME, w.getNombre()));

					listaWorkOuts.add(w);
				}
				co.close();

			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return listaWorkOuts;
		} else {
			try {
				FileInputStream fic = new FileInputStream(WORKOUTSFILEROUTE);
				ObjectInputStream ois = new ObjectInputStream(fic);
				while (fic.getChannel().position() < fic.getChannel().size()) {
					WorkOut workout = (WorkOut) ois.readObject();
					listaWorkOuts.add(workout);
				}
				ois.close();
				return listaWorkOuts;
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
		return listaWorkOuts;
	}

}
