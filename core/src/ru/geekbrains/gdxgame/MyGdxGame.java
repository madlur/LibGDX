package ru.geekbrains.gdxgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    int click;
    Anim animation;
    boolean dir = true;
    float x,y;

    @Override
    public void create() {
        batch = new SpriteBatch();
        animation = new Anim("girl.jpg", 9, 6, Animation.PlayMode.LOOP);
        x = Gdx.input.getX();
        y = 0;
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);

        animation.setTime(Gdx.graphics.getDeltaTime());

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) dir = true;
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) dir = false;

        if (!animation.getFrame().isFlipX() && dir) animation.getFrame().flip(true, false);
        if (animation.getFrame().isFlipX() && !dir) animation.getFrame().flip(true, false);

        batch.begin();
        batch.draw(animation.getFrame(), xDirection(), y);
        batch.end();
    }

    public float xDirection() {
        if (animation.getFrame().isFlipX()) {
            if (x >= Gdx.graphics.getWidth() - animation.getFrame().getRegionWidth()) {
                animation.getFrame().flip(true, false);
                dir = false;
                return x -= animation.getFrame().getRegionWidth()/100f;
            }
            return x += animation.getFrame().getRegionWidth()/100f;
        }
        else {
            if (x <= 0) {
                animation.getFrame().flip(true, false);
                dir = true;
                return x += animation.getFrame().getRegionWidth()/100f;
            }
            return x -= animation.getFrame().getRegionWidth()/100f;
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        animation.dispose();
    }
}
