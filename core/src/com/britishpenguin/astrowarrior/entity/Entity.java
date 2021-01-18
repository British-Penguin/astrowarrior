package com.britishpenguin.astrowarrior.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.britishpenguin.astrowarrior.AstroWarrior;

public class Entity extends Sprite {

    protected AstroWarrior astroWarrior;
    public int maxHealth;
    public int currentHealth;
    private int damageTime;
    private final Type type;

    public Entity(AstroWarrior astroWarrior, float posX, float posY, float rotation, int maxHealth, int currentHealth, Type type, Texture texture) {
        super(texture);
        this.type = type;
        this.setX(posX);
        this.setY(posY);
        this.setRotation(rotation);
        this.astroWarrior = astroWarrior;
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
        this.damageTime = 0;
    }

    public void update(float dt) {
        if (damageTime > 0 && damageTime < 4000 * dt) damageTime++;
        else damageTime = 0;
    }

    protected void moveInDirection(float amt, float angle, float dt) {
        this.setX(this.getX() + (amt * MathUtils.cos(angle)) * dt);
        this.setY(this.getY() + (amt * MathUtils.sin(angle)) * dt);
    }

    protected void lerpInDirection(float amt, float angle, float dt, float speed) {
        float desiredX = this.getX() + (amt * MathUtils.cos(angle)) * dt;
        float desiredY = this.getY() + (amt * MathUtils.sin(angle)) * dt;

        this.setX(MathUtils.lerp(this.getX(), desiredX, dt * speed));
        this.setY(MathUtils.lerp(this.getY(), desiredY, dt * speed));
    }

    public void renderDuplicateAt(float x, float y, float rotation) {
        float oldX = this.getX();
        float oldY = this.getY();
        float oldRotation = this.getRotation();

        this.setX(x);
        this.setY(y);
        this.setRotation(rotation);
        this.draw(AstroWarrior.getInstance().batch);
        this.setX(oldX);
        this.setY(oldY);
        this.setRotation(oldRotation);
    }

    public void damage(int points) {
        if (maxHealth == -1 || currentHealth == -1) return;
        if (isBeingDamaged()) return;

        if (currentHealth - points < 0) {
            currentHealth = 0;
        }

        if (type == Type.FRIENDLY)
            AstroWarrior.getInstance().playSound("sound/hurt.wav", 1.0f, MathUtils.random(0.5f, 1.5f));
        else if (type == Type.ENEMY)
            AstroWarrior.getInstance().playSound("sound/attack.wav", 0.5f, MathUtils.random(0.5f, 1.5f));
        currentHealth -= points;
        damageTime = 1;

        if (currentHealth <= 0) {
            AstroWarrior.getInstance().playSound("sound/death.wav", 0.7f, MathUtils.random(0.5f, 1.5f));
            AstroWarrior.getInstance().removeEntityFromRenderList(this);
        }
    }

    public boolean isBeingDamaged() {
        return damageTime > 0;
    }

    public Type getType() {
        return type;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public enum Type {
        FRIENDLY,
        ENEMY;

        public static Type other(Type type) {
            if (type == FRIENDLY)
                return ENEMY;
            else if (type == ENEMY)
                return FRIENDLY;

            return null;
        }
    }

}
