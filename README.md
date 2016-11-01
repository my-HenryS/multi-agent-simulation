

# SocialForceSimulation

**本版本为Release1.0.3版本，各功能仍在不断更新调整中，可能出现重构。**

**欲开始运行仿真请打开app包，并运行其中的一个Main程序。Gui内的SimulationPanelMain是可视化的仿真界面，主要用于演示；Console内的TextOutputMain是可以将运行结果以字符串形式输出的Main程序，主要用于输出具体的数据以便实验使用。**

***项目分为5个package，分别为app、container、drawer、geom、model***

1. **app为应用层**，设计的基本接口有Scene, SceneGenerator, SceneListener, SceneLoader, SceneParameter, SceneValue, SocialForceApplication。目前已实例化的类有SquareRoomLoader, 负责加载基本矩形房间；SVSR_AgentGenerator，负责在限定区域内等间距均匀生成Agent, SVSR_Exit设计缺口，SVSR_SafetyRegion在Scene中添加安全区域。SimpleApplication类包含一系列Scene(目前设计为1个)模拟行人疏散过程。

2. **container是克隆所需的库**，主要用于在未来实现各种的加速功能。设计接口有AgentPool, EntityPool, Pool。已实例化LinkListPool类，即装有不同/相同Entity的Pool。

3. **drawer是用于实现场景可视化的层**。

4. **geom是几何层**，主要实现各种形状及数学实体的初始化。设计接口有Box, ClipperShape, DimensionEntity, DistanceShape, Expandable, Force, Point, Shape, Vector, Velocity。已实例化的基本类有Box2D（矩形）, Circle2D（实心圆）, ComplexBox2D（复杂矩形集合）, Force2D（力）, Point2D（点）, Segment2D（线段）, Vector2D（向量）, Velocity2D（速度）。

5. **model是社会力模型层**，用于计算各实体之间相互力的作用，并在场景中实现。基本接口有：Agent, Blockable, ForceRegulation, InteractiveEntity, Moveable, Path, PathFinder, SocialForceModel。实例化的类有：

   - AStarPath----A*算法专用路径（规划中）
   - AStarPathFinder----A*算法路径找寻
   - BaseAgent——Agent的基本实现
   - BodyForce——力的数学计算
   - Entity----实体
   - PsychologicalForceRegulation----心理力公式
   - SafetyRegion----安全区域
   - SimpleSocialForceModel----社会力模型
   - StraightPath----直线找寻路径
   - TypeMatchRegulation
   - Wall——墙

***模型公式实现如下（部分展示）***

```java
public static final double A = 0.3;
public static final double B = 0.18;
force.add(target.getShape().getReferencePoint());
force.sub(source.getShape().getReferencePoint());
double scale = A * Math.exp(- target.getShape().distanceTo(source.getShape()) / B);
force.scale(scale / force.length());
```

```java
A = 2000;
B = 0.1;
k1 = 1.2 * 100000;
k2 = 2.4 * 100000;
g = 0;
bodyForce = A*Math.exp(-distance/B) + k1*g*distance;
slidingForce = k2*g*distance*t.dot(tempVector);
```

本项目目的在于实现单、多场景的社会力仿真，并对其运算效率进行优化，获得相关优化参数。


目前阶段的工作在于部署可视化策略，逐步实现A*算法以及性能优化。