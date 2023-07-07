package com.tedu.element.component;

import com.tedu.element.ElementObj;
import com.tedu.geometry.Box;
import com.tedu.geometry.Vector2;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * 碰撞器组件
 * 默认是矩形碰撞箱
 * @author LSR
 */
public class BoxCollider extends ComponentBase{

    Rectangle shape = null;
    Vector2 center = new Vector2();
    Vector2 offset = new Vector2();

    public BoxCollider() {
        shape = new Rectangle(0, 0, 10, 10);
    }

    @Override
    public ComponentBase create(String data) {
        String[] split = data.split(",");
        String shape = "Rectangle";
        float offX = 0f;
        float offY = 0f;
        int w = 10;
        int h = 10;
        for(String sp : split) {
            String[] kv = sp.split(":");

            switch (kv[0]) {
                case "offX":
                    offX = Float.parseFloat(kv[1]);
                    break;
                case "offY":
                    offY = Float.parseFloat(kv[1]);
                    break;
                case "w":
                    w = Integer.parseInt(kv[1]);
                    break;
                case "h":
                    h = Integer.parseInt(kv[1]);
                    break;
                case "shape":
                    shape = kv[1];
                    break;
            }
        }
        if (shape .equals("Rectangle")) {
            this.shape = new Rectangle(w, h);
            this.setOffset(new Vector2(offX, offY));
        }

        return this;
    }

    public BoxCollider setSize() {
        return this;
    }

    public Vector2 getSize() {
        int h = this.shape.height;
        int w = this.shape.width;
        return new Vector2(w, h);
    }

    public Shape getShape() {
        return shape;
    }

    /**
     * 设置碰撞形状的原点
     * @param center 原点坐标
     * @return 碰撞组件自身
     */
    public BoxCollider setCenter(Vector2 center) {

        ElementObj parent = getParent();
        Transform tr = parent.transform;
        Vector2 oldCenter = this.center.clone();
        this.center = center.clone();

        if (tr == null)
            return this;

        if (center.equal(oldCenter))
            return this;

        float dx = center.x - oldCenter.x;
        float dy = center.y - oldCenter.y;


        this.moveBy(new Vector2(dx, dy));

        return this;
    }

    public Vector2 getCenter() {
        return this.center;
    }

    public Vector2 getOffset() {
        return offset;
    }

    /**
     * 设置碰撞形状距离原点的偏移量
     * @param offset 偏移量
     * @return 碰撞组件自身
     */
    public BoxCollider setOffset(Vector2 offset) {
        this.moveBy(offset);
        return this;
    }

    public BoxCollider moveBy(Vector2 vec) {
        Point loc = this.shape.getLocation();
        this.shape.setLocation((int)(loc.x + vec.x), (int)(loc.y + vec.y));
        return this;
    }

    @Override
    public void onDraw(Graphics g) {
        super.onDraw(g);
//        g.setColor(Color.RED);
//        Graphics2D g2d = (Graphics2D)g;
//        g2d.draw(shape);
    }
}
