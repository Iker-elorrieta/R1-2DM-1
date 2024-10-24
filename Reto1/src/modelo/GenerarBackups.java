package modelo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class GenerarBackups {

	public static void main(String[] args) {
		ArrayList<Usuario> nuevosUsuarios = new ArrayList<>();
		nuevosUsuarios.add(new Usuario());
		nuevosUsuarios.add(new Usuario());
		nuevosUsuarios.add(new Usuario());

		// Guardar usuarios en el archivo
		guardarUsuarios(nuevosUsuarios);

		// Usuario usuario = new Usuario();
		// guardarUsuarios(usuario.mObtenerTodosLosUsuarios());
	}

	private static final String FILEROUTE = "backups/usuarios.dat";

	private static void guardarUsuarios(ArrayList<Usuario> nuevosUsuarios) {
		ArrayList<Usuario> usuariosExistentes = leerUsuariosDesdeArchivo();

		// Comprobar si los usuarios ya están en el archivo y añadir los nuevos usuarios
		// no duplicados
		for (Usuario nuevoUsuario : nuevosUsuarios) {
			if (!usuariosExistentes.contains(nuevoUsuario)) {
				System.out.println("el usuario no exitse");
				usuariosExistentes.add(nuevoUsuario);
			}
			System.out.println("el aaa exitse");
		}

		// Guardar la lista de usuarios actualizada en el archivo
		escribirUsuariosEnArchivo(usuariosExistentes);
	}

	// Método para leer usuarios desde el archivo usuarios.dat
	private static ArrayList<Usuario> leerUsuariosDesdeArchivo() {
		ArrayList<Usuario> usuarios = new ArrayList<>();

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILEROUTE))) {
			usuarios = (ArrayList<Usuario>) ois.readObject();
		} catch (FileNotFoundException e) {
			// El archivo no existe, se crea uno nuevo más tarde
			System.out.println("Archivo no encontrado, se creará uno nuevo.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return usuarios;
	}

	// Método para escribir usuarios en el archivo usuarios.dat
	private static void escribirUsuariosEnArchivo(ArrayList<Usuario> usuarios) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILEROUTE))) {
			oos.writeObject(usuarios);
			System.out.println("Usuarios guardados correctamente en " + FILEROUTE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}