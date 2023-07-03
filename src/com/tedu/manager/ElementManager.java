package com.tedu.manager;

import com.tedu.element.ElementObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 元素管理器 <p>
 * 单例类，用List存储所有的元素，并提供方法给予View和Controller获取数据
 * @author LSR
 */
public class ElementManager {
    // 实现单例
    private static ElementManager EM = null;

    /**
     * 用于获取ElementManager单例
     * @return ElementManager唯一单例
     */
    public static ElementManager getManager() {
        if (EM == null) {
            EM = new ElementManager();
        }
        return EM;
    }
    // 构造函数私有化
    private ElementManager(){
        init(); // 实例化方法
    }

//    static {// 类加载时被执行
//        EM = new ElementManager(); // 只会执行一次
//    }

    /**
     * 初始化方法 <p>
     * 用于将来可能出现的功能扩展，要重写init方法准备的
     */
    public void init() {
        gameElements = new HashMap<ElementType, List<ElementObj>>();

        // 将每种元素集合都放入map中
        gameElements.put(ElementType.PLAYER, new ArrayList<ElementObj>());
        gameElements.put(ElementType.MAP, new ArrayList<ElementObj>());
        gameElements.put(ElementType.ENEMY, new ArrayList<ElementObj>());
    }

    private Map<ElementType, List<ElementObj>> gameElements;

    /**
     * 获取本管理器所有元素
     * @return 元素Map集合
     */
    public Map<ElementType, List<ElementObj>> getGameElements() {
        return gameElements;
    }

    /**
     * 向管理器添加元素
     * @param obj 要添加的元素
     * @param type 元素类型
     */
    public void addElement(ElementObj obj, ElementType type) {
        gameElements.get(type).add(obj);
    }

    /**
     * 获取某一类型的元素列表
     * @param type 元素类型
     * @return 该类型的元素列表
     */
    public List<ElementObj> getElementsByType(ElementType type){
        return gameElements.get(type);
    }


}


