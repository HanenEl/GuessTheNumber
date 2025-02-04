package guess.services;

public interface GameService {
    public boolean isGameOver();
    public String getMainMessage();
    public String getResultMessage();
    public void checkGuess(int guess);
    public void reset();
}
