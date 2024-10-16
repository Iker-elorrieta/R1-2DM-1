package modelo;




import java.io.IOException;
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

public class Contacto {

	//*** Atributos ***

	private String idContacto;
	private String nombre;
	private double telefono;
	private String email;

	private static String collectionName = "contactos";
	private static String fieldNombre = "nombre";
	private static String fieldTelefono = "telefono";
	private static String fieldEmail = "email";

	//*** Constructores ***

	public Contacto(){

	}

	public Contacto(String pNombre, double pTelefono, String pEmail){
		this.nombre = pNombre;
		this.telefono = pTelefono;
		this.email = pEmail;
	}




	//*** M�todos get-set ***

	public String getIdContacto() {
		return idContacto;
	}

	public void setIdContacto(String idContacto) {
		this.idContacto = idContacto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getTelefono() {
		return telefono;
	}

	public void setTelefono(double telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}




	//*** M�todos CRUD ***



	public Contacto mObtenerContacto(int idContacto) {
		Firestore co =null;

		try {			
			co= Conexion.conectar();

			DocumentSnapshot contacto = co.collection(collectionName).document(String.valueOf(idContacto)).get().get();

			setIdContacto(contacto.getId());
			setNombre(contacto.getString(fieldNombre));
			
			if(contacto.get(fieldTelefono) instanceof String) {
				
				setTelefono(contacto.getDouble(fieldTelefono));

			}else {
				System.out.println(contacto.getString(fieldTelefono));
				setTelefono(0);
				
				System.out.println(contacto.getId());

			}
			
			
			
			setEmail(contacto.getString(fieldEmail));


		} catch ( InterruptedException | ExecutionException e) {
			System.out.println("Error: Clase Contacto, metodo mObtenerContactos");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;
	}

	public ArrayList<Contacto> mObtenerContactos() {
		Firestore co =null;


		ArrayList<Contacto> listaContactos = new 	ArrayList<Contacto>();

		try {			
			co= Conexion.conectar();

			ApiFuture<QuerySnapshot> query = co.collection(collectionName).get();

			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> contactos = querySnapshot.getDocuments();
			for (QueryDocumentSnapshot contacto : contactos) {

				Contacto c=new Contacto();
				c.setIdContacto(contacto.getId());
				c.setNombre(contacto.getString(fieldNombre));
				
				if(contacto.get(fieldTelefono) instanceof Double) {
					c.setTelefono(contacto.getDouble(fieldTelefono));

				}else {
					System.out.println(contacto.getId());
					c.setTelefono(0);

				}
				
				c.setEmail(contacto.getString(fieldEmail));
				listaContactos.add(c);
			}


		} catch ( InterruptedException | ExecutionException e) {
			System.out.println("Error: Clase Contacto, metodo mObtenerContactos");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaContactos;
	}

	public void mIngresarContacto() {

		Firestore co =null;
		try {
			co= Conexion.conectar();

			CollectionReference root = co.collection(collectionName);
			

			Map<String, Object> nuevoContacto = new HashMap<>();
			nuevoContacto.put(fieldNombre, this.nombre);
			nuevoContacto.put(fieldEmail, this.email);
			nuevoContacto.put(fieldTelefono,this.telefono);
			DocumentReference newCont = root.document();
			newCont.set(nuevoContacto);
			System.out.println("Insertando");


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void mEliminar() {

		Firestore co =null;
		try {
			co= Conexion.conectar();

			CollectionReference root = co.collection(collectionName);
			
			DocumentReference conRef = root.document(this.idContacto); 
			conRef.delete();
			System.out.println("Eliminado");
			
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public void mModificar() {
	    Firestore co = null;
	    try {
	        co = Conexion.conectar();
	        CollectionReference root = co.collection(collectionName);
	        DocumentReference conRef = root.document(this.idContacto);
	        
	        ApiFuture<DocumentSnapshot> future = conRef.get();
	        DocumentSnapshot document = future.get();

	        if (document.exists()) {
	            Map<String, Object> contactoCambios = document.getData();

	            contactoCambios.put(fieldNombre, this.nombre);
	            contactoCambios.put(fieldTelefono, this.telefono);
	            contactoCambios.put(fieldEmail, this.email);

	        
	            conRef.update(contactoCambios);
	            System.out.println("Modificado");
	            
	            
	        
	        } else {
	            System.out.println("El documento no existe.");
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (InterruptedException | ExecutionException e) {
	        e.printStackTrace(); 
	    }
	



	}
	
	
	
	

	




}
