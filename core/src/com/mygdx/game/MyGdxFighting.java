package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxFighting extends ApplicationAdapter {
    private SpriteBatch batch;
    private Troll troll;
    private Texture background;
    private BlockFactory blockFactory;
    private static final int WINDOW_WIDTH = 1600;
    private static final int WINDOW_HEIGHT = 900;
    private static int level = 1;
    private static final int MAX_LEVEL = 16;

    @Override
    public void create() {
        blockFactory = new BlockFactory();
        batch = new SpriteBatch();
        troll = new Troll(batch);
        background = new Texture("city.jpg");
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear( 0.3f, 0.2F, 0.1F, 1);
        batch.begin();
        batch.draw(background,0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
        blockFactory.blocksDraw(batch, deltaTime);
        if(troll.getPositionY() > 800) {
            batch.draw(new Texture("winner.png"), -30,70);
            if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                troll.setPositionY(0);
                level++;
            }
        } else {
            troll.draw(deltaTime, BlockFactory.getBlocksArray());
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    public static int getWindowHeight() {
        return WINDOW_HEIGHT;
    }

    public static int getLevel() {
        return level;
    }

    public static int getMaxLevel() {
        return MAX_LEVEL;
    }
}
