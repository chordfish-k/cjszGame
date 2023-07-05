package com.tedu.controller;

/**
 * 实体状态
 */
public enum EntityState {
    LIVING, // 存活状态
    HIDE, // 隐藏状态
    FREEZE, // 冻结状态，无法运动
    DIED // 死亡状态，下一帧立即移除
}
