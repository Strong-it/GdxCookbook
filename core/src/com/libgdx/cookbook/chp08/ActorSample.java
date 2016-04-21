package com.libgdx.cookbook.chp08;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.libgdx.cookbook.help.BaseScreen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.math.Interpolation.*;

public class ActorSample extends BaseScreen {
    private static final String TAG = ActorSample.class.getSimpleName();
    
    private MyActor myactor;
    private Stage stage;
    
    @Override
    public void show() {
        viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT);
        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(this);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
//        Gdx.input.setInputProcessor(stage);

        myactor = new MyActor();

        stage.addActor(myactor);
        
        Gdx.app.log(TAG, "Press these keys.\n"
                + "\t1 - RotatedBy Action\n"
                + "\t2 - MoveTo Action\n"
                + "\t3 - FadeOut Action\n"
                + "\t4 - FadeIn Action\n"
                + "\t5 - Color Action\n"
                + "\t6 - Scale Action\n"
                + "\t7 - Size Action\n"
                + "\t8 - Hide Action\n"
                + "\t9 - Visble Action\n"
                + "\t0 - TimeScale Action\n"
                + "\tQ - Sequence Action\n"
                + "\tW - Repeat Action\n"
                + "\tE - Forever Action\n"
                + "\tR - Parallel Action\n"
                + "\tT - After Action\n"
                + "\tY - Delay Action\n"
                + "\tU - RemoveActor Action\n");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
        
        if (Gdx.input.isKeyJustPressed(Keys.B)) {
            goMainScreen();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        batch.dispose();
        stage.dispose();
    }

    
    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
        
        case Keys.NUM_1:
            Gdx.app.log(TAG, "RotateBy Action");
            myactor.clearActions();
            // Swing interpolation
            myactor.addAction(rotateBy(90f, 1f, swing));
            break;
            
            
        case Keys.NUM_2:
            Gdx.app.log(TAG, "MoveTo Action");
            myactor.clearActions();
            myactor.addAction(moveTo(myactor.getX() + 0.3f, myactor.getY() + 0.3f, 1f));
            break;
            
        case Keys.NUM_3:
            Gdx.app.log(TAG, "FadeOut Action");
            myactor.clearActions();
            myactor.addAction(fadeOut(2f));
            break;
            
        case Keys.NUM_4:
            Gdx.app.log(TAG, "FadeIn Action");
            myactor.clearActions();
            myactor.addAction(fadeIn(2f));
            break;
            
        case Keys.NUM_5:
            Gdx.app.log(TAG, "Color Action");
            myactor.clearActions();
            myactor.addAction(color(Color.RED, 2f));
            break;
            
        case Keys.NUM_6:
            Gdx.app.log(TAG, "Scale Action");
            myactor.clearActions();
            myactor.addAction(scaleTo(2f, 2f, 2f));
            break;
            
        case Keys.NUM_7:
            Gdx.app.log(TAG, "Size Action");
            myactor.clearActions();
            myactor.addAction(sizeTo(0.3f, 0.3f, 2f));
            break;
            
        case Keys.NUM_8:
            Gdx.app.log(TAG, "Hide Action");
            myactor.clearActions();
            myactor.addAction(Actions.hide());
            break;
            
        case Keys.NUM_9:
            Gdx.app.log(TAG, "Visible Action");
            myactor.clearActions();
            myactor.addAction(visible(true));
            break;
        
        case Keys.NUM_0:
            Gdx.app.log(TAG, "TimeScale Action");
            myactor.clearActions();
            myactor.addAction(timeScale(1.5f, rotateBy(90f, 2f)));
            break;
            
        case Keys.Q:
            Gdx.app.log(TAG, "Secuence Action");
            myactor.clearActions();
            myactor.addAction(sequence(moveTo(myactor.getX() +0.3f , myactor.getY() + 0.3f, 2f), rotateBy(90f, 2f)));
            break;
            
        case Keys.W:
            Gdx.app.log(TAG, "Repeat Action");
            myactor.clearActions();
            myactor.addAction(repeat(3, rotateBy(90f, 2f)));
            break;
            
        case Keys.E:
            Gdx.app.log(TAG, "Forever Action");
            myactor.clearActions();
            myactor.addAction(forever(rotateBy(90f, 2f)));
            break;
            
        case Keys.R:
            Gdx.app.log(TAG, "Parallel Action3");
            myactor.clearActions();
            myactor.addAction(parallel(moveTo(myactor.getX() -0.3f, myactor.getY(), 1.5f), fadeIn(1.75f)));
            break;
            
        case Keys.T:
            Gdx.app.log(TAG, "After Action");
            myactor.clearActions();
            // Blocks a sequence
            myactor.addAction(sequence(fadeOut(2f), fadeIn(2f), rotateBy(-90f,2f), after(fadeIn(10f)), fadeOut(2f)));
            break;
            
        case Keys.Y:
            Gdx.app.log(TAG, "Delay Action");
            myactor.clearActions();
            myactor.addAction(delay(5f, rotateBy(90f, 2f)));
            break;

        case Keys.U:
            Gdx.app.log(TAG, "RemoveActor Action");
            myactor.clearActions();
            myactor.addAction(removeActor());
            break;

        default:
            Gdx.app.log(TAG, "Unregistered key");
            break;
        }

        return true;
    }


    public class MyActor extends Actor implements Disposable {
        TextureRegion region =  new TextureRegion( new Texture(Gdx.files.internal("data/scene2d/myactor.png")) );
        Vector2 touchPostion;
        
        public MyActor() {
            setPosition(SCENE_WIDTH * 0.5f, SCENE_HEIGHT * 0.5f);
            setWidth(1.61f);
            setHeight(0.58f);
            
            touchPostion = new Vector2(-1.0f, -1.0f);
            
//            前2个参数要是修改了就重新设置了actor的位置
//            setBounds(getX(), getY(), getWidth(), getHeight());
            
            addListener(new EventListener() {
                
                @Override
                public boolean handle(Event event) {
                    Gdx.app.log(TAG, "handle");
                    return false;
                }
            });
            
            /***
             * 如果设置了InputMultiplexer，那么像touchDown这样的函数只能有一个触发响应
            addListener(new InputListener() {

                @Override
                public boolean touchDown(InputEvent event, float x, float y,
                        int pointer, int button) {
                    Gdx.app.log(TAG, "touchDown");
                    return super.touchDown(event, x, y, pointer, button);
                }
                
            });*/
        }
        
        @Override
        public void draw(Batch batch, float parentAlpha) {
            Color color = getColor();
            batch.setColor(color.r, color.g, color.b, color.a);
            batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), 
                        getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }

        /*
        @Override
        public void act(float delta) {
            // 得加上这句话，否则Actions不执行
            super.act(delta);
            if (Gdx.input.justTouched()) {
                touchPostion.set(Gdx.input.getX(), Gdx.input.getY());
                Gdx.app.log(TAG, "x1=" + touchPostion.x + "  y=" + touchPostion.y);
                // 将screen坐标转换为stage坐标，其实也是world坐标
                stage.screenToStageCoordinates(touchPostion);
                Gdx.app.log(TAG, "x1=" + touchPostion.x + "  y=" + touchPostion.y);
            }
            Actor actor = stage.hit(touchPostion.x, touchPostion.y, true);
            if (actor instanceof MyActor) {
                Gdx.app.log(TAG, "click me");
                touchPostion.set(-1.0f, -1.0f);  // 触发完事件要重新设值
            }
        }*/

        @Override
        public void dispose() {
            region.getTexture().dispose();
        }
        
    }
}
