package com.tedu.controller;

import com.tedu.element.ElementObj;
import com.tedu.element.tank.EnemyTank;
import com.tedu.element.tank.PlayerTank;
import com.tedu.manager.ElementManager;
import com.tedu.manager.ElementType;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * 游戏主线程 <p>
 * 用于控制游戏加载，关卡加载，运行时自动化，游戏判定，地图切换，资源读取和释放等
 *
 * @author LSR
 */
public class GameThread extends Thread {

    private ElementManager em = null;
    private int gameRunFrameSleep = 16; // 1000 / 16 =  60Hz
    private long gameTime = 0L; // 帧计时器

    public GameThread() {
        em = ElementManager.getManager();
    }

    @Override
    public void run() { // 游戏主线程
        while (true) {
            // 游戏开始前：读进度条，加载游戏资源
            gameLoad();
            // 游戏进行时：游戏过程中
            gameRun();
            // 游戏场景结束：游戏资源回收（场景资源）
            gameOver();

            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行游戏的加载
     */
    private void gameLoad() {
        load();
    }

    /**
     * 游戏进行时
     */
    private void gameRun() {
        while (true) {
            Map<ElementType, List<ElementObj>> all = em.getGameElements();

            updateElements(all);
            testElementsCollision();

            gameTime++;
            try {
                sleep(gameRunFrameSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 游戏场景结束
     */
    private void gameOver() {

    }

    public int getGameRunFrameSleep() {
        return gameRunFrameSleep;
    }

    public GameThread setGameRunFrameSleep(int duration) {
        this.gameRunFrameSleep = duration;
        return this;
    }

    public void load() {
        // 图片导入
        ImageIcon icon = new ImageIcon("image/tank/play1/player1_up.png");
        // 创建元素
        ElementObj player = new PlayerTank(100, 100, 50, 50, icon);
        em.addElement(player, ElementType.PLAYER);

        for (int i=0; i<10; i++) {
            ElementObj enemy = new EnemyTank().create("");
            em.addElement(enemy, ElementType.ENEMY);
        }
    }

    /**
     * 逐元素执行帧更新
     * @param all 所有类型元素List的Map集合
     */
    public void updateElements(Map<ElementType, List<ElementObj>> all) {
        for (ElementType type : ElementType.values()) { // values()按枚举定义顺序返回枚举数组
            List<ElementObj> list = all.get(type);
            for (int i = list.size() - 1; i >= 0; i--) {
                ElementObj obj = list.get(i);
                if (obj.getEntityState() == EntityState.DIED) {
                    obj.onDestroy();
                    list.remove(i);
                    continue;
                }
                obj.onUpdate(gameTime); // 调用ElementObj的帧更新方法
            }
        }
    }

    /**
     * 元素碰撞检测
     * 暂时先写子弹和敌人的检测
     */
    public void testElementsCollision() {
        List<ElementObj> enemies = em.getElementsByType(ElementType.ENEMY);
        List<ElementObj> bullets = em.getElementsByType(ElementType.BULLET);

        for (int i=0; i<enemies.size(); i++) {
            for (int j=0; j<bullets.size(); j++) {
                ElementObj enemy = enemies.get(i);
                ElementObj bullet = bullets.get(j);
                if (enemy.checkCollisionWith(bullet)) {
                    enemy.onCollision(bullet);
                    bullet.onCollision(enemy);
                    break;
                }
            }
        }
    }
}
