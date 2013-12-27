package com.badlogic.gdx.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentType;
import com.badlogic.gdx.math.Vector2;

public class Path extends Component {
	public static ComponentType CType = ComponentType.getTypeFor(Path.class);
	
	private com.badlogic.gdx.math.Path<Vector2> path;
	private float timer;
	private final float duration;
	
	private Loop loop;
	private boolean reverse;
	
	private boolean done;
	private boolean waiting;
	
	/**
	 * @param path - path for the entity to follow
	 * @param duration - time it should take to traverse the path
	 */
	public Path(com.badlogic.gdx.math.Path<Vector2> path, float duration) {
		this.path = path;
		this.duration = duration;
	}
	
	/**
	 * @param path - path for the entity to follow
	 * @param duration - time it should take to traverse the path
	 * @param delay - how long it should take before the entity will start traversing the path
	 */
	public Path(com.badlogic.gdx.math.Path<Vector2> path, float duration, float delay) {
		this.path = path;
		this.duration = duration;
		this.timer = -delay;
		this.loop = Loop.PingPong;
	}
	
	
	
	public void update(float t)
	{
		if (done || waiting)
			return;
		
		timer += t;
		if (timer >= duration)
		{
			switch(loop)
			{
				case Repeat:
					timer = 0f;
					break;
				case PingPong:
					if (!reverse){
						reverse = true;
						timer = 0;
					}
					else
						done = true;
					break;
				case PingPongLoop:
					reverse = !reverse;
					timer = 0;
					break;	
				default:
					done = true;
					break;
			}
		}
	}
	
	/**
	 * Get's the current location along the path that the timer indicates that the entity is at
	 * @param out
	 */
	public void getValue(Vector2 out)
	{
		if (timer < 0)
			path.valueAt(out, 0);
		
		if (!reverse)
		{
			path.valueAt(out, timer / duration);
		}
		else
		{
			path.valueAt(out, (duration - timer) / duration);
		}
	}
	
	public static enum Loop {
		None,
		Repeat,
		PingPong,
		PingPongLoop;
	}

	public boolean isPaused() {
		return waiting;
	}
	
	/**
	 * Pause the entity's travel along the path
	 */
	public void pause()
	{
		waiting = true;
	}
	
	/**
	 * Allow the entity to continue traveling along the path if it has been told to wait
	 */
	public void resume()
	{
		waiting = false;
	}

	public boolean isDone() {
		return done;
	}
}
