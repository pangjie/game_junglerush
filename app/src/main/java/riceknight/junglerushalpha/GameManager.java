package riceknight.junglerushalpha;

public class GameManager {

    private static GameManager INSTANCE;
    private static final int INITIAL_SCORE = 0;
    private static int highScore = 0;
    private static int mCurrentScore = 0;

    public static GameManager getInstance(){
        if(INSTANCE == null){
            INSTANCE = new GameManager();
        }
        return INSTANCE;
    }

    public int getCurrentScore(){
        return mCurrentScore;
    }

    public int getHighScore() {
        return highScore;
    }

    public void incrementScore(int pIncrementBy){
        mCurrentScore += pIncrementBy;
        if(mCurrentScore > highScore) {
            highScore = mCurrentScore;
        }
        MainActivity.mScore.setText("HIGHS: "+Integer.toString(GameManager.getInstance().getHighScore())+"\nSCORE: "+Integer.toString(GameManager.getInstance().getCurrentScore()));
        UserData.getInstance().setHighScore(highScore);
    }

    public void setHighScore(int hs) {
        highScore = hs;
    }

    public void resetGame(){
        mCurrentScore = GameManager.INITIAL_SCORE;
        MainActivity.mScore.setText("HIGHS: "+Integer.toString(GameManager.getInstance().getHighScore())+"\nSCORE: "+Integer.toString(GameManager.getInstance().getCurrentScore()));
        UserData.getInstance().setHighScore(highScore);
    }
}
