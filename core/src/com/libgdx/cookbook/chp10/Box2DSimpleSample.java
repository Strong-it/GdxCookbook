package com.libgdx.cookbook.chp10;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.libgdx.cookbook.help.BaseScreen;
/**
 * shape 形状是一个2D的几何对象。例如圆 多边形
 * 
 * rigid body 刚体 一块十分坚硬的物质，它上面的任何两点之间的距离都是完全不变的。
 * 
 * fixture  夹具  将形状绑定到物体上，并添加密度(density)、摩擦(friction)、和恢复(restitution)等材料特性。
 * 夹具还将形状放入碰撞系统(碰撞系统(Broad Phase))中，以使之能与其他形状想碰撞
 * 
 * constraint 约束就是消除物体自由度的物理连接。一个2D物体有3个自由度（2个平移坐标和一个旋转坐标）。如果我们把一个物体钉在墙上(像摆锤那样)，
 * 那我们就把它约束到墙上。这样此物体就只能绕着这个钉子旋转，因此这个约束消除了它的2个自由度
 * 
 * joint 关节 是一种用于把2个或者更多物体固定到一起的约束。Box2D支持若干种关节类型：旋转 、棱柱、距离等等
 *
 */
public class Box2DSimpleSample extends BaseScreen {
    private static final String TAG = "Box2DSimpleSample";

    // Box2D use the metric system
    private static final float SCENE_WIDTH = 12.8f; // 13 metres wide
    private static final float SCENE_HEIGHT = 7.2f; // 7 metres high

    private Vector3 point = new Vector3();

    // General Box2D
    Box2DDebugRenderer debugRenderer;  // 渲染world
    BodyDef defaultDynamicBodyDef;
    World world;   // Box2d里面的物体由world create

    // Squares
    FixtureDef boxFixtureDef;
    PolygonShape square;  // 多边形

    // Circles
    CircleShape circle;  // 圆形
    FixtureDef circleFixtureDef;

    // To switch between boxes and balls
    boolean boxMode = true;

    @Override
    public void show() {
        viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT);
        // Center camera to get (0,0) as the origin of the Box2D world
        viewport.getCamera().position.set(viewport.getCamera().position.x
                + SCENE_WIDTH * 0.5f, viewport.getCamera().position.y
                + SCENE_HEIGHT * 0.5f, 0);
        viewport.getCamera().update();

        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(this);

        // Create Physics World 设置重力, allow to sleep and consequently ar not simulated
        world = new World(new Vector2(0, -9.8f), true);

        // Tweak debug information
        debugRenderer = new Box2DDebugRenderer(true, /* draw bodies */
        false, /* don't draw joints */
        true, /* draw aabbs */
        true, /* draw inactive bodies */
        false, /* don't draw velocities */
        true /* draw contacts */);

        // Creates a ground to avoid objects falling forever
        createGround();

        // Default Body Definition  动态物体,有速度，可以接受力和冲量的作用；
        defaultDynamicBodyDef = new BodyDef();
        defaultDynamicBodyDef.type = BodyType.DynamicBody;

        // Shape for square
        square = new PolygonShape();
        // 1 meter-sided square half-width = 0.5f half-height= 0.5f
        square.setAsBox(0.5f, 0.5f);

        // Shape for circles
        circle = new CircleShape();
        // 0.5 metres for radius  设置半径=0.5
        circle.setRadius(0.5f);

        // Fixture definition for our shapes
        boxFixtureDef = new FixtureDef();
        boxFixtureDef.shape = square;  // 将形状绑定到物体上
        boxFixtureDef.density = 0.8f;  // 密度
        boxFixtureDef.friction = 0.8f;  // 摩擦 值的范围为 0 到 1，为 0 表示完全没有摩擦，为 1 并不意味着不存在滑动
        boxFixtureDef.restitution = 0.15f;  // 弹性 值的范围为 0 到 1, 为 0 表示完全没有弹性，碰撞时所有的能量将全部被反弹回去

        // Fixture definition for our shapes
        circleFixtureDef = new FixtureDef();
        circleFixtureDef.shape = circle;
        circleFixtureDef.density = 0.5f;
        circleFixtureDef.friction = 0.4f;
        circleFixtureDef.restitution = 0.6f;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // If the game doesn't render at 60fps, the physics will go mental.
        // That'll be covered in Box2DFixedTimeStepSample
        world.step(1 / 60f, 6, 2);
//        world.step(delta, 6, 2);

        debugRenderer.render(world, viewport.getCamera().combined);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void hide() {
        debugRenderer.dispose();
        batch.dispose();
        square.dispose();
        circle.dispose();
        world.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {

            // Translate screen coordinates into world units
            viewport.getCamera().unproject(point.set(screenX, screenY, 0));

            if (boxMode)
                createSquare(point.x, point.y);
            else
                // Circle mode
                createCircle(point.x, point.y);
            boxMode = !boxMode;
            return true;
        }
        return false;
    }

    private void createSquare(float x, float y) {
        defaultDynamicBodyDef.position.set(x, y);

        Body body = world.createBody(defaultDynamicBodyDef);

        body.createFixture(boxFixtureDef);
    }

    private void createCircle(float x, float y) {
        defaultDynamicBodyDef.position.set(x, y);

        Body body = world.createBody(defaultDynamicBodyDef);

        body.createFixture(circleFixtureDef);
    }

    private void createGround() {

        float halfGroundWidth = SCENE_WIDTH;
        float halfGroundHeight = 0.5f; // 1 meter high

        // Create a static body definition, 没有速度，不接受力和冲量的作用
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyType.StaticBody;

        // Set the ground position (origin)
        groundBodyDef.position.set(halfGroundWidth * 0.5f, halfGroundHeight);

        // Create a body from the defintion and add it to the world
        Body groundBody = world.createBody(groundBodyDef);

        // Create a rectangle shape which will fit the world_width and 1 meter
        // high
        // (setAsBox takes half-width and half-height as arguments)
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(halfGroundWidth * 0.5f, halfGroundHeight);
        // Create a fixture from our rectangle shape and add it to our ground
        // body
        groundBody.createFixture(groundBox, 0.0f);
        // Free resources
        groundBox.dispose();

    }
}
