package com.tedu.manager;

import com.tedu.controller.Direction;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 加载器，读取配置文件的工具类
 * 提供一系列static方法
 * @author LSR
 */
public class GameLoad {
    public static Map<Direction, ImageIcon> imgMap = null;

    static {
        imgMap = new HashMap<>();
        imgMap.put(Direction.LEFT, new ImageIcon("image/tank/play1/player1_left.png"));
        imgMap.put(Direction.RIGHT, new ImageIcon("image/tank/play1/player1_right.png"));
        imgMap.put(Direction.UP, new ImageIcon("image/tank/play1/player1_up.png"));
        imgMap.put(Direction.DOWN, new ImageIcon("image/tank/play1/player1_down.png"));
    }

    // 读取配置文件的类
    private static Properties pro = new Properties();

    /**
     * 传入地图id加载地图
     * @param mapId 地图文件id
     */
    public static void MapLoad(int mapId) {
        String mapName = "com/tedu/text/" + mapId + ".map";
        // 用io流获取文件对象，加载包内文件
        ClassLoader classLoader = GameLoad.class.getClassLoader(); // 类加载器
        InputStream maps = classLoader.getResourceAsStream(mapName);
        if (maps == null) {
            System.out.println("配置文件读取异常，清重新安装");
            return;
        }
        try {
            pro.load(maps);
            Enumeration<?> names = pro.propertyNames();
            System.out.println(names);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MapLoad(5);
    }
}
