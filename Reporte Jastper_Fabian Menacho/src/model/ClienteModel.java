package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidad.Cliente;
import entidad.TipoCliente;
import util.MySqlDBConexion;

public class ClienteModel {
	
	public List<Cliente> consultaClub(String idCliente){
		ArrayList<Cliente> data = new ArrayList<Cliente>();
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null; //Trae la data de la BD
		try {
			con = MySqlDBConexion.getConexion();
			String sql ="SELECT c.idCliente, c.nombres, c.apellidos, c.fechaNacimiento, t.nombre "
					+ "FROM reporte.cliente c join reporte.tipo_cliente t  on c.idTipoCliente = t.idTpoCliente "
					+ "WHERE c.idCliente = ?";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, idCliente);
			System.out.println("SQL-->" + pstm);
			rs = pstm.executeQuery();
			
			Cliente c = null;
			TipoCliente t = null;
			while(rs.next()){
				c = new Cliente();
				c.setIdCliente(rs.getInt(1));
				c.setNombre(rs.getString(2));
				c.setApellido(rs.getString(3));
				c.setFechaNac(rs.getDate(4));
				
				t = new TipoCliente();
				t.setNombreTipo(rs.getString(5));
				
				c.setIdTipo(t.getIdTipo());
				data.add(c);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null)pstm.close();
				if (con != null)con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
}
