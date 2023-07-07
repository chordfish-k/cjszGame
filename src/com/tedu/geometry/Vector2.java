package com.tedu.geometry;

import java.awt.*;

public class Vector2{
    public float x;
    public float y;

    public Vector2() {
        this(0, 0);
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public Point toPoint() {
        return new Point((int)this.x, (int)this.y);
    }

    public boolean equal(Vector2 b) {
        return x == b.x && y == b.y;
    }

    public Vector2 add(Vector2 b) {
        return new Vector2(x + b.x, y + b.y);
    }

    public Vector2 sub(Vector2 b) {
        return new Vector2(x - b.x, y - b.y);
    }

    public Vector2 mul(float k) {
        return new Vector2(x * k, y * k );
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2 vec) {
        set(vec.x, vec.y);
    }

    /**
     * 求两个向量的距离
     * @param b 另一个向量
     * @return 两个向量的距离
     */
    public float distanceTo(Vector2 b) {
        return (float) Math.sqrt(Math.pow(x - b.x, 2) + Math.pow(y - b.y, 2));
    }

    /**
     * 求两个向量距离的平方
     * @param b 另一个向量
     * @return 两个向量距离的平方
     */
    public float distanceRawTo(Vector2 b) {
        return (float) Math.pow(x - b.x, 2) + (float) Math.pow(y - b.y, 2);
    }


    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Vector2 clone() {
        return new Vector2(x, y);
    }
}

