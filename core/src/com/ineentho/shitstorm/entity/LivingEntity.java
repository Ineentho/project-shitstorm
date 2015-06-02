package com.ineentho.shitstorm.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.ineentho.shitstorm.ProjectShitstorm;
import com.ineentho.shitstorm.collision.BodyTypes;
import com.ineentho.shitstorm.collision.CollisionableEntity;
import com.ineentho.shitstorm.util.Facing;
import com.ineentho.shitstorm.weapon.Axe;
import com.ineentho.shitstorm.weapon.Weapon;

public class LivingEntity implements CollisionableEntity {
    protected final Sprite sprite;
    protected final Body body;
    private Weapon weapon;
    private Facing movementDirection = Facing.RIGHT;
    private boolean moving;
    private boolean justStoppedMoving;
    private World world;
    private Vector2 size;
    private LivingEntity target;
    private Fixture footFixture;
    private Fixture bodyFixture;
    private boolean canJump;

    public LivingEntity(World world, Texture texture, Vector2 size, short filterCategory) {
        this.world = world;
        this.size = size;
        sprite = new Sprite(texture);
        sprite.setSize(size.x * 2, size.y * 2);
        body = createBody(world);
        body.setUserData(this);
        createFixture(body, filterCategory);
        createFootFixture(body);
        weapon = new Axe(world, this);
    }

    public void teleport(Vector2 position, float angle) {
        body.setTransform(position, angle);
    }

    public void draw(SpriteBatch batch) {
        preDraw();
        sprite.draw(batch);
        weapon.draw(batch);
    }

    protected void preDraw() {

    }

    public boolean hasTarget() {
        return target != null;
    }

    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    private void createFixture(Body body, short filterCategory) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x, size.y);


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.filter.categoryBits = filterCategory;
        fixtureDef.filter.maskBits = (short) ~filterCategory;
        this.bodyFixture = body.createFixture(fixtureDef);
        shape.dispose();
    }


    private void createFootFixture(Body body) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x / 10, size.y / 10, new Vector2(0, -size.y), 0);
        FixtureDef def = new FixtureDef();
        def.shape = shape;
        def.isSensor = true;
        this.footFixture = body.createFixture(def);
    }

    private Body createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set((sprite.getX() + sprite.getWidth() / 2),
                (sprite.getY() + sprite.getHeight() / 2));


        return world.createBody(bodyDef);
    }

    public void update(float delta) {
        updateMovement();
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));

        Vector2 offset;
        if (movementDirection == Facing.RIGHT) {
            offset = new Vector2(.74f, .5f);
        } else {
            offset = new Vector2(-1.74f, .5f);
        }

        weapon.update(delta, offset);
    }

    private void updateMovement() {
        if (moving) {
            if (movementDirection == Facing.LEFT) {
                if (body.getLinearVelocity().x > -10)
                    body.applyForce(-100, 0, sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2, true);
                else
                    body.setLinearVelocity(-10, body.getLinearVelocity().y);
            }

            if (movementDirection == Facing.RIGHT) {
                if (body.getLinearVelocity().x < 10)
                    body.applyForce(100, 0, sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2, true);
                else
                    body.setLinearVelocity(10, body.getLinearVelocity().y);
            }
        }

        if (justStoppedMoving) {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
            justStoppedMoving = false;
        }
    }

    private void changedDirection() {
        weapon.changedDirection();
    }

    protected void jump() {
        if (canJump) {
            body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, 10));
            canJump = false;
        }
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public void attack() {
        if (weapon.attack())
            ProjectShitstorm.getInstance().playSound("axeSwing");
    }

    public Facing getFacing() {
        return movementDirection;
    }

    public void setMovingDirection(Facing direction, boolean moving) {
        Facing preDirection = this.movementDirection;
        if (direction != null)
            this.movementDirection = direction;

        boolean preMoving = this.moving;
        this.moving = moving;
        if (!moving && preMoving) {
            justStoppedMoving = true;
        }

        if (preDirection != movementDirection) {
            changedDirection();
        }
    }

    protected boolean isMoving() {
        return moving;
    }

    @Override
    public void onCollision(Fixture fixture, Body other) {
        Object userData = other.getUserData();

        if (fixture == footFixture) {
            if (userData instanceof BodyTypes && ((BodyTypes) userData) == BodyTypes.GROUND) {
                groundCollision();
            }
        } else if (fixture == bodyFixture) {
            if (userData instanceof LivingEntity) {
                onCollideWith((LivingEntity) userData);
            }
        }
    }

    private void groundCollision() {
        resetJump();
    }

    private void resetJump() {
        canJump = true;
    }

    protected void onCollideWith(LivingEntity otherEntity) {

    }

    public World getWorld() {
        return world;
    }
}
