package com.tedu.element.tank;

import com.tedu.controller.Direction;
import com.tedu.element.ElementObj;
import com.tedu.element.bullet.Bullet;
import com.tedu.element.component.HealthValue;
import com.tedu.element.component.RigidBody;
import com.tedu.element.component.Sprite;
import com.tedu.game.Game;
import com.tedu.geometry.Vector2;
import com.tedu.manager.ElementManager;
import com.tedu.manager.ElementType;
import com.tedu.manager.GameLoad;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EnemyTank extends TankBase{

    private Random ran = null;
    private long localTime = 0L;
    private int ranSpan = 80;
    private int ranSpanOffset = 20;
    private float turnRan = 0.4f;
    private float moveRan = 0.8f;
    private float attackSpan = 0.8f;
    private int type = 1;
    private PlayerTank pt = null;

    Sprite sp = null;
    HealthValue hv = null;
    RigidBody rb = null;

    public EnemyTank() {
        ran = new Random();
        sp = (Sprite) getComponent("Sprite");
        hv = (HealthValue) getComponent("HealthValue");
        rb = (RigidBody) addComponent("RigidBody");
        this.setSpeed(25);
    }

    @Override
    public ElementObj create(String data) {
        String[] split = data.split(",");

        transform.setX(Float.parseFloat(split[0]))
                .setY(Float.parseFloat(split[1]));
        type = Integer.parseInt(split[2]);

        sp.setSprite(GameLoad.imgMap.get("bot_" + split[2]))
                .setCenter(new Vector2(0.5f, 0.5f));

        hv.setMaxHealth(2, true);
        return this;
    }

    @Override
    public void onCollision(ElementObj other) {

    }

    @Override
    public void onUpdate(long time) {
        super.onUpdate(time);

        // 获取玩家
        if (pt == null) {
            List<ElementObj> pl = ElementManager.getManager().getElementsByType(ElementType.PLAYER);
            if (pl.size() > 0) {
                pt = (PlayerTank) pl.get(0);
            }
        }
    }

    @Override
    protected void spriteChange(long time) {
        // 根据方向改变贴图
        sp.setSprite(GameLoad.imgMap.get("bot" + type + "_" + facing.name().toLowerCase()));
    }

    @Override
    protected void move(long time) {
        super.move(time);

        if (time - localTime < ranSpan + ran.nextInt(ranSpanOffset))
            return;

        localTime = time;



        // 是否转向
        if (ran.nextFloat() < turnRan) {

            Direction dir = this.getFacing();

            if (type == 1) {
                if (dir == Direction.LEFT) dir = Direction.RIGHT;
                else if (dir == Direction.RIGHT) dir = Direction.LEFT;
            }
            else if (type == 2) {
                // 随机方向
//            List<Direction> dirs = new ArrayList<>();
//            for (Direction d : Direction.values()) {
//                if (d != this.getFacing()) {
//                    dirs.add(d);
//                }
//            }
//            Direction newDir = dirs.get(ran.nextInt(3));
//            this.setFacing(newDir);

                // 判断该bot与玩家的相对位置
                Vector2 vec;


                if (pt != null) {
                    vec = pt.transform.getPos().sub(this.transform.getPos());
                    if (Math.abs(vec.x) > Math.abs(vec.y)) { // 趋向横向移动
                        if (vec.x > 0) {
                            dir = Direction.RIGHT;
                        } else {
                            dir = Direction.LEFT;
                        }
                    } else { // 趋向纵向移动
                        if (vec.y > 0) {
                            dir = Direction.DOWN;
                        } else {
                            dir = Direction.UP;
                        }
                    }
                }
            }

            this.setFacing(dir);
        }

        // 是否移动
        if (ran.nextFloat() < moveRan) {
            switch (this.facing) {
                case UP:
                    setFacing(Direction.UP);
                    rb.setVelocity(new Vector2(0, -getSpeed()));
                    break;
                case DOWN:
                    setFacing(Direction.DOWN);
                    rb.setVelocity(new Vector2(0, getSpeed()));
                    break;
                case LEFT:
                    setFacing(Direction.LEFT);
                    rb.setVelocity(new Vector2(-getSpeed(), 0));
                    break;
                case RIGHT:
                    setFacing(Direction.RIGHT);
                    rb.setVelocity(new Vector2(getSpeed(), 0));
                    break;
            }
        }

        // 是否攻击
        if (ran.nextFloat() < attackSpan) {
            float x = transform.getX();
            float y = transform.getY();

            String dataStr = "x:" + x + ",y:" + y + ",f:" + getFacing().name() + ",by:ENEMY";

            ElementObj ele = new Bullet().create(dataStr);

            ElementManager.getManager().addElement(ele, ElementType.BULLET);
        }
    }
}
