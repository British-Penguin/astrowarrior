package com.britishpenguin.astrowarrior.config.keybinding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KeybindManager {

    private List<Keybind> keybinds;

    public KeybindManager() {
        this.keybinds = new ArrayList<>();
    }

    public void addKeybind(Keybind keybind) {
        this.keybinds.add(keybind);
    }

    public void setKeyState(int keycode, boolean state) {
        for (Keybind keybind : keybinds.stream().filter(k -> k.getMainKeycode() == keycode || k.getAlternateKeycode() == keycode).collect(Collectors.toList()))  {
            keybind.setState(state);
        }
    }

}
