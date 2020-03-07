package flashcards;

import org.jetbrains.annotations.NotNull;

import java.io.OutputStream;
import java.io.PrintStream;

public class WriterFactory {
    private static WriterFactory instance;
    private final PrintStream writer;

    private WriterFactory() {
        System.setOut(new ModifiedPrintStream(System.out));
        writer = System.out;
    }

    public static WriterFactory getInstance() {
        if (instance == null) {
            synchronized (ReaderFactory.class) {
                if (instance == null) {
                    instance = new WriterFactory();
                }
            }
        }
        return instance;
    }

    public PrintStream getWriter() {
        return writer;
    }

    private static class ModifiedPrintStream extends PrintStream {
        private final IOBroadcaster broadcaster;


        public ModifiedPrintStream(@NotNull OutputStream out) {
            super(out);
            broadcaster = IOBroadcaster.getInstance();
        }

        @Override
        public void println(String x) {
            super.println(x);
            broadcaster.broadcast(x);
            broadcaster.broadcast(System.lineSeparator());
        }

        @Override
        public void println() {
            super.println();
            broadcaster.broadcast(System.lineSeparator());
        }

        @Override
        public PrintStream printf(@NotNull String format, Object... args) {
            String msg = String.format(format, args);
            print(msg);
            broadcaster.broadcast(msg);
            return this;
        }
    }
}