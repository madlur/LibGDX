package ru.geekbrains.gdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CharacterAnim extends Anim {
    private float currentX;
    private float currentY;

    private final float moveSpeed;
    private boolean directionLeft;

    public CharacterAnim(String name, int col, int row, Animation.PlayMode playMode, float cX, float cY) {
        super(name, col, row, playMode);

        this.currentX = cX;
        this.currentY = cY;
        this.moveSpeed = 1f;
    }

    public boolean isDirectionLeft() {
        return directionLeft;
    }

    public void setDirectionLeft(boolean directionLeft) {
        this.directionLeft = directionLeft;
    }

    public void drawCharacter(SpriteBatch batch) {

        TextureRegion currentTexture = this.getFrame();

        checkFlip(currentTexture);
        moveChar(currentTexture);

        batch.draw(currentTexture,
                currentX - currentTexture.getRegionWidth() / 2f,
                currentY);
    }

    private void checkFlip(TextureRegion texture) {
        if (directionLeft) {
            texture.flip(!texture.isFlipX(), false);
        } else {
            texture.flip(texture.isFlipX(), false);
        }
    }

    private void moveChar(TextureRegion texture) {
        boolean needToFlip = false;
        if (directionLeft) {
            needToFlip = !(currentX - moveSpeed - (texture.getRegionWidth() / 2f) > 0);
            currentX -= moveSpeed * (needToFlip ? -1 : 1);
            directionLeft = !needToFlip;
        } else {
            needToFlip = !(currentX + moveSpeed + (texture.getRegionWidth() / 2f) < Gdx.graphics.getWidth());
            currentX += moveSpeed * (needToFlip ? -1 : 1);
            directionLeft = needToFlip;
        }
        texture.flip(needToFlip, false);
    }

    public void checkChangingOfDirection() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) setDirectionLeft(true);
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) setDirectionLeft(false);
    }
}
