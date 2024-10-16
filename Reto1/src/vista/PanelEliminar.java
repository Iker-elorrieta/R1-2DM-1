package vista;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;

import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JButton;


public class PanelEliminar extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tablaContactos;
	private DefaultTableModel defaultTableModel;

	private JButton btnEliminar;
	private JLabel lblNewLabel;
	/**
	 * Create the panel.
	 */
	public PanelEliminar() {
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
		tablaContactos.setRowSelectionAllowed(true);
		//tablaContactos.setCellSelectionEnabled(false);


		tablaContactos.setDefaultEditor(Object.class, null); //Anulamos la edici�n en la propia celda


		jScrollPanel.setViewportView(tablaContactos);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(446, 84, 152, 23);
		add(btnEliminar);

		lblNewLabel = new JLabel("Selecciona la fila  a eliminar");
		lblNewLabel.setBounds(118, 125, 157, 14);
		add(lblNewLabel);


	}


	public DefaultTableModel getDefaultTableModel() {
		return defaultTableModel;
	}

	public void setDefaultTableModel(DefaultTableModel defaultTableModel) {
		this.defaultTableModel = defaultTableModel;
	}

	public JTable getTablaContactos() {
		return tablaContactos;
	}

	public void setTablaContactos(JTable tablaContactos) {
		this.tablaContactos = tablaContactos;
	}

	public JButton getBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnEliminar(JButton btnEliminar) {
		this.btnEliminar = btnEliminar;
	}
}