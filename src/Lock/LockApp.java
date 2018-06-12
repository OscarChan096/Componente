package Lock;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class LockApp extends JLabel implements MouseListener, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ConexionMySQL interfazMysql;
	Container contenedorLock;
	Container contenedorFrame;
	JFrame frame;
	private Image background;
	JTextField usuarioField;
	JPasswordField contrasenaField;
	private Vector<LockListener> lockListeners = new Vector<LockListener>();
	private String pass, usuario;

	public LockApp(JFrame frame) {
		addMouseListener(this);
		this.setPreferredSize(new Dimension(35, 35));
		this.frame = frame;
	}

	public void conectarBD() {
		interfazMysql = new ConexionMySQL();
		Connection bdCon = interfazMysql.Conectar();
	}

	public void login() {

		JFrame dialog = new JFrame();
		dialog.setLocationRelativeTo(frame);
		dialog.setUndecorated(true);
		dialog.setSize(250, 110);

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

		dialog.setAlwaysOnTop(true);

		dialog.setVisible(true);

		aceptarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				conectarBD();
				String usuarioIn = usuarioField.getText().toString();
				char[] passChar = contrasenaField.getPassword();
				String passIn = new String(passChar);

				ResultSet regs = interfazMysql.consultarClientes();
				boolean bol = false;
				try {
					while (regs.next()) {
						usuario = regs.getString("usuario");
						pass = regs.getString("contrasenia");
						if (usuario.equals(usuarioIn) && pass.contentEquals(passIn)) {
							LockEvent le = new LockEvent(this, frame, contenedorFrame);
							notificarEvento(le);
							dialog.dispose();
							bol = true;
						}
					}
					if (!bol) {
						JOptionPane.showMessageDialog(dialog, "Datos incorrectos o inexistentes",
								"Error de atenticacion", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (SQLException errSelect) {
					errSelect.printStackTrace();
				}

			}
		});

	}

	public synchronized void addLockListener(LockListener listener) {
		lockListeners.addElement(listener);
	}

	public synchronized void removeLockListener(LockListener listener) {
		lockListeners.removeElement(listener);
	}

	public void notificarEvento(LockEvent le) {
		Vector lista;
		synchronized (this) {
			lista = (Vector) lockListeners.clone();
		}
		for (int i = 0; i < lista.size(); i++) {
			LockListener listener = (LockListener) lista.elementAt(i);
			listener.claveCorrecta(le);
		}
	}

	public void paintComponent(Graphics g) {

		int width = this.getSize().width;
		int height = this.getSize().height;

		this.setOpaque(false);
		this.background = new ImageIcon("src/icon/icx.png").getImage();

		g.drawImage(this.background, 0, 0, width, height, null);

		super.paintComponent(g);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				contenedorLock = new Container();
				contenedorFrame = frame.getContentPane();
				contenedorLock.setLayout(null);
				frame.setContentPane(contenedorLock);
				frame.setDefaultCloseOperation(0);
				login();
			}
		});
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}