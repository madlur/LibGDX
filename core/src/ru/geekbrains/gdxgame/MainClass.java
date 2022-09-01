package ru.geekbrains.gdxgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import ru.geekbrains.gdxgame.screens.MenuScreen;

public class MainClass extends Game {

    @Override
    public void create() {
        Gdx.graphics.setWindowedMode(500, 300);
        setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }


    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
