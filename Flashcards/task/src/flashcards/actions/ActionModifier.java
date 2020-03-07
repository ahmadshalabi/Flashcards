package flashcards.actions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;

public class ActionModifier {
    private static int argsLock;
    private static ActionModifier instance;
    private String importFromFile;
    private String exportToFile;

    private ActionModifier() {
    }

    public static ActionModifier getInstance() {
        if (instance == null) {
            synchronized (ActionModifier.class) {
                if (instance == null) {
                    instance = new ActionModifier();
                }
            }
        }
        return instance;
    }

    public void setInstance(String[] args) {
//        if (argsLock == 0) {
        Map<String, String> argsMapping = Arrays.stream(args)
                .collect(ArgsModifierCollector.collector());
        setImportFromFile(argsMapping.getOrDefault("import", null));
        setExportToFile(argsMapping.getOrDefault("export", null));
        argsLock++;
//        }
    }

    public String getImportFrom() {
        return importFromFile;
    }

    private void setImportFromFile(String importFromFile) {
        this.importFromFile = importFromFile;
    }

    public String getExportToFile() {
        return exportToFile;
    }

    private void setExportToFile(String exportToFile) {
        this.exportToFile = exportToFile;
    }

    private static final class ActionModifierHolder {
        private static final ActionModifier INSTANCE = new ActionModifier();
    }

    private static final class ArgsModifierCollector {

        private Map<String, String> args = new HashMap<>();

        private String second;

        public static Collector<String, ?, Map<String, String>> collector() {
            return Collector.of(ArgsModifierCollector::new, ArgsModifierCollector::accept, ArgsModifierCollector::combine, ArgsModifierCollector::finish);
        }

        private void accept(String str) {
            String first = second;
            second = str;
            if (first != null && second != null && first.startsWith("-")) {
                args.put(first.replace("-", ""), second);
                second = null;
            }
        }

        private ArgsModifierCollector combine(ArgsModifierCollector other) {
            throw new UnsupportedOperationException("Parallel Stream not supported");
        }

        private Map<String, String> finish() {
            return args;
        }
    }
}