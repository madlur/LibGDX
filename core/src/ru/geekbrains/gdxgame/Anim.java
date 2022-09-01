package ru.geekbrains.gdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Anim {

    private TextureAtlas atlas;

    private Animation<TextureRegion> anim;
    private float time;

    public Anim(String path, String name, Animation.PlayMode playMode) {
        atlas = new TextureAtlas(path);
        anim = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(1 / 15f, atlas.findRegions(name));
        anim.setPlayMode(playMode);

        time += Gdx.graphics.getDeltaTime();
    }

    public TextureRegion getFrame() {
        return anim.getKeyFrame(time);
    }

    public void setTime(float time) {
        this.time += time;
    }

    public void zeroTime() {
        this.time = 0;
    }

    public boolean isAnimationOver() {
        return anim.isAnimationFinished(time);
    }

    public void setPlayMode(Animation.PlayMode playMode) {
        anim.setPlayMode(playMode);
    }

    public void dispose() {
        atlas.dispose();
    }
}
