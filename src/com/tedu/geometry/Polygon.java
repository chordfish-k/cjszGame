package com.tedu.geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * 多边形类
 * @author LSR
 */
public class Polygon {
    protected List<Vector2> points = null;
    protected Vector2 center = new Vector2();

    /**
     * 创建多边形
     * @param center 多边形的中心点，如果用于移动物体的碰撞体，应将改变中心点以改变
     *               整个多边形的位置
     */
    public Polygon(Vector2 center) {
        this.points = new ArrayList<>();
        this.center = center;
    }

    /**
     * 向该多边形添加一个点，用相对于中心点的坐标表示
     * @param p
     */
    public void addPoint(Vector2 p) {
        this.points.add(p);
    }

    /**
     * 返回该多边形的所有点
     * @return
     */
    public List<Vector2> getPoints() {
        return this.points;
    }

    /**
     * 清除多边形的所有点
     */
    public void clear() {
        this.points.clear();
    }

    public Vector2 getCenter() {
        return center;
    }

    public void setCenter(Vector2 center) {
        this.center = center;
    }
}
