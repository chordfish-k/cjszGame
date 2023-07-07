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
        Vector2 oldCenter =  this.center.clone();
        this.center = center;
        Vector2 d = center.sub(oldCenter);

        for(int i=0; i<getPoints().size(); i++) {
            Vector2 p = getPoints().get(i);
            p.set(getPoints().get(i).add(d));
            //System.out.println(p.y);
        }
        //System.out.println(getPoints().get(0).y);
    }

    /**
     * 检测一个点是否在该多边形内
     * @param p 二维点
     * @return 检测结果
     */
    public boolean testPoint(Vector2 p) {
        Vector2 o = this.center.clone();
        //System.out.println("p: " + p);
        //System.out.println("===");
        for (int i=0; i<points.size(); i++) {
            Vector2 a = points.get(i);
            Vector2 b = points.get((i+1) % points.size());
            Vector2 p2a = a.sub(p);
            Vector2 p2b = b.sub(p);
            // 叉乘
            float re = p2b.x * p2a.y - p2b.y * p2a.x;
            //System.out.println(a + ", " + b + ", " + p + ", " + p2a + ", " + p2b + ", "+ re);
            if (re < 0) {
                return false;
            }
        }
        //System.out.println("---");
        return true;
    }

    public boolean testPolygon(Polygon polygon) {
        for (Vector2 p : polygon.getPoints()) {
            if (testPoint(p)) {
                return true;
            }
        }
        for (Vector2 p : getPoints()) {
            if (polygon.testPoint(p)) {
                return true;
            }
        }
        return false;
    }

    public Polygon clone() {
        Polygon p = new Polygon(this.center);
        for(Vector2 point : this.points) {
            p.addPoint(point.clone());
        }
        return p;
    }

}
