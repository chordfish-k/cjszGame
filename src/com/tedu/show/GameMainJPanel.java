package com.tedu.show;

import com.tedu.element.ElementObj;
import com.tedu.element.Player;
import com.tedu.manager.ElementManager;
import com.tedu.manager.ElementType;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 游戏的主要面板 <p>
 * 显示元素，刷新界面（多线程）
 * @author LSR
 *
 */
public class GameMainJPanel extends JPanel {
    // 联动管理器
    private ElementManager em;

    public GameMainJPanel() {
        init();
//        以下代码以后会重写
        load();
    }

    public void init() {
        em = ElementManager.getManager();
    }

    public void load() {
        // 图片导入
        ImageIcon icon = new ImageIcon("image/tank/play1/player1_up.png");
        // 创建元素
        ElementObj player = new Player(100, 100, 50, 50, icon);
        // 将对象放入到元素管理器中
        em.addElement(player, ElementType.PLAYER);
    }

    @Override //Graphics 画笔
    public void paint(Graphics g) {
        super.paint(g);

        Map<ElementType, List<ElementObj>> all = em.getGameElements();
        Set<ElementType> keySet = all.keySet();

        for(ElementType type : keySet) {
            List<ElementObj> list = all.get(type);
            for(int i=0; i<list.size(); i++) {
                list.get(i).showElement(g);
            }
        }
    }
}
