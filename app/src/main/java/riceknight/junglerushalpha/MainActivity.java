package riceknight.junglerushalpha;

import android.view.KeyEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;


public class MainActivity extends SimpleBaseGameActivity {

    private static int CAMERA_WIDTH = 480;
    private static int CAMERA_HEIGHT = 800;
    private static Scene scene;
    private static EngineOptions JungleRushEngineOptions;
    private static ITexture backgroundTexture, mouse, cat, elephant, start, pause, scoreBG, ready;
    private static ITextureRegion mBackgroundTextureRegion;
    private static ITextureRegion mMouseTextureRegion, mCatTextureRegion, mElephantTextureRegion;
    private static ITextureRegion mStartTextureRegion, mPauseTextureRegion, mScoreBGTextureRegion;
    private static ITextureRegion mReadyTextureRegion, mPausedTextureRegion, mFailTextureRegion;
    private static Sprite backgroundSprite;
    private static Sprite mMouse, mCat, mElephant;
    private static Sprite mStart, mPause, mScoreBG;
    private static Sprite mReady, mPaused, mFail;
    private static HUD hud;
    private static Music mMusic, mSound;
    private static Font mFont, mFontNotice;
    public static Text mScore, mReadyText, mPausedText, mFailText;
    public static String sScore;
    public static ArrayList<Enemy> arrayEn;
    public static boolean paused = true;

    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        JungleRushEngineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
        JungleRushEngineOptions.getAudioOptions().setNeedsMusic(true);
        JungleRushEngineOptions.getAudioOptions().setNeedsSound(true);
        return JungleRushEngineOptions;
    }

    @Override
    protected void onCreateResources() {
        try {
            UserData.getInstance().init(getApplicationContext());
            GameManager.getInstance().setHighScore(UserData.getInstance().getHighScore());

            backgroundTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("background.jpg");
                }
            });
            mouse = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("mouse.png");
                }
            });
            cat = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("cat.png");
                }
            });
            elephant = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("elephant.png");
                }
            });
            start = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("start.png");
                }
            });
            pause = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("pause.png");
                }
            });
            scoreBG = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("scoreBG.png");
                }
            });
            ready = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("ready.png");
                }
            });
            mMusic = MusicFactory.createMusicFromAsset(getMusicManager(), this, "background.wav");
            mSound = MusicFactory.createMusicFromAsset(getMusicManager(), this, "attack.wav");
            mFont = FontFactory.createFromAsset(mEngine.getFontManager(), mEngine.getTextureManager(), 256, 256, TextureOptions.DEFAULT, getAssets(), "Andale Mono.ttf", 24f, true, Color.WHITE_ARGB_PACKED_INT);
            mFontNotice = FontFactory.createFromAsset(mEngine.getFontManager(), mEngine.getTextureManager(), 256, 256, TextureOptions.DEFAULT, getAssets(), "Andale Mono.ttf", 48f, true, Color.WHITE_ARGB_PACKED_INT);

            backgroundTexture.load();
            mouse.load();
            cat.load();
            elephant.load();
            start.load();
            pause.load();
            scoreBG.load();
            ready.load();

            mFont.load();
            mFontNotice.load();

            mMusic.setLooping(true);
            mMusic.play();

            mBackgroundTextureRegion = TextureRegionFactory.extractFromTexture(backgroundTexture);
            mMouseTextureRegion = TextureRegionFactory.extractFromTexture(mouse);
            mCatTextureRegion = TextureRegionFactory.extractFromTexture(cat);
            mElephantTextureRegion = TextureRegionFactory.extractFromTexture(elephant);
            mStartTextureRegion = TextureRegionFactory.extractFromTexture(start);
            mPauseTextureRegion = TextureRegionFactory.extractFromTexture(pause);
            mScoreBGTextureRegion = TextureRegionFactory.extractFromTexture(scoreBG);
            mReadyTextureRegion = TextureRegionFactory.extractFromTexture(ready);

            sScore = "HIGHS: "+Integer.toString(GameManager.getInstance().getHighScore())+"\nSCORE: "+Integer.toString(GameManager.getInstance().getCurrentScore());
            mScore = new Text(180,15, mFont, sScore, getVertexBufferObjectManager());
            mReadyText = new Text(100,320, mFontNotice, "!GO!GO!GO!", getVertexBufferObjectManager());
            mPausedText = new Text(160,320, mFontNotice, "PAUSED", getVertexBufferObjectManager());
            mFailText = new Text(160,320, mFontNotice, "!Fail!", getVertexBufferObjectManager());
            arrayEn = new ArrayList<Enemy>();
            hud = new HUD();

        } catch (IOException e) {
            Debug.e(e);
        }

    }

    @Override
    protected Scene onCreateScene() {
        scene = new Scene();
        backgroundSprite = new Sprite(0, 0, mBackgroundTextureRegion, getVertexBufferObjectManager());
        mReady = new Sprite(40, 240, mReadyTextureRegion, getVertexBufferObjectManager());

        mStart = new Sprite(0,0, mStartTextureRegion, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pSceneTouchEvent.getAction()==TouchEvent.ACTION_DOWN) {
                    scene.detachChild(mFailText);
                    scene.detachChild(mReadyText);
                    scene.detachChild(mPausedText);
                    GameManager.getInstance().resetGame();
                    onResumeGame();
                }
                return true;
            }
        };

        mPause = new Sprite(320, 0, mPauseTextureRegion, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pSceneTouchEvent.getAction()==TouchEvent.ACTION_DOWN) {
                    scene.detachChild(mFailText);
                    scene.detachChild(mReadyText);
                    scene.detachChild(mPausedText);
                    scene.attachChild(mPausedText);
                    onPauseGame();
                }
                return true;
            }
        };

        mScoreBG = new Sprite(160, 0, mScoreBGTextureRegion, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pSceneTouchEvent.getAction()==TouchEvent.ACTION_DOWN) {
                    GameManager.getInstance().setHighScore(0);
                    UserData.getInstance().setHighScore(0);
                    mScore.setText("HIGHS: "+Integer.toString(GameManager.getInstance().getHighScore())+"\nSCORE: "+Integer.toString(GameManager.getInstance().getCurrentScore()));
                }
                return true;
            }
        };

        mMouse = new Sprite(30, 650, mMouseTextureRegion, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pSceneTouchEvent.getAction()==TouchEvent.ACTION_DOWN) {
                    mSound.play();
                    scene.detachChild(mReadyText);
                    if (!arrayEn.isEmpty()) {
                        if(arrayEn.get(0).attackWeakness("mouse") ) {
                            GameManager.getInstance().incrementScore(1);
                            arrayEn.get(0).die(MainActivity.this);
                            arrayEn.clear();
                            EnemyComing(MainActivity.this, getVertexBufferObjectManager());
                        }
                        else {
                            arrayEn.get(0).die(MainActivity.this);
                            arrayEn.clear();
                            Fail();
//                            GameManager.getInstance().resetGame();
                        }
                    }
                    else {
                    }
                }
                return true;
            }
        };

        mCat = new Sprite(190, 650, mCatTextureRegion, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pSceneTouchEvent.getAction()==TouchEvent.ACTION_DOWN) {
                    mSound.play();
                    scene.detachChild(mReadyText);
                    if (!arrayEn.isEmpty()) {
                        if(arrayEn.get(0).attackWeakness("cat") ) {
                            GameManager.getInstance().incrementScore(1);
                            arrayEn.get(0).die(MainActivity.this);
                            arrayEn.clear();
                            EnemyComing(MainActivity.this, getVertexBufferObjectManager());
                        }
                        else {
                            arrayEn.get(0).die(MainActivity.this);
                            arrayEn.clear();
                            Fail();
//                            GameManager.getInstance().resetGame();
                        }
                    }
                    else {
                    }
                }
                return true;
            }
        };

        mElephant = new Sprite(350, 650, mElephantTextureRegion, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pSceneTouchEvent.getAction()==TouchEvent.ACTION_DOWN) {
                    mSound.play();
                    scene.detachChild(mReadyText);
                    if (!arrayEn.isEmpty()) {
                        if(arrayEn.get(0).attackWeakness("elephant") ) {
                            GameManager.getInstance().incrementScore(1);
                            arrayEn.get(0).die(MainActivity.this);
                            arrayEn.clear();
                            EnemyComing(MainActivity.this, getVertexBufferObjectManager());
                        }
                        else {
                            arrayEn.get(0).die(MainActivity.this);
                            arrayEn.clear();
                            Fail();
//                            GameManager.getInstance().resetGame();
                        }
                    }
                    else {
                    }
                }
                return true;
            }
        };

        scene.attachChild(backgroundSprite);
        scene.attachChild(mMouse);
        scene.attachChild(mCat);
        scene.attachChild(mElephant);
        scene.attachChild(mStart);
        scene.attachChild(mPause);
        scene.attachChild(mReadyText);

        hud.attachChild(mScoreBG);
        hud.attachChild(mScore);
        getEngine().getCamera().setHUD(hud);

        scene.registerTouchArea(mMouse);
        scene.registerTouchArea(mCat);
        scene.registerTouchArea(mElephant);
        scene.registerTouchArea(mStart);
        scene.registerTouchArea(mPause);
        scene.registerTouchArea(mScoreBG);

        scene.setTouchAreaBindingOnActionDownEnabled(true);

        if (!arrayEn.isEmpty()) {
            arrayEn.get(0).die(MainActivity.this);
            arrayEn.clear();
        }

        return scene;
    }

    public static void Fail() {
        scene.detachChild(mReadyText);
        scene.detachChild(mPausedText);
        scene.attachChild(mFailText);
    }

    public static void EnemyComing(final SimpleBaseGameActivity mActivity, VertexBufferObjectManager pVertexBufferObjectManager) {
        Enemy enemy;
        switch ((int) (Math.random()*3+1)) {
            case 1:
                enemy =  new Enemy(190, 0, mMouseTextureRegion, pVertexBufferObjectManager);
                enemy.weakness = "cat";
                break;
            case 2:
                enemy =  new Enemy(190, 0, mCatTextureRegion, pVertexBufferObjectManager);
                enemy.weakness = "elephant";
                break;
            case 3:
                enemy =  new Enemy(190, 0, mElephantTextureRegion, pVertexBufferObjectManager);
                enemy.weakness = "mouse";
                break;
            default:
                enemy =  new Enemy(190, 0, mCatTextureRegion, pVertexBufferObjectManager);
                enemy.weakness = "cat";
                break;
        }
        scene.attachChild(enemy);
        arrayEn.add(enemy);
        enemy.startMoving(mActivity);
    }

    @Override
    public  void onResumeGame() {
        if(mMusic != null && !mMusic.isPlaying()) {
            mMusic.play();
        }
        if (paused) {
            paused = false;
            if(arrayEn.isEmpty()) {
                EnemyComing(MainActivity.this, getVertexBufferObjectManager());
            }
            else {
                arrayEn.get(0).goon();
            }
        }
        else {
            if(arrayEn.isEmpty()) {
                EnemyComing(MainActivity.this, getVertexBufferObjectManager());
            }
        }
        super.onResumeGame();
    }

    @Override
    public synchronized void onPauseGame() {
        if(mMusic != null && mMusic.isPlaying()) {
            mMusic.pause();
        }
        scene.detachChild(mFailText);
        scene.detachChild(mReadyText);
        scene.detachChild(mPausedText);
        scene.attachChild(mPausedText);
        if (paused) {

        }
        else {
            paused = true;
            if (arrayEn.isEmpty()) {

            }
            else {
                arrayEn.get(0).die(MainActivity.this);
                arrayEn.clear();
            }
        }
        super.onResumeGame();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_HOME)
        {
            onPauseGame();
        }
        return super.onKeyDown(keyCode, event);
    }

}
