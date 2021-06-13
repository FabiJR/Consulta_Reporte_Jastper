package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

import entidad.Cliente;
import entidad.TipoCliente;
import model.ClienteModel;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JRViewer;
import reporte.GeneradorReporte;

public class frmConsultaCliente extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JTextField txtDni;
	private JButton btnFiltrar;
	private JPanel panelReporte;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmConsultaCliente frame = new frmConsultaCliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public frmConsultaCliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 607, 399);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("Consulta de Cliente");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(193, 11, 201, 39);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("DNI");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(162, 74, 52, 19);
		contentPane.add(lblNewLabel_1);
		
		txtDni = new JTextField();
		txtDni.setBounds(224, 75, 139, 20);
		contentPane.add(txtDni);
		txtDni.setColumns(10);
		
		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.addActionListener(this);
		btnFiltrar.setBounds(373, 74, 89, 23);
		contentPane.add(btnFiltrar);
		
		panelReporte = new JPanel();
		panelReporte.setBounds(10, 113, 571, 236);
		contentPane.add(panelReporte);
	}
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnFiltrar) {
			doBtnFiltrarActionPerformed(arg0);
		}
	}
	protected void doBtnFiltrarActionPerformed(ActionEvent arg0) {
		String dni = txtDni.getText().trim();
	
		ClienteModel model = new ClienteModel();
		List<Cliente> lstData = model.consultaCliente(dni);
	
		// 1 La data
		JRBeanCollectionDataSource dataSource 
				= new JRBeanCollectionDataSource(lstData);

		// 2 El diseño del reporte (Diseño)
		String file = "reporteDirector.jasper";
		
		// 3 Se genera el reporte
		JasperPrint jasperPrint =
			GeneradorReporte.genera(file, dataSource, null);

		// 4 Se muestra en el visor
		JRViewer jRViewer = new JRViewer(jasperPrint);
		
		// 5 Se añade el visor al panel
		panelReporte.removeAll();
		panelReporte.add(jRViewer);
		panelReporte.repaint();
		panelReporte.revalidate();
		
	}
}
