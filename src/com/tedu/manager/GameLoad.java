package com.tedu.manager;

import com.tedu.element.ElementObj;
import com.tedu.element.map.MapObj;
import com.tedu.show.GameJFrame;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 加载器，读取配置文件的工具类
 * 提供一系列static方法
 * @author LSR
 */
public class GameLoad {
    // 资源管理器
    private static final ElementManager em = ElementManager.getManager();
    // 图片集合
    public static Map<String, ImageIcon> imgMap = new HashMap<>();
    // 元素集合
    public static Map<String, Class<?>> objMap = new HashMap<>();
    // 碰撞检查集合
    public static Map<ElementType, List<ElementType>> colMap = new HashMap<>();

    // 读取配置文件的类
    private static final Properties pro = new Properties();

    /**
     * 传入地图id加载地图
     * @param mapId 地图文件id
     */
    public static void LoadMap(int mapId) {
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
            while (names.hasMoreElements()) {
                String key = names.nextElement().toString();
                String[] arrs = pro.getProperty(key).split(";");
                for(int i=0; i<arrs.length; i++){
                    ElementObj element =  new MapObj().create(key+ "," +arrs[i]);
                    em.addElement(element,ElementType.MAP);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        pro.clear();
    }

    /**
     * 加载图片资源
     */
    public static void loadImage() {
        String fileName = "com/tedu/text/GameData.re";
        // 用io流获取文件对象，加载包内文件
        ClassLoader classLoader = GameLoad.class.getClassLoader(); // 类加载器
        InputStream ist = classLoader.getResourceAsStream(fileName);
        if (ist == null) {
            System.out.println("配置文件读取异常，清重新安装");
            return;
        }
        try {
            pro.clear();
            pro.load(ist);
            Set<Object> set = pro.keySet();
            for (Object o : set) {
                String path = pro.getProperty(o.toString());
                // 存入map中
                imgMap.put(o.toString(), new ImageIcon(path));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        pro.clear();
    }

    public static void loadCollision() {
        String fileName = "com/tedu/text/Collision.re";

        ClassLoader classLoader = GameLoad.class.getClassLoader(); // 类加载器
        InputStream ist = classLoader.getResourceAsStream(fileName);
        if (ist == null) {
            System.out.println("配置文件读取异常，清重新安装");
            return;
        }
        try {
            for (ElementType type : em.getGameElements().keySet()) {
                colMap.put(type, new ArrayList<>());
            }

            pro.load(ist);
            Set<Object> set = pro.keySet();
            for (Object o : set) {
                ElementType key = ElementType.valueOf(o.toString().toUpperCase());

                String[] colList = pro.getProperty(o.toString()).split(",");
                for (String col : colList) {
                    ElementType type = ElementType.valueOf(col.toUpperCase());
                    colMap.get(key).add(type);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        pro.clear();
    }

    public static void loadElement() {
        String fileName = "com/tedu/text/Elements.re";

        ClassLoader classLoader = GameLoad.class.getClassLoader(); // 类加载器
        InputStream ist = classLoader.getResourceAsStream(fileName);
        if (ist == null) {
            System.out.println("配置文件读取异常，清重新安装");
            return;
        }
        try {
            pro.load(ist);
            Set<Object> set = pro.keySet();
            for (Object o : set) {
                String packageName = pro.getProperty(o.toString());
                // 使用反射的方式直接根据名字获取这个类
                Class<?> cForName = Class.forName(packageName);
                objMap.put(o.toString(), cForName);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        pro.clear();
    }

    public static void loadPlayer() {
        // 创建元素
        ElementObj obj = createElementByName("player", "20,20,up");

        if (obj != null) {
            em.addElement(obj, ElementType.PLAYER);
        }
    }

    public static void loadEnemies() {
        Random ran = new Random();

        for (int i=0; i<1; i++) {
            int x = 100;
            int y = 50;
//            int x = ran.nextInt(GameJFrame.SIZE_W);
//            int y = ran.nextInt(GameJFrame.SIZE_H);
            ElementObj enemy = createElementByName("enemy", x+","+y+","+"up");
            em.addElement(enemy, ElementType.ENEMY);
        }
    }


    public static ElementObj createElementByName(String name) {
        return createElementByName(name, "");
    }

    /**
     * 根据类名创建元素类对象
     * @param name 类名（在配置文件中记录）
     * @param data 调用该元素类的create()方法要用的字符串数据
     * @return 该元素类的新对象
     */
    public static ElementObj createElementByName(String name, String data) {
        ElementObj obj = null;
        try {
            Object newIns = objMap.get(name).newInstance();
            if (newIns instanceof ElementObj) {
                obj = ((ElementObj) newIns).create(data);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
