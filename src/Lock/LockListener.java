package Lock;

import java.util.EventListener;

public interface LockListener extends EventListener{
    public void claveCorrecta(LockEvent evt);
}