package ru.geekbrains.gdxgame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class PhysX {
    private final World world;
    private final Box2DDebugRenderer debugRenderer;

    public PhysX() {
        world = new World(new Vector2(0.0f, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();
        world.setContactListener(new MyContactListener());
    }

    public Body addObject(RectangleMapObject object) {
        Rectangle rect = object.getRectangle();
        String type = (String) object.getProperties().get("BodyType");
        BodyDef def = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();

        if (type.equals("StaticBody")) def.type = BodyDef.BodyType.StaticBody;
        if (type.equals("DynamicBody")) def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(rect.x + rect.width/2.0f, rect.y + rect.height/2.0f);
        polygonShape.setAsBox(rect.width/2.0f, rect.height/2.0f);
        fdef.shape = polygonShape;

        def.gravityScale = (float) object.getProperties().get("gravityScale");

        fdef.friction = (float) object.getProperties().get("friction");
        fdef.density = (float) object.getProperties().get("density");
        fdef.restitution = (float) object.getProperties().get("restitution");

        Body body;
        body = world.createBody(def);
        body.setFixedRotation(true);
        String name = object.getName();
        body.createFixture(fdef).setUserData(name);

        if(name != null && name.equals("hero")) {
            polygonShape.setAsBox(rect.width/4.0f, rect.height/12.0f, new Vector2(0.0f,-rect.width/2.0f), 0.0f);
            body.createFixture(fdef).setUserData("legs");
            body.getFixtureList().get(body.getFixtureList().size - 1).setSensor(true);
        }

        polygonShape.dispose();
        return body;
    }


    public void destroyBody(Body body) {world.destroyBody(body);}
    public void setGravity(Vector2 gravity) {world.setGravity(gravity);}
    public void step() {world.step(1/60.0f, 3, 3);}
    public void debugDraw(OrthographicCamera cam){debugRenderer.render(world, cam.combined);}

    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }
}
