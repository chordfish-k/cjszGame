package com.tedu.element.tank;

import com.tedu.controller.Direction;
import com.tedu.element.ElementObj;
import com.tedu.element.component.HealthValue;

import javax.swing.*;
import java.awt.*;

public class TankBase extends ElementObj{

    HealthValue hv = null;

    protected float speed = 1;

    // 当前面向的方向
    protected Direction facing = Direction.UP;

    public TankBase(){
        this(0, 0, 0, 0, null);
    }

    public TankBase(float x, float y, int w, int h, ImageIcon sprite) {
        super(x, y, w, h, sprite);
        hv = (HealthValue) addComponent("HealthValue");
    }

    @Override
    public void onDraw(Graphics g) {
        g.drawImage(getSprite().getImage(),
                Math.round(getX()), Math.round(getY()),
                getW(), getH(), null);
    }

    @Override
    public void onLoad() {
        super.onLoad();


    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onUpdate(long time) {
        tankBehavior(time);
    }

    /**
     * 坦克基本行为模式<p>
     * 改变贴图，移动，发射炮弹
     */
    protected final void tankBehavior(long time) {
        spriteChange(time);
        move(time);
        attack(time);
    }

    /**
     * 处理贴图变化（带重写）
     */
    protected void spriteChange(long time) {
    }

    /**
     * 移动函数（需重写）
     */
    protected void move(long time) {
    }

    /**
     * 攻击函数（需重写）
     */
    protected void attack(long time) {
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
