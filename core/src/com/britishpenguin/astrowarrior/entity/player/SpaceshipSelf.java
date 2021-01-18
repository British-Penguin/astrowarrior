package com.britishpenguin.astrowarrior.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.britishpenguin.astrowarrior.AstroWarrior;
import com.britishpenguin.astrowarrior.config.keybinding.Keybind;
import com.britishpenguin.astrowarrior.entity.Entity;
import com.britishpenguin.astrowarrior.entity.other.Bullet;
import com.britishpenguin.astrowarrior.utils.Maths;

import java.util.stream.Collectors;

public class SpaceshipSelf extends Entity {

    public int mana;
    public boolean mouseLook;

    private boolean moving;
    private long shootPressed;

    public SpaceshipSelf(AstroWarrior astroWarrior, float posX, float posY, float rotation, int mana) {
        super(astroWarrior, posX, posY, rotation, 20, 20, Type.FRIENDLY, new Texture("textures/ship/friendly/normal.png"));
        this.mouseLook = false;
        this.setScale(2);
        this.mana = mana;
        this.moving = false;
        this.shootPressed = 0L;
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        updateMovement(dt);
        updateRotation(dt);
        updateCollision(dt);

        if (astroWarrior.gameSettings.toggleMouseLook.isPressed())
            this.mouseLook = !this.mouseLook;

        Keybind shoot = astroWarrior.gameSettings.keyShoot;
        if (shoot.isKeyDown() && System.currentTimeMillis() - shootPressed >= 250) {
            shootPressed = System.currentTimeMillis();
            astroWarrior.playSound("sound/shoot.wav", 1.0f, MathUtils.random(0.7f, 1.3f));
            astroWarrior.addEntityToRenderList(new Bullet(astroWarrior, this.getX(), this.getY(), this.getRotation(), 5, Type.FRIENDLY));
        }

        if (this.isBeingDamaged())
            this.setTexture(new Texture("textures/ship/friendly/damage.png"));
        else
            this.setTexture(new Texture("textures/ship/friendly/normal.png"));
    }

    private void updateMovement(float dt) {
        float speed = 400;
        if (this.isBeingDamaged()) speed /= 1.2;

        if (astroWarrior.gameSettings.keyMoveForward.isKeyDown())
            moveInDirection(speed, Maths.degreesToRadians(this.getRotation()), dt);
        if (astroWarrior.gameSettings.keyMoveBackward.isKeyDown())
            moveInDirection(-speed / 2f, Maths.degreesToRadians(this.getRotation()), dt);

        if (astroWarrior.gameSettings.keyDashLeft.isPressed()) {
            if (!this.isBeingDamaged() && mana >= 10) {
                mana -= 10;
                astroWarrior.playSound("sound/dash.wav", 0.6f, MathUtils.random(0.7f, 1.3f), 0.0f);
                lerpInDirection(speed * 2000, Maths.degreesToRadians(this.getRotation() + 90), dt, 2);
            } else {
                astroWarrior.playSound("sound/no.wav", 3f, MathUtils.random(0.7f, 1.3f), 0.0f);
            }
        }
        if (astroWarrior.gameSettings.keyDashRight.isPressed()) {
            if (!this.isBeingDamaged() && mana >= 10) {
                mana -= 10;
                astroWarrior.playSound("sound/dash.wav", 0.6f, MathUtils.random(0.7f, 1.3f), 1f);
                lerpInDirection(speed * 2000, Maths.degreesToRadians(this.getRotation() - 90), dt, 2);
            } else {
                astroWarrior.playSound("sound/no.wav", 3f, MathUtils.random(0.7f, 1.3f), 1f);
            }
        }

        moving = (astroWarrior.gameSettings.keyMoveForward.isKeyDown() || astroWarrior.gameSettings.keyMoveBackward.isKeyDown())
                && !(astroWarrior.gameSettings.keyMoveForward.isKeyDown() && astroWarrior.gameSettings.keyMoveBackward.isKeyDown());

        if (this.getX() > Gdx.graphics.getWidth() - 50) renderDuplicateAt(this.getX() - Gdx.graphics.getWidth(), this.getY(), this.getRotation());
        if (this.getX() < 50) renderDuplicateAt(this.getX() + Gdx.graphics.getWidth(), this.getY(), this.getRotation());
        if (this.getY() > Gdx.graphics.getHeight() - 50) renderDuplicateAt(this.getX(), this.getY() - Gdx.graphics.getHeight(), this.getRotation());
        if (this.getY() < 50) renderDuplicateAt(this.getX(), this.getY() + Gdx.graphics.getHeight(), this.getRotation());
        if (this.getX() > Gdx.graphics.getWidth()) this.setX(0);
        if (this.getX() < 0) this.setX(Gdx.graphics.getWidth());
        if (this.getY() > Gdx.graphics.getHeight()) this.setY(0);
        if (this.getY() < 0) this.setY(Gdx.graphics.getHeight());
    }

    private void updateRotation(float dt) {
        if (!mouseLook) {
            if (astroWarrior.gameSettings.keyRotateLeft.isKeyDown())
                this.rotate((moving ? 300 : 100) * dt);
            if (astroWarrior.gameSettings.keyRotateRight.isKeyDown())
                this.rotate(-(moving ? 300 : 100) * dt);
        } else {
            float xInput = Gdx.input.getX();
            float yInput = Gdx.graphics.getHeight() - Gdx.input.getY();

            float angle = MathUtils.radiansToDegrees * MathUtils.atan2(yInput - this.getY(), xInput - this.getX());
            if (angle < 0) angle += 360;
            if (angle > 360) angle -= 360;
            if (this.getRotation() < 0) this.rotate(360);
            if (this.getRotation() > 360) this.rotate(-360);
            this.setRotation(MathUtils.lerp(this.getRotation(), angle, dt * 10));
        }

        if (this.getRotation() < 0) this.rotate(360);
        if (this.getRotation() > 360) this.rotate(-360);
    }

    private void updateCollision(float dt) {
        for (Entity ent : astroWarrior.renderedSprites.stream().filter(e -> e.getType() == Type.other(this.getType())).collect(Collectors.toList())) {
            if (this.getBoundingRectangle().overlaps(ent.getBoundingRectangle()))
                this.damage(1);
        }
    }

    @Override
    public void damage(int points) {
        super.damage(points);
    }
}
