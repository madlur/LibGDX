package ru.geekbrains.gdxgame;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CharacterAnim {
    Anim heroAnimation;
    private boolean reverse = false;

    public CharacterAnim() {
        heroAnimation = new Anim("atlas/runatlas.atlas", "RunRight", Animation.PlayMode.LOOP);
    }


    public TextureRegion getFrame() {
        if (heroAnimation.getFrame().isFlipX() && !reverse)
            heroAnimation.getFrame().flip(true, false);

        if (!heroAnimation.getFrame().isFlipX() && reverse)
            heroAnimation.getFrame().flip(true, false);


        return heroAnimation.getFrame();
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

}
