package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import modelo.Usuario;
import modelo.Workout;
import javax.swing.UIManager;

public class PanelWorkout extends JPanel {
	private static final long serialVersionUID = 1L;

	private JComboBox<String> filtroNivel;
	private JList<?> workoutsList;
	private ArrayList<Workout> workouts;
	private Workout workouSelect = null;
	private Usuario user;
	@SuppressWarnings("rawtypes")
	private DefaultListModel workoutListModel;
	private JLabel lblUrl;
	private JButton btnIrAVideo;
	private JButton btnIniciar;
	private JTextArea textAreaEjercicios;
	private JLabel lblNEjer;
	private JButton btnPerfil;
	private JScrollPane scrollPaneDescripcion;
	private JScrollPane scrollPaneEjercicios;
	private JTextArea textAreaDescripcion;

	public PanelWorkout() {

		setBackground(new Color(230, 230, 250));
		setBounds(288, 11, 688, 541);
		setLayout(null);

		JPanel topPanel = new JPanel(new BorderLayout(10, 10));
		topPanel.setBounds(0, 33, 688, 43);
		topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		topPanel.setBackground(new Color(230, 230, 250));

		filtroNivel = new JComboBox<>(new String[] { "Todos", "Nivel 0", "Nivel 1", "Nivel 2" });
		filtroNivel.addActionListener(e -> actualizarListaWorkouts());
		topPanel.add(new JLabel("Filtrar por nivel:"), BorderLayout.WEST);
		topPanel.add(filtroNivel, BorderLayout.CENTER);
		add(topPanel);

		JPanel centerPanel = new JPanel();
		centerPanel.setBounds(0, 75, 688, 418);
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		centerPanel.setBackground(new Color(245, 245, 245));

		workoutListModel = new DefaultListModel<>();
		centerPanel.setLayout(null);
		workoutsList = new JList<>();
		
		JScrollPane scrollPane = new JScrollPane(workoutsList);
		scrollPane.setBounds(0, 10, 688, 208);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Workouts"));
		centerPanel.add(scrollPane);

		JPanel detailsPanel = new JPanel();
		detailsPanel.setBounds(0, 218, 428, 192);
		detailsPanel.setBorder(BorderFactory.createTitledBorder("Detalles del Workout"));
		detailsPanel.setBackground(new Color(245, 245, 245));
		detailsPanel.setLayout(null);
		lblNEjer = new JLabel("Nº Ejercicios: -");
		lblNEjer.setBounds(36, 43, 142, 14);
		detailsPanel.add(lblNEjer);

		lblUrl = new JLabel("Video: -");
		lblUrl.setBounds(36, 81, 373, 14);
		detailsPanel.add(lblUrl);

		btnIrAVideo = new JButton("Ver Video");
		btnIrAVideo.setBounds(36, 122, 98, 31);
		detailsPanel.add(btnIrAVideo);

		centerPanel.add(detailsPanel);
		add(centerPanel);

		scrollPaneEjercicios = new JScrollPane();
		scrollPaneEjercicios.setBounds(436, 328, 240, 82);
		scrollPaneEjercicios.setBorder(BorderFactory.createTitledBorder("Ejercicios"));
		centerPanel.add(scrollPaneEjercicios);

		textAreaEjercicios = new JTextArea();
		textAreaEjercicios.setFont(UIManager.getFont("Label.font"));
		textAreaEjercicios.setEditable(false);
		scrollPaneEjercicios.setViewportView(textAreaEjercicios);

		scrollPaneDescripcion = new JScrollPane();
		scrollPaneDescripcion.setBorder(BorderFactory.createTitledBorder("Descripcion"));
		scrollPaneDescripcion.setBounds(436, 229, 240, 91);
		centerPanel.add(scrollPaneDescripcion);

		textAreaDescripcion = new JTextArea();
		textAreaEjercicios.setFont(UIManager.getFont("Label.font"));
		textAreaDescripcion.setEditable(false);
		textAreaDescripcion.setLineWrap(true);
		scrollPaneDescripcion.setViewportView(textAreaDescripcion);

		btnIniciar = new JButton("Iniciar Workout");
		btnIniciar.setBounds(0, 493, 688, 40);
		btnIniciar.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnIniciar.setBackground(new Color(102, 153, 255));
		btnIniciar.setForeground(Color.WHITE);
		btnIniciar.setFocusPainted(false);
		btnIniciar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		add(btnIniciar);

		btnPerfil = new JButton("Perfil");
		btnPerfil.setBounds(595, 0, 93, 23);
		add(btnPerfil);

		if (user != null) {
			actualizarListaWorkouts();
		}
	}

	@SuppressWarnings("unchecked")
	private void actualizarListaWorkouts() {
		workoutListModel.clear();
		String nivelSeleccionado = (String) filtroNivel.getSelectedItem();
		int filterLevel = nivelSeleccionado.equals("Todos") ? -1 : Integer.parseInt(nivelSeleccionado.split(" ")[1]);

		for (Workout workout : this.workouts) {
			if (workout.getNivel() <= user.getNivel() && (filterLevel == -1 || workout.getNivel() == filterLevel)) {
				workoutListModel.addElement("Workout: " + workout.getNombre());
			}
		}
		workoutsList.setModel(workoutListModel);
	}

	public void setWorkouts(ArrayList<Workout> workouts) {
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

	public JList<?> getWorkoutsList() {
		return workoutsList;
	}

	public void setWorkoutsList(JList<?> workoutsList) {
		this.workoutsList = workoutsList;
	}

	public Workout getWorkoutseleccionado() {
		return this.workouSelect;
	}

	public JButton getBtnIniciar() {
		return btnIniciar;
	}

	public void setBtnIniciar(JButton btnIniciar) {
		this.btnIniciar = btnIniciar;
	}

	public JLabel getLblUrl() {
		return lblUrl;
	}

	public void setLblUrl(JLabel lblUrl) {
		this.lblUrl = lblUrl;
	}

	public JTextArea getTextArea() {
		return textAreaEjercicios;
	}

	public void setTextArea(JTextArea textArea) {
		this.textAreaEjercicios = textArea;
	}

	public JLabel getLblNEjer() {
		return lblNEjer;
	}

	public void setLblNEjer(JLabel lblNEjer) {
		this.lblNEjer = lblNEjer;
	}

	public JButton getBtnPerfil() {
		return btnPerfil;
	}

	public void setBtnPerfil(JButton btnPerfil) {
		this.btnPerfil = btnPerfil;
	}

	public JTextArea getTextAreaDescripcion() {
		return textAreaDescripcion;
	}

	public void setTextAreaDescripcion(JTextArea textAreaDescripcion) {
		this.textAreaDescripcion = textAreaDescripcion;
	}
}
