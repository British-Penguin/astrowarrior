package com.britishpenguin.astrowarrior.config;

import com.badlogic.gdx.InputProcessor;

public class KeyInputProcessor implements InputProcessor {

    private final GameSettings gameSettings;

    public KeyInputProcessor(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    @Override
    public boolean keyDown(int keycode) {
        this.gameSettings.keybindManager.setKeyState(keycode, true);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        this.gameSettings.keybindManager.setKeyState(keycode, false);
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}
