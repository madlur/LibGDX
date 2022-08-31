package ru.geekbrains.gdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Anim {

    private TextureAtlas atlas;

    private Texture img;
    private Animation<TextureRegion> anim;
    private float time;

    public Anim(String name, int col, int row, Animation.PlayMode playMode) {
//        img = new Texture(name);
//        TextureRegion region0 = new TextureRegion(img);
//        int xCnt = region0.getRegionWidth() / col;
//        int yCnt = region0.getRegionHeight() / row;
//        TextureRegion[][] regions0 = region0.split(xCnt, yCnt);
//        TextureRegion[] region1 = new TextureRegion[regions0.length * regions0[0].length];
//        int cnt = 0;
//        for (int i = 0; i < regions0.length; i++) {
//            for (int j = 0; j < regions0[0].length; j++) {
//                region1[cnt++] = regions0[i][j];
//            }
//        }
        atlas = new TextureAtlas(name);
        anim = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(1 / 15f, atlas.findRegions("RunRight"));
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
//        img.dispose();
        atlas.dispose();
    }
}
