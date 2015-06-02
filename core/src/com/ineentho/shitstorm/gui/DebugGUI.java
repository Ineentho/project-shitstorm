package com.ineentho.shitstorm.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ineentho.shitstorm.ProjectShitstorm;

public class DebugGUI {
    private boolean debugMenu = true;
    private boolean physicsDebug = true;

    public boolean isPhysicsDebug() {
        return physicsDebug;
    }

    public boolean isDebugMenu() {
        return debugMenu;
    }

    public void draw() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F3))
            debugMenu = !debugMenu;

        if (Gdx.input.isKeyJustPressed(Input.Keys.P))
            physicsDebug = !physicsDebug;

        if (!debugMenu)
            return;

        BitmapFont font = ProjectShitstorm.getInstance().getFont("size12");
        int margin = 10;
        StringBuilder sb = new StringBuilder();
        sb.append("[F3] Toggle debug menu [" + onOff(debugMenu) + "]\n");
        sb.append("[P] Toggle physic body outlines [" + onOff(physicsDebug) + "]\n");
        sb.append("FPS: " + Gdx.graphics.getFramesPerSecond());

        SpriteBatch batch = new SpriteBatch();
        batch.begin();
        font.draw(batch, sb.toString(), margin, Gdx.graphics.getHeight() - margin);
        batch.end();
    }

    private String onOff(boolean bool) {
        return bool ? "On" : "Off";
    }
}
