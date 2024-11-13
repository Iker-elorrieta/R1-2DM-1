package vista;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import modelo.Historial;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

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
		panel_2.setBounds(0, 0, 730, 55);
		add(panel_2);
		panel_2.setLayout(null);

		btnAtras = new JButton("Atrás");
		btnAtras.setBounds(8, 8, 122, 40);
		panel_2.add(btnAtras);
		btnAtras.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnAtras.setBackground(new Color(102, 153, 255));
		btnAtras.setForeground(Color.WHITE);
		btnAtras.setFocusPainted(false);
		btnAtras.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		JPanel panelCentral = new JPanel();
		panelCentral.setBounds(0, 55, 730, 486);
		add(panelCentral);
		panelCentral.setLayout(null);

		JScrollPane jScrollPanel;
		jScrollPanel = new JScrollPane();
		jScrollPanel.setBounds(10, 81, 668, 394);
		panelCentral.add(jScrollPanel);
		String columnas[] = { "Nombre", "Nivel", "Tiempo estimado", "Tiempo realizado", "Fecha", "Ejercicios hechos" };

		defaultTableModel = new DefaultTableModel(columnas, 0);

		tabla = new JTable(defaultTableModel);
		tabla.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tabla.setFillsViewportHeight(true);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setRowSelectionAllowed(false);
		tabla.setCellSelectionEnabled(false);
		tabla.setDefaultEditor(Object.class, null);

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(defaultTableModel);
		tabla.setRowSorter(sorter);

		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		sortKeys.add(new RowSorter.SortKey(4, SortOrder.DESCENDING));
		sorter.setSortKeys(sortKeys);
		sorter.sort();

		jScrollPanel.setViewportView(tabla);

		JLabel lblTitulo = new JLabel("Historial de workouts");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(8, 8, 670, 43);
		panelCentral.add(lblTitulo);
	}

	public void actualizarVentana() {
		defaultTableModel.setRowCount(0);

		if (historicosDelUsurio != null) {
			for (Historial historial : historicosDelUsurio) {

				String fecha = historial.getFecha().toString();

				String[] partes = fecha.split(" ");
				String dia = partes[2];
				String mes = partes[1];
				String ano = partes[5];

				String fechaFormateada = dia + " - " + mes + " - " + ano;

				String[] fila = { historial.getWorkout().getNombre(), String.valueOf(historial.getWorkout().getNivel()),
						String.valueOf(
								String.format("%02d:%02d", ((int) historial.getWorkout().getTiempoEstimado() / 60),
										((int) historial.getWorkout().getTiempoEstimado() % 60))),
						String.valueOf(String.format("%02d:%02d", ((int) historial.getTiempoRealizacion() / 60),
								((int) historial.getTiempoRealizacion() % 60))),
						fechaFormateada, historial.getPorcentajeCompletado() + "%" };
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