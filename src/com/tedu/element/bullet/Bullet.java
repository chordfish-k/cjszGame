package com.tedu.element.bullet;

import com.tedu.controller.Direction;
import com.tedu.element.ElementState;
import com.tedu.element.ElementObj;
import com.tedu.element.component.BoxCollider;
import com.tedu.element.component.HealthValue;
import com.tedu.show.GameJFrame;
import com.tedu.geometry.Vector2;

import java.awt.*;

/**
 * 子弹类
 */
public class Bullet extends ElementObj {
    private int damage; // 攻击力
    private int speed; // 移速
    private Direction facing; //朝向
    private int radius = 5; // 子弹半径

    BoxCollider col = null;

    public Bullet() {
        col = (BoxCollider) addComponent("BoxCollider", "shape:Rectangle,w:"+2 * radius+",h:"+2 * radius);
    }

    @Override
    public ElementObj create(String data) {
        // 解析数据
        String[] split = data.split(",");
        float x = 0f;
        float y = 0f;

//        ((Rectangle)col.getShape()).setSize(2 * radius, 2 * radius);

        for (String piece : split) {
            String[] kv = piece.split(":");

            switch (kv[0]) {
                case "x":
                    x = Float.parseFloat(kv[1]);
                    break;
                case "y":
                    y = Float.parseFloat(kv[1]);
                    break;
                case "f":
                    this.facing = Direction.valueOf(kv[1]);
                    break;
            }
        }
        switch (this.facing) {
            case UP:
            case DOWN:
                col.setOffset(new Vector2(-radius, 0));
                break;
            case LEFT:
            case RIGHT:
                col.setOffset(new Vector2(0, -radius));
                break;
        }

        this.setW(10);
        this.setH(10);
        this.transform.setPos(new Vector2(x, y));
        this.speed = 10;
        this.damage = 1;
        this.radius = 5;

        return this;
    }

    @Override
    public void onDraw(Graphics g) {
        if (transform == null)
            return;

        g.setColor(Color.red);

        int x = Math.round(transform.getX());
        int y = Math.round(transform.getY());

        switch (this.facing) {
            case UP:
            case DOWN:
                x -= radius;
                break;
            case LEFT:
            case RIGHT:
                y -= radius;
                break;
        }
        g.fillOval(x, y, 2 * radius, 2 * radius);
    }

    @Override
    public void onUpdate(long time) {
        super.onUpdate(time);
        checkDead();
        spriteChange(time);
        move();
    }

    private void spriteChange(long time) {
//        if (time % 2 == 0) {
//            radius += 1;
//        }
    }

    private void checkDead() {
        if (transform == null)
            return;
        float x = transform.getX();
        float y = transform.getY();
        if (x < 0 || y < 0 || x > GameJFrame.SIZE_W || y > GameJFrame.SIZE_H) {
            this.setElementState(ElementState.DIED);
        }
    }

    public void move() {
        if (this.getElementState() == ElementState.DIED)
            return;

        if (transform == null)
            return;

        float x = transform.getX();
        float y = transform.getY();

        switch (this.facing) {
            case UP:
                y = transform.getY() - this.speed;
                break;
            case DOWN:
                y = transform.getY() + this.speed;
                break;
            case LEFT:
                x= transform.getX() - this.speed;
                break;
            case RIGHT:
                x = transform.getX() + this.speed;
                break;
        }

        transform.setPos(new Vector2(x, y));

    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onCollision(ElementObj other) {
        // 消除自身
        destroy();
        // 被子弹打到的元素，如果有HealthValue组件，则扣血
        HealthValue hv = (HealthValue) other.getComponent("HealthValue");
        if (hv != null) {
            hv.damageBy(getDamage());
        }
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
