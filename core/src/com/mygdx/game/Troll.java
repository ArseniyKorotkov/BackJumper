package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Troll {

    private static final double STEP_FACTOR = 0.24;
    private static final double WALLS_FACTOR = -36;
    private static final double JUMP_FACTOR = 600;
    private final Vector2 position = new Vector2(600, 0);
    private final ArrayList<Texture> trollScreens = new ArrayList<>();
    private final SpriteBatch batch;
    private double deltaTime;
    private double screenCounter;
    private double speed;
    private float spareY;
    private int rotation;
    private int screenNow;
    private boolean isFighting;
    private boolean isJumping;
    private boolean canJumpKick = true;
    private boolean doJumpKick;
    private boolean wayHasBlockRight;
    private boolean wayHasBlockLeft;
    private boolean wayHasBlockDown;

    public Troll(SpriteBatch batch) {
        screenNow = 9;
        speed = 280;
        this.batch = batch;
        for (int i = 0; i < 36; i++) {
            trollScreens.add(new Texture("trolls\\troll_" + i + "0.png"));
        }
    }

    public void draw(float deltaTime, ArrayList<Block> blocks) {
        this.deltaTime = deltaTime;
        addedBooleans(blocks);
        if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
            && !isFighting && !isJumping) {
            walkRight();
        } else if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
                   && !isFighting && !isJumping) {
            walkLeft();
        } else if ((Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && (screenCounter == 0) && (!isJumping || doJumpKick)) || (isFighting)) {
            fight();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || isJumping) {
            jump();
        } else {
            stay();
        }
    }

    private void walkRight() {
        screenNow = 6;
        if (position.x + trollScreens.get(0).getWidth() + WALLS_FACTOR < MyGdxFighting.getWindowWidth() && !wayHasBlockRight) {
            position.x += speed * deltaTime;
        }

        screenCounter += deltaTime * speed * STEP_FACTOR;
        if (screenCounter >= 12) {
            screenCounter = screenCounter % 12;
        }
        int i = (int) (screenNow + screenCounter);
        batch.draw(trollScreens.get(i - (i / 13) * (i % 12) * 2), position.x, position.y);
        screenNow = 9;
        if (!wayHasBlockDown && position.y > 0) {
            position.y -= JUMP_FACTOR * deltaTime;
            position.y = (float) Math.ceil(position.y);
        } else if (position.y < 0) {
            position.y++;
        }
    }

    private void walkLeft() {
        screenNow = 30;
        if (position.x > -20 && !wayHasBlockLeft) {
            position.x -= speed * deltaTime;
        }

        screenCounter += deltaTime * speed * STEP_FACTOR;
        if (screenCounter >= 12) {
            screenCounter = screenCounter % 12;
        }
        int i = (int) (screenNow - screenCounter);
        batch.draw(trollScreens.get(i + (23 / i) * (24 % i) * 2), position.x, position.y);
        screenNow = 27;
        if (!wayHasBlockDown && position.y > 0) {
            position.y -= JUMP_FACTOR * deltaTime;
            position.y = (float) Math.ceil(position.y);
        } else if (position.y < 0) {
            position.y++;
        }
    }

    private void fight() {
        isFighting = true;
        if (screenNow <= 9) {
            if (screenNow == 9 && !wayHasBlockRight) {
                position.x += speed / 2;
            }
            screenCounter += deltaTime * speed * 2;
            screenNow = (int) (screenCounter / (speed / 16));
            if (screenCounter >= speed / 2) {
                position.x -= speed / 2;
                screenCounter = 0;
                isFighting = false;
                screenNow = 9;
            }
            batch.draw(trollScreens.get(screenNow), (float) (position.x - screenCounter), position.y);


        } else if (screenNow >= 18) {
            if (screenNow == 27 && !wayHasBlockLeft) {
                position.x -= speed / 2;
            }
            screenCounter += deltaTime * speed * 2;
            screenNow = (int) (screenCounter / (speed / 16) + 18);
            if (screenCounter >= speed / 2) {
                position.x += speed / 2;
                screenCounter = 0;
                isFighting = false;
                screenNow = 27;
            }
            batch.draw(trollScreens.get(screenNow), (float) (position.x + screenCounter), position.y);
        }


    }

    private void jump() {
        isJumping = true;
        if (screenNow == 9) {
            if (rotation < 300) {
                position.y += speed * deltaTime * 1.3;
                rotation += JUMP_FACTOR * deltaTime;
            } else {
                if (canJumpKick) {
                    doJumpKick = true;
                }
                if (rotation < 360) {
                    rotation += JUMP_FACTOR * deltaTime;
                }
                position.y -= speed * deltaTime * 1.5;
                if (position.y <= 0 || wayHasBlockDown) {
                    if (position.y <= 0) {
                        position.y = 0;
                    } else {
                        position.y = spareY;
                    }
                    rotation = 0;
                    isJumping = false;
                    doJumpKick = false;
                }
            }
            if (!wayHasBlockLeft) {
                position.x -= speed * deltaTime;
            }
        } else if (screenNow == 27) {
            if (rotation > -300) {
                position.y += speed * deltaTime * 1.3;
                rotation -= JUMP_FACTOR * deltaTime;
            } else {
                if (canJumpKick) {
                    doJumpKick = true;
                }
                if (rotation > -360) {
                    rotation -= JUMP_FACTOR * deltaTime;
                }
                position.y -= speed * deltaTime * 1.5;
                if (position.y <= 0 || wayHasBlockDown) {
                    if (position.y <= 0) {
                        position.y = 0;
                    } else {
                        position.y = spareY;
                    }
                    rotation = 0;
                    isJumping = false;
                    doJumpKick = false;
                }
            }
            if (!wayHasBlockRight) {
                position.x += speed * deltaTime;
            }
        }
        batch.draw(trollScreens.get(screenNow - 4), position.x, position.y,
                (float) trollScreens.get(screenNow).getWidth() / 2, (float) trollScreens.get(screenNow).getHeight() / 2,
                trollScreens.get(screenNow).getWidth() + 10, trollScreens.get(screenNow).getHeight() + 10,
                1, 1, rotation, 0, 0,
                trollScreens.get(screenNow).getWidth(), trollScreens.get(screenNow).getHeight(), false, false);


    }

    private void stay() {
        screenCounter = 0;
        if (!wayHasBlockDown && position.y > 0) {
            position.y -= JUMP_FACTOR * deltaTime;
            position.y = (float) Math.ceil(position.y);
        } else if (position.y < 0) {
            position.y++;
        }
        batch.draw(trollScreens.get(screenNow), position.x, position.y);
    }

    private void addedBooleans(ArrayList<Block> blocks) {
        wayHasBlockRight = false;
        wayHasBlockLeft = false;
        wayHasBlockDown = false;
        for (Block block : blocks) {
            if (position.x < block.getPosition().x + block.getTexture().getWidth() + WALLS_FACTOR
                && block.getPosition().x < position.x
                && ((block.getPosition().y + block.getTexture().getHeight() < position.y + trollScreens.get(0).getHeight()
                     && block.getPosition().y + block.getTexture().getHeight() > position.y)
                    || (block.getPosition().y < position.y + trollScreens.get(0).getHeight()
                        && block.getPosition().y > position.y))) {
                wayHasBlockLeft = true;
            }
            if (block.getPosition().x + block.getTexture().getWidth() > position.x + trollScreens.get(0).getWidth()
                && block.getPosition().x < position.x + trollScreens.get(0).getWidth() + WALLS_FACTOR
                && ((block.getPosition().y + block.getTexture().getHeight() < position.y + trollScreens.get(0).getHeight()
                     && block.getPosition().y + block.getTexture().getHeight() > position.y)
                    || (block.getPosition().y < position.y + trollScreens.get(0).getHeight()
                        && block.getPosition().y > position.y))) {
                wayHasBlockRight = true;
            }
            if (position.x + trollScreens.get(0).getWidth() + WALLS_FACTOR > block.getPosition().x
                && position.x < block.getPosition().x + block.getTexture().getWidth() + WALLS_FACTOR
                && position.y - (block.getPosition().y + block.getTexture().getHeight()) < 10
                && position.y - (block.getPosition().y + block.getTexture().getHeight()) > -10) {
                spareY = block.getPosition().y + block.getTexture().getHeight();
                wayHasBlockDown = true;
            }
        }
    }

    public float getPositionY() {
        return position.y;
    }

    public void setPositionY(float y) {
        position.y = y;
    }


}
