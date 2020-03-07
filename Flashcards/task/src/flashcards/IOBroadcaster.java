package flashcards;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class IOBroadcaster {
    private static IOBroadcaster instance;
    private final PropertyChangeSupport changeSupport;
    private String msg;

    private IOBroadcaster() {
        changeSupport = new PropertyChangeSupport(this);
        addListener(LogService.getInstance());
    }

    public static IOBroadcaster getInstance() {
        if (instance == null) {
            synchronized (IOBroadcaster.class) {
                if (instance == null) {
                    instance = new IOBroadcaster();
                }
            }
        }
        return instance;
    }

    public void addListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void broadcast(String msg) {
        changeSupport.firePropertyChange("msg", this.msg, msg);
        this.msg = msg;
    }

    public void broadcast(int num) {
        broadcast(String.valueOf(num));
    }
}