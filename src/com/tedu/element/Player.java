package com.tedu.element;

import javax.swing.*;
import java.awt.*;

/**
 * 玩家类
 * @author LSR
 */
public class Player extends ElementObj{

    public Player(int x, int y, int w, int h, ImageIcon sprite) {
        super(x, y, w, h, sprite);
    }

    @Override
    public void showElement(Graphics g) {
        g.drawImage(getSprite().getImage(),
                getX(), getY(),
                getW(), getH(), null);
    }
}
