package com.tedu.element.tank;

import com.tedu.controller.Direction;
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

    public PlayerTank(float x, float y, int w, int h, ImageIcon sprite) {
        super(x, y, w, h, sprite);

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
    protected void move() {
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
    protected void spriteChange() {
        setSprite(GameLoad.imgMap.get(facing));
    }

    @Override
    protected void attack() {

    }
}
