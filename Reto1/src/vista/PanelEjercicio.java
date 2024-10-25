package vista;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;

import javax.swing.JButton;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.BorderFactory;

import modelo.Ejercicio;
import modelo.Serie;
import modelo.Usuario;
import modelo.WorkOut;


public class PanelEjercicio extends JPanel {
	private static final long serialVersionUID = 1L;
	private WorkOut workouSelect;
	private Usuario user;
	private Ejercicio ejercicio;
	private int labelAltura = 24;
	private int margenEntrePanelSeires = 15;
	private JLabel lblDescripcionE;
	private JLabel lblWorkout;
	private JPanel panelMenu;

	private JLabel lblCTiempoE;
	private JLabel lblCWorkout;
	private JLabel lblCDescanso;
	JButton btnIniciar;
	JButton btnPausar;
	JLabel lblNSerie2;
	JLabel lblImg2;
	JLabel lblCSerie;
	Serie serieActual;
	ArrayList<JLabel> conjuntoDeCronometros = new  ArrayList<JLabel>(); //Segun eneko con esto puedo se puede seleccionar a que label acceder
	
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
		panelCronometroW_1_1.setBorder(BorderFactory.createTitledBorder("Descanso 1 min"));
		panelCronometroW_1_1.setBackground(new Color(245, 245, 245));
		panelCronometroW_1_1.setBounds(0, 51, 168, 51);
		panelCentral.add(panelCronometroW_1_1);

		lblCDescanso = new JLabel("00:45");
		lblCDescanso.setHorizontalAlignment(SwingConstants.CENTER);
		lblCDescanso.setFont(new Font("Tahoma", Font.PLAIN, 17));
		panelCronometroW_1_1.add(lblCDescanso);


		JButton btnSalir = new JButton("Salir");
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
		btnPausar.setBackground(new Color(102, 153, 255));
		btnPausar.setBounds(290, 406, 156, 40);
		btnPausar.setVisible(false);
		panelCentral.add(btnPausar);


		lblNSerie2 = new JLabel("Default");
		lblNSerie2.setBounds(169, 55, 107, 14);
		panelMenu.add(lblNSerie2);

		lblImg2 = new JLabel("Default");
		lblImg2.setBounds(56, 24, 87, 70);
		panelMenu.add(lblImg2);
		lblImg2.setHorizontalAlignment(SwingConstants.CENTER);
		lblImg2.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		 lblCSerie = new JLabel("Default");
		lblCSerie.setBounds(314, 55, 107, 14);
		panelMenu.add(lblCSerie);

	}

	public void actualizarVentana () {
		ejercicio = workouSelect.getEjercicios().get(0);
		lblDescripcionE.setText(ejercicio.getNombre() + "- Descripción");
		lblWorkout.setText("Workout "+ workouSelect.getNombre());

		panelMenu.removeAll();
		//Generamos los iconos de forma dinamica
		for (Serie serie : ejercicio.getSeries()) {

			serieActual = serie;
			lblNSerie2 = new JLabel(serie.getNombre());
			lblNSerie2.setBounds(208, labelAltura, 107, 14);
			panelMenu.add(lblNSerie2);

			lblImg2 = new JLabel(serie.getImagenURL());
			lblImg2.setBounds(134, labelAltura, 64, 21);
			panelMenu.add(lblImg2);
			lblImg2.setHorizontalAlignment(SwingConstants.CENTER);
			lblImg2.setFont(new Font("Tahoma", Font.PLAIN, 17));

			lblCSerie = new JLabel("Default");
			lblCSerie.setBounds(314, labelAltura, 107, 14);
			lblCSerie.setText((String.format("%02d:%02d:%02d", (
						(int) serie.getTiempoSerie() / 60), 		//min
						((int)  serie.getTiempoSerie() % 60), 		//seg
						(int) (( serie.getTiempoSerie() - serie.getTiempoSerie() % 60)) * 1000)));
			panelMenu.add(lblCSerie);

			conjuntoDeCronometros.add(lblCSerie);
			labelAltura += lblNSerie2.getHeight() + margenEntrePanelSeires;

			if(labelAltura > panelMenu.getHeight()-20) {
				panelMenu.setPreferredSize(new Dimension(400, labelAltura + margenEntrePanelSeires));
			}
		}
	}
	public void setWorkouSelect(WorkOut workouSelect) {
		this.workouSelect = workouSelect;
	}

	public JLabel getLblCTiempoE() {
		return lblCTiempoE;
	}

	public void setLblCTiempoE(JLabel lblCTiempoE) {
		this.lblCTiempoE = lblCTiempoE;
	}

	public JLabel getLblCWorkout() {
		return lblCWorkout;
	}

	public void setLblCWorkout(JLabel lblCWorkout) {
		this.lblCWorkout = lblCWorkout;
	}

	public JLabel getLblCDescanso() {
		return lblCDescanso;
	}

	public void setLblCDescanso(JLabel lblCDescanso) {
		this.lblCDescanso = lblCDescanso;
	}

	public JButton getBtnIniciar() {
		return btnIniciar;
	}

	public void setBtnIniciar(JButton btnIniciar) {
		this.btnIniciar = btnIniciar;
	}

	public JButton getBtnPausar() {
		return btnPausar;
	}

	public void setBtnPausar(JButton btnPausar) {
		this.btnPausar = btnPausar;
	}
	
	public Serie getSerieActual() {
		return serieActual;
	}

	public JLabel getLblCSerie() {
		return lblCSerie;
	}

	public void setLblCSerie(JLabel lblCSerie) {
		this.lblCSerie = lblCSerie;
	}

	public ArrayList<JLabel> getConjuntoDeCronometros() {
		return conjuntoDeCronometros;
	}

	public void setConjuntoDeCronometros(ArrayList<JLabel> conjuntoDeCronometros) {
		this.conjuntoDeCronometros = conjuntoDeCronometros;
	}
	
	
	
}
