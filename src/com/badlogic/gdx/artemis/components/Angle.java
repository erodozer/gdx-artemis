package com.badlogic.gdx.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentType;

public class Angle extends Component {
	public static ComponentType CType = ComponentType.getTypeFor(Angle.class);
	
	public float degrees;
	
	public Angle(float degrees)
	{
		this.degrees = degrees;
	}
}
