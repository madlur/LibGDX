package ru.geekbrains.gdxgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.geekbrains.gdxgame.CharacterAnim;
import ru.geekbrains.gdxgame.MainClass;

public class GameScreen implements Screen {

    private MainClass game;
    private SpriteBatch batch;
    private CharacterAnim character;

    public GameScreen(MainClass game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.character = new CharacterAnim("girl.jpg", 9, 6, Animation.PlayMode.LOOP,
                Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f + 40);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(Color.SKY);

        character.setTime(Gdx.graphics.getDeltaTime());
        character.checkChangingOfDirection();

        batch.begin();
        character.drawCharacter(batch);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        character.dispose();
    }
}