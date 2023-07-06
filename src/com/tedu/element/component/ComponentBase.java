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
}
