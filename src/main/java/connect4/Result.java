package connect4;

public enum Result {
    CONTINUE,
    WIN,
    LOST,
    DRAW;

    public Result oppositeResult() {
        switch (this) {
            case WIN:
                return LOST;
            case LOST:
                return WIN;
            default:
                return this;
        }
    }
}
