public interface ParseActionsInterface {

    /**
     * Enumerations meant to direct the actions of Ui and Parser classes.
     */
    enum ActionType {
        EXIT, LIST, FIND, DELETE, DONE, TODO, DEADLINE, EVENT, INVALID;
    }
}
