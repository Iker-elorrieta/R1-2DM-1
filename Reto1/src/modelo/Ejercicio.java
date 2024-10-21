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
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;

public class Ejercicio {

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

    public Ejercicio(String nombre, String descripcion, ArrayList<Serie> series, String imagenURL) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.series = series;
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

    public String getimagenURL() {
        return imagenURL;
    }

    public void setimagenURL(String imagenURL) {
        this.imagenURL = imagenURL;
    }

    // *** Métodos CRUD ***

    // Método para agregar un nuevo ejercicio
    public void mIngresarEjercicio() {
        Firestore co = null;
        try {
            co = Conexion.conectar();
            CollectionReference root = co.collection(COLLECTION_NAME);

            if (!root.document(nombre).get().get().exists()) {
                Map<String, Object> nuevoEjercicio = new HashMap<>();
                nuevoEjercicio.put(FIELD_DESCRIPCION, this.descripcion);
                nuevoEjercicio.put(FIELD_VIDEO_URL, this.imagenURL);
                
                          
                root.document(this.nombre).set(nuevoEjercicio);
                
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

    // Método para obtener un ejercicio por su nombre
    public Ejercicio mObtenerEjercicio(String nombreEjercicio) {
        Firestore co = null;

        try {
            co = Conexion.conectar();
            DocumentSnapshot ejercicioSnapshot = co.collection(COLLECTION_NAME).document(nombreEjercicio).get().get();
            if (ejercicioSnapshot.exists()) {
                this.nombre = ejercicioSnapshot.getString(FIELD_NOMBRE);
                this.descripcion = ejercicioSnapshot.getString(FIELD_DESCRIPCION);
                this.imagenURL = ejercicioSnapshot.getString(FIELD_VIDEO_URL);
                this.series = new ArrayList<>();

                
                
                System.out.println("Ejercicio encontrado: " + this.nombre);
            } else {
                JOptionPane.showMessageDialog(null, "El ejercicio no existe", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            System.out.println("Error: Clase Ejercicio, método mObtenerEjercicio");
            e.printStackTrace();
        }

        return this;
    }

    // Método para obtener todos los ejercicios
    public ArrayList<Ejercicio> mObtenerEjercicios() {
        Firestore co = null;
        ArrayList<Ejercicio> listaEjercicios = new ArrayList<>();
        try {
            co = Conexion.conectar();

            ApiFuture<QuerySnapshot> query = co.collection(COLLECTION_NAME).get();
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> ejercicios = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot ejercicio : ejercicios) {
                Ejercicio e = new Ejercicio();
                e.setNombre(ejercicio.getId());
                e.setDescripcion(ejercicio.getString(FIELD_DESCRIPCION));
                e.setimagenURL(ejercicio.getString(FIELD_VIDEO_URL));
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
