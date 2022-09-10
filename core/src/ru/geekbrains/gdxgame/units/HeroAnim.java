package ru.geekbrains.gdxgame.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.geekbrains.gdxgame.Anim;

import java.util.HashMap;

public class HeroAnim {
    Anim heroAnimation;
    private TextureAtlas atlas;
    private Animation<TextureRegion> baseAnm;
    HashMap<Actions, Animation<TextureRegion>> manAssetss;
    private boolean reverse;
    private static float dScale = 6f;
    private final float FPS = 1 / 7f;
    private float time;

    public HeroAnim() {
        manAssetss = new HashMap<>();
        atlas = new TextureAtlas("atlas/runatlas.atlas");
        manAssetss.put(Actions.RUN, new Animation<TextureRegion>(FPS, atlas.findRegions("RunRight")));
        baseAnm = manAssetss.get(Actions.RUN);
    }


    public TextureRegion getFrame() {
        if (time > baseAnm.getAnimationDuration() && reverse) time = 0;
        if (time > baseAnm.getAnimationDuration()) time = 0;
        TextureRegion tr = baseAnm.getKeyFrame(time);
        if (tr.isFlipX() && !reverse) {
            tr.flip(true, false);
        }
        if (!tr.isFlipX() && reverse)
            tr.flip(true, false);

        return tr;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    public Rectangle getRect(OrthographicCamera camera, TextureRegion region) {
        float cx = Gdx.graphics.getWidth() / 2 - region.getRegionWidth() / 2 / camera.zoom / dScale;
        float cy = Gdx.graphics.getHeight() / 2 - region.getRegionHeight() / 2 / camera.zoom / dScale;
        float cW = region.getRegionWidth() / camera.zoom / dScale;
        float cH = region.getRegionHeight() / camera.zoom / dScale;
        return new Rectangle(cx, cy, cW, cH);
    }

    public float setTime(float deltaTime) {
        time += deltaTime;
        return time;
    }

    public void dispose() {
        atlas.dispose();
        this.manAssetss.clear();
    }

}
