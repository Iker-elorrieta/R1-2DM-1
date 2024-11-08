package modelo;

import com.google.cloud.Timestamp;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import conexion.Conexion;

public class Historial implements Serializable {
	private static final long serialVersionUID = 1L;

	// Atributos
	// nombre del historico sera el nombre del usuario, nivel y tiempo estimado
	private Workout workout;
	private double tiempoRealizacion; // no usamos para calcular hace falta que sea double?
	private Date fecha;
	private double porcentajeCompletado;

	// Nombres de campos en Firestore
	private static final String COLLECTION_NAME = "historico";
	private static final String FIELD_WORKOUT = "Workout";

	private static final String FIELD_NIVEL = "nivel";
	private static final String FIELD_TIEMPO_ESTIMADO = "tiempoEstimado";

	private static final String FIELD_TIEMPO_REALIZACION = "tiempoRealizacion";
	private static final String FIELD_FECHA = "fecha";
	private static final String FIELD_PORCENTAJE_COMPLETADO = "porcentajeCompletado";

	// Constructores
	public Historial() {
	}

	public Historial(Workout workout, Date fecha, double porcentajeCompletado, String tiempoRealizacion) {
		this.workout = workout;
		this.fecha = fecha;
		this.porcentajeCompletado = porcentajeCompletado;
		this.tiempoRealizacion = Double.parseDouble(tiempoRealizacion.split(":")[0]) * 60
				+ Double.parseDouble(tiempoRealizacion.split(":")[1]);
	}

	// Métodos Getters y Setters

	public double getTiempoRealizacion() {
		return tiempoRealizacion;
	}

	public Workout getWorkout() {
		return workout;
	}

	public void setWorkout(Workout workout) {
		this.workout = workout;
	}

	public void setTiempoRealizacion(double tiempoRealizacion) {
		this.tiempoRealizacion = tiempoRealizacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getPorcentajeCompletado() {
		return porcentajeCompletado;
	}

	public void setPorcentajeCompletado(double porcentajeCompletado) {
		this.porcentajeCompletado = porcentajeCompletado;
	}

	public void mIngresarHistorico(String emailUsuario, Workout workout) {
		Firestore co = null;
		try {
			co = Conexion.conectar();
			DocumentReference historialCo = co.collection(new Usuario().getCollectionName()).document(emailUsuario);
			CollectionReference coleccionHistorico = historialCo.collection(COLLECTION_NAME);

			Map<String, Object> h = new HashMap<>();
			h.put(FIELD_TIEMPO_REALIZACION, this.tiempoRealizacion);
			h.put(FIELD_FECHA, this.fecha);
			h.put(FIELD_PORCENTAJE_COMPLETADO, this.porcentajeCompletado);
			h.put(FIELD_WORKOUT, workout.mObtenerWorkoutByID(workout.getNombre()));
			System.out.println(workout.mObtenerWorkoutByID(workout.getNombre()));
			coleccionHistorico.document().set(h);

			// Esto puede servir en algun mometo
			/*
			 * DocumentReference workOutDoc = coleccionHistorico.add(h).get();
			 * 
			 * CollectionReference workoutCol = workOutDoc.collection(FIELD_WORKOUT);
			 * Map<String, Object> workOut = new HashMap<>(); workOut.put(FIELD_NOMBRE,
			 * workout.getNombre()); workOut.put(FIELD_NIVEL, workout.getNivel());
			 * workOut.put(FIELD_TIEMPO_ESTIMADO, workout.getTiempoEstimado());
			 * 
			 * workoutCol.document().set(workOut);
			 */

			co.close();
		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// CRUD: obtenerHistorico
	public ArrayList<Historial> mObtenerHistorico(String coleccionUsuario, String emailUsuario) {
		Firestore co = null;
		ArrayList<Historial> listaHistorial = new ArrayList<>();
		try {
			co = Conexion.conectar();
			DocumentReference usuarioDoc = co.collection(coleccionUsuario).document(emailUsuario);
			CollectionReference coleccionHistorico = usuarioDoc.collection(COLLECTION_NAME);
			List<QueryDocumentSnapshot> documentosHistorico = coleccionHistorico.get().get().getDocuments();

			for (QueryDocumentSnapshot doc : documentosHistorico) {
				Historial historial = new Historial();
				historial.setTiempoRealizacion(doc.getDouble(FIELD_TIEMPO_REALIZACION));
				historial.setFecha(obtenerFechaDate(doc, FIELD_FECHA)); // Convertir Timestamp a Date
				historial.setPorcentajeCompletado(doc.getDouble(FIELD_PORCENTAJE_COMPLETADO));

				DocumentReference workoutReference = (DocumentReference) doc.getData().get(FIELD_WORKOUT);
				if (workoutReference != null) {
					Workout workout = new Workout();
					workout.setNombre(workoutReference.get().get().getId());
					workout.setNivel(workoutReference.get().get().getDouble(FIELD_NIVEL));
					workout.setTiempoEstimado(workoutReference.get().get().getDouble(FIELD_TIEMPO_ESTIMADO));
					historial.setWorkout(workout);
				}
				listaHistorial.add(historial);
			}
			co.close();
		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaHistorial;
	}

	public static Date obtenerFechaDate(DocumentSnapshot documentSnapshot, String fieldName) {
		Timestamp timestamp = documentSnapshot.getTimestamp(fieldName);
		return (timestamp != null) ? timestamp.toDate() : null;
	}
}
