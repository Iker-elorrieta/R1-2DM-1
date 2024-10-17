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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modelo.Usuario;
import modelo.WorkOut;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;


public class PanelWorkout2 extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	private JComboBox<String> levelFilter;

	private JList<?> workoutsList;
	private ArrayList<WorkOut> workouts;
	private Usuario user;
	@SuppressWarnings("rawtypes")
	private DefaultListModel workoutListModel;




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
		workoutsList = new JList<>();


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
		topPanel.setBounds(0, 115, 688, 45);
		topPanel.add(panelFiltro, BorderLayout.CENTER);
		topPanel.add(btnHistorialWK, BorderLayout.EAST);
		add(topPanel);


		JButton startWorkoutButton = new JButton("Iniciar Workout");
		startWorkoutButton.setBounds(0, 326, 688, 23);
		startWorkoutButton.addActionListener(e -> startWorkout());

		JPanel workoutDetailPanel = new JPanel();
		workoutDetailPanel.setBounds(0, 192, 688, 349);
		workoutDetailPanel.setLayout(null);
		JScrollPane scrollPane = new JScrollPane(workoutsList);
		scrollPane.setBounds(0, 0, 315, 326);
		workoutDetailPanel.add(scrollPane);
		workoutDetailPanel.add(startWorkoutButton);
		add(workoutDetailPanel);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(367, 0, 321, 326);
		workoutDetailPanel.add(scrollPane_1);

		JTextArea textArea = new JTextArea();
		textArea.setEnabled(false);
		textArea.setEditable(false);
		scrollPane_1.setViewportView(textArea);

		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescripcion.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDescripcion.setBounds(427, 160, 185, 32);
		add(lblDescripcion);


		workoutsList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
					int selectedIndex = workoutsList.getSelectedIndex();
					if (selectedIndex != -1) {
						WorkOut selectedWorkout = workouts.get(selectedIndex);
						textArea.setText(selectedWorkout.getDescripcion());
					} else {
						textArea.setText("");
					}
				}
			
		});

	}
	@SuppressWarnings("unchecked") // ME solicita esto para que no salga un warning
	private void actualizarListaWorkOuts() {
		workoutListModel.clear();
		String nivelSeleccionado = (String) levelFilter.getSelectedItem();
		int filterLevel = nivelSeleccionado.equals("Todos") ? -1 : Integer.parseInt(nivelSeleccionado.split(" ")[1]);

		for (WorkOut workout : this.workouts) {
			if (workout.getNivel() <= user.getNivel() && (filterLevel == -1 || workout.getNivel() == filterLevel)) {
				workoutListModel.addElement(String.format("WorkOut: %s ", workout.getNombre()));
			}
		}
		workoutsList.setModel(workoutListModel) ;


	}




	private void startWorkout() {
		WorkOut workOutSeleccionado = workouts.get(workoutsList.getSelectedIndex());
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