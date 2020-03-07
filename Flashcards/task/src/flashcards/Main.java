package flashcards;

import flashcards.actions.ActionModifier;

public class Main {

    public static void main(String[] args) {
        ActionModifier actionModifier = ActionModifier.getInstance();
        actionModifier.setInstance(args);
        FlashCardService flashCardService = new FlashCardServiceImpl();
        flashCardService.start();
    }
}