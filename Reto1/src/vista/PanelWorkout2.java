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
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import modelo.Usuario;
import modelo.WorkOut;
import javax.swing.JTextArea;


public class PanelWorkout2 extends JPanel {
	private static final long serialVersionUID = 1L;

	private JComboBox<String> filtroNivel;
	private JList<?> workoutsList;
	private ArrayList<WorkOut> workouts;
	WorkOut workouSelect = null;
	private Usuario user;
	private DefaultListModel workoutListModel;
	private JLabel lblUrl;
	private JButton btnIrAVideo;

	public PanelWorkout2() {

		setBackground(new Color(230, 230, 250)); // Un color más claro
		setBounds(288, 11, 688, 541);
		setLayout(null);

		// Título principal
		JLabel lblLogin = new JLabel("Workout", SwingConstants.CENTER);
		lblLogin.setBounds(0, 0, 0, 0);
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblLogin.setBorder(new EmptyBorder(10, 0, 10, 0));
		add(lblLogin);

		// Panel superior para filtros y botones
		JPanel topPanel = new JPanel(new BorderLayout(10, 10));
		topPanel.setBounds(0, 0, 688, 43);
		topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		topPanel.setBackground(new Color(230, 230, 250));

		// Filtro por nivel
		filtroNivel = new JComboBox<>(new String[]{"Todos", "Nivel 0", "Nivel 1", "Nivel 2"});
		filtroNivel.addActionListener(e -> actualizarListaWorkOuts());
		topPanel.add(new JLabel("Filtrar por nivel:"), BorderLayout.WEST);
		topPanel.add(filtroNivel, BorderLayout.CENTER);

		// Botón de historial
		JButton btnHistorialWK = new JButton("Ver Histórico");
		btnHistorialWK.addActionListener(e -> JOptionPane.showMessageDialog(this, "Mostrando histórico de workouts..."));
		topPanel.add(btnHistorialWK, BorderLayout.EAST);
		add(topPanel);

		// Panel central para la lista y detalles del workout
		JPanel centerPanel = new JPanel();
		centerPanel.setBounds(0, 43, 688, 458);
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		centerPanel.setBackground(new Color(245, 245, 245));

		// Lista de workouts
		workoutListModel = new DefaultListModel<>();
		centerPanel.setLayout(null);
		workoutsList = new JList<>();
		JScrollPane scrollPane = new JScrollPane(workoutsList);
		scrollPane.setBounds(0, 10, 688, 208);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Workouts"));
		centerPanel.add(scrollPane);

		// Detalles del workout seleccionado
		JPanel detailsPanel = new JPanel(null);
		detailsPanel.setBounds(0, 218, 428, 240);
		detailsPanel.setBorder(BorderFactory.createTitledBorder("Detalles del Workout"));
		detailsPanel.setBackground(new Color(245, 245, 245));
		JLabel lblNEjer = new JLabel("Nº Ejercicios: -");
		lblNEjer.setBounds(20, 30, 200, 25);
		detailsPanel.add(lblNEjer);

		lblUrl = new JLabel("Video: -");
		lblUrl.setBounds(20, 60, 387, 25);
		detailsPanel.add(lblUrl);

		btnIrAVideo = new JButton("Ver Video");
		btnIrAVideo.setBounds(20, 90, 120, 30);
		detailsPanel.add(btnIrAVideo);

		centerPanel.add(detailsPanel);

		add(centerPanel);
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(438, 229, 167, 128);
		scrollPane_1.setBorder(BorderFactory.createTitledBorder("Ejercicios"));

		centerPanel.add(scrollPane_1);
		
		JTextArea textArea = new JTextArea();
		textArea.setEnabled(false);
		textArea.setEditable(false);
		scrollPane_1.setViewportView(textArea);
		
		
		// Botón para iniciar el workout
		JButton startWorkoutButton = new JButton("Iniciar Workout");
		startWorkoutButton.setBounds(0, 501, 688, 40);
		startWorkoutButton.addActionListener(e -> startWorkout());
		startWorkoutButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		startWorkoutButton.setBackground(new Color(102, 153, 255));
		startWorkoutButton.setForeground(Color.WHITE);
		startWorkoutButton.setFocusPainted(false);
		startWorkoutButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		add(startWorkoutButton);

		// Listeners para la selección en la lista
		workoutsList.addListSelectionListener(e -> {
			int selectedIndex = workoutsList.getSelectedIndex();
			if (selectedIndex != -1) {
				for (WorkOut workout : workouts) {
					if (workout.getNombre().equals(workoutsList.getSelectedValue().toString().split(": ")[1].trim())) {
						workouSelect = workout;
						break;
					}
				}
				if(workouSelect!=null) {
				lblNEjer.setText("Nº Ejercicios: " + workouSelect.getNumEjercicios());
				lblUrl.setText("Video: " + workouSelect.getVideoURL());
				
				textArea.setText(workouSelect.getListaEjercicios());
				}

				
			}
		});

		// Inicializar lista de workouts si es necesario
		if (user != null) {
			actualizarListaWorkOuts();
		}
	}

	private void actualizarListaWorkOuts() {
		workoutListModel.clear();
		String nivelSeleccionado = (String) filtroNivel.getSelectedItem();
		int filterLevel = nivelSeleccionado.equals("Todos") ? -1 : Integer.parseInt(nivelSeleccionado.split(" ")[1]);

		for (WorkOut workout : this.workouts) {
			if (workout.getNivel() <= user.getNivel() && (filterLevel == -1 || workout.getNivel() == filterLevel)) {
				workoutListModel.addElement("WorkOut: " + workout.getNombre());
			}
		}
		workoutsList.setModel(workoutListModel);
	}

	private void startWorkout() {
		int selectedIndex = workoutsList.getSelectedIndex();
		if (selectedIndex == -1 || workouSelect == null) {
			JOptionPane.showMessageDialog(this, "Por favor, seleccione un workout para iniciar.");
			return;
		}
		JOptionPane.showMessageDialog(this,
				"Iniciando " + workouSelect.getNombre() +
				//	"\nNúmero de ejercicios: " + workouSelect.getNumeroEjercicios() +
				"\nURL del video: " + workouSelect.getVideoURL());
	}

	public void setWorkouts(ArrayList<WorkOut> workouts) {
		this.workouts = workouts;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public JComboBox<String> getFiltroNivel() {
		return filtroNivel;
	}

	public void setFiltroNivel(JComboBox<String> filtroNivel) {
		this.filtroNivel = filtroNivel;
	}

	public JButton getBtnIrAVideo() {
		return btnIrAVideo;
	}

	public void setBtnIrAVideo(JButton btnIrAVideo) {
		this.btnIrAVideo = btnIrAVideo;
	}

	public JList<?> getWorkoutsList() {
		return workoutsList;
	}

	public void setWorkoutsList(JList<?> workoutsList) {
		this.workoutsList = workoutsList;
	}

	public WorkOut getWorkOutseleccionado() {
		return this.workouSelect;
	}
}
