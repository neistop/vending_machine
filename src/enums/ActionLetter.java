package enums;

public enum ActionLetter {
    A("a"),
    B("b"),
    C("c"),
    D("d"),
    E("e"),
    F("f"),
    G("g"),
    H("h"),
    I("i"),
    J("j");

    private final String value;

    ActionLetter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
