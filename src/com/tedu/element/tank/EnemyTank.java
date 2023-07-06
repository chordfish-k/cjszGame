package com.tedu.element.tank;

import com.tedu.element.ElementObj;
import com.tedu.element.bullet.Bullet;
import com.tedu.element.component.HealthValue;
import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.util.Random;

public class EnemyTank extends TankBase{

    public EnemyTank() {
    }

    @Override
    public ElementObj create(String data) {
        String[] split = data.split(",");
        this.setX(Float.parseFloat(split[0]));
        this.setY(Float.parseFloat(split[1]));
        this.setSprite(GameLoad.imgMap.get("bot_" + split[2]));
        this.setW(getSprite().getIconWidth());
        this.setH(getSprite().getIconHeight());

        HealthValue hv = (HealthValue)this.getComponent("HealthValue");
        hv.setMaxHealth(2, true);
        return this;
    }

    @Override
    public void onCollision(ElementObj other) {
        if (other instanceof Bullet) {
            Bullet b = (Bullet)other;
            //damageBy(b.getDamage());
        }
    }
}
