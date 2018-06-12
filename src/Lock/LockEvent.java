package Lock;

import java.awt.Container;
import java.util.EventObject;

import javax.swing.JFrame;

public class LockEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Object source;
	protected JFrame frame;
	protected Container contenedor;

	public LockEvent(Object source, JFrame frame, Container contenedor) {
		super(source);
		this.source = source;
		this.frame = frame;
		this.contenedor = contenedor;
	}
	
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
	public Container getContenedor() {
		return contenedor;
	}
	
	public JFrame getFrame() {
		return frame;
	}

	public Object getSource() {
		return source;
	}

}
