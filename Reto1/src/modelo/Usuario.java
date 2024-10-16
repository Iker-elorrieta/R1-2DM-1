package modelo;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;

import conexion.Conexion;

public class Usuario {

	// *** Atributos ***

	public enum enumTipoUsuario {
		CLIENTE, ENTRENADOR

	}

	// Email sera el campo de inicio de sesion

	private String nombre;
	private String apellidos;
	private String email; // Campo Unico al ser unico podria ser el ID
	private String pass;
	private Date fechaNacimiento;
	private Date fechaRegistro;
	private double nivel; // Inicialmente 0
	private enumTipoUsuario tipoUsuario;
	// ArrayList<WorkOuts> workoutsRealizados ;

	private static final String COLLECTION_NAME = "usuarios";
	private static final String FIELD_NOMBRE = "nombre";
	private static final String FIELD_APELLIDOS = "apellidos";
	private static final String FIELD_PASS = "pass";
	private static final String FIELD_FECHA_NACIMIENTO = "fechaNacimiento";
	private static final String FIELD_FECHA_REGISTRO = "fechaRegistro";
	private static final String FIELD_NIVEL = "nivel";

	// *** Constructores ***

	public Usuario() {

	}

	// nivel inicial 0
	public Usuario(String nombre, String apellidos, String email, String pass, Date fechaNacimiento,
			Date fechaRegistro) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.pass = pass;
		this.fechaNacimiento = fechaNacimiento;
		this.fechaRegistro = fechaRegistro;
		this.nivel = 0; // Inicializamos
	}

	public Usuario(String nombre, String apellidos, String email, String pass, Date fechaNacimiento, Date fechaRegistro,
			double nivel) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.pass = pass;
		this.fechaNacimiento = fechaNacimiento;
		this.fechaRegistro = fechaRegistro;
		this.nivel = nivel;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public double getNivel() {
		return nivel;
	}

	public void setNivel(double nivel) {
		this.nivel = nivel;
	}

	public enumTipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(enumTipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	// *** M�todos CRUD ***

	public Usuario mObtenerUsuario(String idIntroducido, String passIntroducida) {
		Firestore co = null;

		try {
			co = Conexion.conectar();

			if (co.collection(COLLECTION_NAME).document(idIntroducido).get().get().exists()) {
				DocumentSnapshot contacto = co.collection(COLLECTION_NAME).document(idIntroducido).get().get();

				if (contacto.getString(FIELD_PASS).equals(passIntroducida)) {
					setEmail(contacto.getId());
					setNombre(contacto.getString(FIELD_NOMBRE));
					setApellidos(contacto.getString(FIELD_APELLIDOS));
					setPass(contacto.getString(FIELD_PASS));
					setFechaRegistro(obtenerFechaDate(contacto, FIELD_FECHA_REGISTRO));
					setFechaNacimiento(obtenerFechaDate(contacto, FIELD_FECHA_NACIMIENTO));
					setNivel(contacto.getDouble(FIELD_NIVEL));
					System.out.println("Correcto");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Usuario o contraseña incorecctos", "ERROR",
						JOptionPane.ERROR_MESSAGE);
			}

		} catch (InterruptedException | ExecutionException e) {
			System.out.println("Error: Clase Usuario, metodo mObtenerUsuario");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;
	}

	// Convertir de timeStamp de Firestore a date
	public Date obtenerFechaDate(DocumentSnapshot documentSnapshot, String fieldName) {
		Timestamp timestamp = documentSnapshot.getTimestamp(fieldName);
		return (timestamp != null) ? timestamp.toDate() : null;
	}

	public void mIngresarContacto() {

		Firestore co = null;
		try {
			co = Conexion.conectar();

			CollectionReference root = co.collection(COLLECTION_NAME);

			Map<String, Object> nuevoUsuario = new HashMap<>();
			nuevoUsuario.put(FIELD_NOMBRE, this.nombre);
			nuevoUsuario.put(FIELD_APELLIDOS, this.apellidos);
			nuevoUsuario.put(FIELD_PASS, this.pass);
			nuevoUsuario.put(FIELD_FECHA_NACIMIENTO, this.fechaNacimiento);
			nuevoUsuario.put(FIELD_FECHA_REGISTRO, this.fechaRegistro);
			nuevoUsuario.put(FIELD_NIVEL, this.nivel);
			DocumentReference newCont = root.document(this.email);
			newCont.set(nuevoUsuario);
			System.out.println("Insertando");

		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//	  public void mModificar() { Firestore co = null; try { co =
//	  Conexion.conectar(); CollectionReference root =
//	  co.collection(collectionName); DocumentReference conRef =
//	  root.document(this.idContacto);
//	  
//	  ApiFuture<DocumentSnapshot> future = conRef.get(); DocumentSnapshot document
//	  = future.get();
//	  
//	  if (document.exists()) { Map<String, Object> contactoCambios =
//	  document.getData();
//	  
//	  contactoCambios.put(fieldNombre, this.nombre);
//	  contactoCambios.put(fieldTelefono, this.telefono);
//	  contactoCambios.put(fieldEmail, this.email);
//	  
//	  
//	  conRef.update(contactoCambios); System.out.println("Modificado");
//	  
//	  
//	  
//	  } else { System.out.println("El documento no existe."); }
//	  
//	  } catch (IOException e) { e.printStackTrace(); } catch (InterruptedException
//	  | ExecutionException e) { e.printStackTrace(); }

}
