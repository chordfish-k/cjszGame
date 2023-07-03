package com.tedu.game;

import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;

/**
 * 游戏入口类
 */
public class GameStart {

    /**
     * 程序唯一入口
     */
    public static void main(String[] args) {
        GameJFrame gj = new GameJFrame();
        // 实例化面板，注入到GameJFrame中
        GameMainJPanel mainJPanel = new GameMainJPanel();
        gj.setPanel(mainJPanel);
        gj.start(); // 显示窗体
    }
}
