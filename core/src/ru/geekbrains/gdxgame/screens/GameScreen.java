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
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.geekbrains.gdxgame.units.HeroAnim;
import ru.geekbrains.gdxgame.MainClass;
import ru.geekbrains.gdxgame.PhysX;

import java.util.ArrayList;


public class GameScreen implements Screen {

    private MainClass game;
    private SpriteBatch batch;
    private HeroAnim character;
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private static final float STEP = 12f;
    private final float MOVE_POWER = 20000f;
    private final float JUMP_POWER = 400000f;
    private PhysX physX;
    private final int[] bg;
    private final int[] l1;
    private Body body;
    private final Rectangle heroRect;
    private final Music music;
//    private final Sound sound;
    public static ArrayList<Body> bodies;
    public static boolean  onGround;
    public static boolean isWin = false;
    public static boolean isDie = false;


    public GameScreen(MainClass game) {
        bodies = new ArrayList<>();
        this.game = game;
        batch = new SpriteBatch();
        character = new HeroAnim();
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

//        sound = Gdx.audio.newSound(Gdx.files.internal("step_sound.mp3"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
//        sound.play(1, 1f, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            body.applyForceToCenter(new Vector2(-MOVE_POWER, 0), true);
            character.setReverse(true);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            body.applyForceToCenter(new Vector2(MOVE_POWER, 0), true);
            character.setReverse(false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && onGround) {
            body.applyForceToCenter(new Vector2(0, JUMP_POWER), true);
        }



        if (Gdx.input.isKeyPressed(Input.Keys.P)) camera.zoom += 0.01f;
        if (Gdx.input.isKeyPressed(Input.Keys.O) && camera.zoom > 0) camera.zoom -= 0.01f;

        camera.position.x = body.getPosition().x * physX.PPM;
        camera.position.y = body.getPosition().y * physX.PPM;
        camera.update();

        ScreenUtils.clear(Color.BLACK);

        mapRenderer.setView(camera);
        mapRenderer.render(bg);

        Rectangle tmp = character.getRect(camera, character.getFrame());
        ((PolygonShape)body.getFixtureList().get(0).getShape()).setAsBox(tmp.width/2/physX.PPM*camera.zoom, tmp.height/2/ physX.PPM*camera.zoom);
        ((PolygonShape)body.getFixtureList().get(1).getShape()).setAsBox(
                tmp.width/4/physX.PPM*camera.zoom,
                tmp.height/12/physX.PPM*camera.zoom,
                new Vector2(0,-tmp.height/2/physX.PPM*camera.zoom),
                0);

        character.setTime(Gdx.graphics.getDeltaTime());

        batch.begin();
        batch.draw(character.getFrame(), tmp.x, tmp.y, tmp.width, tmp.height);
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
        this.character.dispose();
        this.mapRenderer.dispose();
        music.dispose();
    }
}