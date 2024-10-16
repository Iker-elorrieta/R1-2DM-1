package vista;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;

import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;


public class PanelConsultar extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tablaContactos;
	private DefaultTableModel defaultTableModel;
	

	/**
	 * Create the panel.
	 */
	public PanelConsultar() {
		setBackground(Color.orange);
		setBounds(288, 11, 688, 541);
		setLayout(null);

		
		
		JScrollPane jScrollPanel;
		jScrollPanel = new JScrollPane();
		jScrollPanel.setBounds(40, 150, 508, 267);
		add(jScrollPanel);
		
		

		String columnas[] = { "Id", "Nombre", "Teléfono", "E-mail" };

		defaultTableModel = new DefaultTableModel(columnas, 0);

		 tablaContactos = new JTable(defaultTableModel);
		tablaContactos.setAutoCreateRowSorter(true);
		tablaContactos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaContactos.setRowSelectionAllowed(false);
		tablaContactos.setCellSelectionEnabled(false);

		
		tablaContactos.setDefaultEditor(Object.class, null); //Anulamos la edici�n en la propia celda


		jScrollPanel.setViewportView(tablaContactos);

		
	}

	public DefaultTableModel getDefaultTableModel() {
		return defaultTableModel;
	}

	public void setDefaultTableModel(DefaultTableModel defaultTableModel) {
		this.defaultTableModel = defaultTableModel;
	}

}
