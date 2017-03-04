package org.socialforce.drawer.impl;

import org.socialforce.drawer.Drawable;
import org.socialforce.drawer.Drawer;
import org.socialforce.drawer.DrawerInstaller;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.BaseAgent;

import java.awt.*;
import java.util.LinkedList;

/**
 * 安装Entity的Drawer所用的安装器。
 * 这个安装器可以依据已注册的Entity Class与注册的Drawer的对应，给对应的Entity安装正确的Drawer。
 * Created by Ledenel on 2017/3/1.
 */
public class EntityDrawerInstaller implements DrawerInstaller {
    public class Support{
        Drawer drawer;
        Class<? extends Drawable> drawType;

        public Support(Drawer drawer, Class<? extends Drawable> drawable) {
            this.drawer = drawer;
            this.drawType = drawable;
        }
    }

    /**
     * 用指定设备构造Drawer的安装器。
     * 其注册顺序是有严格限制的，在指定对象安装时，安装器会寻找“第一个与该对象兼容的类或接口”并返回对应的drawer。
     * 例如:
     * 注册Agent的drawer1
     * 注册Entity的drawer2
     * 会使得所有的Agent安装Drawer1（即使他们是Entity），所有的Entity安装Drawer2。
     * @param device
     */
    public EntityDrawerInstaller(Graphics2D device) {
        registerDrawer(new PositionAgentMarkDrawer(device), BaseAgent.class);
        registerDrawer(new TargetAgentMarkDrawer(device), BaseAgent.class);
        registerDrawer(new AgentDrawer(device), BaseAgent.class);
        registerDrawer(new EntityDrawer(device), InteractiveEntity.class);

    }

    LinkedList<Support> supports = new LinkedList<>();

    /**
     * creates and set a proper drawer for a drawType.
     *
     * @param drawable the drawType.
     * @return true if the installer has a proper drawer for the drawType; otherwise false.
     */
    @Override
    public boolean addDrawerSupport(Drawable drawable) {
        for (Support sup : supports){
            Class<? extends Drawable> cls = sup.drawType;
            if(cls.isAssignableFrom(drawable.getClass())) {
                drawable.setDrawer(sup.drawer);
                return true;
            }
        }
        return false;
    }

    /**
     * register a drawer in this installer for a specific drawType type.
     * the drawer will be replaced while there is already a drawer registered for the type.
     *
     * @param registeredDrawer
     * @param drawableType
     */
    @Override
    public void registerDrawer(Drawer registeredDrawer, Class<? extends Drawable> drawableType) {
        supports.add(new Support(registeredDrawer, drawableType));
    }



    @Override
    public void unregister(Class<? extends Drawable> type) {
        supports.removeIf(support -> support.drawType.equals(type));
    }

    @Override
    public Iterable<Drawer> getRegisteredDrawers() {
        return supports.stream().map(support -> support.drawer)::iterator;
    }
}
