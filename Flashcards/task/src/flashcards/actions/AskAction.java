package flashcards.actions;

import flashcards.Card;

import java.util.Objects;
import java.util.stream.IntStream;

public class AskAction extends AbstractAction {
    private static final String ASK_TIMES_PROMPT_MSG = "How many times to ask?";
    private static final String QUESTION_PROMPT_MSG = "Print the definition of \"%s\":%n";
    private static final String CORRECT_MSG = "Correct answer.";
    private static final String WRONG_MSG = "Wrong answer. The correct one is \"%s\"%s.%n";
    private static final String WRONG_DEFINITION_OF_MSG = ", you've just written the definition of \"%s\"";

    @Override
    public void execute() {
        int numOfQuestions = getNumOfQuestions();
        IntStream.range(0, numOfQuestions)
                .mapToObj(i -> cardStore.getRandomCard())
                .forEach(this::askQuestion);
    }

    @Override
    public String name() {
        return "ask";
    }

    private int getNumOfQuestions() {
        writer.println(ASK_TIMES_PROMPT_MSG);
        return reader.nextInt();
    }

    private void askQuestion(Card card) {
        System.out.printf(QUESTION_PROMPT_MSG, card.getTerm());
        String answeredDefinition = reader.nextLine();
        String realDefinition = card.getDefinition();
        if (Objects.equals(answeredDefinition, realDefinition)) {
            writer.println(CORRECT_MSG);
        } else {
            Card answeredCard = new Card(answeredDefinition);
            if (cardStore.isDefinitionNotExist(answeredCard)) {
                writer.printf(WRONG_MSG, realDefinition, "");
            } else {
                writer.printf(WRONG_MSG,
                        realDefinition,
                        String.format(WRONG_DEFINITION_OF_MSG, cardStore.getTermOfDefinition(answeredDefinition)));
            }
            card.updateMistakes();
        }
    }
}