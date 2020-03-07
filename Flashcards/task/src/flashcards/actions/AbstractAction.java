package flashcards.actions;

import flashcards.CardStore;
import flashcards.ReaderFactory;
import flashcards.WriterFactory;
import flashcards.serialization.Serializer;

import java.io.PrintStream;

public abstract class AbstractAction implements Action {
    protected final CardStore cardStore;

    protected final ReaderFactory reader;
    protected final PrintStream writer;
    protected final ActionModifier actionModifier;
    protected final Serializer serializer;

    public AbstractAction() {
        cardStore = CardStore.getInstance();
        actionModifier = ActionModifier.getInstance();
        reader = ReaderFactory.getInstance();
        writer = WriterFactory.getInstance().getWriter();
        serializer = Serializer.getInstance();
    }
}