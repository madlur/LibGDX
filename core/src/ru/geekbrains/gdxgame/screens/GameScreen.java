package ru.geekbrains.gdxgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.geekbrains.gdxgame.CharacterAnim;
import ru.geekbrains.gdxgame.MainClass;


public class GameScreen implements Screen {

    private MainClass game;
    private SpriteBatch batch;
    private CharacterAnim character;
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private static final float STEP = 12f;
    private Rectangle mapSize;
    private ShapeRenderer shapeRenderer;

    public GameScreen(MainClass game) {
        this.game = game;
        this.batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        this.character = new CharacterAnim("atlas/runatlas.atlas", 9, 6, Animation.PlayMode.LOOP,
                0, Gdx.graphics.getHeight() / 6f);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        map = new TmxMapLoader().load("map/map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        map.getLayers().get("объекты").getObjects().getByType(RectangleMapObject.class); // выбор объекта по типу
        RectangleMapObject tmp = (RectangleMapObject) map.getLayers().get("объекты").getObjects().get("камера"); // выбор объекта по типу
        camera.position.x = tmp.getRectangle().x;
        camera.position.y = tmp.getRectangle().y;

        tmp = (RectangleMapObject) map.getLayers().get("объекты").getObjects().get("граница");
        mapSize = tmp.getRectangle();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && mapSize.x < (camera.position.x-1) ) camera.position.x -= STEP;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (mapSize.x + mapSize.width) > (camera.position.x+1) ) camera.position.x += STEP;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) camera.position.y += STEP;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.position.y -= STEP;

        if (Gdx.input.isKeyPressed(Input.Keys.O)) camera.zoom += 0.01f;
        if (Gdx.input.isKeyPressed(Input.Keys.P) && camera.zoom > 0) camera.zoom -= 0.01f;


        camera.update();

        ScreenUtils.clear(Color.SKY);

        character.setTime(Gdx.graphics.getDeltaTime());
        character.checkChangingOfDirection();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        character.drawCharacter(batch);
        batch.end();

        mapRenderer.setView(camera);
        mapRenderer.render();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            game.setScreen(new MenuScreen(game));
        }
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GOLD);
        shapeRenderer.rect(mapSize.x, mapSize.y, mapSize.width, mapSize.height);
        shapeRenderer.end();

    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
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