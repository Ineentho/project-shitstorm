package com.ineentho.shitstorm.collision;

import com.badlogic.gdx.physics.box2d.*;

public class CollisionListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        sendContact(contact.getFixtureA(), contact.getFixtureB());
        sendContact(contact.getFixtureB(), contact.getFixtureA());
    }

    private void sendContact(Fixture fixture, Fixture other) {
        Object userData = fixture.getBody().getUserData();
        if (userData instanceof CollisionableEntity) {
            ((CollisionableEntity) userData).onCollision(other.getBody());
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
