package ru.geekbrains.gdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import ru.geekbrains.gdxgame.screens.GameScreen;

public class MyContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if(a.getUserData() != null && b.getUserData() != null) {
            String tmpA = (String) a.getUserData();
            String tmpB = (String) b.getUserData();

            if (tmpA.equals("hero") && tmpB.equals("box")) {
                GameScreen.bodies.add(b.getBody());
            }
            if (tmpA.equals("box") && tmpB.equals("hero")) {
                GameScreen.bodies.add(a.getBody());
            }
            if (tmpA.equals("legs") && tmpB.equals("ground")) {
                GameScreen.onGround = true;
            }
            if (tmpB.equals("legs") && tmpA.equals("ground")) {
                GameScreen.onGround = true;
            }

        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a.getUserData() != null && b.getUserData() != null) {
            String tmpA = (String) a.getUserData();
            String tmpB = (String) b.getUserData();

            if (tmpA.equals("legs") && tmpB.equals("ground")) {
                GameScreen.onGround = false;
                Gdx.app.log("endContact", "between " + tmpA.toString() + " and " + tmpB.toString());
            }
            if (tmpA.equals("legs") && !tmpB.equals("ground")) {
                GameScreen.onGround = false;
                Gdx.app.log("endContact", "between " + tmpA.toString() + " and " + tmpB.toString());
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
