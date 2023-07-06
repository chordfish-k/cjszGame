package com.tedu.element.map;

import com.tedu.element.ElementObj;
import com.tedu.element.component.HealthValue;

import javax.swing.*;
import java.awt.*;

public class MapObj extends ElementObj {
    HealthValue hv = null;

    @Override
    public void onDraw(Graphics g) {
        g.drawImage(getSprite().getImage(),
                Math.round(getX()), Math.round(getY()),
                getW(), getH(), null);
    }

    @Override
    public void onLoad() {

    }

    @Override
    public ElementObj create(String data) {
        hv = (HealthValue) addComponent("HealthValue");
        hv.setMaxHealth(1, true);

        String[] arr = data.split(",");

        ImageIcon sprite = null;

        switch (arr[0]) {
            case "GRASS":
                sprite = new ImageIcon("image/wall/grass.png");
                break;
            case "BRICK":
                sprite = new ImageIcon("image/wall/brick.png");
                break;
            case "RIVER":
                sprite = new ImageIcon("image/wall/river.png");
                hv.setDamageable(false);
                break;
            case "IRON":
                sprite = new ImageIcon("image/wall/iron.png");
                hv.setMaxHealth(2, true);
                break;
        }
        int x = Integer.parseInt(arr[1]);
        int y = Integer.parseInt(arr[2]);
        assert sprite != null;
        int w = sprite.getIconWidth();
        int h = sprite.getIconHeight();
        this.setH(h);
        this.setW(w);
        this.setX(x);
        this.setY(y);
        this.setSprite(sprite);
        return this;
    }
}
