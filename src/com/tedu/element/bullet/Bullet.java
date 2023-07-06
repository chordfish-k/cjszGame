package com.tedu.element.bullet;

import com.tedu.controller.Direction;
import com.tedu.element.ElementState;
import com.tedu.element.ElementObj;
import com.tedu.element.component.HealthValue;
import com.tedu.element.map.MapObj;
import com.tedu.element.tank.TankBase;
import com.tedu.show.GameJFrame;

import java.awt.*;

/**
 * 子弹类
 */
public class Bullet extends ElementObj {
    private int damage; // 攻击力
    private int speed; // 移速
    private Direction facing; //朝向
    private int radius = 5; // 子弹半径

    public Bullet() {
    }

    @Override
    public ElementObj create(String data) {
        // 解析数据
        String[] split = data.split(",");
        for (String piece : split) {
            String[] kv = piece.split(":");

            switch (kv[0]) {
                case "x":
                    this.setX(Float.parseFloat(kv[1]));
                    break;
                case "y":
                    this.setY(Float.parseFloat(kv[1]));
                    break;
                case "f":
                    this.facing = Direction.valueOf(kv[1]);
                    break;
            }
        }
        this.setW(10);
        this.setH(10);
        this.speed = 10;
        this.damage = 1;
        this.radius = 5;

        return this;
    }

    @Override
    public void onDraw(Graphics g) {
        g.setColor(Color.red);

        int x = Math.round(this.getX());
        int y = Math.round(this.getY());

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
        float x = this.getX();
        float y = this.getY();
        if (x < 0 || y < 0 || x > GameJFrame.SIZE_W || y > GameJFrame.SIZE_H) {
            this.setElementState(ElementState.DIED);
        }
    }

    public void move() {
        if (this.getElementState() == ElementState.DIED)
            return;

        switch (this.facing) {
            case UP:
                this.setY(this.getY() - this.speed);
                break;
            case DOWN:
                this.setY(this.getY() + this.speed);
                break;
            case LEFT:
                this.setX(this.getX() - this.speed);
                break;
            case RIGHT:
                this.setX(this.getX() + this.speed);
                break;
        }
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
