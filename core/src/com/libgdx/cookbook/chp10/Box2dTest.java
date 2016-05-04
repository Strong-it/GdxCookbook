package com.libgdx.cookbook.chp10;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.libgdx.cookbook.help.BaseScreen;

/**
 * 将Box2d中的刚体与精灵绑定
 *
 */

public class Box2dTest extends BaseScreen {

	SpriteBatch batch;
	Sprite sprite;
	Texture img;
	World world;
	Body body;
	
	Box2DDebugRenderer box2dRender;
	
	@Override
	public void show() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();
		
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		sprite = new Sprite(img);
		
		sprite.setX(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2);
		sprite.setY(Gdx.graphics.getHeight()  - sprite.getHeight());
		
		world = new World(new Vector2(0, -9.8f), true);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);
		
		body = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);
		
		/**
		 ** 夹具 定义了Body的各种物理属性
		 ** The shape, this must be set. The shape will be cloned, so you can create the shape on the stack.
		 ** public Shape shape;

		 ** The friction coefficient, usually in the range [0,1].
		 ** public float friction = 0.2f;

		 ** The restitution (elasticity) usually in the range [0,1].
	     ** public float restitution = 0;

		 ** The density, usually in kg/m^2.
		 ** public float density = 0;
		 **/
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 2;
		fixtureDef.restitution = 1.0f;  // 冲量 也叫弹性值，物体碰撞之后的返回值
		
		/**
		 * Body创建夹具 Fixture，Fixture后期修改FixtureDef定义的值
		 */
		body.createFixture(fixtureDef);
		shape.dispose();
		
		box2dRender = new Box2DDebugRenderer();
		
		createGround();
	}

	@Override
	public void render(float delta) {
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		sprite.setPosition(body.getPosition().x, body.getPosition().y);
		
		Gdx.gl.glClearColor(0.39f, 0.58f, 0.92f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
		batch.begin();
		batch.draw(sprite, sprite.getX(), sprite.getY());
		batch.end();
		
		box2dRender.render(world, camera.combined);
		
		goBackMainScreen();
	}
	
    private void createGround() {

        float halfGroundWidth = Gdx.graphics.getWidth();
        float halfGroundHeight = 20f; // 1 meter high

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

	@Override
	public void dispose() {
		img.dispose();
		world.dispose();
	}

}
