/**
 * An interface used to designate actions for the Ui. The parser determines an action and passes one of the
 * provided enumerations in order to prompt the Ui to act.
 */
public interface ParseActionsInterface {

    /**
     * Enumerations meant to direct the actions of Ui and Parser classes.
     */
    enum ActionType {
        EXIT, LIST, FIND, DELETE, DONE, TODO, DEADLINE, EVENT, INVALID;
    }
}
