package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Block {

    private Texture texture;
    private final Vector2 position;

    public Block(float x, float y, Texture texture) {
        this.texture = texture;
        position = new Vector2(x,y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return texture;
    }


    public void downPositionYOf(float y) {
        position.y -= y;
    }

    public void setPositionY(int y) {
        this.position.y = y;
    }
}
