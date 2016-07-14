package riceknight.junglerushalpha;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.modifier.IModifier;

public class Enemy extends Sprite {

    public static String weakness;
    public static float time = 1.2f;
    private MoveModifier fall;

    public Enemy(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
    }

    public void stop() {
        unregisterEntityModifier(fall);
    }

    public void goon() {
        registerEntityModifier(fall);
    }

    public boolean attackWeakness(String animal) {
        if(animal.equals(weakness)) {
            return true;
        }
        else
            return false;
    }

    public void die(final SimpleBaseGameActivity mMainActivity) {
        mMainActivity.getEngine().runOnUpdateThread(new Runnable() {
            @Override
            public void run() {
                Enemy.this.detachSelf();
            }
        });
    }

    public void startMoving(final SimpleBaseGameActivity mMainActivity) {
        fall = new MoveModifier(time, 190, 190, 0, 550);
        fall.addModifierListener(new IModifier.IModifierListener<IEntity>() {
            @Override
            public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

            }

            @Override
            public void onModifierFinished(IModifier<IEntity> pModifier, final IEntity pItem) {
                mMainActivity.getEngine().runOnUpdateThread(new Runnable() {
                    @Override
                    public void run() {
                        Enemy.this.detachSelf();
                    }
                });
                MainActivity.arrayEn.remove(pItem);
                MainActivity.arrayEn.clear();
                MainActivity.Fail();
//                GameManager.getInstance().resetGame();
            }
        });
        registerEntityModifier(fall);
    }
}