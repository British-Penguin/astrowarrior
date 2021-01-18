package com.britishpenguin.astrowarrior.config;

import com.badlogic.gdx.Input;
import com.britishpenguin.astrowarrior.config.keybinding.Keybind;
import com.britishpenguin.astrowarrior.config.keybinding.KeybindManager;

public class GameSettings {

    public KeybindManager keybindManager;

    public Keybind keyMoveForward = new Keybind("Move Forward", Input.Keys.W, Input.Keys.UP);
    public Keybind keyMoveBackward = new Keybind("Move Backwards", Input.Keys.S, Input.Keys.DOWN);
    public Keybind keyRotateLeft = new Keybind("Rotate Left", Input.Keys.A, Input.Keys.LEFT);
    public Keybind keyRotateRight = new Keybind("Rotate Right", Input.Keys.D, Input.Keys.RIGHT);
    public Keybind keyDashLeft = new Keybind("Dash Left", Input.Keys.B, Input.Keys.UNKNOWN);
    public Keybind keyDashRight = new Keybind("Dash Right", Input.Keys.N, Input.Keys.UNKNOWN);
    public Keybind keyShoot = new Keybind("Shoot", Input.Keys.SPACE, Input.Keys.UNKNOWN);
    public Keybind toggleMouseLook = new Keybind("Toggle Mouse Look", Input.Keys.T, Input.Keys.UNKNOWN);

    public GameSettings() {
        keybindManager = new KeybindManager();
        keybindManager.addKeybind(keyMoveForward);
        keybindManager.addKeybind(keyMoveBackward);
        keybindManager.addKeybind(keyRotateLeft);
        keybindManager.addKeybind(keyRotateRight);
        keybindManager.addKeybind(toggleMouseLook);
        keybindManager.addKeybind(keyShoot);
        keybindManager.addKeybind(keyDashLeft);
        keybindManager.addKeybind(keyDashRight);
    }

}
