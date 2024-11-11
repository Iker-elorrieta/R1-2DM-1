package modelo;

import com.google.cloud.Timestamp;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import conexion.Conexion;
import principal.Principal;

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

	private static final String HISTORICOFILEROUTE = "backups/historico.xml";

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
		Principal principal = new Principal();
		if (principal.getInternet()) {
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
		} else {
			try {
				File archivo = new File(HISTORICOFILEROUTE);
				DocumentBuilderFactory dbFactoria = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactoria.newDocumentBuilder();

				Document doc = dBuilder.parse(archivo);
				doc.getDocumentElement().normalize();

				// Obtener todos los elementos <usuario>
				NodeList usuarios = doc.getElementsByTagName("usuario");

				// Iterar sobre cada usuario
				for (int i = 0; i < usuarios.getLength(); i++) {
					Node usuarioNode = usuarios.item(i);

					if (usuarioNode.getNodeType() == Node.ELEMENT_NODE) {
						Element usuarioElement = (Element) usuarioNode;

						// Verificar si el atributo email coincide
						if (usuarioElement.getAttribute("email").equals("a")) {
							System.out.println("Historial para el usuario con email: " + "a");

							// Obtener los registros de este usuario
							NodeList registros = usuarioElement.getElementsByTagName("registro");

							// Iterar sobre cada registro
							for (int j = 0; j < registros.getLength(); j++) {
								Node registroNode = registros.item(j);

								if (registroNode.getNodeType() == Node.ELEMENT_NODE) {
									Element registroElement = (Element) registroNode;

									// Obtener y mostrar cada dato del registro
									String nombre = registroElement.getElementsByTagName("nombre").item(0)
											.getTextContent();
									String tiempoEstimado = registroElement.getElementsByTagName("tiempoEstimado")
											.item(0).getTextContent();
									String nivel = registroElement.getElementsByTagName("nivel").item(0)
											.getTextContent();
									String fecha = registroElement.getElementsByTagName("fecha").item(0)
											.getTextContent();
									String porcentajeCompletado = registroElement
											.getElementsByTagName("porcentajeCompletado").item(0).getTextContent();
									String tiempoRealizacion = registroElement.getElementsByTagName("tiempoRealizacion")
											.item(0).getTextContent();

									// Imprimir el registro
									System.out.println("  Registro:");
									System.out.println("    Nombre: " + nombre);
									System.out.println("    Tiempo Estimado: " + tiempoEstimado);
									System.out.println("    Nivel: " + nivel);
									System.out.println("    Fecha: " + fecha);
									System.out.println("    Porcentaje Completado: " + porcentajeCompletado);
									System.out.println("    Tiempo Realización: " + tiempoRealizacion);
								}
							}
						}
					}
				}
			} catch (ParserConfigurationException | SAXException | IOException e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	public static Date obtenerFechaDate(DocumentSnapshot documentSnapshot, String fieldName) {
		Timestamp timestamp = documentSnapshot.getTimestamp(fieldName);
		return (timestamp != null) ? timestamp.toDate() : null;
	}
}
