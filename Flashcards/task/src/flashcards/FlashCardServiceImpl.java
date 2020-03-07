package flashcards;

import flashcards.actions.Action;
import flashcards.actions.ActionModifier;
import flashcards.actions.ActionsFactory;
import flashcards.serialization.Deserializer;

import java.io.PrintStream;

public class FlashCardServiceImpl implements FlashCardService {
    private static final String SUCCESSFUL_MSG = "%d cards have been loaded.%n";
    private static final String PROMPT_MSG = "Input the action (%s):%n";
    private static final String MSG = "Bye bye!";

    private final CardStore cardStore;
    private final ActionModifier actionModifier;
    private final Deserializer deserializer;
    private final PrintStream writer;
    private final ActionsFactory actionsFactory;
    private final ReaderFactory reader;

    public FlashCardServiceImpl() {
        cardStore = CardStore.getInstance();
        actionModifier = ActionModifier.getInstance();
        deserializer = Deserializer.getInstance();
        writer = WriterFactory.getInstance().getWriter();
        reader = ReaderFactory.getInstance();
        actionsFactory = ActionsFactory.getInstance();
    }

    @Override
    public void start() {
        String importFrom = actionModifier.getImportFrom();
        if (importFrom != null) {
            cardStore.add(deserializer.read(importFrom));
            if (cardStore.size() != 0) {
                writer.printf(SUCCESSFUL_MSG, cardStore.size());
            }
        }

        do {
            writer.printf(PROMPT_MSG, actionsFactory.getListOfAction());
            String candidateAction = reader.nextLine();
            Action action = actionsFactory.getAction(candidateAction);
            action.execute();
            writer.println();
        } while (true);
    }
}