package flashcards.actions;

import flashcards.LogService;

public class LogAction extends AbstractAction {
    private static final String PROMPT_MSG = "File name:";
    private static final String SUCCESSFUL = "The log has been saved.";

    private final LogService logService;

    public LogAction() {
        super();
        this.logService = LogService.getInstance();
    }

    @Override
    public void execute() {
        String filename = getFilename();
        writer.println(SUCCESSFUL);
        logService.write(filename);
    }

    private String getFilename() {
        writer.println(PROMPT_MSG);
        return reader.nextLine();
    }

    @Override
    public String name() {
        return "log";
    }
}