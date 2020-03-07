package flashcards.actions;

import flashcards.Card;
import flashcards.serialization.Deserializer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public class ImportAction extends AbstractAction {

    private static final String PROMPT_MSG = "File name:";
    private static final String ERR_MSG = "File not found.";
    private static final String SUCCESSFUL_MSG = "%d cards have been loaded.%n";

    private final Deserializer deserializer;

    public ImportAction() {
        super();
        deserializer = Deserializer.getInstance();
    }

    @Override
    public void execute() {
        String filename = getFilename();
        if (Files.exists(Paths.get(filename))) {
            Set<Card> cards = deserializer.read(filename);
            cardStore.add(cards);
            writer.printf(SUCCESSFUL_MSG, cards.size());
            return;
        }
        writer.println(ERR_MSG);
    }

    @Override
    public String name() {
        return "import";
    }

    private String getFilename() {
        writer.println(PROMPT_MSG);
        return reader.nextLine();
    }
}