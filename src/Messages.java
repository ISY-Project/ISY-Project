package src;

public enum Messages {
    WIN_THE_GAME("Let's win this!"),
    LOSE_THE_GAME("They are going to lose!"),
    VICTORY_IS_OURS("Victory is ours!"),
    SHOW_NO_MERCY("We'll Show no mercy!"),
    MOTHER("Even your mother can't save you now!"),
    ;

    private final String value;

    private Messages(String value) {
        this.value = value;
    }

    public static Messages getRandomMessage() {
        Messages[] messages = values();
        int randomIndex = (int) (Math.random() * messages.length);
        return messages[randomIndex];
    }

    public String getValue() {
        return value;
    }
}
