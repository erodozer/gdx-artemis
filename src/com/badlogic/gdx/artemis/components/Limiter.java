package com.badlogic.gdx.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentType;

/**
 * Allows a number limit to be set on something, and to keep it's current value
 * @author nhydock
 *
 */
public class Limiter extends Component {
	public static ComponentType CType = ComponentType.getTypeFor(Limiter.class);
	
	public float current;
	public final float max;
	public final float min;
	
	public Limiter(float min, float max)
	{
		this.min = min;
		this.max = max;
		this.current = Math.max(0, min);
	}
	
}
