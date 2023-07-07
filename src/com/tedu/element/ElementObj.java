package com.tedu.element;

import com.tedu.element.component.BoxCollider;
import com.tedu.element.component.ComponentBase;
import com.tedu.element.component.Transform;
import com.tedu.geometry.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 所有元素的基类
 * @author LSR
 */
public abstract class ElementObj {
    private int w = 0;
    private int h = 0;
    private ImageIcon sprite = null;
    private ElementState elementState = ElementState.LIVING;
    private List<ElementObj> children = null;
    private Map<String, ComponentBase> components = null;

    public Transform transform = null;

    public ElementObj() {
        components = new HashMap<>();
        transform = (Transform) addComponent("Transform");
    }

    /**
     * 带参构造函数
     * @param x 左上角x坐标
     * @param y 左上角y坐标
     * @param sprite 元素贴图
     */
    public ElementObj(float x, float y, ImageIcon sprite) {
        this();
        this.w = 0;
        this.h = 0;
        if(this.transform != null) {
            this.transform.setPos(new Vector2(x, y));
        }
        //this.sprite = sprite;
    }

    public ElementObj create() {
        return this.create("");
    }

    /**
     * 工厂模式创建对象（需重写）
     * @param data 必要的数据
     * @return 将数据解析后新建的对象
     */
    public ElementObj create(String data) {
        return this;
    }

    /**
     * 当前元素创建中执行，主要加载Component
     */
    public void onLoad() {
        for(String cpKey : components.keySet()) {
            components.get(cpKey).onLoad();
        }
    }

    /**
     * 当元素创建后执行
     */
    public void onCreate() {
        for(String cpKey : components.keySet()) {
            components.get(cpKey).onCreate();
        }
    }

    /**
     * 销毁当前对象
     */
    public void destroy(){
        setElementState(ElementState.DIED);
    }

    /**
     * 当监听到键盘按键按下时触发
     * @param key 按键码
     */
    public void onKeyPressed(int key) {
        //不强制重写
    }

    /**
     * 当监听到键盘按键松开时触发
     * @param key 按键码
     */
    public void onKeyReleased(int key) {
        //不强制重写
    }

    /**
     * 帧更新
     */
    public void onUpdate(long time) {
        for(String cpKey : components.keySet()) {
            components.get(cpKey).onUpdate();
        }
    }

    /**
     * 当对象被销毁前触发
     */
    public void onDestroy() {
        for(String cpKey : components.keySet()) {
            components.get(cpKey).onDestroy();
        }
        components.clear();
    }

    /**
     * 当触发碰撞后触发
     */
    public void onCollision(ElementObj other) {

    }

    /**
     * 显示元素，抽象方法
     * @param g 当前JPanel的画笔
     */
    public abstract void onDraw(Graphics g);

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public ElementState getElementState() {
        return elementState;
    }

    public List<ElementObj> getChildren() {
        return children;
    }

    public Map<String, ComponentBase> getComponents() {
        return components;
    }

    public ComponentBase getComponent(String name) {
        return components.get(name);
    }

    public void setElementState(ElementState elementState) {
        this.elementState = elementState;
    }

    /**
     * 获取本元素的外包围盒
     * @return 元素的碰撞矩形对象
     */
    public Shape getShape() {
        BoxCollider col = (BoxCollider) getComponent("BoxCollider");
        if (col == null)
            return null;

//        Sprite sp = (Sprite) this.getComponent("Sprite");
//        if (this.transform == null || sp == null)
//            return new Rectangle(0,0,0,0);
//
//        Rectangle r = new Rectangle(Math.round(transform.getX()), Math.round(transform.getY()),
//                Math.round(sp.getWidth() * transform.getScaleX()),
//                Math.round(sp.getHeight() * transform.getScaleY()));
        return col.getShape();
    }

    /**
     * 根据外包围盒，判断两个元素是否有重合（碰撞）
     * @param obj 需要比较的另一个元素
     * @return 是否碰撞的判断
     */
    public boolean checkCollisionWith(ElementObj obj) {
        BoxCollider col = (BoxCollider) getComponent("BoxCollider");
        if (col == null)
            return false;
        Area shapeA = new Area(this.getShape());
        Area shapeB = new Area(obj.getShape());
        shapeA.intersect(shapeB);
        return !shapeA.isEmpty();
    }
    public ComponentBase addComponent(String name) {
        return addComponent(name, "");
    }

    public ComponentBase addComponent(String name, String data) {
        ComponentBase cb = null;
        try {
            Class<?> c =  Class.forName("com.tedu.element.component."+name);
            cb = ((ComponentBase) c.newInstance()).create(data);
            this.components.put(name, cb);
            cb.setParent(this);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return cb;
    }
}
