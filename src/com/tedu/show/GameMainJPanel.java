package com.tedu.show;

import com.tedu.element.ElementObj;
import com.tedu.element.component.ComponentBase;
import com.tedu.manager.ElementManager;
import com.tedu.manager.ElementType;
import com.tedu.manager.UIManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * 游戏的主要面板 <p>
 * 显示元素，刷新界面（多线程）
 * @author LSR
 *
 */
public class GameMainJPanel extends JPanel implements Runnable{
    // 联动管理器
    private ElementManager em;
    private UIManager um;
    // 刷新间隔
    private int refreshSleep = 15;

    public GameMainJPanel() {
        init();
    }

    public void init() {
        em = ElementManager.getManager();
        um = UIManager.getManager();

        this.setLayout(null);

        JLabel healthLab = new JLabel();this.add(healthLab);
        healthLab.setText("Health:2");
        healthLab.setBounds(5,GameJFrame.SIZE_H-50,100,GameJFrame.INFO_H);
        um.addUI("healthLabel", healthLab);
    }


    @Override //Graphics 画笔
    public void paint(Graphics g) {
        super.paint(g);

        Map<ElementType, List<ElementObj>> all = em.getGameElements();

        for(ElementType type: ElementType.values()) { // values()按枚举定义顺序返回枚举数组
            if (type == ElementType.DIE){
                continue;
            }

            List<ElementObj> list = all.get(type);
            for(int i=0; i<list.size(); i++) {
                ElementObj obj = list.get(i);
                // 先调用该元素的onDraw
                obj.onDraw(g);
                // 调用该元素下所有组件的onDraw
                for(ComponentBase cp : obj.getComponents().values()) {
                    cp.onDraw(g);
                }
            }
        }


    }

    @Override
    public void run() {
        // 渲染线程
        while (true) {
            this.repaint();
            try {
                Thread.sleep(refreshSleep); // 1000/10=100Hz
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getRefreshSleep() {
        return refreshSleep;
    }

    public GameMainJPanel setRefreshSleep(int refreshSleep) {
        this.refreshSleep = refreshSleep;
        return this;
    }
}
