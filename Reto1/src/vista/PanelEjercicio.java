package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import modelo.Ejercicio;
import modelo.Serie;
import modelo.Workout;

public class PanelEjercicio extends JPanel {
	private static final long serialVersionUID = 1L;
	private Workout workouSelect;
	private JLabel lblDescripcionE;
	private JLabel lblWorkout;
	private JPanel panelMenu;

	private JLabel lblCTiempoE;
	private JLabel lblCWorkout;
	private JLabel lblCDescanso;
	private JLabel lblNombreSerie;
	private JLabel lblImgSerie;
	private JLabel lblCSerie;

	JLabel lblCProximaSerie;
	JLabel lblLaProximaSerie;

	private JButton btnIniciar;
	private JButton btnPausar;
	private JButton btnSiguiente;
	private JButton btnSalir;
	private JScrollPane scrollPane_2;
	private JTextArea textAreaDescripcion;
	JLabel lblTiempoDescansoTexto;
	private ArrayList<JLabel> conjuntoDeCronometros; // En esta variable alamaceraremos las etiquetas donde se
														// visualizaran los cronometros de la serie no se puede hacer get
														// set de ellas directamente por que se crean de forma dinamica
														// posteriormente

	public PanelEjercicio() {

		setBackground(new Color(230, 230, 250));
		setBounds(288, 11, 688, 541);
		setLayout(null);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 0, 688, 55);
		add(panel_2);
		panel_2.setLayout(null);

		JPanel panelCronometroW = new JPanel();
		panelCronometroW.setBounds(0, 0, 167, 51);
		panel_2.add(panelCronometroW);
		panelCronometroW.setBorder(BorderFactory.createTitledBorder("Cronometro workout"));
		panelCronometroW.setBackground(new Color(245, 245, 245));

		lblCWorkout = new JLabel("00:00");
		lblCWorkout.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblCWorkout.setHorizontalAlignment(SwingConstants.CENTER);
		lblCWorkout.setBounds(10, 16, 89, 19);
		panelCronometroW.add(lblCWorkout);

		JPanel panel = new JPanel();
		panel.setBounds(165, 0, 280, 51);
		panel_2.add(panel);

		lblDescripcionE = new JLabel("Ejercicio X- Descripción");
		lblDescripcionE.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescripcionE.setFont(new Font("Tahoma", Font.PLAIN, 17));
		panel.add(lblDescripcionE);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(455, 0, 234, 51);
		panel_2.add(panel_1);

		lblWorkout = new JLabel("Workout ");
		lblWorkout.setHorizontalAlignment(SwingConstants.CENTER);
		lblWorkout.setFont(new Font("Tahoma", Font.PLAIN, 17));
		panel_1.add(lblWorkout);

		JPanel panelCentral = new JPanel();
		panelCentral.setBounds(0, 55, 688, 486);
		add(panelCentral);
		panelCentral.setLayout(null);

		JPanel panelCronometroW_1 = new JPanel();
		panelCronometroW_1.setBorder(BorderFactory.createTitledBorder("Tiempo Ejercicio"));
		panelCronometroW_1.setBackground(new Color(245, 245, 245));
		panelCronometroW_1.setBounds(0, 0, 168, 51);
		panelCentral.add(panelCronometroW_1);

		lblCTiempoE = new JLabel("00:00");
		lblCTiempoE.setHorizontalAlignment(SwingConstants.CENTER);
		lblCTiempoE.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblCTiempoE.setBounds(10, 16, 89, 19);
		panelCronometroW_1.add(lblCTiempoE);

		JPanel panelCronometroW_1_1 = new JPanel();
		panelCronometroW_1_1.setBorder(BorderFactory.createTitledBorder("Descanso"));
		panelCronometroW_1_1.setBackground(new Color(245, 245, 245));
		panelCronometroW_1_1.setBounds(0, 73, 168, 40);
		panelCentral.add(panelCronometroW_1_1);

		lblCDescanso = new JLabel("00:45");
		lblCDescanso.setHorizontalAlignment(SwingConstants.CENTER);
		lblCDescanso.setFont(new Font("Tahoma", Font.PLAIN, 17));
		panelCronometroW_1_1.add(lblCDescanso);

		btnSalir = new JButton("Salir");
		btnSalir.setBounds(522, 406, 156, 40);
		panelCentral.add(btnSalir);
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSalir.setBackground(new Color(102, 153, 255));
		btnSalir.setForeground(Color.WHITE);
		btnSalir.setFocusPainted(false);
		btnSalir.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		btnIniciar = new JButton("play");
		btnIniciar.setForeground(Color.WHITE);
		btnIniciar.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnIniciar.setFocusPainted(false);
		btnIniciar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		btnIniciar.setBackground(new Color(102, 153, 255));
		btnIniciar.setBounds(290, 406, 156, 40);
		panelCentral.add(btnIniciar);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(128, 113, 453, 270);
		panelCentral.add(scrollPane);

		panelMenu = new JPanel();
		scrollPane.setViewportView(panelMenu);
		panelMenu.setLayout(null);

		btnPausar = new JButton("Pausar");
		btnPausar.setForeground(Color.WHITE);
		btnPausar.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnPausar.setFocusPainted(false);
		btnPausar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		btnPausar.setBackground(new Color(255, 105, 180));
		btnPausar.setBounds(290, 406, 156, 40);
		panelCentral.add(btnPausar);

		btnSiguiente = new JButton("Siguiente Ejercicio");
		btnSiguiente.setForeground(Color.WHITE);
		btnSiguiente.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSiguiente.setFocusPainted(false);
		btnSiguiente.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		btnSiguiente.setBackground(new Color(102, 153, 255));
		btnSiguiente.setBounds(290, 406, 200, 40);

		panelCentral.add(btnSiguiente);

		lblNombreSerie = new JLabel("Default");
		lblNombreSerie.setBounds(146, 55, 217, 14);
		panelMenu.add(lblNombreSerie);

		lblImgSerie = new JLabel("Default");
		lblImgSerie.setBounds(10, 17, 124, 85);
		panelMenu.add(lblImgSerie);
		lblImgSerie.setHorizontalAlignment(SwingConstants.CENTER);
		lblImgSerie.setFont(new Font("Tahoma", Font.PLAIN, 17));

		lblCSerie = new JLabel("Default");
		lblCSerie.setBounds(364, 55, 97, 14);
		panelMenu.add(lblCSerie);

		lblLaProximaSerie = new JLabel("La Proxima serie se iniciara en");
		lblLaProximaSerie.setBounds(178, 67, 182, 35);
		panelCentral.add(lblLaProximaSerie);

		lblCProximaSerie = new JLabel("00:05");
		lblCProximaSerie.setHorizontalAlignment(SwingConstants.CENTER);
		lblCProximaSerie.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblCProximaSerie.setBounds(386, 71, 42, 21);
		panelCentral.add(lblCProximaSerie);
		
				scrollPane_2 = new JScrollPane();
				scrollPane_2.setBounds(453, 0, 225, 82);
				panelCentral.add(scrollPane_2);
				scrollPane_2.setBorder(BorderFactory.createTitledBorder("Descripcion"));
				
				textAreaDescripcion = new JTextArea();
				textAreaDescripcion.setEnabled(false);
				textAreaDescripcion.setEditable(false);
				scrollPane_2.setViewportView(textAreaDescripcion);
				
				lblTiempoDescansoTexto = new JLabel("Tiempo de descanso");
				lblTiempoDescansoTexto.setBounds(10, 47, 200, 21);
				panelCentral.add(lblTiempoDescansoTexto);

	}

	public void actualizarVentana(Ejercicio ejercicio) {
		btnIniciar.setVisible(true);
		btnPausar.setVisible(false);
		btnSiguiente.setVisible(false);
		lblLaProximaSerie.setVisible(true);
		lblCProximaSerie.setVisible(true);
		int labelAltura = 24;
		int margenEntrePanelSeires = 15;
		conjuntoDeCronometros = new ArrayList<JLabel>();
		lblCWorkout.setText("00:00");
		lblCTiempoE.setText("00:00");
		lblCProximaSerie.setText("00:05");
		textAreaDescripcion.setText(workouSelect.getDescripcion());
		
		 lblTiempoDescansoTexto.setText("Tiempo de descanso " +  ejercicio.getTiempoDescanso() + " seg" );
		panelMenu.removeAll();
		lblDescripcionE.setText(ejercicio.getNombre() + "- Descripción");
		lblWorkout.setText("Workout " + workouSelect.getNombre());

		lblCDescanso.setText(
				String.format("%02d:%02d", ((int) workouSelect.getEjercicios().get(0).getTiempoDescanso() / 60), // min
						((int) workouSelect.getEjercicios().get(0).getTiempoDescanso() % 60)));// seg

		// Generamos los iconos de forma dinamica
		for (Serie serie : ejercicio.getSeries()) {

			lblNombreSerie = new JLabel(serie.getNombre());
			lblNombreSerie.setBounds(146, labelAltura, 188, 14);
			panelMenu.add(lblNombreSerie);

			lblImgSerie = new JLabel(serie.getImagenURL());
			lblImgSerie.setBounds(15, labelAltura, 124, 85);
			panelMenu.add(lblImgSerie);
			lblImgSerie.setIcon(new ImageIcon(new ImageIcon(serie.getImagenURL()).getImage()
					.getScaledInstance(lblImgSerie.getWidth(), lblImgSerie.getHeight(), Image.SCALE_SMOOTH)));

			lblCSerie = new JLabel("Default");
			lblCSerie.setBounds(334, labelAltura, 100, 14);
			lblCSerie.setText((String.format("%02d:%02d", ((int) serie.getTiempoSerie() / 60), // min
					((int) serie.getTiempoSerie() % 60))));// seg
			panelMenu.add(lblCSerie);

			conjuntoDeCronometros.add(lblCSerie);
			labelAltura += lblImgSerie.getHeight() + margenEntrePanelSeires;

			if (labelAltura > panelMenu.getHeight() - 20) {
				panelMenu.setPreferredSize(new Dimension(400, labelAltura + margenEntrePanelSeires));
			}
			panelMenu.revalidate();
			panelMenu.repaint();
		}
	}

	public JLabel getLblLaProximaSerie() {
		return lblLaProximaSerie;
	}

	public void setLblLaProximaSerie(JLabel lblLaProximaSerie) {
		this.lblLaProximaSerie = lblLaProximaSerie;
	}

	public JLabel getLblCProximaSerie() {
		return lblCProximaSerie;
	}

	public void setLblCProximaSerie(JLabel lblCProximaSerie) {
		this.lblCProximaSerie = lblCProximaSerie;
	}

	public void setWorkouSelect(Workout workouSelect) {
		this.workouSelect = workouSelect;
	}

	public JLabel getLblCTiempoE() {
		return lblCTiempoE;
	}

	public JLabel getLblCWorkout() {
		return lblCWorkout;
	}

	public JLabel getLblCDescanso() {
		return lblCDescanso;
	}

	public JButton getBtnIniciar() {
		return btnIniciar;
	}

	public JButton getBtnPausar() {
		return btnPausar;
	}

	public JButton getBtnSiguiente() {
		return btnSiguiente;
	}

	public ArrayList<JLabel> getConjuntoDeCronometros() {
		return conjuntoDeCronometros;
	}

	public JButton getBtnSalir() {
		return btnSalir;
	}
}
