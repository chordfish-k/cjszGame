package com.tedu.geometry;

public class Box extends Polygon{
    protected int w;
    protected int h;

    public Box() {
        this(new Vector2(), 0, 0);
    }

    public Box(Vector2 center, int w, int h) {
        super(center);

        float cx = center.x;
        float cy = center.y;
        float hw = w / 2f;
        float hh = h / 2f;

        addPoint(new Vector2(cx - hw, cy + hh));
        addPoint(new Vector2(cx + hw, cy + hh));
        addPoint(new Vector2(cx + hw, cy - hh));
        addPoint(new Vector2(cx - hw, cy - hh));
    }

    public void setSize(Vector2 size) {
        w = (int) size.x;
        h = (int) size.y;
        float cx = center.x;
        float cy = center.y;
        float hw = w / 2f;
        float hh = h / 2f;

        this.clear();
        this.addPoint(new Vector2(cx - hw, cy + hh));
        this.addPoint(new Vector2(cx + hw, cy + hh));
        this.addPoint(new Vector2(cx + hw, cy - hh));
        this.addPoint(new Vector2(cx - hw, cy - hh));
    }

    /**
     * 获取盒子大小
     * @return (宽，高)的二维向量
     */
    public Vector2 getSize() {
        return new Vector2(w, h);
    }
}
