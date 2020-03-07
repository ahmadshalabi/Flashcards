package flashcards.actions;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public class ActionsFactory {

    private static ActionsFactory instance;
    private final Map<String, Action> actions;
    private final String listOfAction;

    private ActionsFactory() {
        actions = loadActions();
        listOfAction = generateListOfAction();
    }

    public static ActionsFactory getInstance() {
        if (instance == null) {
            synchronized (ActionsFactory.class) {
                if (instance == null) {
                    instance = new ActionsFactory();
                }
            }
        }
        return instance;
    }

    public String getListOfAction() {
        return listOfAction;
    }

    public Action getAction(String action) {
        return actions.getOrDefault(action, getDefaultAction());
    }

    private Map<String, Action> loadActions() {
        return ServiceLoader.load(Action.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toMap(
                        Action::name,
                        action -> action,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }

    private String generateListOfAction() {
        return actions.keySet()
                .stream()
                .filter(key -> !"default".equals(key))
                .collect(Collectors.joining(", "));
    }

    private Action getDefaultAction() {
        return actions.get("default");
    }
}