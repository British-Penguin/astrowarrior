package com.britishpenguin.astrowarrior.config.keybinding;

public class Keybind {

    private String name;
    private int mainKeycode, alternateKeycode;

    private long pressStart;
    private boolean pressed;

    public Keybind(String name, int mainKeycode, int alternateKeycode) {
        this.name = name;
        this.mainKeycode = mainKeycode;
        this.alternateKeycode = alternateKeycode;
        this.pressed = false;
        this.pressStart = -1;
    }

    public void setState(boolean pressed) {
        if (!pressed)
            pressStart = -1;
        else if (!this.pressed)
            pressStart = System.currentTimeMillis();
        this.pressed = pressed;
    }

    public boolean isKeyDown() {
        return this.pressed;
    }

    public boolean isPressed() {
        long timeSincePress = getTimeSincePressed();
        return timeSincePress == 0L;
    }

    public long getStartPressTime() {
        return pressStart;
    }

    public long getTimeSincePressed() {
        return System.currentTimeMillis() - pressStart;
    }

    public String getName() {
        return name;
    }

    public int getMainKeycode() {
        return mainKeycode;
    }

    public void setMainKeycode(int mainKeycode) {
        this.mainKeycode = mainKeycode;
    }

    public int getAlternateKeycode() {
        return alternateKeycode;
    }

    public void setAlternateKeycode(int alternateKeycode) {
        this.alternateKeycode = alternateKeycode;
    }
}
