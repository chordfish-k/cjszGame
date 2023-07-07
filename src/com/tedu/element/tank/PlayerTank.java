package com.tedu.element.tank;

import com.tedu.controller.Direction;
import com.tedu.element.ElementObj;
import com.tedu.element.bullet.Bullet;
import com.tedu.element.component.HealthValue;
import com.tedu.element.component.RigidBody;
import com.tedu.element.component.Sprite;
import com.tedu.manager.ElementManager;
import com.tedu.manager.ElementType;
import com.tedu.manager.GameLoad;
import com.tedu.show.GameJFrame;
import com.tedu.geometry.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * 玩家类
 * @author LSR
 */
public class PlayerTank extends TankBase{

    private boolean moving = false; // 移动状态
    private boolean attacking = false; // 攻击状态
    private long attackSpan = 0; // 攻击间隔
    private long lastAttackTime = 0;

    Sprite sp = null;
    HealthValue hv = null;
    RigidBody rb = null;

    public PlayerTank() {
        sp = (Sprite) getComponent("Sprite");
        hv = (HealthValue) getComponent("HealthValue");
        rb = (RigidBody) addComponent("RigidBody");
        this.speed = 25;
    }

    @Override
    public ElementObj create(String data) {
        String[] split = data.split(",");



        transform.setX(Float.parseFloat(split[0]))
                .setY(Float.parseFloat(split[1]));
        sp.setSprite(GameLoad.imgMap.get(split[2]))
                .setCenter(new Vector2(0.5f, 0.5f));

        // TODO: 改成设置碰撞体的宽高
        this.setW(sp.getSprite().getIconWidth());
        this.setH(sp.getSprite().getIconHeight());

        this.attackSpan = 10;
        hv.setMaxHealth(10, true);
        return this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onKeyPressed(int key) {
        switch (key) {
            case KeyEvent.VK_W:
                this.moving = true;
                setFacing(Direction.UP);
                break;
            case KeyEvent.VK_S:
                this.moving = true;
                setFacing(Direction.DOWN);
                break;
            case KeyEvent.VK_A:
                this.moving = true;
                setFacing(Direction.LEFT);
                break;
            case KeyEvent.VK_D:
                this.moving = true;
                setFacing(Direction.RIGHT);
                break;
            case KeyEvent.VK_SPACE:
                this.attacking = true;
                break;
        }
    }

    public void onKeyReleased(int key) {
        switch (key) {
            case KeyEvent.VK_W:
                if(getFacing() == Direction.UP)
                    moving = false;
                break;
            case KeyEvent.VK_S:
                if(getFacing() == Direction.DOWN)
                    moving = false;
                break;
            case KeyEvent.VK_A:
                if(getFacing() == Direction.LEFT)
                    moving = false;
                break;
            case KeyEvent.VK_D:
                if(getFacing() == Direction.RIGHT)
                    moving = false;
                break;
            case KeyEvent.VK_SPACE:
                this.attacking = false;
                break;
        }
    }

    @Override
    protected void move(long time) {
        if (!this.moving) {
            rb.setVelocity(new Vector2(0, 0));
            return;
        }
        if (this.transform == null) return;

        if (getFacing() == Direction.LEFT && transform.getX() > 0) {
            rb.setVelocity(new Vector2(-getSpeed(), 0));
            //transform.setPos(new Vector2(transform.getX() - getSpeed(), transform.getY()));
        }
        if (getFacing() == Direction.RIGHT && transform.getX() + getW() + 25 < GameJFrame.SIZE_W) {
            rb.setVelocity(new Vector2(getSpeed(), 0));
            //transform.setPos(new Vector2(transform.getX() + getSpeed(), transform.getY()));
        }
        if (getFacing() == Direction.UP && transform.getY() > 0) {
            rb.setVelocity(new Vector2(0, -getSpeed()));
            //transform.setPos(new Vector2(transform.getX(), transform.getY() - getSpeed()));
        }
        if (getFacing() == Direction.DOWN && transform.getY() + getH() + 50 < GameJFrame.SIZE_H) {
            rb.setVelocity(new Vector2(0, getSpeed()));
            //transform.setPos(new Vector2(transform.getX(), transform.getY() + getSpeed()));
        }

        // 根据方向改变贴图
        sp.setSprite(GameLoad.imgMap.get(facing.name().toLowerCase()));
    }

    @Override
    protected void spriteChange(long time) {

    }

    @Override
    protected void attack(long time) {
        if(!attacking)
            return;

        if(time - lastAttackTime < attackSpan)
            return;

        lastAttackTime = time;

        float x = transform.getX();
        float y = transform.getY();
//        int H = (int)(sp.getHeight() * tr.getScaleY());
//        int W = (int)(sp.getWidth() * tr.getScaleX());
//        int halfH = H / 2;
//        int halfW = W / 2;
//
//        switch (getFacing()) {
//            case UP: x += halfW; break;
//            case DOWN: x += halfW; y += getH() - 10; break;
//            case LEFT: y += halfH; break;
//            case RIGHT: y += halfH; x += getW() - 10; break;
//        }
        String dataStr = "x:" + x + ",y:" + y + ",f:" + getFacing().name();

        ElementObj ele = new Bullet().create(dataStr);

        ElementManager.getManager().addElement(ele, ElementType.BULLET);
    }
}
