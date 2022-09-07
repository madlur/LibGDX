package ru.geekbrains.gdxgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.geekbrains.gdxgame.MainClass;

public class GameOverScreen implements Screen {

    private MainClass game;
    private SpriteBatch batch;
    private Texture img;
    private float menuX;
    private float menuY;

    private Rectangle startRect;
    private ShapeRenderer shapeRenderer;

    public GameOverScreen(MainClass game) {
        this.game = game;
        batch = new SpriteBatch();
        img = new Texture("lose.png");

        startRect = new Rectangle(Gdx.graphics.getWidth() / 2f - img.getWidth() / 2f,
                Gdx.graphics.getHeight() / 2f - img.getHeight() / 2f,
                img.getWidth(), img.getHeight());
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(Color.BLACK);

        this.menuX = Gdx.graphics.getWidth() / 2f - img.getWidth() / 2f;
        this.menuY = Gdx.graphics.getHeight() / 2f - img.getHeight() / 2f;

        batch.begin();
        batch.draw(img, menuX, menuY);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GOLD);
        shapeRenderer.rect(startRect.x, startRect.y, startRect.width, startRect.height);
        shapeRenderer.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
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
        this.batch.dispose();
        this.img.dispose();
    }
}
