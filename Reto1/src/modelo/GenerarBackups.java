package modelo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GenerarBackups {

	private static final String USUARIOSFILEROUTE = "backups/usuarios.dat";
	private static final String WORKOUTSFILEROUTE = "backups/workouts.dat";
	private static final String HISTORICOFILEROUTE = "backups/historico.xml";

	public static void main(String[] args) {
		escribirUsuariosEnArchivo(new Usuario().mObtenerTodosLosUsuarios());
		escribirWorkoutsEnArchivo(new Workout().mObtenerWorkouts());
	}

	private static void escribirUsuariosEnArchivo(ArrayList<Usuario> usuarios) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USUARIOSFILEROUTE))) {
			eliminarContenido();
			for (Usuario usu : usuarios) {
				oos.writeObject(usu);
				escribirHistoricoEnXML(usu.getEmail(), usu.getCollectionName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void eliminarContenido() {
		try {
			File archivoXML = new File(HISTORICOFILEROUTE);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(archivoXML);
			doc.getDocumentElement().normalize();

			NodeList historial = doc.getElementsByTagName("usuario");

			for (int i = historial.getLength() - 1; i >= 0; i--) {
				Node usuario = historial.item(i);
				usuario.getParentNode().removeChild(usuario);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(HISTORICOFILEROUTE));
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void escribirHistoricoEnXML(String emailUsuario, String coleccion) {
		Historial historia = new Historial();
		ArrayList<Historial> historial = historia.mObtenerHistorico(coleccion, emailUsuario);

		try {
			File archivoXML = new File(HISTORICOFILEROUTE);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(archivoXML);
			Element elementoRaiz = doc.getDocumentElement();

			Element usuario = doc.createElement("usuario");
			elementoRaiz.appendChild(usuario);

			usuario.setAttribute("email", emailUsuario);

			for (Historial registroHistorial : historial) {
				Workout workout = registroHistorial.getWorkout();

				Element registro = doc.createElement("registro");
				usuario.appendChild(registro);

				Element nombre = doc.createElement("nombre");
				nombre.appendChild(doc.createTextNode(workout.getNombre()));
				registro.appendChild(nombre);

				Element tiempoEstimado = doc.createElement("tiempoEstimado");
				tiempoEstimado.appendChild(doc.createTextNode(String.valueOf(workout.getTiempoEstimado())));
				registro.appendChild(tiempoEstimado);

				Element nivel = doc.createElement("nivel");
				nivel.appendChild(doc.createTextNode(String.valueOf(workout.getNivel())));
				registro.appendChild(nivel);

				Element fecha = doc.createElement("fecha");
				fecha.appendChild(doc.createTextNode(registroHistorial.getFecha().toString()));
				registro.appendChild(fecha);

				Element porcentajeCompletado = doc.createElement("porcentajeCompletado");
				porcentajeCompletado
						.appendChild(doc.createTextNode(String.valueOf(registroHistorial.getPorcentajeCompletado())));
				registro.appendChild(porcentajeCompletado);

				Element tiempoRealizacion = doc.createElement("tiempoRealizacion");
				tiempoRealizacion
						.appendChild(doc.createTextNode(String.valueOf(registroHistorial.getTiempoRealizacion())));
				registro.appendChild(tiempoRealizacion);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(HISTORICOFILEROUTE));
			transformer.transform(source, result);

		} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
			e.printStackTrace();
		}
	}

	private static void escribirWorkoutsEnArchivo(ArrayList<Workout> workouts) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(WORKOUTSFILEROUTE))) {
			for (Workout wk : workouts) {
				oos.writeObject(wk);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}