package com.badlogic.gdx.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentType;

/**
 * Defines how many degrees the component should rotate per second
 * @author nhydock
 */
public class Rotation extends Component {
	public static ComponentType CType = ComponentType.getTypeFor(Rotation.class);
	
	public final float rate;
	
	public Rotation(float rate)
	{
		this.rate = rate;
	}
}
