package com.tedu.element.tank;

import com.tedu.controller.Direction;
import com.tedu.element.ElementObj;

import javax.swing.*;
import java.awt.*;

public class TankBase extends ElementObj {

    protected float speed = 5;
    // 当前面向的方向
    protected Direction facing = Direction.UP;

    public TankBase(float x, float y, int w, int h, ImageIcon sprite) {
        super(x, y, w, h, sprite);
    }

    @Override
    public void showElement(Graphics g) {
        g.drawImage(getSprite().getImage(),
                Math.round(getX()), Math.round(getY()),
                getW(), getH(), null);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        tankBehavior();
    }

    /**
     * 坦克基本行为模式<p>
     * 改变贴图，移动，发射炮弹
     */
    protected final void tankBehavior() {
        spriteChange();
        move();
        attack();
    }

    /**
     * 处理贴图变化（带重写）
     */
    protected void spriteChange() {
    }

    /**
     * 移动函数（需重写）
     */
    protected void move() {
    }

    /**
     * 攻击函数（需重写）
     */
    protected void attack() {
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Direction getFacing() {
        return facing;
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
    }
}
