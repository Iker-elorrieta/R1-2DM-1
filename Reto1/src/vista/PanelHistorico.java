package vista;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import modelo.Historial;

public class PanelHistorico extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTable tabla;
	private JButton btnAtras;
	DefaultTableModel defaultTableModel;
	private ArrayList<Historial> historicosDelUsurio;
	public PanelHistorico() {

		setBackground(new Color(230, 230, 250));
		setBounds(288, 11, 688, 541);
		setLayout(null);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 0, 688, 55);
		add(panel_2);
		panel_2.setLayout(null);

		JPanel panelCentral = new JPanel();
		panelCentral.setBounds(0, 55, 688, 486);
		add(panelCentral);
		panelCentral.setLayout(null);

		btnAtras = new JButton("Atrás");
		btnAtras.setBounds(10, 11, 156, 40);
		panelCentral.add(btnAtras);
		btnAtras.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnAtras.setBackground(new Color(102, 153, 255));
		btnAtras.setForeground(Color.WHITE);
		btnAtras.setFocusPainted(false);
		btnAtras.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		JScrollPane jScrollPanel;
		jScrollPanel = new JScrollPane();
		jScrollPanel.setBounds(40, 150, 620, 325);
		panelCentral.add(jScrollPanel);
		String columnas[] = { "NombreWorkout", "Nivel", "Timepo Estimado", "Tiempo de realizacion","Fecha ", "% Ejercicios Completados" };

		defaultTableModel = new DefaultTableModel(columnas, 0);

		tabla = new JTable(defaultTableModel);
		tabla.setFillsViewportHeight(true);
		tabla.setAutoCreateRowSorter(true);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setRowSelectionAllowed(false);
		tabla.setCellSelectionEnabled(false);

		tabla.setDefaultEditor(Object.class, null);



		jScrollPanel.setViewportView(tabla);
	}


	public void actualizarVentana() {
		defaultTableModel.setRowCount(0);

		if (historicosDelUsurio != null) {
			for (Historial historial : historicosDelUsurio) {
				System.out.print(historial.getWorkout().toString());

				String[] fila = {
						historial.getWorkout().getNombre(),

						String.valueOf(historial.getWorkout().getNivel()),
						String.valueOf(  
								String.format("%02d:%02d",
										((int) historial.getWorkout().getTiempoEstimado() / 60), // min
										((int) historial.getWorkout().getTiempoEstimado() % 60)))
						,
						String.valueOf(  
								String.format("%02d:%02d",
										((int) historial.getTiempoRealizacion() / 60), // min
										((int) historial.getTiempoRealizacion() % 60)))
						,
	
						historial.getFecha().toString(),
						"%" + historial.getPorcentajeCompletado()
				};
				defaultTableModel.addRow(fila);

			}
		}
	}



	public JButton getBtnAtras() {
		return btnAtras;
	}

	public void setBtnAtras(JButton btnAtras) {
		this.btnAtras = btnAtras;
	}

	public void setHistoricosDelUsurio(ArrayList<Historial> historicosDelUsurio) {
		this.historicosDelUsurio = historicosDelUsurio;
	}


}