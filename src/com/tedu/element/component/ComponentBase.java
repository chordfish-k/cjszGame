package com.tedu.element.component;

import com.tedu.element.ElementObj;

import java.awt.*;

public abstract class ComponentBase{
    protected ElementObj parent = null;

    public ComponentBase() {
        this("");
    }

    public ComponentBase(String data) {
        onCreate();
    }

    public ComponentBase create(String data) {
        return this;
    }

    public void setParent(ElementObj parent) {
        this.parent = parent;
    }

    public ElementObj getParent() {
        return parent;
    }

    public void onLoad() {}

    public void onCreate() {}

    public void onDestroy() {}

    public void onUpdate() {}

    public void onFixUpdate() {}

    /**
     * 当该帧执行渲染时执行，可以重写，用于显示测试用图像
     */
    public void onDraw(Graphics g) {}
}
