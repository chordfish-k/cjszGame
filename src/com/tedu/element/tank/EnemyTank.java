package com.tedu.element.tank;

import com.tedu.element.ElementObj;
import com.tedu.element.bullet.Bullet;

import javax.swing.*;
import java.util.Random;

public class EnemyTank extends TankBase{

    public EnemyTank() {
    }

    @Override
    public ElementObj create(String data) {
        Random ran = new Random();
        int x = ran.nextInt(800);
        int y = ran.nextInt(800);
        this.setX(x);
        this.setY(y);
        this.setW(50);
        this.setH(50);
        this.setSprite(new ImageIcon("image/tank/bot/bot_up.png"));
        return this;
    }

    @Override
    public void onCollision(ElementObj other) {
        if (other instanceof Bullet) {
            Bullet b = (Bullet)other;
            damageBy(b.getDamage());
        }
    }
}
