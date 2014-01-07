package com.badlogic.gdx.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Allows an entity to move in a direction at a constant rate
 * @author nhydock
 *
 */
public class Movement extends Component {
	public static ComponentType CType = ComponentType.getTypeFor(Movement.class);
	
	/**
	 * Movement vectors
	 */
	public Vector2 velocity = new Vector2(), 
				   acceleration = new Vector2();
	public float maxSpd;
	public float friction;
	
	/**
	 * Creates velocity that does not actually move
	 */
	public Movement() {
		setTo(0, 0);
	}
	
	/**
	 * Creates a movement component with a set velocity
	 * @param x - horizontal movement rate
	 * @param y - vertical movement rate
	 */
	public Movement(float x, float y)
	{
		setVelocityTo(x, y);
	}
	
	/**
	 * Creates a velocity at a set rate
	 * @param angle - trajectory angle
	 * @param speed - trajectory speed
	 */
	public Movement(int angle, float speed)
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
		maxSpd = speed;
		velocity.x = (float)MathUtils.cosDeg(angle)*speed;
		velocity.y = (float)MathUtils.sinDeg(angle)*speed;	
	}
	
	/**
	 * Calculates horizontal and vertical acceleration rates from trajectory and sets them
	 * @param angle - trajectory angle
	 * @param speed - max speed
	 * @param a - acceleration
	 */
	public void setTo(int angle, float speed, float a)
	{
		maxSpd = speed;
		acceleration.x = (float)MathUtils.cosDeg(angle)*a;
		acceleration.y = (float)MathUtils.sinDeg(angle)*a;	
	}
	
	/**
	 * Forcibly set the movement's velocity rate
	 * @param x - horizontal rate
	 * @param y - vertical rate
	 */
	public void setVelocityTo(float x, float y)
	{
		velocity.x = x;
		velocity.y = y;
	}
	
	/**
	 * Set the acceleration rates
	 * @param x - horizontal rate
	 * @param y - vertical rate
	 */
	public void setAcceleration(float x, float y)
	{
		acceleration.x = x;
		acceleration.y = y;
	}
	
	/**
	 * Set a speed clamp
	 * @param m
	 */
	public void setMaxSpeed(float m)
	{
		maxSpd = m;
	}
	
	public void setFriction(float f)
	{
		friction = f;
	}
	
	public void update(float delta)
	{
		velocity.add(acceleration.x * delta, acceleration.y * delta);
		if (maxSpd > 0f)
			velocity.limit(maxSpd);
		
		float spd = velocity.len();
		if (friction > 0f && spd > 0f) {
			//clamp when going really slow
			if (spd < .5f) 
				velocity.set(0, 0);
			else
				velocity.scl(1 - friction * delta);
		}
	}
}
