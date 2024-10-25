package vista;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class PanelHistorico extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTable tabla;

	public PanelHistorico() {

		setBackground(new Color(230, 230, 250));
		setBounds(288, 11, 688, 541);
		setLayout(null);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 0, 688, 55);
		add(panel_2);
		panel_2.setLayout(null);

		JButton btnAtras = new JButton("Atrás");
		btnAtras.setBounds(10, 11, 156, 40);
		panel_2.add(btnAtras);
		btnAtras.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnAtras.setBackground(new Color(102, 153, 255));
		btnAtras.setForeground(Color.WHITE);
		btnAtras.setFocusPainted(false);
		btnAtras.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		JPanel panelCentral = new JPanel();
		panelCentral.setBounds(0, 55, 688, 486);
		add(panelCentral);
		panelCentral.setLayout(null);

		JScrollPane jScrollPanel;
		jScrollPanel = new JScrollPane();
		jScrollPanel.setBounds(40, 150, 508, 267);
		add(jScrollPanel);

		String columnas[] = { "Id", "Nombre", "Teléfono", "E-mail" };

		DefaultTableModel defaultTableModel = new DefaultTableModel(columnas, 0);

		tabla = new JTable(defaultTableModel);
		tabla.setAutoCreateRowSorter(true);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setRowSelectionAllowed(false);
		tabla.setCellSelectionEnabled(false);

		tabla.setDefaultEditor(Object.class, null);

		jScrollPanel.setViewportView(tabla);

	}
}