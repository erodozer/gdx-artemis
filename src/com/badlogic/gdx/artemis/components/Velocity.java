package com.badlogic.gdx.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentType;
import com.badlogic.gdx.math.MathUtils;

/**
 * Allows an entity to move in a direction at a constant rate
 * @author nhydock
 *
 */
public class Velocity extends Component {
	public static ComponentType CType = ComponentType.getTypeFor(Velocity.class);
	
	/**
	 * Horizontal movement rate
	 */
	public float x;
	/**
	 * Vertical movement rate
	 */
	public float y;
	
	/**
	 * Creates velocity that does not actually move
	 */
	public Velocity() {
		setTo(0, 0);
	}
	
	/**
	 * Creates a velocity at a set rate
	 * @param x - horizontal movement rate
	 * @param y - vertical movement rate
	 */
	public Velocity(float x, float y)
	{
		setTo(x, y);
	}
	
	/**
	 * Creates a velocity at a set rate
	 * @param angle - trajectory angle
	 * @param speed - trajectory speed
	 */
	public Velocity(int angle, float speed)
	{
		setTo(angle, speed);
	}


	/**
	 * Calculates horizontal and vertical rates from trajectory and sets them
	 * @param angle
	 * @param speed
	 */
	public void setTo(int angle, float speed)
	{
		this.x = (float)MathUtils.cosDeg(angle)*speed;
		this.y = (float)MathUtils.sinDeg(angle)*speed;	
	}
	
	/**
	 * Sets rates
	 * @param x - horizontal rate
	 * @param y - vertical rate
	 */
	public void setTo(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
}
