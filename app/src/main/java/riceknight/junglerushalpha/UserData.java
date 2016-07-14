package riceknight.junglerushalpha;

import android.content.Context;
import android.content.SharedPreferences;

public class UserData {

    private static UserData INSTANCE;
    private static String PREFS_NAME = "GAME_USERDATA";
    private static String HIGH_SCORE = "high_score";

    private SharedPreferences mSettings;
    private SharedPreferences.Editor mEditor;

    private int HighScore;
    
    public synchronized static UserData getInstance() {
        if(INSTANCE == null){
            INSTANCE = new UserData();
        }
        return INSTANCE;
    }

    public synchronized void init(Context pContext) {
        if (mSettings == null) {
            mSettings = pContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            mEditor = mSettings.edit();
            HighScore = mSettings.getInt(HIGH_SCORE, 0);
        }
    }

    public synchronized int getHighScore() {
        return HighScore;
    }

    public synchronized void setHighScore(int nHigScore) {
        HighScore = nHigScore;
        mEditor.putInt(HIGH_SCORE, HighScore);
        mEditor.commit();
    }
}

