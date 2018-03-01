package com.hashcode;

public class Ride {
    int index, a, b, x, y, s, f, distance;

    public Ride(int index, int a, int b, int x, int y, int s, int f) {
        this.index = index;
        this.a = a;
        this.b = b;
        this.x = x;
        this.y = y;
        this.s = s;
        this.f = f;

        this.distance = Math.abs(a - x) + Math.abs(b - y);
    }
}
