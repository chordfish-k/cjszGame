package com.tedu.controller;

import com.tedu.element.ElementObj;
import com.tedu.element.ElementState;

import com.tedu.element.component.RigidBody;
import com.tedu.manager.ElementManager;
import com.tedu.manager.ElementType;
import com.tedu.manager.GameLoad;

import java.util.List;
import java.util.Map;

/**
 * 游戏主线程 <p>
 * 用于控制游戏加载，关卡加载，运行时自动化，游戏判定，地图切换，资源读取和释放等
 *
 * @author LSR
 */
public class GameThread extends Thread {

    private final ElementManager em;
    private int gameRunFrameSleep = 16; // 1000 / 16 =  60Hz
    private long gameTime = 0L; // 帧计时器

    private PhysicsThread physicTh = null;

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
     * 1.资源文件
     * 2.元素类
     * 3.地图
     * 4.其他
     */
    private void gameLoad() {
        GameLoad.loadImage();
        GameLoad.loadElement();
        GameLoad.LoadMap(1);

        GameLoad.loadPlayer();
        GameLoad.loadEnemies();
        GameLoad.loadCollision();

        //callOnCreate();

        physicTh = new PhysicsThread();
        physicTh.start();

//        // 调用所有元素的onLoad
//        Map<ElementType, List<ElementObj>> all = em.getGameElements();
//        for (ElementType type : ElementType.values()) { // values()按枚举定义顺序返回枚举数组
//            List<ElementObj> list = all.get(type);
//            for (int i = list.size() - 1; i >= 0; i--) {
//                ElementObj obj = list.get(i);
//                obj.onLoad();
//            }
//        }

    }

    /**
     * 游戏进行时
     */
    private void gameRun() {
        while (true) {
            if (em != null) {
                Map<ElementType, List<ElementObj>> all = em.getGameElements();

                //PhyUpdateElements(all);
                updateElements(all);

                try {
                    sleep(gameRunFrameSleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            gameTime++;
        }
    }


    /**
     * 游戏场景结束
     */
    private void gameOver() {

    }


    /**
     * 调用所有元素和组件的onCreate
     */
    public void callOnCreate() {
        Map<ElementType, List<ElementObj>> all = em.getGameElements();
        for (ElementType type : ElementType.values()) { // values()按枚举定义顺序返回枚举数组
            List<ElementObj> list = all.get(type);
            for (int i = list.size() - 1; i >= 0; i--) {
                ElementObj obj = list.get(i);
                obj.onCreate();
            }
        }
    }

    public int getGameRunFrameSleep() {
        return gameRunFrameSleep;
    }

    public GameThread setGameRunFrameSleep(int duration) {
        this.gameRunFrameSleep = duration;
        return this;
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
                if (obj.getElementState() == ElementState.DIED && !em.isLocked()) {
                    obj.onDestroy();
                    list.remove(i);
                    continue;
                }
                obj.onUpdate(gameTime); // 调用ElementObj的帧更新方法
            }
        }
    }

    private void PhyUpdateElements(Map<ElementType, List<ElementObj>> all) {
        for (ElementType type : ElementType.values()) { // values()按枚举定义顺序返回枚举数组
            List<ElementObj> list = all.get(type);
            for (int i = list.size() - 1; i >= 0; i--) {
                ElementObj obj = list.get(i);
                RigidBody rb = (RigidBody) obj.getComponent("RigidBody");

                if (rb != null) // 调用所有有RigidBody组件元素的onFixUpdate()方法
                    rb.onFixUpdate();
            }
        }

        try {
            sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 元素碰撞检测
     * 暂时先写子弹和敌人的检测
     */
    public void testElementsCollision(List<ElementObj> listA, List<ElementObj> listB) {

        for (int i=0; i<listA.size(); i++) {
            for (int j=0; j<listB.size(); j++) {
                ElementObj a = listA.get(i);
                ElementObj b = listB.get(j);
                if (a.checkCollisionWith(b)) {
                    a.onCollision(b);
                    b.onCollision(a);
                    break;
                }
            }
        }
    }
}
