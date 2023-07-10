package com.tedu.element.tank;

import com.tedu.controller.Direction;
import com.tedu.element.ElementObj;
import com.tedu.element.component.BoxCollider;
import com.tedu.element.component.HealthValue;
import com.tedu.element.component.Sprite;
import com.tedu.geometry.Vector2;

import javax.swing.*;
import java.awt.*;

public class TankBase extends ElementObj{

    HealthValue hv = null;
    Sprite sp = null;
    BoxCollider col = null;

    protected float speed = 1;

    // 当前面向的方向
    protected Direction facing = Direction.LEFT;

    public TankBase(){
        this(0, 0, null);
    }

    public TankBase(float x, float y, ImageIcon sprite) {
        super(x, y, sprite);
        hv = (HealthValue) addComponent("HealthValue");
        sp = (Sprite) addComponent("Sprite");
        col = (BoxCollider) addComponent("BoxCollider", "shape:Rectangle,w:36,h:36");

    }

    @Override
    public void onDraw(Graphics g) {
        if (sp == null || sp.getSprite() == null)
            return;
        Vector2 center = sp.getCenter();
        g.drawImage(sp.getSprite().getImage(),
                Math.round(transform.getX() - sp.getWidth() * transform.getScaleX() * center.x),
                Math.round(transform.getY() - sp.getHeight() * transform.getScaleY() * center.y),
                (int)(sp.getWidth() * transform.getScaleX()),
                (int)(sp.getHeight() * transform.getScaleY()), null);
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
        super.onUpdate(time);
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
