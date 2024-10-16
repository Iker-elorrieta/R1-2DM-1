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
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;



public class Usuario {

	//*** Atributos ***


	public  enum enumTipoUsuario{
		CLIENTE,
		ENTRENADOR

	}

	//Email sera el campo de inicio de sesion

	private String idUsuario;
	private String nombre;
	private String apellidos;
	private String email; //Campo Unico al ser unico podria ser el ID
	private String pass;
	private Date fechaNacimiento;
	private Date fechaRegistro;
	private double nivel; // Inicialmente 0
	private enumTipoUsuario tipoUsuario ;
	// ArrayList<WorkOuts> workoutsRealizados ;




	private static final String COLLECTION_NAME = "usuarios";
	private static final String FIELD_ID_USUARIO = "idUsuario";
	private static final String FIELD_NOMBRE = "nombre";
	private static final String FIELD_APELLIDOS = "apellidos";
	private static final String FIELD_EMAIL = "email";
	private static final String FIELD_PASS = "pass";
	private static final String FIELD_FECHA_NACIMIENTO = "fechaNacimiento";
	private static final String FIELD_FECHA_REGISTRO = "fechaRegistro";
	private static final String FIELD_NIVEL = "nivel";
	private static final String FIELD_TIPO_USUARIO = "tipoUsuario";


	//*** Constructores ***

	public Usuario(){

	}

	//nivel inicial 0
	public Usuario(String idUsuario, String nombre, String apellidos, String email, String pass, Date fechaNacimiento,
			Date fechaRegistro, enumTipoUsuario tipoUsuario) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.pass = pass;
		this.fechaNacimiento = fechaNacimiento;
		this.fechaRegistro = fechaRegistro;
		this.nivel = 0; //Inicializamos
		this.tipoUsuario = tipoUsuario;
	}


	public Usuario(String idUsuario, String nombre, String apellidos, String email, String pass, Date fechaNacimiento,
			Date fechaRegistro, double nivel, enumTipoUsuario tipoUsuario) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.pass = pass;
		this.fechaNacimiento = fechaNacimiento;
		this.fechaRegistro = fechaRegistro;
		this.nivel = nivel;
		this.tipoUsuario = tipoUsuario;
	}





	//*** M�todos get-set ***


	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
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






	//*** M�todos CRUD ***




	public Usuario mObtenerUsuario(String idIntroducido, String passIntroducida) {
		Firestore co =null;

		try {			
			co= Conexion.conectar();

			DocumentSnapshot contacto = co.collection(COLLECTION_NAME).document(idIntroducido).get().get();
			if(contacto.exists()) {
				if(contacto.getString(FIELD_PASS).equals(passIntroducida)) {
				
				setIdUsuario(contacto.getId());
				setNombre(contacto.getString(FIELD_NOMBRE));
				setApellidos(contacto.getString(FIELD_APELLIDOS));
				setEmail(contacto.getString(FIELD_APELLIDOS));
				setPass(contacto.getString(FIELD_PASS));
				setFechaRegistro(obtenerFechaDate(contacto, FIELD_FECHA_REGISTRO));
				setFechaNacimiento(obtenerFechaDate(contacto, FIELD_FECHA_NACIMIENTO));
				setNivel(contacto.getDouble(FIELD_NIVEL));
				}
				else {
					JOptionPane.showMessageDialog(null, "Usuario o contraseña incorecctos","ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}else {
				JOptionPane.showMessageDialog(null, "Usuario o contraseña incorecctos","ERROR", JOptionPane.ERROR_MESSAGE);
			}

		} catch ( InterruptedException | ExecutionException e) {
			System.out.println("Error: Clase Usuario, metodo mObtenerUsuario");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;
	}

	//Convertir de timeStamp de Firestore a date
	public Date obtenerFechaDate(DocumentSnapshot documentSnapshot, String fieldName) {
		Timestamp timestamp = documentSnapshot.getTimestamp(fieldName);
		return (timestamp != null) ? timestamp.toDate() : null;
	}




	/*
	 * public void mIngresarContacto() {
	 * 
	 * Firestore co =null; try { co= Conexion.conectar();
	 * 
	 * CollectionReference root = co.collection(collectionName);
	 * 
	 * 
	 * Map<String, Object> nuevoContacto = new HashMap<>();
	 * nuevoContacto.put(fieldNombre, this.nombre); nuevoContacto.put(fieldEmail,
	 * this.email); nuevoContacto.put(fieldTelefono,this.telefono);
	 * DocumentReference newCont = root.document(); newCont.set(nuevoContacto);
	 * System.out.println("Insertando");
	 * 
	 * 
	 * } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * }
	 * 
	 * public void mEliminar() {
	 * 
	 * Firestore co =null; try { co= Conexion.conectar();
	 * 
	 * CollectionReference root = co.collection(collectionName);
	 * 
	 * DocumentReference conRef = root.document(this.idContacto); conRef.delete();
	 * System.out.println("Eliminado");
	 * 
	 * 
	 * 
	 * } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * }
	 * 
	 * 
	 * public void mModificar() { Firestore co = null; try { co =
	 * Conexion.conectar(); CollectionReference root =
	 * co.collection(collectionName); DocumentReference conRef =
	 * root.document(this.idContacto);
	 * 
	 * ApiFuture<DocumentSnapshot> future = conRef.get(); DocumentSnapshot document
	 * = future.get();
	 * 
	 * if (document.exists()) { Map<String, Object> contactoCambios =
	 * document.getData();
	 * 
	 * contactoCambios.put(fieldNombre, this.nombre);
	 * contactoCambios.put(fieldTelefono, this.telefono);
	 * contactoCambios.put(fieldEmail, this.email);
	 * 
	 * 
	 * conRef.update(contactoCambios); System.out.println("Modificado");
	 * 
	 * 
	 * 
	 * } else { System.out.println("El documento no existe."); }
	 * 
	 * } catch (IOException e) { e.printStackTrace(); } catch (InterruptedException
	 * | ExecutionException e) { e.printStackTrace(); }
	 * 
	 * 
	 * 
	 * 
	 * }
	 * 
	 */






}
