package flashcards;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;

public class LogService implements PropertyChangeListener {
    private static LogService instance;
    private final List<String> log;

    private LogService() {
        log = new LinkedList<>();
    }

    public static LogService getInstance() {
        if (instance == null) {
            synchronized (LogService.class) {
                if (instance == null) {
                    instance = new LogService();
                }
            }
        }
        return instance;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        log.add((String) evt.getNewValue());
    }

    public void write(String filename) {
        FileIOUtil.write(filename, log);
        log.clear();
    }
}