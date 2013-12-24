package com.badlogic.gdx.artemis.systems;


import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.artemis.components.*;

abstract public class InputSystem extends EntityProcessingSystem implements InputProcessor {
    
    protected ComponentMapper<InputHandler> inputMap;
    
	@SuppressWarnings("unchecked")
	public InputSystem(Aspect a) {
		super(a.all(InputHandler.class));
	}
	
	@Override
	public void initialize(){
		inputMap = new ComponentMapper<InputHandler>(InputHandler.class, this.world);
	}
	
	@Override
	abstract protected void process(Entity e);

	@Override
	public boolean keyDown(int key) {
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		return false;
	}

	@Override
	public boolean keyUp(int key) {
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		return false;
	}

}
