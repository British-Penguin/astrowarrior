package com.britishpenguin.astrowarrior.entity.other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.britishpenguin.astrowarrior.AstroWarrior;
import com.britishpenguin.astrowarrior.entity.Entity;
import com.britishpenguin.astrowarrior.utils.Maths;

import java.util.stream.Collectors;

public class Bullet extends Entity {

    private final float startX;
    private final float startY;
    private final int damage;

    private final long spawnTime;

    public Bullet(AstroWarrior astroWarrior, float posX, float posY, float rotation, int damage, Type type) {
        super(astroWarrior, posX, posY, rotation, -1, -1, type, new Texture("textures/bullet/" + type.name().toLowerCase() + ".png"));
        this.setScale(2);
        this.startX = posX;
        this.startY = posY;
        this.damage = damage;
        this.spawnTime = System.currentTimeMillis();
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        moveInDirection(750, Maths.degreesToRadians(this.getRotation()), dt);

        if (System.currentTimeMillis() - spawnTime > 1000)
            astroWarrior.removeEntityFromRenderList(this);

        for (Entity ent : astroWarrior.renderedSprites.stream().filter(e -> e.getType() == Type.other(this.getType()) && !(e instanceof Bullet)).collect(Collectors.toList())) {
            if (this.getBoundingRectangle().overlaps(ent.getBoundingRectangle())) {
                astroWarrior.removeEntityFromRenderList(this);
                ent.damage(damage);
            }
        }

        if (this.getX() > Gdx.graphics.getWidth() + 40) astroWarrior.removeEntityFromRenderList(this);
        else if (this.getX() < -40) astroWarrior.removeEntityFromRenderList(this);
        else if (this.getY() > Gdx.graphics.getHeight() + 40) astroWarrior.removeEntityFromRenderList(this);
        else if (this.getY() < -40) astroWarrior.removeEntityFromRenderList(this);
    }

}
