package com.tedu.element.tank;

import com.tedu.element.ElementObj;
import com.tedu.element.bullet.Bullet;
import com.tedu.element.component.HealthValue;
import com.tedu.element.component.Sprite;
import com.tedu.element.component.Transform;
import com.tedu.geometry.Vector2;
import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.util.Random;

public class EnemyTank extends TankBase{

    Sprite sp = null;
    HealthValue hv = null;

    public EnemyTank() {
        sp = (Sprite) getComponent("Sprite");
        hv = (HealthValue) getComponent("HealthValue");
    }

    @Override
    public ElementObj create(String data) {
        String[] split = data.split(",");

        transform.setX(Float.parseFloat(split[0]))
                .setY(Float.parseFloat(split[1]));

        sp.setSprite(GameLoad.imgMap.get("bot_" + split[2]))
                .setCenter(new Vector2(0.5f, 0.5f));
        this.setW(sp.getSprite().getIconWidth());
        this.setH(sp.getSprite().getIconHeight());

        hv.setMaxHealth(2, true);
        return this;
    }

    @Override
    public void onCollision(ElementObj other) {
    }
}
