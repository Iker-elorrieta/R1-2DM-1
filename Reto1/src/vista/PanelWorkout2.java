package vista;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.SwingConstants;

import modelo.Usuario;
import modelo.WorkOut;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class PanelWorkout2 extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	private JComboBox<String> levelFilter;

	private JList<WorkOut> workoutsList;
	private ArrayList<WorkOut> workouts;
	private Usuario user;
	private DefaultListModel<WorkOut> workoutListModel;




	/**
	 * Create the panel.
	 */
	public PanelWorkout2() {
		
		setBackground(new Color(204, 193, 221));
		setBounds(288, 11, 688, 541);
		setLayout(null);

		JLabel lblLogin = new JLabel("Workout");
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblLogin.setBounds(0, 0, 185, 101);
		add(lblLogin);



		workoutListModel = new DefaultListModel<>();
		workoutsList = new JList<>(workoutListModel);


		levelFilter = new JComboBox<>(new String[]{"Todos", "Nivel 0", "Nivel 1", "Nivel 2"});
		levelFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarListaWorkOuts();
			}
		});

		// Llamar a updateWorkoutList() después de inicializar levelFilter
		if(user!=null) {
		actualizarListaWorkOuts();
		}
		JButton btnHistorialWK = new JButton("Ver Histórico");
		btnHistorialWK.addActionListener(e -> JOptionPane.showMessageDialog(this, "Mostrando histórico de workouts..."));

		JPanel panelFiltro = new JPanel(new BorderLayout());
		panelFiltro.add(new JLabel("Filtrar por nivel:"), BorderLayout.WEST);
		panelFiltro.add(levelFilter, BorderLayout.CENTER);

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBounds(0, 83, 688, 101);
		topPanel.add(panelFiltro, BorderLayout.CENTER);
		topPanel.add(btnHistorialWK, BorderLayout.EAST);
		add(topPanel);


		JButton startWorkoutButton = new JButton("Iniciar Workout");
		startWorkoutButton.addActionListener(e -> startWorkout());

		JPanel workoutDetailPanel = new JPanel(new BorderLayout());
		workoutDetailPanel.setBounds(0, 192, 688, 349);
		workoutDetailPanel.add(new JScrollPane(workoutsList), BorderLayout.CENTER);
		workoutDetailPanel.add(startWorkoutButton, BorderLayout.SOUTH);
		add(workoutDetailPanel);


	}
	private void actualizarListaWorkOuts() {
		workoutListModel.clear();
		String nivelSeleccionado = (String) levelFilter.getSelectedItem();
		int filterLevel = nivelSeleccionado.equals("Todos") ? -1 : Integer.parseInt(nivelSeleccionado.split(" ")[1]);

		for (WorkOut workout : workouts) {
			if (workout.getNivel() <= user.getNivel() && (filterLevel == -1 || workout.getNivel() == filterLevel)) {
				workoutListModel.addElement(workout);
			}
		}
	}


	private void startWorkout() {
		WorkOut workOutSeleccionado = workoutsList.getSelectedValue();
		if (workOutSeleccionado == null) {
			JOptionPane.showMessageDialog(this, "Por favor, seleccione un workout para iniciar.");
			return;
		}

		// Mostrar detalles del workout seleccionado
		JOptionPane.showMessageDialog(this,
				"Iniciando " + workOutSeleccionado.getNombre() +
				"\nNúmero de ejercicios: " +  "PENDIENTE"+
				"\nURL del video: " + workOutSeleccionado.getVideoURL());
	}
	public ArrayList<WorkOut> getWorkouts() {
		return workouts;
	}
	public void setWorkouts(ArrayList<WorkOut> workouts) {
		this.workouts = workouts;
	}
	public Usuario getUser() {
		return user;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}

}