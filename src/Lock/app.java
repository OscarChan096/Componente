package Lock;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class app extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ConexionMySQL interfazMysql;
	JTable table;
	JPanel panel = new JPanel();
	JButton btn, btnConexion, aceptarbtn, consultarbtn;
	JTextField usuarioField;
	JPasswordField contrasenaField;

	public static void main(String[] args) {
		app ap = new app();
	}

	public app() {
		setSize(200, 100);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		Border border = BorderFactory.createLineBorder(Color.GRAY);
		
		JPanel panel2 = new JPanel();
		panel2.setBorder(border);
		add(panel2, BorderLayout.CENTER);

		btn = new JButton("Registrar");
		btn.setActionCommand("registrar");
		btn.addActionListener(this);
		btn.setBackground(Color.WHITE);

		btnConexion = new JButton("Conectar");
		btnConexion.setActionCommand("acConectar");
		btnConexion.addActionListener(this);
		btnConexion.setBackground(Color.WHITE);
		
		setVisible(true);

		Login log = new Login();
		LockApp la = new LockApp(this);
		la.addLockListener(log);

		add(panel, BorderLayout.WEST);
		
		panel2.add(btn);
		panel2.add(btnConexion);
		panel.add(la, BorderLayout.WEST);
	}

	public void registrarUser() {
		JFrame dialog = new JFrame();
		dialog.setLocationRelativeTo(this);
		dialog.setSize(250, 146);

		dialog.add(Box.createRigidArea(new Dimension(10, 10)), BorderLayout.WEST);
		dialog.add(Box.createRigidArea(new Dimension(10, 10)), BorderLayout.EAST);

		// Border border = BorderFactory.createLineBorder(Color.GRAY);

		JPanel panel = new JPanel();
		// panel.setBorder(border);
		panel.setLayout(new BorderLayout());
		dialog.add(panel, BorderLayout.CENTER);

		JPanel usuarioPanel = new JPanel();
		usuarioPanel.setLayout(new BoxLayout(usuarioPanel, BoxLayout.Y_AXIS));
		panel.add(usuarioPanel, BorderLayout.NORTH);

		JPanel contrasenaPanel = new JPanel();
		contrasenaPanel.setLayout(new BoxLayout(contrasenaPanel, BoxLayout.Y_AXIS));
		panel.add(contrasenaPanel, BorderLayout.CENTER);

		JPanel btnPanel = new JPanel();
		panel.add(btnPanel, BorderLayout.SOUTH);

		JLabel usuarioLab = new JLabel("Usuario");
		usuarioField = new JTextField();
		JLabel contrasenaLab = new JLabel("Contraseña");
		contrasenaField = new JPasswordField();
		JButton aceptarBtn = new JButton("Aceptar");
		aceptarBtn.setBackground(Color.WHITE);

		usuarioPanel.add(usuarioLab);
		usuarioPanel.add(usuarioField);
		contrasenaPanel.add(contrasenaLab);
		contrasenaPanel.add(contrasenaField);
		btnPanel.add(aceptarBtn);

		dialog.setVisible(true);

		aceptarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String usuario = usuarioField.getText().toString();
				char[] passChar = contrasenaField.getPassword();
				String pass = new String(passChar);

				interfazMysql.insertarCliente(usuario, pass);
				System.out.println("agregado");

			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("registrar")) {
			registrarUser();
		} else if (e.getActionCommand().equals("acConectar")) {
			interfazMysql = new ConexionMySQL();
			Connection bdCon = interfazMysql.Conectar();

			if (bdCon != null) {
				JOptionPane.showMessageDialog(null, "Conectado");
			}
		}

	}

}