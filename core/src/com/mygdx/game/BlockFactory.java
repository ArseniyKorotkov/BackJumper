package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class BlockFactory {

    private static ArrayList<Block> blocks;
    private static Texture texture = new Texture("blockLongest.png");
    private final static int BLOCKS_SIZE = 6 * MyGdxFighting.getLevel();


    public BlockFactory() {
        initFactory();
    }

    public void blocksDraw(SpriteBatch batch, float deltaTime) {
        if(BLOCKS_SIZE != 6 * MyGdxFighting.getLevel()) {
            initFactory();
        }
        texture = new Texture("blocks\\block" + (MyGdxFighting.getMaxLevel() - MyGdxFighting.getLevel()) +"00.png");
        int counter = (int) (Math.random() * BLOCKS_SIZE);
        for (int i = 0; i < BLOCKS_SIZE; i++) {
            if (i == counter) {
                blocks.get(i).downPositionYOf(3000 * ((float)MyGdxFighting.getLevel()/5) * deltaTime);
            }
            batch.draw(blocks.get(i).getTexture(), blocks.get(i).getPosition().x, blocks.get(i).getPosition().y);
            if (blocks.get(i).getPosition().y < -50) {
                blocks.get(i).setPositionY(MyGdxFighting.getWindowHeight() + 100);
            }
        }

    }


    public static ArrayList<Block> getBlocksArray() {
        return blocks;
    }

    private void initFactory() {
        float randomX;
        float randomY;
        blocks = new ArrayList<>();
        for (int i = 0; i < BLOCKS_SIZE; i++) {
            randomX = (float) Math.random() * (MyGdxFighting.getWindowWidth() - texture.getWidth());
            randomY = (float) (Math.random() * MyGdxFighting.getWindowHeight()) + MyGdxFighting.getWindowHeight();

            blocks.add(new Block(randomX, randomY, texture));

        }
    }
}
