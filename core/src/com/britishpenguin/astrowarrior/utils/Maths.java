package com.britishpenguin.astrowarrior.utils;

public class Maths {

    public static float degreesToRadians(float degrees) {
        return degrees / 180 * (float)Math.PI;
    }

    public static float distance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)));
    }

}
