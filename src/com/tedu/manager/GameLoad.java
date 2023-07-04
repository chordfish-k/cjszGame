package com.tedu.manager;

import com.tedu.controller.Direction;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class GameLoad {
    public static Map<Direction, ImageIcon> imgMap = null;

    static {
        imgMap = new HashMap<>();
        imgMap.put(Direction.LEFT, new ImageIcon("image/tank/play1/player1_left.png"));
        imgMap.put(Direction.RIGHT, new ImageIcon("image/tank/play1/player1_right.png"));
        imgMap.put(Direction.UP, new ImageIcon("image/tank/play1/player1_up.png"));
        imgMap.put(Direction.DOWN, new ImageIcon("image/tank/play1/player1_down.png"));
    }
}
