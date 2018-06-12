package Lock;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class LockMain extends JFrame{
	
	JTable table;

	public static void main(String[] args) {
		JFrame frame = new LockMain();
	}
	
	public LockMain() {
		setVisible(true);
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		
		JButton btn = new JButton("HOLA");
		
		//add(la, BorderLayout.NORTH);
		add(btn, BorderLayout.CENTER);
	}

}
