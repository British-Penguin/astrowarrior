package com.britishpenguin.astrowarrior.entity.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.britishpenguin.astrowarrior.AstroWarrior;
import com.britishpenguin.astrowarrior.entity.Entity;
import com.britishpenguin.astrowarrior.entity.other.Bullet;
import com.britishpenguin.astrowarrior.utils.Maths;

public class SpaceshipEnemy extends Entity {

    private long lastShot = System.currentTimeMillis();
    private final long firstShotDelay;
    private final float aimOffset;

    private Action currentAction;
    private long lastActionChange = System.currentTimeMillis();

    private float targetedPosX = -1f;
    private float targetedPosY = -1f;

    public SpaceshipEnemy(AstroWarrior astroWarrior, float posX, float posY, float rotation) {
        super(astroWarrior, posX, posY, rotation, 20, 20, Type.ENEMY, new Texture("textures/ship/enemy/normal.png"));
        this.setScale(2);
        this.currentAction = Action.values()[MathUtils.random(0, 2)];
        this.firstShotDelay = MathUtils.random(1000, 5000) + System.currentTimeMillis();
        this.aimOffset = MathUtils.random(-10, 10);
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        switch (currentAction) {
            case SHOOTING:
                float playerX = astroWarrior.player.getX();
                float playerY = astroWarrior.player.getY();
                float angle = MathUtils.radiansToDegrees * MathUtils.atan2(playerY - this.getY(), playerX - this.getX());
                angle += aimOffset;
                if (angle < 0) angle += 360;
                if (angle > 360) angle -= 360;
                this.setRotation(MathUtils.lerp(this.getRotation(), angle, dt * 2));
                if (System.currentTimeMillis() - lastShot > MathUtils.random(1000, 2000) && System.currentTimeMillis() >= firstShotDelay) {
                    lastShot = System.currentTimeMillis();
                    astroWarrior.playSound("sound/shoot.wav", 1.0f, 0.4f * MathUtils.random(0.7f, 1.3f));
                    astroWarrior.addEntityToRenderList(new Bullet(astroWarrior, this.getX(), this.getY(), this.getRotation(), 2, Type.ENEMY));
                }
                break;

            case MOVING:
                if (targetedPosX == -1f)
                    targetedPosX = MathUtils.random(0, Gdx.graphics.getWidth());
                if (targetedPosY == -1f)
                    targetedPosY = MathUtils.random(0, Gdx.graphics.getHeight());

                if (targetedPosX == this.getX() && targetedPosY == this.getY()) {
                    rollAction();
                    targetedPosX = targetedPosY = -1f;
                    break;
                }

                angle = MathUtils.radiansToDegrees * MathUtils.atan2(targetedPosY - this.getY(), targetedPosX - this.getX());
                if (angle < 0) angle += 360;
                if (angle > 360) angle -= 360;
                this.setRotation(MathUtils.lerp(this.getRotation(), angle, dt * 2));

                moveInDirection(100, Maths.degreesToRadians(this.getRotation()), dt);
                break;

            case TARGETING:
                playerX = astroWarrior.player.getX();
                playerY = astroWarrior.player.getY();
                angle = MathUtils.radiansToDegrees * MathUtils.atan2(playerY - this.getY(), playerX - this.getX());
                angle += aimOffset;
                if (angle < 0) angle += 360;
                if (angle > 360) angle -= 360;
                if (this.getRotation() > 360) this.rotate(-360);
                if (this.getRotation() < 0) this.rotate(360);
                this.setRotation(MathUtils.lerp(this.getRotation(), angle, dt * 2));
                moveInDirection(100, Maths.degreesToRadians(this.getRotation()), dt);
                break;
        }

        if (this.getX() > Gdx.graphics.getWidth() - 50) renderDuplicateAt(this.getX() - Gdx.graphics.getWidth(), this.getY(), this.getRotation());
        if (this.getX() < 50) renderDuplicateAt(this.getX() + Gdx.graphics.getWidth(), this.getY(), this.getRotation());
        if (this.getY() > Gdx.graphics.getHeight() - 50) renderDuplicateAt(this.getX(), this.getY() - Gdx.graphics.getHeight(), this.getRotation());
        if (this.getY() < 50) renderDuplicateAt(this.getX(), this.getY() + Gdx.graphics.getHeight(), this.getRotation());
        if (this.getX() > Gdx.graphics.getWidth()) this.setX(0);
        if (this.getX() < 0) this.setX(Gdx.graphics.getWidth());
        if (this.getY() > Gdx.graphics.getHeight()) this.setY(0);
        if (this.getY() < 0) this.setY(Gdx.graphics.getHeight());

        if (System.currentTimeMillis() - lastActionChange > 5000)
            rollAction();

        if (this.isBeingDamaged())
            this.setTexture(new Texture("textures/ship/enemy/damage.png"));
        else
            this.setTexture(new Texture("textures/ship/enemy/normal.png"));
    }

    private void rollAction() {
        lastActionChange = System.currentTimeMillis();
        this.currentAction = Action.values()[MathUtils.random(0, 2)];
    }

    public enum Action {
        MOVING,
        TARGETING,
        SHOOTING
    }
}
