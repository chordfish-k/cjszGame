package com.tedu.element.tank;

import com.tedu.controller.Direction;
import com.tedu.element.ElementObj;
import com.tedu.element.bullet.Bullet;
import com.tedu.manager.ElementManager;
import com.tedu.manager.ElementType;
import com.tedu.manager.GameLoad;
import com.tedu.show.GameJFrame;

import javax.swing.*;
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

    public PlayerTank(float x, float y, int w, int h, ImageIcon sprite) {
        super(x, y, w, h, sprite);
        this.attackSpan = 10;
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
        if (!this.moving) return;

        if (getFacing() == Direction.LEFT && this.getX() > 0) {
            this.setX(this.getX() - getSpeed());
        }
        if (getFacing() == Direction.RIGHT && this.getX() + getW() + 25 < GameJFrame.SIZE_W) {
            this.setX(this.getX() + getSpeed());
        }
        if (getFacing() == Direction.UP && this.getY() > 0) {
            this.setY(this.getY() - getSpeed());
        }
        if (getFacing() == Direction.DOWN && this.getY() + getH() + 50 < GameJFrame.SIZE_H) {
            this.setY(this.getY() + getSpeed());
        }
    }

    @Override
    protected void spriteChange(long time) {
        setSprite(GameLoad.imgMap.get(facing));
    }

    @Override
    protected void attack(long time) {
        if(!attacking)
            return;

        if(time - lastAttackTime < attackSpan)
            return;

        lastAttackTime = time;

        float x = getX();
        float y = getY();
        int halfH = getH() / 2;
        int halfW = getW() / 2;

        switch (getFacing()) {
            case UP: x += halfW; break;
            case DOWN: x += halfW; y += getH() - 10; break;
            case LEFT: y += halfH; break;
            case RIGHT: y += halfH; x += getW() - 10; break;
        }
        String dataStr = "x:" + x + ",y:" + y + ",f:" + getFacing().name();

        ElementObj ele = new Bullet().create(dataStr);

        ElementManager.getManager().addElement(ele, ElementType.BULLET);
    }
}
