package Lock;

import javax.swing.JFrame;

public class Login implements LockListener{
    
    
    public Login(){
    }

	@Override
	public void claveCorrecta(LockEvent evt) {
		JFrame frame = evt.getFrame();
		frame.setContentPane(evt.getContenedor());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
    
}