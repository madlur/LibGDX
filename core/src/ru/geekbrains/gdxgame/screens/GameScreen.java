package ru.geekbrains.gdxgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.geekbrains.gdxgame.CharacterAnim;
import ru.geekbrains.gdxgame.MainClass;
import ru.geekbrains.gdxgame.PhysX;

import java.util.ArrayList;


public class GameScreen implements Screen {

    private MainClass game;
    private SpriteBatch batch;
    private CharacterAnim character;
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private static final float STEP = 12f;
    private PhysX physX;
    private final int[] bg;
    private final int[] l1;
    private Body body;
    private final Rectangle heroRect;
    private final Music music;
    private final Sound sound;
    public static ArrayList<Body> bodies;
    public static boolean onGround;
    public static boolean isWin = false;
    public static boolean isDie = false;


    public GameScreen(MainClass game) {
        bodies = new ArrayList<>();
        this.game = game;
        this.batch = new SpriteBatch();
        this.character = new CharacterAnim();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 0.6f;

        map = new TmxMapLoader().load("map/new_map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        bg = new int[1];
        bg[0] = map.getLayers().getIndex("background");
        l1 = new int[2];
        l1[0] = map.getLayers().getIndex("layer 2");
        l1[1] = map.getLayers().getIndex("layer 3");

        physX = new PhysX();

        map.getLayers().get("objects").getObjects().getByType(RectangleMapObject.class); //выбор объектов по типу
        RectangleMapObject tmp = (RectangleMapObject) map.getLayers().get("setting").getObjects().get("hero"); //выбор объекта по имени
        heroRect = tmp.getRectangle();
        body = physX.addObject(tmp);

        Array<RectangleMapObject> obj = map.getLayers().get("objects").getObjects().getByType(RectangleMapObject.class);
        for (int i = 0; i < obj.size; i++) {
            physX.addObject(obj.get(i));
        }

        music = Gdx.audio.newMusic(Gdx.files.internal("game_sound.mp3"));
        music.setLooping(true);
        music.play();

        sound = Gdx.audio.newSound(Gdx.files.internal("step_sound.mp3"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        sound.play(1, 1f, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            body.applyForceToCenter(new Vector2(-10000, 0), true);
            character.setReverse(true);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.applyForceToCenter(new Vector2(10000, 0), true);
            character.setReverse(false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) && onGround) {
            body.applyForceToCenter(new Vector2(0, 400000), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.position.y -= STEP;

        if (Gdx.input.isKeyPressed(Input.Keys.P)) camera.zoom += 0.01f;
        if (Gdx.input.isKeyPressed(Input.Keys.O) && camera.zoom > 0) camera.zoom -= 0.01f;

        camera.position.x = body.getPosition().x;
        camera.position.y = body.getPosition().y;
        camera.update();

        ScreenUtils.clear(Color.BLACK);

        mapRenderer.setView(camera);
        mapRenderer.render(bg);

        batch.setProjectionMatrix(camera.combined);
        heroRect.x = body.getPosition().x - heroRect.width / 2;
        heroRect.y = body.getPosition().y - heroRect.height / 2;
        batch.begin();
        batch.draw(character.getFrame(), heroRect.x, heroRect.y, heroRect.width, heroRect.height);
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            dispose();
            game.setScreen(new MenuScreen(game));
        }

        mapRenderer.render(l1);

        physX.step();
        physX.debugDraw(camera);

        for (int i = 0; i < bodies.size(); i++) {
            physX.destroyBody(bodies.get(i));
        }
        bodies.clear();

        if (isWin) {
            isWin = false;
            dispose();
            game.setScreen(new WinScreen(game));
        }
        if (isDie) {
            isDie = false;
            dispose();
            game.setScreen(new GameOverScreen(game));
        }
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
        this.batch.dispose();
        this.physX.dispose();
        this.sound.dispose();
        this.music.dispose();
    }
}