package vista;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;

import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;


public class PanelIngresar extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tablaContactos;
	private DefaultTableModel defaultTableModel;
	private JTextField textFieldNombre;
	private JTextField textFieldEmail;
	private JTextField textFieldTel;
	
	private JButton anahdir;
	/**
	 * Create the panel.
	 */
	public PanelIngresar() {
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
		
		JLabel lblName = new JLabel("Nombre");
		lblName.setBounds(52, 43, 46, 14);
		add(lblName);
		
		JLabel lblTel = new JLabel("Tel");
		lblTel.setBounds(52, 88, 46, 14);
		add(lblTel);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(301, 43, 46, 14);
		add(lblEmail);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(108, 40, 86, 20);
		add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(358, 40, 86, 20);
		add(textFieldEmail);
		
		textFieldTel = new JTextField();
		textFieldTel.setColumns(10);
		textFieldTel.setBounds(108, 85, 86, 20);
		add(textFieldTel);
		
		 anahdir = new JButton("Insertar Contacto");
		anahdir.setBounds(292, 84, 152, 23);
		add(anahdir);

		
	}

	public DefaultTableModel getDefaultTableModel() {
		return defaultTableModel;
	}

	public void setDefaultTableModel(DefaultTableModel defaultTableModel) {
		this.defaultTableModel = defaultTableModel;
	}
	
	public JButton getBtnInsertarContarctos() {
		return anahdir;
	}

	public void setBtnInsertarContarctos(JButton anahdir) {
		this.anahdir = anahdir;
	}

	
//TEXTFIELD
	public JTextField getTextFieldNombre() {
		return textFieldNombre;
	}
	public void setTextFieldNombre(JTextField textFieldNombre) {
		this.textFieldNombre = textFieldNombre;
	}
	
	public JTextField getTextFieldEmail() {
		return textFieldEmail;
	}

	public void setTextFieldEmail(JTextField textFieldEmail) {
		this.textFieldEmail = textFieldEmail;
	}	
	
	public JTextField getTextFieldTel() {
		return textFieldTel;
	}
	public void setTextFieldTel(JTextField textFieldTel) {
		this.textFieldTel = textFieldTel;
	}	
}