package com.libgdx.cookbook.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class Caveman implements Telegraph {

    private static final String TAG = "Caveman";

    private StateMachine<Caveman, CavemanState> fsm;
    private float hungry; // [0-100]
    private float energy; // [0-100]
    private float food; // [20-100] - pending to eat
    private boolean threatened;

    // 定义枚举类型
    public enum CavemanState implements State<Caveman> {
        GLOBAL_STATE() {  // 这个Global State其实可以随便命名，主要是后面setGlobalState函数起作用，而且update每次都要调用
            @Override
            public void enter(Caveman caveman) {
               caveman.say("enter GLOBAL_STATE");
            }

            @Override
            public void update(Caveman caveman) {
                // 1 in 1000
                if (MathUtils.randomBoolean(0.001f) && !caveman.getFSM().isInState(PEEING)) {
                    caveman.say("OH! Gotta pee");
                    caveman.getFSM().changeState(PEEING);
                }
            }

            @Override
            public void exit(Caveman caveman) {
                caveman.say("exit GLOBAL_STATE");
            }

            @Override
            public boolean onMessage(Caveman caveman, Telegram telegram) {
                return false;
            }
        },
        
        PEEING() {
            @Override
            public void enter(Caveman caveman) {
                if(caveman.getFSM().getPreviousState().equals(RUN_TO_HOME))
                    caveman.say("This hiding place will lose him until I finish");
                else if(caveman.getFSM().getPreviousState().equals(SLEEP))
                    caveman.say("****! not now please...");
                else 
                    caveman.say("I really need this");
            }

            @Override
            public void update(Caveman caveman) {
                caveman.decreaseEnergy(.005f);
                caveman.increaseHungry(.01f);
                
                caveman.getFSM().changeState(caveman.getFSM().getPreviousState());
            }

            @Override
            public void exit(Caveman caveman) {
                caveman.say("exit PEEING");
            }

            @Override
            public boolean onMessage(Caveman entity, Telegram telegram) {
                return false;
            }
        },
        
        IDLE() {
            private double arrivalTime;
            
            @Override
            public void enter(Caveman caveman) {
                caveman.say("enter IDLE");
                
                arrivalTime = TimeUtils.millis();
            }

            @Override
            public void update(Caveman caveman) {
                
                caveman.increaseHungry(.05f);
                caveman.increaseEnergy(.001f);
                
                // Hungry but strong enough to go hunting
                if(caveman.hungry > 20 && caveman.energy > 20) {
                    caveman.say("I think that I will go hunting");
                    caveman.getFSM().changeState(HUNTING);
                }
                
                // Tired, go to sleep
                else if(caveman.energy < 40) {
                    caveman.say("I'm exhausted");
                    caveman.getFSM().changeState(SLEEP);
                }
                
                // Too long doing nothing? go sleep
                else if(TimeUtils.millis()-arrivalTime > 4000) {
                    caveman.say("Time to sleep");
                    caveman.getFSM().changeState(SLEEP);
                }
            }

            @Override
            public void exit(Caveman caveman) {
                caveman.say("exit IDLE");
                
            }

            @Override
            public boolean onMessage(Caveman entity, Telegram telegram) {
                return false;
            }
        },
        
        SLEEP() {
            @Override
            public void enter(Caveman caveman) {
                caveman.say("enter SLEEP Good night!");
            }

            @Override
            public void update(Caveman caveman) {
                caveman.increaseEnergy(.08f);
                caveman.increaseHungry(.01f);
                
                if(caveman.energy==100) {
                    caveman.say("(yaaaaawn) I'm a new man");
                    caveman.getFSM().changeState(IDLE);
                }
            }

            @Override
            public void exit(Caveman caveman) {
                caveman.say("exit SLEEP");
            }
            
            @Override
            public boolean onMessage(Caveman entity, Telegram telegram) {
                return false;
            }
        },
        
        HUNTING() {
            @Override
            public void enter(Caveman caveman) {
                caveman.say("enter HUNTING");
                if(caveman.getFSM().getPreviousState().equals(PEEING))
                    caveman.say("Where are you my little deer?");
                else if(caveman.hungry > 50)
                    caveman.say("I should take my best spear today");
                else
                    caveman.say("I'm a little fat to go hunting now but I feel like eating a good deer");
            }

            @Override
            public void update(Caveman caveman) {
                caveman.increaseHungry(.01f);
                caveman.decreaseEnergy(.05f);
                
                // 1 in 250
                if (MathUtils.randomBoolean(0.004f)) {
                    caveman.say("Hey! I got a rabbit, I better go home to cook it");
                    caveman.food = MathUtils.random(20, 100);
                    caveman.getFSM().changeState(RUN_TO_HOME);
                }
            }

            @Override
            public void exit(Caveman caveman) {
                caveman.say("exit HUNTING");
            }

            @Override
            public boolean onMessage(Caveman entity, Telegram telegram) {
                if (telegram.message == MessageType.GRRRRRRRR) {
                    entity.threatened = true;
                    entity.getFSM().changeState(RUN_TO_HOME);

                    return true;
                }
                return false;
            }
        },
        
        RUN_TO_HOME() {

            private double beginningTime;
            
            @Override
            public void enter(Caveman caveman) {
                caveman.say("enter RUN_TO_HOME");
                if(caveman.threatened)
                    caveman.say("OMG! This dinosaur is gonna eat me!");
                else
                    caveman.say("It's gonna be a long way to home...");
                
                beginningTime = TimeUtils.millis();
                
            }

            @Override
            public void update(Caveman caveman) {
                caveman.increaseHungry(.01f);
                caveman.decreaseEnergy(.01f);
                
                if(TimeUtils.millis() - beginningTime > 4000)
                    if(caveman.threatened) {
                        caveman.threatened = false;
                        caveman.getFSM().changeState(IDLE);
                    }
                    else
                        caveman.getFSM().changeState(EAT);
            }

            @Override
            public void exit(Caveman caveman) {
                caveman.say("exit  RUN_TO_HOME");
            }

            @Override
            public boolean onMessage(Caveman entity, Telegram telegram) {
                return false;
            }
            
        },
        
        EAT() {
            @Override
            public void enter(Caveman caveman) {
                caveman.say("enter  EAT");
                if(caveman.food > 50)
                    caveman.say("I will fill up with this meal");
                else
                    caveman.say("It's gonna be a good snack");
                
            }

            @Override
            public void update(Caveman caveman) {
                caveman.decreaseEnergy(.005f);
                
                caveman.say("Yummy yummy!!");
                caveman.decreaseHungry(caveman.food);
                caveman.food=0;
                caveman.getFSM().changeState(IDLE);
            }

            @Override
            public void exit(Caveman caveman) {
                caveman.say("exit  EAT");
            }

            @Override
            public boolean onMessage(Caveman entity, Telegram telegram) {
                return false;
            }
        };
    }

    public Caveman() {
        fsm = new DefaultStateMachine<Caveman, CavemanState> (this, CavemanState.IDLE);
        
        hungry = MathUtils.random(0, 100);
        energy = MathUtils.random(0, 100);
        threatened = false;
        
        fsm.setGlobalState(CavemanState.GLOBAL_STATE);
    }
    
    public StateMachine<Caveman, CavemanState> getFSM() {
        return fsm;
    }
    
    public void update(float delta) {
        fsm.update();
    }
    
    public void increaseEnergy(float value) {
        energy = MathUtils.clamp(energy+value, 0, 100);
    }
    
    public void decreaseEnergy(float value) {
        energy = MathUtils.clamp(energy-value, 0, 100);
    }
    
    public void increaseHungry(float value) {
        hungry = MathUtils.clamp(hungry+value, 0, 100);
    }
    
    public void decreaseHungry(float value) {
        hungry = MathUtils.clamp(hungry-value, 0, 100);
    }
    
    private void say(String thought) {
        Gdx.app.log(TAG, thought);
    }
    
    @Override
    public boolean handleMessage(Telegram msg) {
        return fsm.handleMessage(msg);
    }

}
