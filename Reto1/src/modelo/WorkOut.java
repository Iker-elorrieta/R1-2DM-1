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

public class WorkOut {

	// *** Atributos ***
	private String nombre;
	private String descripcion;
	private double nivel;
	private String videoURL;
	private ArrayList<Ejercicio> ejercicios;
	private double tiempoEstimado;

	private static final String COLLECTION_NAME = "workouts";
	private static final String FIELD_NOMBRE = "nombre";
	private static final String FIELD_DESCRIPCION = "descripcion";
	private static final String FIELD_NIVEL = "nivel";
	private static final String FIELD_VIDEO_URL = "videoURL";
	private static final String FIELD_EJERCICIOS = "ejercicios";
	private static final String FIELD_TIEMPO_ESTIMADO = "tiempoEstimado";

	// *** Constructores ***

	public WorkOut() {
		this.ejercicios = new ArrayList<>();
	}

	//Para poder crear los WorKouts de moemnto sin ejercicios
	public WorkOut(String nombre, String descripcion, double nivel, String videoURL) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.nivel = nivel;
		this.videoURL = videoURL;
	}

	public WorkOut(String nombre, String descripcion, double nivel, String videoURL, ArrayList<Ejercicio> ejercicios) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.nivel = nivel;
		this.videoURL = videoURL;
		this.ejercicios = ejercicios;
		//  this.tiempoEstimado = calcularTiempoEstimado();
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

	// Calcular el tiempo total estimado sumando la duración de cada ejercicio
	/* private double calcularTiempoEstimado() {
        double totalTiempo = 0;
        for (Ejercicio ejercicio : ejercicios) {
            totalTiempo += ejercicio.getDuracionSegundos() + ejercicio.getDescansoSegundos();
        }
        return totalTiempo;
    }
	 */



	// *** Métodos CRUD ***

	// Método para agregar un nuevo workout
	public void mIngresarWorkout() {
		Firestore co = null;
		try {
			co = Conexion.conectar();
			CollectionReference root = co.collection(COLLECTION_NAME);

			if(!root.document(nombre).get().get().exists()) {
				Map<String, Object> nuevoWorkout = new HashMap<>();
				nuevoWorkout.put(FIELD_NOMBRE, this.nombre);
				nuevoWorkout.put(FIELD_DESCRIPCION, this.descripcion);
				nuevoWorkout.put(FIELD_NIVEL, this.nivel);
				nuevoWorkout.put(FIELD_VIDEO_URL, this.videoURL);
				// nuevoWorkout.put(FIELD_TIEMPO_ESTIMADO, this.tiempoEstimado);
				root.document(this.nombre).set(nuevoWorkout);

				System.out.println("Workout insertado con éxito");
			}else{
				System.out.println("Ya introducido");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}









	// Método para obtener un workout por su nombre
	public WorkOut mObtenerWorkout(String nombreWorkout) {
		Firestore co = null;

		try {
			co = Conexion.conectar();
			DocumentSnapshot workoutSnapshot = co.collection(COLLECTION_NAME).document(nombreWorkout).get().get();
			if (workoutSnapshot.exists()) {
				this.nombre = workoutSnapshot.getString(FIELD_NOMBRE);
				this.descripcion = workoutSnapshot.getString(FIELD_DESCRIPCION);
				this.nivel = workoutSnapshot.getLong(FIELD_NIVEL).intValue();
				this.videoURL = workoutSnapshot.getString(FIELD_VIDEO_URL);
				System.out.println("URL: " + workoutSnapshot.getString(FIELD_VIDEO_URL));
				//this.tiempoEstimado = workoutSnapshot.getLong(FIELD_TIEMPO_ESTIMADO).intValue();
				System.out.println("Workout encontrado: " + this.nombre);
			} else {
				JOptionPane.showMessageDialog(null, "El workout no existe", "ERROR", JOptionPane.ERROR_MESSAGE);
			}

		} catch (InterruptedException | ExecutionException | IOException e) {
			System.out.println("Error: Clase Workout, método mObtenerWorkout");
			e.printStackTrace();
		}

		return this;
	}


	public ArrayList<WorkOut> mObtenerWorkouts() {
		Firestore co =null;
		ArrayList<WorkOut> listaWorkOuts = new 	ArrayList<WorkOut>();
		try {			
			co= Conexion.conectar();

			ApiFuture<QuerySnapshot> query = co.collection(COLLECTION_NAME).get();
			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> workouts = querySnapshot.getDocuments();
			for (QueryDocumentSnapshot workout : workouts) {
				WorkOut w =new WorkOut();
				w.setNombre(workout.getId());
				w.setDescripcion(workout.getString(FIELD_DESCRIPCION));
				w.setNivel(workout.getDouble(FIELD_NIVEL));
				w.setVideoURL(workout.getString(FIELD_VIDEO_URL));

				listaWorkOuts.add(w);
			}

		} catch ( InterruptedException | ExecutionException e) {
			System.out.println("Error: Clase Contacto, metodo mObtenerContactos");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaWorkOuts;
	}


}
