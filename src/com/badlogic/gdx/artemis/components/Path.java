package com.badlogic.gdx.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentType;
import com.badlogic.gdx.math.Vector2;

public class Path extends Component {
	public static ComponentType CType = ComponentType.getTypeFor(Path.class);
	
	public com.badlogic.gdx.math.Path<Vector2> path;
	public float timer;
	public final float duration;
	
	public Loop loop;
	public boolean reverse;
	
	private boolean done;
	
	/**
	 * @param path - path for the entity to follow
	 * @param duration - time it should take to traverse the path
	 */
	public Path(com.badlogic.gdx.math.Path<Vector2> path, float duration) {
		this.path = path;
		this.duration = duration;
	}
	
	public void update(float t)
	{
		if (done)
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
					reverse = !reverse;
					timer = 0;
					break;
				default:
					done = true;
					break;
			}
		}
	}
	
	public void getValue(Vector2 out)
	{
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
		PingPong;
	}
}
