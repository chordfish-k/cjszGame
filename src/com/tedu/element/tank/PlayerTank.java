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
import com.tedu.manager.UIManager;
import com.tedu.show.GameJFrame;
import com.tedu.geometry.Vector2;

import javax.swing.*;
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

    private JLabel jl = null;

    Sprite sp = null;
    HealthValue hv = null;
    RigidBody rb = null;

    public PlayerTank() {
        sp = (Sprite) getComponent("Sprite");
        hv = (HealthValue) getComponent("HealthValue");
        hv.setMaxHealth(10, true);
        rb = (RigidBody) addComponent("RigidBody");
        this.speed = 25;

        jl = (JLabel) UIManager.getManager().getUI("healthLabel");
        jl.setText("Health:" + hv.getHealth());

        hv.setOnHealthChangeEvent(new Runnable() {
            @Override
            public void run() {
                if (jl != null) {
                    jl.setText("Health:" + hv.getHealth());
                }
            }
        });
    }

    @Override
    public ElementObj create(String data) {
        String[] split = data.split(",");

        transform.setX(Float.parseFloat(split[0]))
                .setY(Float.parseFloat(split[1]));
        sp.setSprite(GameLoad.imgMap.get(split[2]))
                .setCenter(new Vector2(0.5f, 0.5f));

        this.attackSpan = 10;
        return this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onKeyPressed(int key) {
        super.onKeyPressed(key);
        switch (key) {
            case KeyEvent.VK_W:
                this.moving = true;
                setFacing(Direction.UP);
                rb.setVelocity(new Vector2(0, -getSpeed()));
                break;
            case KeyEvent.VK_S:
                this.moving = true;
                setFacing(Direction.DOWN);
                rb.setVelocity(new Vector2(0, getSpeed()));
                break;
            case KeyEvent.VK_A:
                this.moving = true;
                setFacing(Direction.LEFT);
                rb.setVelocity(new Vector2(-getSpeed(), 0));
                break;
            case KeyEvent.VK_D:
                this.moving = true;
                setFacing(Direction.RIGHT);
                rb.setVelocity(new Vector2(getSpeed(), 0));
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
        if (!moving) {
            rb.setVelocity(new Vector2(0, 0));
        }
    }

    @Override
    protected void move(long time) {
        if (!this.moving) {
            return;
        }
        if (this.transform == null) return;

    }

    @Override
    protected void spriteChange(long time) {
        // 根据方向改变贴图
        sp.setSprite(GameLoad.imgMap.get(facing.name().toLowerCase()));
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

        String dataStr = "x:" + x + ",y:" + y + ",f:" + getFacing().name() + ",by:PLAYER";

        ElementObj ele = new Bullet().create(dataStr);

        ElementManager.getManager().addElement(ele, ElementType.BULLET);
    }
}
