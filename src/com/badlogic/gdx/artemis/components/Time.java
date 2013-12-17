package com.badlogic.gdx.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentType;

/**
 * A countdown timer component
 * @author nhydock
 *
 */
public class Time extends Component {
	public static ComponentType CType = ComponentType.getTypeFor(Time.class);
	
	/**
	 * Amount of time left
	 */
	public float curr;
	/**
	 * Amount of time when set
	 */
	public float start;
	
	public boolean loop;
	
	public Time(float lifeLength)
	{
		this.start = this.curr = lifeLength;
		this.loop = false;
	}
	
	public void update(float delta)
	{
		this.curr -= delta;
		if (this.loop && this.curr <= 0)
			this.curr = start;
	}
}