package com.tedu.element;

import javax.swing.*;
import java.awt.*;

/**
 * 所有元素的基类
 * @author LSR
 */
public abstract class ElementObj {
    private float x = 0;
    private float y = 0;
    private int w = 0;
    private int h = 0;
    private ImageIcon sprite = null;

    public ElementObj() {
    }

    /**
     * 带参构造函数
     * @param x 左上角x坐标
     * @param y 左上角y坐标
     * @param w 元素宽度
     * @param h 元素高度
     * @param sprite 元素贴图
     */
    public ElementObj(float x, float y, int w, int h, ImageIcon sprite) {
        super();
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.sprite = sprite;
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
    public void onUpdate() {

    }

    /**
     * 显示元素，抽象方法
     * @param g 当前JPanel的画笔
     */
    public abstract void showElement(Graphics g);

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

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

    public ImageIcon getSprite() {
        return sprite;
    }

    public void setSprite(ImageIcon sprite) {
        this.sprite = sprite;
    }
}
