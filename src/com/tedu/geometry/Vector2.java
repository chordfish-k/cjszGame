package com.tedu.geometry;

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

