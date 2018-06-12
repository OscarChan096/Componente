package Lock;

import java.sql.*;
import javax.swing.JOptionPane;

public class ConexionMySQL {

	public String db = "bdapplock";
	public String url = "jdbc:mysql://localhost/" + db + "?useUnicode=true" + "&useJDBCCompliantTimezoneShift=true"
			+ "&useLegacyDatetimeCode=false" + "&serverTimezone=UTC";
	public String user = "root";
	public String pass = "admin096";

	Connection link;

	public Connection Conectar() {
		link = null;

		try {
			Class.forName("org.gjt.mm.mysql.Driver"); // version 5
			// Class.forName("com.mysql.cj.jdbc.Driver"); //version 8

			link = DriverManager.getConnection(this.url, this.user, this.pass);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}

		return link;
	}

	public void insertarCliente(String nombre, String contrasenia) {
		try {
			PreparedStatement stInsertar = link.prepareStatement("INSERT INTO login(usuario, contrasenia)" + " values(?, ?)");

			stInsertar.setString(1, nombre);
			stInsertar.setString(2, contrasenia);

			stInsertar.executeUpdate();
		} catch (SQLException error) {
			error.printStackTrace();
		}
	}

	public ResultSet consultarClientes() {
		ResultSet registros = null;
		try {
			PreparedStatement stConsultar = link
					.prepareStatement("Select usuario, contrasenia from login");

			registros = stConsultar.executeQuery();
		} catch (SQLException error) {
			error.printStackTrace();
		}

		return registros;
	}

}
