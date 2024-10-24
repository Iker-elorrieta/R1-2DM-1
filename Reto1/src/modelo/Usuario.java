package modelo;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;

import com.google.api.SystemParameterOrBuilder;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;

public class Usuario implements Serializable {

	// *** Atributos ***

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum enumTipoUsuario {
		CLIENTE, ENTRENADOR

	}

	// Email sera el campo de inicio de sesion

	private String nombre;
	private String apellidos;
	private String email; // Campo Unico al ser unico podria ser el ID
	private String pass;
	private Date fechaNacimiento;
	private double nivel; // Inicialmente 0
	private enumTipoUsuario tipoUsuario;
	// ArrayList<WorkOuts> workoutsRealizados ;

	private static final String COLLECTION_NAME = "usuarios";
	private static final String FIELD_NOMBRE = "nombre";
	private static final String FIELD_APELLIDOS = "apellidos";
	private static final String FIELD_PASS = "pass";
	private static final String FIELD_FECHA_NACIMIENTO = "fechaNacimiento";
	private static final String FIELD_NIVEL = "nivel";

	// *** Constructores ***

	public Usuario() {

	}

	// nivel inicial 0
	public Usuario(String nombre, String apellidos, String email, String pass, Date fechaNacimiento) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.pass = pass;
		this.fechaNacimiento = fechaNacimiento;
		this.nivel = 0; // Inicializamos

	}

	public Usuario(String nombre, String apellidos, String email, String pass, Date fechaNacimiento,
			double nivel) {

		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.pass = pass;
		this.fechaNacimiento = fechaNacimiento;
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
				DocumentSnapshot dsUsuario = co.collection(COLLECTION_NAME).document(idIntroducido).get().get();
				if (dsUsuario.getString(FIELD_PASS).equals(passIntroducida)) {
					setEmail(dsUsuario.getId());
					setNombre(dsUsuario.getString(FIELD_NOMBRE));
					setApellidos(dsUsuario.getString(FIELD_APELLIDOS));
					setPass(dsUsuario.getString(FIELD_PASS));
					setFechaNacimiento(obtenerFechaDate(dsUsuario, FIELD_FECHA_NACIMIENTO));
					setNivel(dsUsuario.getDouble(FIELD_NIVEL));

					JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
					return this;

				} else {
					JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "ERROR",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "ERROR",
						JOptionPane.ERROR_MESSAGE);
			}

			co.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Convertir de timeStamp de Firestore a date
	public Date obtenerFechaDate(DocumentSnapshot documentSnapshot, String fieldName) {
		Timestamp timestamp = documentSnapshot.getTimestamp(fieldName);
		return (timestamp != null) ? timestamp.toDate() : null;
	}

	public void mRegistrarUsuario() {

		Firestore co = null;
		try {
			co = Conexion.conectar();

			CollectionReference root = co.collection(COLLECTION_NAME);
			if (!root.document(this.email).get().get().exists()) {
				Map<String, Object> nuevoUsuario = new HashMap<>();
				nuevoUsuario.put(FIELD_NOMBRE, this.nombre);
				nuevoUsuario.put(FIELD_APELLIDOS, this.apellidos);
				nuevoUsuario.put(FIELD_PASS, this.pass);
				nuevoUsuario.put(FIELD_FECHA_NACIMIENTO, this.fechaNacimiento);
				nuevoUsuario.put(FIELD_NIVEL, this.nivel);
				DocumentReference newCont = root.document(this.email);
				newCont.set(nuevoUsuario);
				JOptionPane.showMessageDialog(null, "Usuario creado con éxito");
			} else {
				JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese email");
			}
			co.close();
		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean mModificarUsuario() {
		Firestore co = null;
		try {
			co = Conexion.conectar();
			CollectionReference root = co.collection(COLLECTION_NAME);
			DocumentReference conRef = root.document(this.email);

			ApiFuture<DocumentSnapshot> future = conRef.get();
			DocumentSnapshot document = future.get();

			if (document.exists()) {
				Map<String, Object> usuarioModificado = document.getData();

				usuarioModificado.put(FIELD_NOMBRE, this.nombre);
				usuarioModificado.put(FIELD_APELLIDOS, this.apellidos);
				usuarioModificado.put(FIELD_PASS, this.pass);
				usuarioModificado.put(FIELD_FECHA_NACIMIENTO, this.fechaNacimiento);

				conRef.update(usuarioModificado);
				JOptionPane.showMessageDialog(null, "Datos modificados con éxito");
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Error al modificar los datos");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}

	public ArrayList<Usuario> mObtenerTodosLosUsuarios() {
		Firestore co = null;

		ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();

		try {
			co = Conexion.conectar();

			ApiFuture<QuerySnapshot> query = co.collection(COLLECTION_NAME).get();

			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> usuariosFireBase = querySnapshot.getDocuments();
			for (QueryDocumentSnapshot usuarioFireBase : usuariosFireBase) {

				Usuario usuario = new Usuario(usuarioFireBase.getString(FIELD_NOMBRE),
						usuarioFireBase.getString(FIELD_APELLIDOS), usuarioFireBase.getId(),
						usuarioFireBase.getString(FIELD_PASS), usuarioFireBase.getDate(FIELD_FECHA_NACIMIENTO));


				listaUsuarios.add(usuario);
			}
		} catch (InterruptedException | ExecutionException e) {
			System.out.println("Error: Clase Contacto, metodo mObtenerContactos");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listaUsuarios;
	}
}